package org.eun.back.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eun.back.domain.MoeContacts;
import org.eun.back.domain.ReferenceTableSettings;
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.repository.MoeContactsRepository;
import org.eun.back.repository.ReferenceTableSettingsRepository;
import org.eun.back.repository.WorkingGroupReferencesRepository;
import org.eun.back.service.ReferenceTableSettingsService;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.eun.back.service.mapper.ReferenceTableSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReferenceTableSettings}.
 */
@Service
@Transactional
public class ReferenceTableSettingsServiceImpl implements ReferenceTableSettingsService {

    private final Logger log = LoggerFactory.getLogger(ReferenceTableSettingsServiceImpl.class);

    private final ReferenceTableSettingsRepository referenceTableSettingsRepository;

    private final ReferenceTableSettingsMapper referenceTableSettingsMapper;

    private final WorkingGroupReferencesRepository workingGroupReferencesRepository;

    private final MoeContactsRepository moeContactsRepository;

    public ReferenceTableSettingsServiceImpl(
        ReferenceTableSettingsRepository referenceTableSettingsRepository,
        ReferenceTableSettingsMapper referenceTableSettingsMapper,
        WorkingGroupReferencesRepository workingGroupReferencesRepository,
        MoeContactsRepository moeContactsRepository
    ) {
        this.referenceTableSettingsRepository = referenceTableSettingsRepository;
        this.referenceTableSettingsMapper = referenceTableSettingsMapper;
        this.workingGroupReferencesRepository = workingGroupReferencesRepository;
        this.moeContactsRepository = moeContactsRepository;
    }

    @Override
    public ReferenceTableSettingsDTO save(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
        log.debug("Request to save ReferenceTableSettings : {}", referenceTableSettingsDTO);
        ReferenceTableSettings referenceTableSettings = referenceTableSettingsMapper.toEntity(referenceTableSettingsDTO);
        referenceTableSettings = referenceTableSettingsRepository.save(referenceTableSettings);
        return referenceTableSettingsMapper.toDto(referenceTableSettings);
    }

    @Override
    public ReferenceTableSettingsDTO update(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
        log.debug("Request to update ReferenceTableSettings : {}", referenceTableSettingsDTO);
        ReferenceTableSettings referenceTableSettings = referenceTableSettingsMapper.toEntity(referenceTableSettingsDTO);
        referenceTableSettings = referenceTableSettingsRepository.save(referenceTableSettings);
        return referenceTableSettingsMapper.toDto(referenceTableSettings);
    }

    @Override
    public Optional<ReferenceTableSettingsDTO> partialUpdate(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
        log.debug("Request to partially update ReferenceTableSettings : {}", referenceTableSettingsDTO);

        return referenceTableSettingsRepository
            .findById(referenceTableSettingsDTO.getId())
            .map(existingReferenceTableSettings -> {
                referenceTableSettingsMapper.partialUpdate(existingReferenceTableSettings, referenceTableSettingsDTO);

                return existingReferenceTableSettings;
            })
            .map(referenceTableSettingsRepository::save)
            .map(referenceTableSettingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferenceTableSettingsDTO> findAll() {
        log.debug("Request to get all ReferenceTableSettings");
        return referenceTableSettingsRepository
            .findAll()
            .stream()
            .map(referenceTableSettingsMapper::toDtoToShowInHomePage)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<?> findAllDataByRefTable(String refTable) {
        log.debug("Request to get all ReferenceTableSettings Data");
        switch (refTable) {
            case "moe_contacts_reference":
            case "moe_contacts":
                return moeContactsRepository.findAll();
            case "working_group_reference":
            case "working_group":
                return workingGroupReferencesRepository.findAllByIsActive(true);
        }
        return null;
    }

    @Override
    public byte[] download(String refTable) throws IOException {
        ReferenceTableSettingsDTO referenceTableSettings = findOneByRefTable(refTable).get();
        try (
            ByteArrayInputStream templateStream = new ByteArrayInputStream(referenceTableSettings.getFile());
            Workbook workbook = new XSSFWorkbook(templateStream)
        ) {
            Map<String, Sheet> sheetMap = new HashMap<>();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                sheetMap.put(sheet.getSheetName(), sheet);
            }
            switch (refTable) {
                case "moe_contacts_reference":
                case "moe_contacts":
                    List<MoeContacts> moeContacts = moeContactsRepository.findAll();
                    Map<String, List<MoeContacts>> dataBySheetName = new HashMap();

                    for (MoeContacts contacts : moeContacts) {
                        String sheetName = contacts.getType();
                        dataBySheetName.computeIfAbsent(sheetName, k -> new ArrayList<>()).add(contacts);
                    }

                    for (Map.Entry<String, List<MoeContacts>> entry : dataBySheetName.entrySet()) {
                        String sheetName = entry.getKey();
                        List<MoeContacts> dataForSheet = entry.getValue();
                        Sheet sheet = sheetMap.get(sheetName);

                        int rowNum = 2;
                        for (MoeContacts wg : dataForSheet) {
                            Row row = sheet.createRow(rowNum++);
                            row.createCell(0).setCellValue(wg.getCountryCode());
                            row.createCell(1).setCellValue(wg.getCountryName());
                            row.createCell(2).setCellValue(wg.getMinistryName());
                            row.createCell(3).setCellValue(wg.getMinistryEnglishName());
                            row.createCell(4).setCellValue(wg.getPostalAddress());
                            row.createCell(5).setCellValue(wg.getInvoicingAddress());
                            row.createCell(6).setCellValue(wg.getShippingAddress());
                            row.createCell(7).setCellValue("");
                            row.createCell(8).setCellValue(wg.getContactEunFirstName());
                            row.createCell(9).setCellValue(wg.getContactEunLastName());
                        }
                    }
                    break;
                case "working_group_reference":
                case "working_group":
                    List<WorkingGroupReferences> workingGroupReferences = workingGroupReferencesRepository.findAllByIsActive(true);
                    Map<String, List<WorkingGroupReferences>> dataBySheetNameWorkingGroup = new HashMap();

                    for (WorkingGroupReferences wg : workingGroupReferences) {
                        String sheetName = wg.getType();
                        dataBySheetNameWorkingGroup.computeIfAbsent(sheetName, k -> new ArrayList<>()).add(wg);
                    }

                    for (Map.Entry<String, List<WorkingGroupReferences>> entry : dataBySheetNameWorkingGroup.entrySet()) {
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
                    break;
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

    //    @Override
    //    public byte[] download(String refTable) throws IOException {
    //        ReferenceTableSettingsDTO referenceTableSettings = findOneByRefTable(refTable).orElseThrow(() -> new RuntimeException("Reference table not found"));
    //
    //        try (ByteArrayInputStream templateStream = new ByteArrayInputStream(referenceTableSettings.getFile());
    //             Workbook workbook = new XSSFWorkbook(templateStream)) {
    //
    //            Map<String, List<?>> dataMap = getDataMap(refTable);
    //
    //            for (Map.Entry<String, List<?>> entry : dataMap.entrySet()) {
    //                String sheetName = entry.getKey();
    //                List<?> dataList = entry.getValue();
    //                Sheet sheet = workbook.getSheet(sheetName);
    //
    //                int rowNum = 2;
    //                for (Object data : dataList) {
    //                    Row row = sheet.createRow(rowNum++);
    //                    populateRow(row, data);
    //                }
    //            }
    //
    //            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
    //                workbook.write(outputStream);
    //                return outputStream.toByteArray();
    //            }
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //        return new byte[0];
    //    }
    //
    //    private Map<String, List<?>> getDataMap(String refTable) {
    //        switch (refTable) {
    //            case "moe_contacts_reference":
    //            case "moe_contacts":
    //                List<MoeContacts> moeContacts = moeContactsRepository.findAll();
    //                return getDataMapBySheetName(moeContacts, MoeContacts::getType);
    //            case "working_group_reference":
    //            case "working_group":
    //                List<WorkingGroupReferences> workingGroupReferences = workingGroupReferencesRepository.findAllByIsActive(true);
    //                return getDataMapBySheetName(workingGroupReferences, WorkingGroupReferences::getType);
    //            default:
    //                throw new IllegalArgumentException("Invalid reference table: " + refTable);
    //        }
    //    }
    //
    //    private <T> Map<String, List<T>> getDataMapBySheetName(List<T> dataList, Function<T, String> sheetNameExtractor) {
    //        Map<String, List<T>> dataBySheetName = new HashMap<>();
    //        for (T data : dataList) {
    //            String sheetName = sheetNameExtractor.apply(data);
    //            dataBySheetName.computeIfAbsent(sheetName, k -> new ArrayList<>()).add(data);
    //        }
    //        return dataBySheetName;
    //    }
    //
    //    private void populateRow(Row row, Object data) {
    //        if (data instanceof MoeContacts) {
    //            populateMoeContactsRow(row, (MoeContacts) data);
    //        } else if (data instanceof WorkingGroupReferences) {
    //            populateWorkingGroupReferencesRow(row, (WorkingGroupReferences) data);
    //        }
    //    }
    //
    //    private void populateMoeContactsRow(Row row, MoeContacts moeContacts) {
    //        row.createCell(0).setCellValue(moeContacts.getCountryCode());
    //        row.createCell(1).setCellValue(moeContacts.getCountryName());
    //        row.createCell(2).setCellValue(moeContacts.getMinistryName());
    //        row.createCell(3).setCellValue(moeContacts.getMinistryEnglishName());
    //        row.createCell(4).setCellValue(moeContacts.getPostalAddress());
    //        row.createCell(5).setCellValue(moeContacts.getInvoicingAddress());
    //        row.createCell(6).setCellValue(moeContacts.getShippingAddress());
    //        row.createCell(7).setCellValue("");
    //        row.createCell(8).setCellValue(moeContacts.getContactEunFirstName());
    //        row.createCell(9).setCellValue(moeContacts.getContactEunLastName());
    //    }
    //
    //    private void populateWorkingGroupReferencesRow(Row row, WorkingGroupReferences wg) {
    //        row.createCell(0).setCellValue(wg.getCountryCode());
    //        row.createCell(1).setCellValue(wg.getCountryName());
    //        row.createCell(2).setCellValue(wg.getCountryRepresentativeFirstName());
    //        row.createCell(3).setCellValue(wg.getCountryRepresentativeLastName());
    //        row.createCell(4).setCellValue(wg.getCountryRepresentativeMail());
    //        row.createCell(5).setCellValue(wg.getCountryRepresentativePosition());
    //        row.createCell(6).setCellValue(wg.getCountryRepresentativeStartDate());
    //        row.createCell(7).setCellValue(wg.getCountryRepresentativeEndDate());
    //        row.createCell(8).setCellValue(wg.getCountryRepresentativeMinistry());
    //        row.createCell(9).setCellValue(wg.getCountryRepresentativeDepartment());
    //        row.createCell(10).setCellValue(wg.getContactEunFirstName());
    //        row.createCell(11).setCellValue(wg.getContactEunLastName());
    //    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReferenceTableSettingsDTO> findOne(Long id) {
        log.debug("Request to get ReferenceTableSettings : {}", id);
        return referenceTableSettingsRepository.findById(id).map(referenceTableSettingsMapper::toDto);
    }

    @Override
    public Optional<ReferenceTableSettingsDTO> findOneByRefTable(String refTable) {
        return referenceTableSettingsRepository.findByRefTable(refTable).map(referenceTableSettingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReferenceTableSettings : {}", id);
        referenceTableSettingsRepository.deleteById(id);
    }
}
