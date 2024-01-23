package org.eun.back.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.eun.back.IntegrationTest;
import org.eun.back.domain.JobInfo;
import org.eun.back.repository.JobInfoRepository;
import org.eun.back.service.dto.JobInfoDTO;
import org.eun.back.service.mapper.JobInfoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JobInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobInfoResourceIT {

    private static final String DEFAULT_ODATA_ETAG = "AAAAAAAAAA";
    private static final String UPDATED_ODATA_ETAG = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_2 = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_TO_CUSTOMER_NO = "AAAAAAAAAA";
    private static final String UPDATED_BILL_TO_CUSTOMER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_TO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BILL_TO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_TO_COUNTRY_REGION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BILL_TO_COUNTRY_REGION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SELL_TO_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_SELL_TO_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_YOUR_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_YOUR_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_PROPOSAL = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_PROPOSAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STARTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURATION_IN_MONTHS = 1;
    private static final Integer UPDATED_DURATION_IN_MONTHS = 2;

    private static final String DEFAULT_PROJECT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_MANAGER = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_MANAGER_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_MANAGER_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EUN_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_EUN_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_VISA_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VISA_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TYPE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TYPE_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_PROGRAM = "AAAAAAAAAA";
    private static final String UPDATED_JOB_PROGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAM_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAM_MANAGER = "BBBBBBBBBB";

    private static final Double DEFAULT_BUDGET_EUN = 1D;
    private static final Double UPDATED_BUDGET_EUN = 2D;

    private static final Double DEFAULT_FUNDING_EUN = 1D;
    private static final Double UPDATED_FUNDING_EUN = 2D;

    private static final Double DEFAULT_FUNDING_RATE = 1D;
    private static final Double UPDATED_FUNDING_RATE = 2D;

    private static final Double DEFAULT_BUDGET_CONSORTIUM = 1D;
    private static final Double UPDATED_BUDGET_CONSORTIUM = 2D;

    private static final Double DEFAULT_FUNDING_CONSORTIUM = 1D;
    private static final Double UPDATED_FUNDING_CONSORTIUM = 2D;

    private static final Integer DEFAULT_OVERHEAD_PERC = 1;
    private static final Integer UPDATED_OVERHEAD_PERC = 2;

    private static final String ENTITY_API_URL = "/api/job-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobInfoMockMvc;

    private JobInfo jobInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobInfo createEntity(EntityManager em) {
        JobInfo jobInfo = new JobInfo()
            .odataEtag(DEFAULT_ODATA_ETAG)
            .externalId(DEFAULT_EXTERNAL_ID)
            .jobNumber(DEFAULT_JOB_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .description2(DEFAULT_DESCRIPTION_2)
            .billToCustomerNo(DEFAULT_BILL_TO_CUSTOMER_NO)
            .billToName(DEFAULT_BILL_TO_NAME)
            .billToCountryRegionCode(DEFAULT_BILL_TO_COUNTRY_REGION_CODE)
            .sellToContact(DEFAULT_SELL_TO_CONTACT)
            .yourReference(DEFAULT_YOUR_REFERENCE)
            .contractNo(DEFAULT_CONTRACT_NO)
            .statusProposal(DEFAULT_STATUS_PROPOSAL)
            .startingDate(DEFAULT_STARTING_DATE)
            .endingDate(DEFAULT_ENDING_DATE)
            .durationInMonths(DEFAULT_DURATION_IN_MONTHS)
            .projectManager(DEFAULT_PROJECT_MANAGER)
            .projectManagerMail(DEFAULT_PROJECT_MANAGER_MAIL)
            .eunRole(DEFAULT_EUN_ROLE)
            .visaNumber(DEFAULT_VISA_NUMBER)
            .jobType(DEFAULT_JOB_TYPE)
            .jobTypeText(DEFAULT_JOB_TYPE_TEXT)
            .jobProgram(DEFAULT_JOB_PROGRAM)
            .programManager(DEFAULT_PROGRAM_MANAGER)
            .budgetEUN(DEFAULT_BUDGET_EUN)
            .fundingEUN(DEFAULT_FUNDING_EUN)
            .fundingRate(DEFAULT_FUNDING_RATE)
            .budgetConsortium(DEFAULT_BUDGET_CONSORTIUM)
            .fundingConsortium(DEFAULT_FUNDING_CONSORTIUM)
            .overheadPerc(DEFAULT_OVERHEAD_PERC);
        return jobInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobInfo createUpdatedEntity(EntityManager em) {
        JobInfo jobInfo = new JobInfo()
            .odataEtag(UPDATED_ODATA_ETAG)
            .externalId(UPDATED_EXTERNAL_ID)
            .jobNumber(UPDATED_JOB_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .description2(UPDATED_DESCRIPTION_2)
            .billToCustomerNo(UPDATED_BILL_TO_CUSTOMER_NO)
            .billToName(UPDATED_BILL_TO_NAME)
            .billToCountryRegionCode(UPDATED_BILL_TO_COUNTRY_REGION_CODE)
            .sellToContact(UPDATED_SELL_TO_CONTACT)
            .yourReference(UPDATED_YOUR_REFERENCE)
            .contractNo(UPDATED_CONTRACT_NO)
            .statusProposal(UPDATED_STATUS_PROPOSAL)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE)
            .durationInMonths(UPDATED_DURATION_IN_MONTHS)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .projectManagerMail(UPDATED_PROJECT_MANAGER_MAIL)
            .eunRole(UPDATED_EUN_ROLE)
            .visaNumber(UPDATED_VISA_NUMBER)
            .jobType(UPDATED_JOB_TYPE)
            .jobTypeText(UPDATED_JOB_TYPE_TEXT)
            .jobProgram(UPDATED_JOB_PROGRAM)
            .programManager(UPDATED_PROGRAM_MANAGER)
            .budgetEUN(UPDATED_BUDGET_EUN)
            .fundingEUN(UPDATED_FUNDING_EUN)
            .fundingRate(UPDATED_FUNDING_RATE)
            .budgetConsortium(UPDATED_BUDGET_CONSORTIUM)
            .fundingConsortium(UPDATED_FUNDING_CONSORTIUM)
            .overheadPerc(UPDATED_OVERHEAD_PERC);
        return jobInfo;
    }

    @BeforeEach
    public void initTest() {
        jobInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createJobInfo() throws Exception {
        int databaseSizeBeforeCreate = jobInfoRepository.findAll().size();
        // Create the JobInfo
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);
        restJobInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeCreate + 1);
        JobInfo testJobInfo = jobInfoList.get(jobInfoList.size() - 1);
        assertThat(testJobInfo.getOdataEtag()).isEqualTo(DEFAULT_ODATA_ETAG);
        assertThat(testJobInfo.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testJobInfo.getJobNumber()).isEqualTo(DEFAULT_JOB_NUMBER);
        assertThat(testJobInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJobInfo.getDescription2()).isEqualTo(DEFAULT_DESCRIPTION_2);
        assertThat(testJobInfo.getBillToCustomerNo()).isEqualTo(DEFAULT_BILL_TO_CUSTOMER_NO);
        assertThat(testJobInfo.getBillToName()).isEqualTo(DEFAULT_BILL_TO_NAME);
        assertThat(testJobInfo.getBillToCountryRegionCode()).isEqualTo(DEFAULT_BILL_TO_COUNTRY_REGION_CODE);
        assertThat(testJobInfo.getSellToContact()).isEqualTo(DEFAULT_SELL_TO_CONTACT);
        assertThat(testJobInfo.getYourReference()).isEqualTo(DEFAULT_YOUR_REFERENCE);
        assertThat(testJobInfo.getContractNo()).isEqualTo(DEFAULT_CONTRACT_NO);
        assertThat(testJobInfo.getStatusProposal()).isEqualTo(DEFAULT_STATUS_PROPOSAL);
        assertThat(testJobInfo.getStartingDate()).isEqualTo(DEFAULT_STARTING_DATE);
        assertThat(testJobInfo.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
        assertThat(testJobInfo.getDurationInMonths()).isEqualTo(DEFAULT_DURATION_IN_MONTHS);
        assertThat(testJobInfo.getProjectManager()).isEqualTo(DEFAULT_PROJECT_MANAGER);
        assertThat(testJobInfo.getProjectManagerMail()).isEqualTo(DEFAULT_PROJECT_MANAGER_MAIL);
        assertThat(testJobInfo.getEunRole()).isEqualTo(DEFAULT_EUN_ROLE);
        assertThat(testJobInfo.getVisaNumber()).isEqualTo(DEFAULT_VISA_NUMBER);
        assertThat(testJobInfo.getJobType()).isEqualTo(DEFAULT_JOB_TYPE);
        assertThat(testJobInfo.getJobTypeText()).isEqualTo(DEFAULT_JOB_TYPE_TEXT);
        assertThat(testJobInfo.getJobProgram()).isEqualTo(DEFAULT_JOB_PROGRAM);
        assertThat(testJobInfo.getProgramManager()).isEqualTo(DEFAULT_PROGRAM_MANAGER);
        assertThat(testJobInfo.getBudgetEUN()).isEqualTo(DEFAULT_BUDGET_EUN);
        assertThat(testJobInfo.getFundingEUN()).isEqualTo(DEFAULT_FUNDING_EUN);
        assertThat(testJobInfo.getFundingRate()).isEqualTo(DEFAULT_FUNDING_RATE);
        assertThat(testJobInfo.getBudgetConsortium()).isEqualTo(DEFAULT_BUDGET_CONSORTIUM);
        assertThat(testJobInfo.getFundingConsortium()).isEqualTo(DEFAULT_FUNDING_CONSORTIUM);
        assertThat(testJobInfo.getOverheadPerc()).isEqualTo(DEFAULT_OVERHEAD_PERC);
    }

    @Test
    @Transactional
    void createJobInfoWithExistingId() throws Exception {
        // Create the JobInfo with an existing ID
        jobInfo.setId(1L);
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);

        int databaseSizeBeforeCreate = jobInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobInfos() throws Exception {
        // Initialize the database
        jobInfoRepository.saveAndFlush(jobInfo);

        // Get all the jobInfoList
        restJobInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].odataEtag").value(hasItem(DEFAULT_ODATA_ETAG)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].jobNumber").value(hasItem(DEFAULT_JOB_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].description2").value(hasItem(DEFAULT_DESCRIPTION_2)))
            .andExpect(jsonPath("$.[*].billToCustomerNo").value(hasItem(DEFAULT_BILL_TO_CUSTOMER_NO)))
            .andExpect(jsonPath("$.[*].billToName").value(hasItem(DEFAULT_BILL_TO_NAME)))
            .andExpect(jsonPath("$.[*].billToCountryRegionCode").value(hasItem(DEFAULT_BILL_TO_COUNTRY_REGION_CODE)))
            .andExpect(jsonPath("$.[*].sellToContact").value(hasItem(DEFAULT_SELL_TO_CONTACT)))
            .andExpect(jsonPath("$.[*].yourReference").value(hasItem(DEFAULT_YOUR_REFERENCE)))
            .andExpect(jsonPath("$.[*].contractNo").value(hasItem(DEFAULT_CONTRACT_NO)))
            .andExpect(jsonPath("$.[*].statusProposal").value(hasItem(DEFAULT_STATUS_PROPOSAL)))
            .andExpect(jsonPath("$.[*].startingDate").value(hasItem(DEFAULT_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].endingDate").value(hasItem(DEFAULT_ENDING_DATE.toString())))
            .andExpect(jsonPath("$.[*].durationInMonths").value(hasItem(DEFAULT_DURATION_IN_MONTHS)))
            .andExpect(jsonPath("$.[*].projectManager").value(hasItem(DEFAULT_PROJECT_MANAGER)))
            .andExpect(jsonPath("$.[*].projectManagerMail").value(hasItem(DEFAULT_PROJECT_MANAGER_MAIL)))
            .andExpect(jsonPath("$.[*].eunRole").value(hasItem(DEFAULT_EUN_ROLE)))
            .andExpect(jsonPath("$.[*].visaNumber").value(hasItem(DEFAULT_VISA_NUMBER)))
            .andExpect(jsonPath("$.[*].jobType").value(hasItem(DEFAULT_JOB_TYPE)))
            .andExpect(jsonPath("$.[*].jobTypeText").value(hasItem(DEFAULT_JOB_TYPE_TEXT)))
            .andExpect(jsonPath("$.[*].jobProgram").value(hasItem(DEFAULT_JOB_PROGRAM)))
            .andExpect(jsonPath("$.[*].programManager").value(hasItem(DEFAULT_PROGRAM_MANAGER)))
            .andExpect(jsonPath("$.[*].budgetEUN").value(hasItem(DEFAULT_BUDGET_EUN.doubleValue())))
            .andExpect(jsonPath("$.[*].fundingEUN").value(hasItem(DEFAULT_FUNDING_EUN.doubleValue())))
            .andExpect(jsonPath("$.[*].fundingRate").value(hasItem(DEFAULT_FUNDING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetConsortium").value(hasItem(DEFAULT_BUDGET_CONSORTIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].fundingConsortium").value(hasItem(DEFAULT_FUNDING_CONSORTIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].overheadPerc").value(hasItem(DEFAULT_OVERHEAD_PERC)));
    }

    @Test
    @Transactional
    void getJobInfo() throws Exception {
        // Initialize the database
        jobInfoRepository.saveAndFlush(jobInfo);

        // Get the jobInfo
        restJobInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, jobInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobInfo.getId().intValue()))
            .andExpect(jsonPath("$.odataEtag").value(DEFAULT_ODATA_ETAG))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.jobNumber").value(DEFAULT_JOB_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.description2").value(DEFAULT_DESCRIPTION_2))
            .andExpect(jsonPath("$.billToCustomerNo").value(DEFAULT_BILL_TO_CUSTOMER_NO))
            .andExpect(jsonPath("$.billToName").value(DEFAULT_BILL_TO_NAME))
            .andExpect(jsonPath("$.billToCountryRegionCode").value(DEFAULT_BILL_TO_COUNTRY_REGION_CODE))
            .andExpect(jsonPath("$.sellToContact").value(DEFAULT_SELL_TO_CONTACT))
            .andExpect(jsonPath("$.yourReference").value(DEFAULT_YOUR_REFERENCE))
            .andExpect(jsonPath("$.contractNo").value(DEFAULT_CONTRACT_NO))
            .andExpect(jsonPath("$.statusProposal").value(DEFAULT_STATUS_PROPOSAL))
            .andExpect(jsonPath("$.startingDate").value(DEFAULT_STARTING_DATE.toString()))
            .andExpect(jsonPath("$.endingDate").value(DEFAULT_ENDING_DATE.toString()))
            .andExpect(jsonPath("$.durationInMonths").value(DEFAULT_DURATION_IN_MONTHS))
            .andExpect(jsonPath("$.projectManager").value(DEFAULT_PROJECT_MANAGER))
            .andExpect(jsonPath("$.projectManagerMail").value(DEFAULT_PROJECT_MANAGER_MAIL))
            .andExpect(jsonPath("$.eunRole").value(DEFAULT_EUN_ROLE))
            .andExpect(jsonPath("$.visaNumber").value(DEFAULT_VISA_NUMBER))
            .andExpect(jsonPath("$.jobType").value(DEFAULT_JOB_TYPE))
            .andExpect(jsonPath("$.jobTypeText").value(DEFAULT_JOB_TYPE_TEXT))
            .andExpect(jsonPath("$.jobProgram").value(DEFAULT_JOB_PROGRAM))
            .andExpect(jsonPath("$.programManager").value(DEFAULT_PROGRAM_MANAGER))
            .andExpect(jsonPath("$.budgetEUN").value(DEFAULT_BUDGET_EUN.doubleValue()))
            .andExpect(jsonPath("$.fundingEUN").value(DEFAULT_FUNDING_EUN.doubleValue()))
            .andExpect(jsonPath("$.fundingRate").value(DEFAULT_FUNDING_RATE.doubleValue()))
            .andExpect(jsonPath("$.budgetConsortium").value(DEFAULT_BUDGET_CONSORTIUM.doubleValue()))
            .andExpect(jsonPath("$.fundingConsortium").value(DEFAULT_FUNDING_CONSORTIUM.doubleValue()))
            .andExpect(jsonPath("$.overheadPerc").value(DEFAULT_OVERHEAD_PERC));
    }

    @Test
    @Transactional
    void getNonExistingJobInfo() throws Exception {
        // Get the jobInfo
        restJobInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobInfo() throws Exception {
        // Initialize the database
        jobInfoRepository.saveAndFlush(jobInfo);

        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();

        // Update the jobInfo
        JobInfo updatedJobInfo = jobInfoRepository.findById(jobInfo.getId()).get();
        // Disconnect from session so that the updates on updatedJobInfo are not directly saved in db
        em.detach(updatedJobInfo);
        updatedJobInfo
            .odataEtag(UPDATED_ODATA_ETAG)
            .externalId(UPDATED_EXTERNAL_ID)
            .jobNumber(UPDATED_JOB_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .description2(UPDATED_DESCRIPTION_2)
            .billToCustomerNo(UPDATED_BILL_TO_CUSTOMER_NO)
            .billToName(UPDATED_BILL_TO_NAME)
            .billToCountryRegionCode(UPDATED_BILL_TO_COUNTRY_REGION_CODE)
            .sellToContact(UPDATED_SELL_TO_CONTACT)
            .yourReference(UPDATED_YOUR_REFERENCE)
            .contractNo(UPDATED_CONTRACT_NO)
            .statusProposal(UPDATED_STATUS_PROPOSAL)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE)
            .durationInMonths(UPDATED_DURATION_IN_MONTHS)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .projectManagerMail(UPDATED_PROJECT_MANAGER_MAIL)
            .eunRole(UPDATED_EUN_ROLE)
            .visaNumber(UPDATED_VISA_NUMBER)
            .jobType(UPDATED_JOB_TYPE)
            .jobTypeText(UPDATED_JOB_TYPE_TEXT)
            .jobProgram(UPDATED_JOB_PROGRAM)
            .programManager(UPDATED_PROGRAM_MANAGER)
            .budgetEUN(UPDATED_BUDGET_EUN)
            .fundingEUN(UPDATED_FUNDING_EUN)
            .fundingRate(UPDATED_FUNDING_RATE)
            .budgetConsortium(UPDATED_BUDGET_CONSORTIUM)
            .fundingConsortium(UPDATED_FUNDING_CONSORTIUM)
            .overheadPerc(UPDATED_OVERHEAD_PERC);
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(updatedJobInfo);

        restJobInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
        JobInfo testJobInfo = jobInfoList.get(jobInfoList.size() - 1);
        assertThat(testJobInfo.getOdataEtag()).isEqualTo(UPDATED_ODATA_ETAG);
        assertThat(testJobInfo.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testJobInfo.getJobNumber()).isEqualTo(UPDATED_JOB_NUMBER);
        assertThat(testJobInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobInfo.getDescription2()).isEqualTo(UPDATED_DESCRIPTION_2);
        assertThat(testJobInfo.getBillToCustomerNo()).isEqualTo(UPDATED_BILL_TO_CUSTOMER_NO);
        assertThat(testJobInfo.getBillToName()).isEqualTo(UPDATED_BILL_TO_NAME);
        assertThat(testJobInfo.getBillToCountryRegionCode()).isEqualTo(UPDATED_BILL_TO_COUNTRY_REGION_CODE);
        assertThat(testJobInfo.getSellToContact()).isEqualTo(UPDATED_SELL_TO_CONTACT);
        assertThat(testJobInfo.getYourReference()).isEqualTo(UPDATED_YOUR_REFERENCE);
        assertThat(testJobInfo.getContractNo()).isEqualTo(UPDATED_CONTRACT_NO);
        assertThat(testJobInfo.getStatusProposal()).isEqualTo(UPDATED_STATUS_PROPOSAL);
        assertThat(testJobInfo.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testJobInfo.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
        assertThat(testJobInfo.getDurationInMonths()).isEqualTo(UPDATED_DURATION_IN_MONTHS);
        assertThat(testJobInfo.getProjectManager()).isEqualTo(UPDATED_PROJECT_MANAGER);
        assertThat(testJobInfo.getProjectManagerMail()).isEqualTo(UPDATED_PROJECT_MANAGER_MAIL);
        assertThat(testJobInfo.getEunRole()).isEqualTo(UPDATED_EUN_ROLE);
        assertThat(testJobInfo.getVisaNumber()).isEqualTo(UPDATED_VISA_NUMBER);
        assertThat(testJobInfo.getJobType()).isEqualTo(UPDATED_JOB_TYPE);
        assertThat(testJobInfo.getJobTypeText()).isEqualTo(UPDATED_JOB_TYPE_TEXT);
        assertThat(testJobInfo.getJobProgram()).isEqualTo(UPDATED_JOB_PROGRAM);
        assertThat(testJobInfo.getProgramManager()).isEqualTo(UPDATED_PROGRAM_MANAGER);
        assertThat(testJobInfo.getBudgetEUN()).isEqualTo(UPDATED_BUDGET_EUN);
        assertThat(testJobInfo.getFundingEUN()).isEqualTo(UPDATED_FUNDING_EUN);
        assertThat(testJobInfo.getFundingRate()).isEqualTo(UPDATED_FUNDING_RATE);
        assertThat(testJobInfo.getBudgetConsortium()).isEqualTo(UPDATED_BUDGET_CONSORTIUM);
        assertThat(testJobInfo.getFundingConsortium()).isEqualTo(UPDATED_FUNDING_CONSORTIUM);
        assertThat(testJobInfo.getOverheadPerc()).isEqualTo(UPDATED_OVERHEAD_PERC);
    }

    @Test
    @Transactional
    void putNonExistingJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();
        jobInfo.setId(count.incrementAndGet());

        // Create the JobInfo
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();
        jobInfo.setId(count.incrementAndGet());

        // Create the JobInfo
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();
        jobInfo.setId(count.incrementAndGet());

        // Create the JobInfo
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobInfoWithPatch() throws Exception {
        // Initialize the database
        jobInfoRepository.saveAndFlush(jobInfo);

        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();

        // Update the jobInfo using partial update
        JobInfo partialUpdatedJobInfo = new JobInfo();
        partialUpdatedJobInfo.setId(jobInfo.getId());

        partialUpdatedJobInfo
            .description(UPDATED_DESCRIPTION)
            .description2(UPDATED_DESCRIPTION_2)
            .billToCountryRegionCode(UPDATED_BILL_TO_COUNTRY_REGION_CODE)
            .statusProposal(UPDATED_STATUS_PROPOSAL)
            .startingDate(UPDATED_STARTING_DATE)
            .durationInMonths(UPDATED_DURATION_IN_MONTHS)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .projectManagerMail(UPDATED_PROJECT_MANAGER_MAIL)
            .visaNumber(UPDATED_VISA_NUMBER)
            .jobProgram(UPDATED_JOB_PROGRAM)
            .programManager(UPDATED_PROGRAM_MANAGER)
            .fundingEUN(UPDATED_FUNDING_EUN)
            .fundingRate(UPDATED_FUNDING_RATE)
            .budgetConsortium(UPDATED_BUDGET_CONSORTIUM);

        restJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobInfo))
            )
            .andExpect(status().isOk());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
        JobInfo testJobInfo = jobInfoList.get(jobInfoList.size() - 1);
        assertThat(testJobInfo.getOdataEtag()).isEqualTo(DEFAULT_ODATA_ETAG);
        assertThat(testJobInfo.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testJobInfo.getJobNumber()).isEqualTo(DEFAULT_JOB_NUMBER);
        assertThat(testJobInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobInfo.getDescription2()).isEqualTo(UPDATED_DESCRIPTION_2);
        assertThat(testJobInfo.getBillToCustomerNo()).isEqualTo(DEFAULT_BILL_TO_CUSTOMER_NO);
        assertThat(testJobInfo.getBillToName()).isEqualTo(DEFAULT_BILL_TO_NAME);
        assertThat(testJobInfo.getBillToCountryRegionCode()).isEqualTo(UPDATED_BILL_TO_COUNTRY_REGION_CODE);
        assertThat(testJobInfo.getSellToContact()).isEqualTo(DEFAULT_SELL_TO_CONTACT);
        assertThat(testJobInfo.getYourReference()).isEqualTo(DEFAULT_YOUR_REFERENCE);
        assertThat(testJobInfo.getContractNo()).isEqualTo(DEFAULT_CONTRACT_NO);
        assertThat(testJobInfo.getStatusProposal()).isEqualTo(UPDATED_STATUS_PROPOSAL);
        assertThat(testJobInfo.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testJobInfo.getEndingDate()).isEqualTo(DEFAULT_ENDING_DATE);
        assertThat(testJobInfo.getDurationInMonths()).isEqualTo(UPDATED_DURATION_IN_MONTHS);
        assertThat(testJobInfo.getProjectManager()).isEqualTo(UPDATED_PROJECT_MANAGER);
        assertThat(testJobInfo.getProjectManagerMail()).isEqualTo(UPDATED_PROJECT_MANAGER_MAIL);
        assertThat(testJobInfo.getEunRole()).isEqualTo(DEFAULT_EUN_ROLE);
        assertThat(testJobInfo.getVisaNumber()).isEqualTo(UPDATED_VISA_NUMBER);
        assertThat(testJobInfo.getJobType()).isEqualTo(DEFAULT_JOB_TYPE);
        assertThat(testJobInfo.getJobTypeText()).isEqualTo(DEFAULT_JOB_TYPE_TEXT);
        assertThat(testJobInfo.getJobProgram()).isEqualTo(UPDATED_JOB_PROGRAM);
        assertThat(testJobInfo.getProgramManager()).isEqualTo(UPDATED_PROGRAM_MANAGER);
        assertThat(testJobInfo.getBudgetEUN()).isEqualTo(DEFAULT_BUDGET_EUN);
        assertThat(testJobInfo.getFundingEUN()).isEqualTo(UPDATED_FUNDING_EUN);
        assertThat(testJobInfo.getFundingRate()).isEqualTo(UPDATED_FUNDING_RATE);
        assertThat(testJobInfo.getBudgetConsortium()).isEqualTo(UPDATED_BUDGET_CONSORTIUM);
        assertThat(testJobInfo.getFundingConsortium()).isEqualTo(DEFAULT_FUNDING_CONSORTIUM);
        assertThat(testJobInfo.getOverheadPerc()).isEqualTo(DEFAULT_OVERHEAD_PERC);
    }

    @Test
    @Transactional
    void fullUpdateJobInfoWithPatch() throws Exception {
        // Initialize the database
        jobInfoRepository.saveAndFlush(jobInfo);

        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();

        // Update the jobInfo using partial update
        JobInfo partialUpdatedJobInfo = new JobInfo();
        partialUpdatedJobInfo.setId(jobInfo.getId());

        partialUpdatedJobInfo
            .odataEtag(UPDATED_ODATA_ETAG)
            .externalId(UPDATED_EXTERNAL_ID)
            .jobNumber(UPDATED_JOB_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .description2(UPDATED_DESCRIPTION_2)
            .billToCustomerNo(UPDATED_BILL_TO_CUSTOMER_NO)
            .billToName(UPDATED_BILL_TO_NAME)
            .billToCountryRegionCode(UPDATED_BILL_TO_COUNTRY_REGION_CODE)
            .sellToContact(UPDATED_SELL_TO_CONTACT)
            .yourReference(UPDATED_YOUR_REFERENCE)
            .contractNo(UPDATED_CONTRACT_NO)
            .statusProposal(UPDATED_STATUS_PROPOSAL)
            .startingDate(UPDATED_STARTING_DATE)
            .endingDate(UPDATED_ENDING_DATE)
            .durationInMonths(UPDATED_DURATION_IN_MONTHS)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .projectManagerMail(UPDATED_PROJECT_MANAGER_MAIL)
            .eunRole(UPDATED_EUN_ROLE)
            .visaNumber(UPDATED_VISA_NUMBER)
            .jobType(UPDATED_JOB_TYPE)
            .jobTypeText(UPDATED_JOB_TYPE_TEXT)
            .jobProgram(UPDATED_JOB_PROGRAM)
            .programManager(UPDATED_PROGRAM_MANAGER)
            .budgetEUN(UPDATED_BUDGET_EUN)
            .fundingEUN(UPDATED_FUNDING_EUN)
            .fundingRate(UPDATED_FUNDING_RATE)
            .budgetConsortium(UPDATED_BUDGET_CONSORTIUM)
            .fundingConsortium(UPDATED_FUNDING_CONSORTIUM)
            .overheadPerc(UPDATED_OVERHEAD_PERC);

        restJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobInfo))
            )
            .andExpect(status().isOk());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
        JobInfo testJobInfo = jobInfoList.get(jobInfoList.size() - 1);
        assertThat(testJobInfo.getOdataEtag()).isEqualTo(UPDATED_ODATA_ETAG);
        assertThat(testJobInfo.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testJobInfo.getJobNumber()).isEqualTo(UPDATED_JOB_NUMBER);
        assertThat(testJobInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJobInfo.getDescription2()).isEqualTo(UPDATED_DESCRIPTION_2);
        assertThat(testJobInfo.getBillToCustomerNo()).isEqualTo(UPDATED_BILL_TO_CUSTOMER_NO);
        assertThat(testJobInfo.getBillToName()).isEqualTo(UPDATED_BILL_TO_NAME);
        assertThat(testJobInfo.getBillToCountryRegionCode()).isEqualTo(UPDATED_BILL_TO_COUNTRY_REGION_CODE);
        assertThat(testJobInfo.getSellToContact()).isEqualTo(UPDATED_SELL_TO_CONTACT);
        assertThat(testJobInfo.getYourReference()).isEqualTo(UPDATED_YOUR_REFERENCE);
        assertThat(testJobInfo.getContractNo()).isEqualTo(UPDATED_CONTRACT_NO);
        assertThat(testJobInfo.getStatusProposal()).isEqualTo(UPDATED_STATUS_PROPOSAL);
        assertThat(testJobInfo.getStartingDate()).isEqualTo(UPDATED_STARTING_DATE);
        assertThat(testJobInfo.getEndingDate()).isEqualTo(UPDATED_ENDING_DATE);
        assertThat(testJobInfo.getDurationInMonths()).isEqualTo(UPDATED_DURATION_IN_MONTHS);
        assertThat(testJobInfo.getProjectManager()).isEqualTo(UPDATED_PROJECT_MANAGER);
        assertThat(testJobInfo.getProjectManagerMail()).isEqualTo(UPDATED_PROJECT_MANAGER_MAIL);
        assertThat(testJobInfo.getEunRole()).isEqualTo(UPDATED_EUN_ROLE);
        assertThat(testJobInfo.getVisaNumber()).isEqualTo(UPDATED_VISA_NUMBER);
        assertThat(testJobInfo.getJobType()).isEqualTo(UPDATED_JOB_TYPE);
        assertThat(testJobInfo.getJobTypeText()).isEqualTo(UPDATED_JOB_TYPE_TEXT);
        assertThat(testJobInfo.getJobProgram()).isEqualTo(UPDATED_JOB_PROGRAM);
        assertThat(testJobInfo.getProgramManager()).isEqualTo(UPDATED_PROGRAM_MANAGER);
        assertThat(testJobInfo.getBudgetEUN()).isEqualTo(UPDATED_BUDGET_EUN);
        assertThat(testJobInfo.getFundingEUN()).isEqualTo(UPDATED_FUNDING_EUN);
        assertThat(testJobInfo.getFundingRate()).isEqualTo(UPDATED_FUNDING_RATE);
        assertThat(testJobInfo.getBudgetConsortium()).isEqualTo(UPDATED_BUDGET_CONSORTIUM);
        assertThat(testJobInfo.getFundingConsortium()).isEqualTo(UPDATED_FUNDING_CONSORTIUM);
        assertThat(testJobInfo.getOverheadPerc()).isEqualTo(UPDATED_OVERHEAD_PERC);
    }

    @Test
    @Transactional
    void patchNonExistingJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();
        jobInfo.setId(count.incrementAndGet());

        // Create the JobInfo
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();
        jobInfo.setId(count.incrementAndGet());

        // Create the JobInfo
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobInfo() throws Exception {
        int databaseSizeBeforeUpdate = jobInfoRepository.findAll().size();
        jobInfo.setId(count.incrementAndGet());

        // Create the JobInfo
        JobInfoDTO jobInfoDTO = jobInfoMapper.toDto(jobInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jobInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobInfo in the database
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobInfo() throws Exception {
        // Initialize the database
        jobInfoRepository.saveAndFlush(jobInfo);

        int databaseSizeBeforeDelete = jobInfoRepository.findAll().size();

        // Delete the jobInfo
        restJobInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobInfo> jobInfoList = jobInfoRepository.findAll();
        assertThat(jobInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
