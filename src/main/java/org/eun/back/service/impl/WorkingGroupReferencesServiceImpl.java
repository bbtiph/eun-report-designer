package org.eun.back.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import org.apache.poi.ss.usermodel.*;
import org.eun.back.domain.MoeContacts;
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.repository.WorkingGroupReferencesRepository;
import org.eun.back.service.WorkingGroupReferencesService;
import org.eun.back.service.dto.WorkingGroupReferencesDTO;
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

    public WorkingGroupReferencesServiceImpl(
        WorkingGroupReferencesRepository workingGroupReferencesRepository,
        WorkingGroupReferencesMapper workingGroupReferencesMapper
    ) {
        this.workingGroupReferencesRepository = workingGroupReferencesRepository;
        this.workingGroupReferencesMapper = workingGroupReferencesMapper;
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
            for (int pageNumber : new int[] { 3, 4, 5, 9 }) {
                Sheet sheet = workbook.getSheetAt(pageNumber);

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
                            MoeContacts moeContactsRes = workingGroupReferencesRepository.findByCountryCodeAndCountryRepresentativeMinistry(
                                workingGroupReferences.getCountryCode(),
                                workingGroupReferences.getCountryRepresentativeMinistry()
                            );
                            if (moeContactsRes != null) {
                                workingGroupReferences.setId(moeContactsRes.getId());
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
}
