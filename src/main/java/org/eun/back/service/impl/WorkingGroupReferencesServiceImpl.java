package org.eun.back.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eun.back.domain.ReferenceTableSettings;
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.repository.WorkingGroupReferencesRepository;
import org.eun.back.security.SecurityUtils;
import org.eun.back.service.CountriesService;
import org.eun.back.service.ReferenceTableSettingsService;
import org.eun.back.service.WorkingGroupReferencesService;
import org.eun.back.service.dto.*;
import org.eun.back.service.mapper.WorkingGroupReferencesIndicatorMapper;
import org.eun.back.service.mapper.WorkingGroupReferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link WorkingGroupReferences}.
 */
@Service
@Transactional
public class WorkingGroupReferencesServiceImpl implements WorkingGroupReferencesService {

    private final Logger log = LoggerFactory.getLogger(WorkingGroupReferencesServiceImpl.class);

    private final WorkingGroupReferencesRepository workingGroupReferencesRepository;

    private final WorkingGroupReferencesMapper workingGroupReferencesMapper;

    private final WorkingGroupReferencesIndicatorMapper workingGroupReferencesIndicatorMapper;

    private final ReferenceTableSettingsService referenceTableSettingsService;

    private final CountriesService countriesService;

    public WorkingGroupReferencesServiceImpl(
        WorkingGroupReferencesRepository workingGroupReferencesRepository,
        WorkingGroupReferencesMapper workingGroupReferencesMapper,
        WorkingGroupReferencesIndicatorMapper workingGroupReferencesIndicatorMapper,
        ReferenceTableSettingsService referenceTableSettingsService,
        CountriesService countriesService
    ) {
        this.workingGroupReferencesRepository = workingGroupReferencesRepository;
        this.workingGroupReferencesMapper = workingGroupReferencesMapper;
        this.workingGroupReferencesIndicatorMapper = workingGroupReferencesIndicatorMapper;
        this.referenceTableSettingsService = referenceTableSettingsService;
        this.countriesService = countriesService;
    }

    @Override
    public Indicator<?> getIndicator(Long countryId, Long reportId) {
        String countryCode = this.countriesService.findOne(countryId).map(CountriesDTO::getCountryCode).orElse(null);
        Indicator<WorkingGroupReferencesIndicatorDTO> indicator = new Indicator<>();
        if (countryCode != null) {
            List<WorkingGroupReferences> data = this.workingGroupReferencesRepository.findAllByIsActiveAndCountryCode(true, countryCode);
            indicator.setData(workingGroupReferencesIndicatorMapper.toDto(data));
            indicator.setValue(String.valueOf(data.size()));
        }
        indicator.setCode("working_group");
        indicator.setLabel("Working Group");
        return indicator;
    }

    @Override
    public WorkingGroupReferencesDTO save(WorkingGroupReferencesDTO workingGroupReferencesDTO) {
        log.debug("Request to save WorkingGroupReferences : {}", workingGroupReferencesDTO);
        WorkingGroupReferences workingGroupReferences = workingGroupReferencesMapper.toEntity(workingGroupReferencesDTO);
        workingGroupReferences = workingGroupReferencesRepository.save(workingGroupReferences);
        return workingGroupReferencesMapper.toDto(workingGroupReferences);
    }

    @Override
    public WorkingGroupReferencesDTO update(WorkingGroupReferencesDTO workingGroupReferencesDTO) {
        log.debug("Request to update WorkingGroupReferences : {}", workingGroupReferencesDTO);
        WorkingGroupReferences workingGroupReferences = workingGroupReferencesMapper.toEntity(workingGroupReferencesDTO);
        workingGroupReferences = workingGroupReferencesRepository.save(workingGroupReferences);
        return workingGroupReferencesMapper.toDto(workingGroupReferences);
    }

    @Override
    public Optional<WorkingGroupReferencesDTO> partialUpdate(WorkingGroupReferencesDTO workingGroupReferencesDTO) {
        log.debug("Request to partially update WorkingGroupReferences : {}", workingGroupReferencesDTO);

        return workingGroupReferencesRepository
            .findById(workingGroupReferencesDTO.getId())
            .map(existingWorkingGroupReferences -> {
                workingGroupReferencesMapper.partialUpdate(existingWorkingGroupReferences, workingGroupReferencesDTO);

                return existingWorkingGroupReferences;
            })
            .map(workingGroupReferencesRepository::save)
            .map(workingGroupReferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkingGroupReferencesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingGroupReferences");
        return workingGroupReferencesRepository.findAllByIsActive(true, pageable).map(workingGroupReferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkingGroupReferencesDTO> findAllByCountry(String countryCode) {
        log.debug("Request to get all WorkingGroupReferences by country");
        return workingGroupReferencesMapper.toDto(workingGroupReferencesRepository.findAllByIsActiveAndCountryCode(true, countryCode));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkingGroupReferencesDTO> findOne(Long id) {
        log.debug("Request to get WorkingGroupReferences : {}", id);
        return workingGroupReferencesRepository.findById(id).map(workingGroupReferencesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkingGroupReferences : {}", id);
        workingGroupReferencesRepository.deleteById(id);
    }

    @Override
    public void upload(MultipartFile file) throws IOException {
        try (InputStream fileInputStream = file.getInputStream()) {
            workingGroupReferencesRepository.updateAllIsActiveToFalse();
            Workbook workbook = WorkbookFactory.create(fileInputStream);

            List<String> keywords = Arrays.asList("working group", "working", "wg", "WG");
            List<Integer> sheetNumbers = new ArrayList<>();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName().toLowerCase();

                for (String keyword : keywords) {
                    if (sheetName.contains(keyword)) {
                        sheetNumbers.add(i);
                        break;
                    }
                }
            }

            for (int pageNumber : sheetNumbers) {
                Sheet sheet = workbook.getSheetAt(pageNumber);
                String sheetName = sheet.getSheetName();
                int i = 0;
                for (Row row : sheet) {
                    i++;
                    if (i > 2) {
                        try {
                            if (row.getCell(0) == null || row.getCell(1) == null) {
                                break;
                            }
                            WorkingGroupReferences workingGroupReferences = new WorkingGroupReferences();
                            workingGroupReferences.setCountryCode(row.getCell(0) != null ? row.getCell(0).toString() : "");
                            Cell cell = row.getCell(1);
                            String cellValue = "";

                            if (cell != null) {
                                if (cell.getCellType() == CellType.FORMULA) {
                                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                                    CellValue evaluatedCellValue = evaluator.evaluate(cell);
                                    switch (evaluatedCellValue.getCellType()) {
                                        case STRING:
                                            cellValue = evaluatedCellValue.getStringValue();
                                            break;
                                        case NUMERIC:
                                            cellValue = String.valueOf(evaluatedCellValue.getNumberValue());
                                            break;
                                        case BOOLEAN:
                                            cellValue = String.valueOf(evaluatedCellValue.getBooleanValue());
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    cellValue = cell.toString();
                                }
                            }

                            workingGroupReferences.setCountryName(cellValue);
                            workingGroupReferences.setCountryRepresentativeFirstName(
                                row.getCell(2) != null ? row.getCell(2).toString() : ""
                            );
                            workingGroupReferences.setCountryRepresentativeLastName(
                                row.getCell(3) != null ? row.getCell(3).toString() : ""
                            );
                            workingGroupReferences.setCountryRepresentativeMail(row.getCell(4) != null ? row.getCell(4).toString() : "");
                            workingGroupReferences.setCountryRepresentativePosition(
                                row.getCell(5) != null ? row.getCell(5).toString() : ""
                            );
                            workingGroupReferences.setCountryRepresentativeMinistry(
                                row.getCell(8) != null ? row.getCell(8).toString() : ""
                            );
                            workingGroupReferences.setCountryRepresentativeDepartment(
                                row.getCell(9) != null ? row.getCell(9).toString() : ""
                            );
                            workingGroupReferences.setContactEunFirstName(row.getCell(10) != null ? row.getCell(10).toString() : "");
                            workingGroupReferences.setContactEunLastName(row.getCell(11) != null ? row.getCell(11).toString() : "");
                            workingGroupReferences.setType(sheetName);
                            workingGroupReferences.setSheetNum((long) pageNumber);
                            WorkingGroupReferences workingGroupReferencesRes = workingGroupReferencesRepository.findByCountryCodeAndCountryRepresentativeMinistryAndType(
                                workingGroupReferences.getCountryCode(),
                                workingGroupReferences.getCountryRepresentativeMinistry(),
                                workingGroupReferences.getType()
                            );
                            if (workingGroupReferencesRes != null) {
                                workingGroupReferences.setId(workingGroupReferencesRes.getId());
                                workingGroupReferences.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().get());
                                workingGroupReferences.setLastModifiedDate(LocalDate.now());
                            } else {
                                workingGroupReferences.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
                                workingGroupReferences.setCreatedDate(LocalDate.now());
                            }
                            workingGroupReferences.setIsActive(true);
                            workingGroupReferencesRepository.save(workingGroupReferences);
                        } catch (Exception e) {
                            log.error("Error ", e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error ", e);
        }
    }

    @Override
    public byte[] download() throws IOException {
        List<WorkingGroupReferences> workingGroupReferences = workingGroupReferencesRepository.findAll();
        ReferenceTableSettingsDTO referenceTableSettings = referenceTableSettingsService.findOneByRefTable("working_group").get();

        try (
            ByteArrayInputStream templateStream = new ByteArrayInputStream(referenceTableSettings.getFile());
            Workbook workbook = new XSSFWorkbook(templateStream)
        ) {
            Map<String, Sheet> sheetMap = new HashMap<>();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                sheetMap.put(sheet.getSheetName(), sheet);
            }

            Map<String, List<WorkingGroupReferences>> dataBySheetName = new HashMap();

            for (WorkingGroupReferences wg : workingGroupReferences) {
                String sheetName = wg.getType();
                dataBySheetName.computeIfAbsent(sheetName, k -> new ArrayList<>()).add(wg);
            }

            for (Map.Entry<String, List<WorkingGroupReferences>> entry : dataBySheetName.entrySet()) {
                String sheetName = entry.getKey();
                List<WorkingGroupReferences> dataForSheet = entry.getValue();
                Sheet sheet = sheetMap.get(sheetName);

                int rowNum = 2;
                for (WorkingGroupReferences wg : dataForSheet) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(wg.getCountryCode());
                    row.createCell(1).setCellValue(wg.getCountryName());
                    row.createCell(2).setCellValue(wg.getCountryRepresentativeFirstName());
                    row.createCell(3).setCellValue(wg.getCountryRepresentativeLastName());
                    row.createCell(4).setCellValue(wg.getCountryRepresentativeMail());
                    row.createCell(5).setCellValue(wg.getCountryRepresentativePosition());
                    row.createCell(6).setCellValue(wg.getCountryRepresentativeStartDate());
                    row.createCell(7).setCellValue(wg.getCountryRepresentativeEndDate());
                    row.createCell(8).setCellValue(wg.getCountryRepresentativeMinistry());
                    row.createCell(9).setCellValue(wg.getCountryRepresentativeDepartment());
                    row.createCell(10).setCellValue(wg.getContactEunFirstName());
                    row.createCell(11).setCellValue(wg.getContactEunLastName());
                }
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
