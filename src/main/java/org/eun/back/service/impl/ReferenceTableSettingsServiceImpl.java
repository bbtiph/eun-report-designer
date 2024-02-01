package org.eun.back.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eun.back.domain.*;
import org.eun.back.repository.*;
import org.eun.back.security.SecurityUtils;
import org.eun.back.service.*;
import org.eun.back.service.dto.EventReferencesDTO;
import org.eun.back.service.dto.JobInfoDTO;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.eun.back.service.mapper.EventReferencesMapper;
import org.eun.back.service.mapper.ReferenceTableSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private final CountriesRepository countriesRepository;

    private final EventReferencesRepository eventReferencesRepository;

    private final EventReferencesService eventReferencesService;

    private final EventReferencesMapper eventReferencesMapper;

    private final RelEventReferencesCountriesRepository relEventReferencesCountriesRepository;

    private final EventReferencesParticipantsCategoryRepository eventReferencesParticipantsCategoryRepository;

    private final JobInfoService jobInfoService;

    private final BirtReportService birtReportService;

    private final FundingAndBudgetService fundingAndBudgetService;

    public ReferenceTableSettingsServiceImpl(
        ReferenceTableSettingsRepository referenceTableSettingsRepository,
        ReferenceTableSettingsMapper referenceTableSettingsMapper,
        WorkingGroupReferencesRepository workingGroupReferencesRepository,
        MoeContactsRepository moeContactsRepository,
        CountriesRepository countriesRepository,
        EventReferencesRepository eventReferencesRepository,
        EventReferencesService eventReferencesService,
        EventReferencesMapper eventReferencesMapper,
        RelEventReferencesCountriesRepository relEventReferencesCountriesRepository,
        EventReferencesParticipantsCategoryRepository eventReferencesParticipantsCategoryRepository,
        JobInfoService jobInfoService,
        BirtReportService birtReportService,
        FundingAndBudgetService fundingAndBudgetService
    ) {
        this.referenceTableSettingsRepository = referenceTableSettingsRepository;
        this.referenceTableSettingsMapper = referenceTableSettingsMapper;
        this.workingGroupReferencesRepository = workingGroupReferencesRepository;
        this.moeContactsRepository = moeContactsRepository;
        this.countriesRepository = countriesRepository;
        this.eventReferencesRepository = eventReferencesRepository;
        this.eventReferencesService = eventReferencesService;
        this.eventReferencesMapper = eventReferencesMapper;
        this.relEventReferencesCountriesRepository = relEventReferencesCountriesRepository;
        this.eventReferencesParticipantsCategoryRepository = eventReferencesParticipantsCategoryRepository;
        this.jobInfoService = jobInfoService;
        this.birtReportService = birtReportService;
        this.fundingAndBudgetService = fundingAndBudgetService;
    }

    @Override
    public ReferenceTableSettingsDTO save(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
        log.debug("Request to save ReferenceTableSettings : {}", referenceTableSettingsDTO);
        ReferenceTableSettings referenceTableSettings = referenceTableSettingsMapper.toEntity(referenceTableSettingsDTO);
        referenceTableSettings = referenceTableSettingsRepository.save(referenceTableSettings);
        return referenceTableSettingsMapper.toDto(referenceTableSettings);
    }

    @Override
    public ReferenceTableSettingsDTO updateReferenceRowByRefTable(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
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
            case "event_references":
            case "event_reference":
            case "event":
                return eventReferencesService.findAllByIsActive(true);
            case "job_info_reference":
            case "job_info":
                return jobInfoService.findAllByStatusProposal("Approved", null);
            case "countries":
                return countriesRepository.findAll();
            case "funding_and_project_of_project":
            case "funding_and_project_for_eun":
                return fundingAndBudgetService.findAll();
        }
        return null;
    }

    @Override
    public List<?> findAllDataByRefTableByCountryCode(String refTable, String countryCode, Map<String, String> params) {
        log.debug("Request to get all ReferenceTableSettings Data by country code");
        switch (refTable) {
            case "moe_contacts_reference":
            case "moe_contacts":
                return moeContactsRepository.findAll();
            case "working_group_reference":
            case "working_group":
                return workingGroupReferencesRepository.findAllByIsActiveAndCountryCode(true, countryCode);
            case "event_reference":
            case "event":
                return eventReferencesService.findAllByCountryId(countriesRepository.findFirstByCountryCodeIgnoreCase(countryCode).getId());
            case "job_info_reference":
            case "job_info":
                return jobInfoService.findAllByStatusProposal("Approved", params);
            case "countries":
                return countriesRepository.findAll();
        }
        return null;
    }

    @Override
    public void upload(MultipartFile file, String refTable) throws IOException {
        try (InputStream fileInputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            List<String> keywords;
            List<Integer> sheetNumbers = new ArrayList<>();
            switch (refTable) {
                case "moe_contacts_reference":
                case "moe_contacts":
                    moeContactsRepository.updateAllIsActiveToFalse();
                    keywords = Arrays.asList("Sheet");
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        Sheet sheet = workbook.getSheetAt(i);
                        String sheetName = sheet.getSheetName().toLowerCase();

                        for (String keyword : keywords) {
                            if (sheetName.contains(keyword.toLowerCase())) {
                                sheetNumbers.add(i);
                                break;
                            }
                        }
                    }

                    for (int pageNumber : sheetNumbers) {
                        Sheet sheet = workbook.getSheetAt(pageNumber);
                        int i = 0;
                        for (Row row : sheet) {
                            i++;
                            if (i > 3) {
                                try {
                                    if (row.getCell(0) == null || row.getCell(1) == null) {
                                        break;
                                    }
                                    MoeContacts moeContacts = new MoeContacts();
                                    moeContacts.setCountryCode(row.getCell(0) != null ? row.getCell(0).toString() : "");
                                    Cell cell = row.getCell(2);
                                    String cellValue = "";

                                    if (cell != null) {
                                        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                                            CellValue evaluatedCellValue = evaluator.evaluate(cell);
                                            switch (evaluatedCellValue.getCellType()) {
                                                case Cell.CELL_TYPE_STRING:
                                                    cellValue = evaluatedCellValue.getStringValue();
                                                    break;
                                                case Cell.CELL_TYPE_NUMERIC:
                                                    cellValue = String.valueOf(evaluatedCellValue.getNumberValue());
                                                    break;
                                                case Cell.CELL_TYPE_BOOLEAN:
                                                    cellValue = String.valueOf(evaluatedCellValue.getBooleanValue());
                                                    break;
                                                default:
                                                    break;
                                            }
                                        } else {
                                            cellValue = cell.toString();
                                        }
                                    }

                                    moeContacts.setCountryName(cellValue);
                                    moeContacts.setMinistryName(row.getCell(4) != null ? row.getCell(4).toString() : "");
                                    moeContacts.setMinistryEnglishName(row.getCell(6) != null ? row.getCell(6).toString() : "");
                                    moeContacts.setPostalAddress(row.getCell(8) != null ? row.getCell(8).toString() : "");
                                    moeContacts.setInvoicingAddress(row.getCell(10) != null ? row.getCell(10).toString() : "");
                                    moeContacts.setShippingAddress(row.getCell(12) != null ? row.getCell(12).toString() : "");
                                    moeContacts.setContactEunFirstName(row.getCell(14) != null ? row.getCell(14).toString() : "");
                                    moeContacts.setContactEunLastName(row.getCell(16) != null ? row.getCell(16).toString() : "");
                                    moeContacts.setType(sheet.getSheetName());
                                    MoeContacts moeContactsRes = moeContactsRepository.findByCountryCodeAndMinistryEnglishName(
                                        moeContacts.getCountryCode(),
                                        moeContacts.getMinistryEnglishName()
                                    );
                                    if (moeContactsRes != null) {
                                        moeContacts.setId(moeContactsRes.getId());
                                    }
                                    moeContacts.setActive(true);
                                    moeContactsRepository.save(moeContacts);
                                } catch (Exception e) {
                                    log.error("Error ", e);
                                }
                            }
                        }
                    }
                    break;
                case "working_group_reference":
                case "working_group":
                    workingGroupReferencesRepository.updateAllIsActiveToFalse();
                    keywords = Arrays.asList("Sheet", "working group", "working", "wg", "WG");

                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        Sheet sheet = workbook.getSheetAt(i);
                        String sheetName = sheet.getSheetName().toLowerCase();

                        for (String keyword : keywords) {
                            if (sheetName.contains(keyword.toLowerCase())) {
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
                            if (i > 3) {
                                try {
                                    if (row.getCell(0) == null || row.getCell(1) == null) {
                                        break;
                                    }
                                    WorkingGroupReferences workingGroupReferences = new WorkingGroupReferences();
                                    workingGroupReferences.setCountryCode(row.getCell(0) != null ? row.getCell(0).toString() : "");
                                    Cell cell = row.getCell(2);
                                    String cellValue = "";

                                    if (cell != null) {
                                        if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                                            CellValue evaluatedCellValue = evaluator.evaluate(cell);
                                            switch (evaluatedCellValue.getCellType()) {
                                                case Cell.CELL_TYPE_STRING:
                                                    cellValue = evaluatedCellValue.getStringValue();
                                                    break;
                                                case Cell.CELL_TYPE_NUMERIC:
                                                    cellValue = String.valueOf(evaluatedCellValue.getNumberValue());
                                                    break;
                                                case Cell.CELL_TYPE_BOOLEAN:
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
                                        row.getCell(4) != null ? row.getCell(4).toString() : ""
                                    );
                                    workingGroupReferences.setCountryRepresentativeLastName(
                                        row.getCell(6) != null ? row.getCell(6).toString() : ""
                                    );
                                    workingGroupReferences.setCountryRepresentativeMail(
                                        row.getCell(8) != null ? row.getCell(8).toString() : ""
                                    );
                                    workingGroupReferences.setCountryRepresentativePosition(
                                        row.getCell(10) != null ? row.getCell(10).toString() : ""
                                    );
                                    //                                    TODO: convert and set date
                                    workingGroupReferences.setCountryRepresentativeStartDate(null);
                                    //                                    TODO: convert and set date
                                    workingGroupReferences.setCountryRepresentativeEndDate(null);
                                    workingGroupReferences.setCountryRepresentativeMinistry(
                                        row.getCell(16) != null ? row.getCell(16).toString() : ""
                                    );
                                    workingGroupReferences.setCountryRepresentativeDepartment(
                                        row.getCell(18) != null ? row.getCell(18).toString() : ""
                                    );
                                    workingGroupReferences.setContactEunFirstName(
                                        row.getCell(20) != null ? row.getCell(20).toString() : ""
                                    );
                                    workingGroupReferences.setContactEunLastName(row.getCell(22) != null ? row.getCell(22).toString() : "");
                                    workingGroupReferences.setType(row.getCell(24) != null ? row.getCell(24).toString() : "");
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
                    break;
                case "event_reference":
                case "event":
                    //                    eventReferencesRepository.updateAllIsActiveToFalse();
                    keywords = Arrays.asList("School Innovation Forum");

                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        Sheet sheet = workbook.getSheetAt(i);
                        String sheetName = sheet.getSheetName().toLowerCase();

                        for (String keyword : keywords) {
                            if (sheetName.contains(keyword.toLowerCase())) {
                                sheetNumbers.add(i);
                                break;
                            }
                        }
                    }

                    for (int pageNumber : sheetNumbers) {
                        Sheet sheet = workbook.getSheetAt(pageNumber);
                        EventReferences event = null;
                        int i = 0;
                        for (Row row : sheet) {
                            i++;
                            if (i > 2 && i < 30) {
                                try {
                                    if (row.getCell(0).toString().length() > 0 || row.getCell(1).toString().length() > 0) {
                                        EventReferences eventReferences = new EventReferences();
                                        try {
                                            double cellValue = row.getCell(2).getNumericCellValue();
                                            DecimalFormat decimalFormat = new DecimalFormat("#");
                                            Long formattedValue = Long.parseLong(decimalFormat.format(cellValue));
                                            eventReferences.setExternalEventId(formattedValue);
                                        } catch (Exception e) {
                                            log.error(String.valueOf(e));
                                        }
                                        eventReferences.setName(row.getCell(0) != null ? row.getCell(0).toString() : "");
                                        eventReferences.setType(row.getCell(1) != null ? row.getCell(1).toString() : "");
                                        eventReferences.isActive(true);

                                        if (eventReferences.getId() == null) {
                                            EventReferences eventReferencesRes = eventReferencesRepository.findFirstByExternalEventId(
                                                eventReferences.getExternalEventId()
                                            );
                                            if (eventReferencesRes != null) {
                                                eventReferences.setId(eventReferencesRes.getId());
                                            }
                                        }
                                        eventReferences = eventReferencesRepository.save(eventReferences);
                                        event = eventReferences;
                                    }

                                    if (row.getCell(3).toString().length() > 0 || row.getCell(4).toString().length() > 0) {
                                        RelEventReferencesCountries relEventReferencesCountries = new RelEventReferencesCountries();
                                        Countries country = countriesRepository.findFirstByCountryNameIgnoreCase(row.getCell(3).toString());
                                        if (country != null) {
                                            relEventReferencesCountries.setCountriesId(country.getId());
                                            relEventReferencesCountries.setEventReferencesId(event.getId());
                                            try {
                                                double cellValue = row.getCell(4).getNumericCellValue();
                                                DecimalFormat decimalFormat = new DecimalFormat("#");
                                                Long formattedValue = Long.parseLong(decimalFormat.format(cellValue));
                                                relEventReferencesCountries.setParticipantsCount(
                                                    formattedValue != null ? formattedValue : 0
                                                );
                                            } catch (Exception e) {
                                                log.error(String.valueOf(e));
                                            }

                                            relEventReferencesCountriesRepository.save(relEventReferencesCountries);
                                        }
                                    }

                                    if (row.getCell(5).toString().length() > 0 || row.getCell(6).toString().length() > 0) {
                                        EventReferencesParticipantsCategory participantsCategory = new EventReferencesParticipantsCategory();
                                        participantsCategory.setEventReference(event);
                                        participantsCategory.setCategory(row.getCell(5) != null ? row.getCell(5).toString() : "");
                                        try {
                                            double cellValue = row.getCell(6).getNumericCellValue();
                                            DecimalFormat decimalFormat = new DecimalFormat("#");
                                            Long formattedValue = Long.parseLong(decimalFormat.format(cellValue));
                                            participantsCategory.setParticipantsCount(formattedValue != null ? formattedValue : 0);
                                        } catch (Exception e) {
                                            log.error(String.valueOf(e));
                                        }

                                        EventReferencesParticipantsCategory eventReferencesParticipantsCategoryRes = eventReferencesParticipantsCategoryRepository.findFirstByCategoryAndEventReference(
                                            participantsCategory.getCategory(),
                                            participantsCategory.getEventReference()
                                        );
                                        if (eventReferencesParticipantsCategoryRes != null) {
                                            participantsCategory.setId(eventReferencesParticipantsCategoryRes.getId());
                                        }
                                        eventReferencesParticipantsCategoryRepository.save(participantsCategory);
                                    }
                                } catch (Exception e) {
                                    log.error("Error ", e);
                                }
                            }
                            //                          TODO: delete kostil

                            //                            if (
                            //                                row.getCell(0).toString().length() == 0 &&
                            //                                row.getCell(1).toString().length() == 0 &&
                            //                                row.getCell(2).toString().length() == 0 &&
                            //                                row.getCell(3).toString().length() == 0 &&
                            //                                row.getCell(4).toString().length() == 0 &&
                            //                                row.getCell(5).toString().length() == 0 &&
                            //                                row.getCell(6).toString().length() == 0
                            //                            ) break;
                        }
                    }
                    break;
            }
        } catch (IOException e) {
            log.error("Error ", e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] download(String refTable) throws IOException, EngineException {
        ReferenceTableSettingsDTO referenceTableSettings = findOneByRefTable(refTable).get();
        return birtReportService.generateReferenceReport(referenceTableSettings);
    }

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

    @Override
    public void deleteReferenceRowByRefTableAndId(String refTable, Long id) {
        switch (refTable) {
            case "moe_contacts_reference":
            case "moe_contacts":
                moeContactsRepository.deleteById(id);
                break;
            case "working_group_reference":
            case "working_group":
                workingGroupReferencesRepository.deleteById(id);
                break;
            case "event_reference":
            case "event":
                eventReferencesService.delete(id);
                break;
            case "job_info_reference":
            case "job_info":
                jobInfoService.delete(id);
                break;
        }
    }

    @Override
    public Object updateReferenceRowByRefTable(String refTable, Object row) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRow = objectMapper.writeValueAsString(row);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        switch (refTable) {
            case "moe_contacts_reference":
            case "moe_contacts":
                MoeContacts moeContacts = objectMapper.readValue(jsonRow, MoeContacts.class);
                if (moeContacts.getId() == null) {
                    //                    TODO: added logic to ext. fields
                }
                return moeContactsRepository.save(moeContacts);
            case "working_group_reference":
            case "working_group":
                WorkingGroupReferences workingGroupReferences = objectMapper.readValue(jsonRow, WorkingGroupReferences.class);
                if (workingGroupReferences.getId() == null) {
                    workingGroupReferences.setIsActive(true);
                    workingGroupReferences.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
                    workingGroupReferences.setCreatedDate(LocalDate.now());
                }
                return workingGroupReferencesRepository.save(workingGroupReferences);
            case "event_reference":
            case "event":
                EventReferencesDTO eventReferencesDTO = objectMapper.readValue(jsonRow, EventReferencesDTO.class);
                if (eventReferencesDTO.getId() == null) {
                    eventReferencesDTO.setIsActive(true);
                }
                eventReferencesDTO
                    .getCountries()
                    .stream()
                    .map(countriesWithParticipantsDTO -> {
                        RelEventReferencesCountries relEventReferencesCountries = new RelEventReferencesCountries();
                        relEventReferencesCountries.setParticipantsCount(countriesWithParticipantsDTO.getParticipantsCount());
                        relEventReferencesCountries.setCountriesId(
                            countriesRepository.findFirstByCountryCodeIgnoreCase(countriesWithParticipantsDTO.getCountryCode()).getId()
                        );
                        relEventReferencesCountries.setEventReferencesId(eventReferencesDTO.getId());
                        return relEventReferencesCountries;
                    })
                    .forEach(relEventReferencesCountries -> {
                        relEventReferencesCountriesRepository.save(relEventReferencesCountries);
                    });

                return eventReferencesRepository.save(eventReferencesMapper.toEntity(eventReferencesDTO));
            case "job_info_reference":
            case "job_info":
                JobInfoDTO jobInfo = objectMapper.readValue(jsonRow, JobInfoDTO.class);
                return jobInfoService.save(jobInfo);
        }
        return null;
    }
}
