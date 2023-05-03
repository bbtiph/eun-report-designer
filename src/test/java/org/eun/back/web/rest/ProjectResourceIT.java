package org.eun.back.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.eun.back.IntegrationTest;
import org.eun.back.domain.Project;
import org.eun.back.domain.enumeration.ProjectStatus;
import org.eun.back.repository.ProjectRepository;
import org.eun.back.service.dto.ProjectDTO;
import org.eun.back.service.mapper.ProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectResourceIT {

    private static final ProjectStatus DEFAULT_STATUS = ProjectStatus.DRAFT;
    private static final ProjectStatus UPDATED_STATUS = ProjectStatus.REQUESTED;

    private static final String DEFAULT_SUPPORTED_COUNTRY_IDS = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORTED_COUNTRY_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPORTED_LANGUAGE_IDS = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORTED_LANGUAGE_IDS = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_TOTAL_BUDGET = 1L;
    private static final Long UPDATED_TOTAL_BUDGET = 2L;

    private static final Long DEFAULT_TOTAL_FUNDING = 1L;
    private static final Long UPDATED_TOTAL_FUNDING = 2L;

    private static final Long DEFAULT_TOTAL_BUDGET_FOR_EUN = 1L;
    private static final Long UPDATED_TOTAL_BUDGET_FOR_EUN = 2L;

    private static final Long DEFAULT_TOTAL_FUNDING_FOR_EUN = 1L;
    private static final Long UPDATED_TOTAL_FUNDING_FOR_EUN = 2L;

    private static final Long DEFAULT_FUNDING_VALUE = 1L;
    private static final Long UPDATED_FUNDING_VALUE = 2L;

    private static final Long DEFAULT_PERCENTAGE_OF_FUNDING = 1L;
    private static final Long UPDATED_PERCENTAGE_OF_FUNDING = 2L;

    private static final Long DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS = 1L;
    private static final Long UPDATED_PERCENTAGE_OF_INDIRECT_COSTS = 2L;

    private static final Long DEFAULT_PERCENTAGE_OF_FUNDING_FOR_EUN = 1L;
    private static final Long UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN = 2L;

    private static final Long DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN = 1L;
    private static final Long UPDATED_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN = 2L;

    private static final String DEFAULT_FUNDING_EXTRA_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_FUNDING_EXTRA_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAMME = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAMME = "BBBBBBBBBB";

    private static final String DEFAULT_EU_DG = "AAAAAAAAAA";
    private static final String UPDATED_EU_DG = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_OF_EUN = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_OF_EUN = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_TASKS = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_TASKS = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECTED_OUTCOMES = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED_OUTCOMES = "BBBBBBBBBB";

    private static final String DEFAULT_EU_CALL_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_EU_CALL_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_EU_PROJECT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_EU_PROJECT_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_EU_CALL_DEADLINE = "AAAAAAAAAA";
    private static final String UPDATED_EU_CALL_DEADLINE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_MANAGER = "BBBBBBBBBB";

    private static final Integer DEFAULT_REFERENCE_NUMBER_OF_PROJECT = 1;
    private static final Integer UPDATED_REFERENCE_NUMBER_OF_PROJECT = 2;

    private static final String DEFAULT_EUN_TEAM = "AAAAAAAAAA";
    private static final String UPDATED_EUN_TEAM = "BBBBBBBBBB";

    private static final Instant DEFAULT_SYS_CREAT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SYS_CREAT_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SYS_CREAT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SYS_CREAT_IP_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SYS_MODIF_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SYS_MODIF_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SYS_MODIF_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SYS_MODIF_IP_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMockMvc;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .status(DEFAULT_STATUS)
            .supportedCountryIds(DEFAULT_SUPPORTED_COUNTRY_IDS)
            .supportedLanguageIds(DEFAULT_SUPPORTED_LANGUAGE_IDS)
            .name(DEFAULT_NAME)
            .acronym(DEFAULT_ACRONYM)
            .period(DEFAULT_PERIOD)
            .description(DEFAULT_DESCRIPTION)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .contactName(DEFAULT_CONTACT_NAME)
            .totalBudget(DEFAULT_TOTAL_BUDGET)
            .totalFunding(DEFAULT_TOTAL_FUNDING)
            .totalBudgetForEun(DEFAULT_TOTAL_BUDGET_FOR_EUN)
            .totalFundingForEun(DEFAULT_TOTAL_FUNDING_FOR_EUN)
            .fundingValue(DEFAULT_FUNDING_VALUE)
            .percentageOfFunding(DEFAULT_PERCENTAGE_OF_FUNDING)
            .percentageOfIndirectCosts(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS)
            .percentageOfFundingForEun(DEFAULT_PERCENTAGE_OF_FUNDING_FOR_EUN)
            .percentageOfIndirectCostsForEun(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN)
            .fundingExtraComment(DEFAULT_FUNDING_EXTRA_COMMENT)
            .programme(DEFAULT_PROGRAMME)
            .euDg(DEFAULT_EU_DG)
            .roleOfEun(DEFAULT_ROLE_OF_EUN)
            .summary(DEFAULT_SUMMARY)
            .mainTasks(DEFAULT_MAIN_TASKS)
            .expectedOutcomes(DEFAULT_EXPECTED_OUTCOMES)
            .euCallReference(DEFAULT_EU_CALL_REFERENCE)
            .euProjectReference(DEFAULT_EU_PROJECT_REFERENCE)
            .euCallDeadline(DEFAULT_EU_CALL_DEADLINE)
            .projectManager(DEFAULT_PROJECT_MANAGER)
            .referenceNumberOfProject(DEFAULT_REFERENCE_NUMBER_OF_PROJECT)
            .eunTeam(DEFAULT_EUN_TEAM)
            .sysCreatTimestamp(DEFAULT_SYS_CREAT_TIMESTAMP)
            .sysCreatIpAddress(DEFAULT_SYS_CREAT_IP_ADDRESS)
            .sysModifTimestamp(DEFAULT_SYS_MODIF_TIMESTAMP)
            .sysModifIpAddress(DEFAULT_SYS_MODIF_IP_ADDRESS);
        return project;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .status(UPDATED_STATUS)
            .supportedCountryIds(UPDATED_SUPPORTED_COUNTRY_IDS)
            .supportedLanguageIds(UPDATED_SUPPORTED_LANGUAGE_IDS)
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .period(UPDATED_PERIOD)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactName(UPDATED_CONTACT_NAME)
            .totalBudget(UPDATED_TOTAL_BUDGET)
            .totalFunding(UPDATED_TOTAL_FUNDING)
            .totalBudgetForEun(UPDATED_TOTAL_BUDGET_FOR_EUN)
            .totalFundingForEun(UPDATED_TOTAL_FUNDING_FOR_EUN)
            .fundingValue(UPDATED_FUNDING_VALUE)
            .percentageOfFunding(UPDATED_PERCENTAGE_OF_FUNDING)
            .percentageOfIndirectCosts(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS)
            .percentageOfFundingForEun(UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN)
            .percentageOfIndirectCostsForEun(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN)
            .fundingExtraComment(UPDATED_FUNDING_EXTRA_COMMENT)
            .programme(UPDATED_PROGRAMME)
            .euDg(UPDATED_EU_DG)
            .roleOfEun(UPDATED_ROLE_OF_EUN)
            .summary(UPDATED_SUMMARY)
            .mainTasks(UPDATED_MAIN_TASKS)
            .expectedOutcomes(UPDATED_EXPECTED_OUTCOMES)
            .euCallReference(UPDATED_EU_CALL_REFERENCE)
            .euProjectReference(UPDATED_EU_PROJECT_REFERENCE)
            .euCallDeadline(UPDATED_EU_CALL_DEADLINE)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .referenceNumberOfProject(UPDATED_REFERENCE_NUMBER_OF_PROJECT)
            .eunTeam(UPDATED_EUN_TEAM)
            .sysCreatTimestamp(UPDATED_SYS_CREAT_TIMESTAMP)
            .sysCreatIpAddress(UPDATED_SYS_CREAT_IP_ADDRESS)
            .sysModifTimestamp(UPDATED_SYS_MODIF_TIMESTAMP)
            .sysModifIpAddress(UPDATED_SYS_MODIF_IP_ADDRESS);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();
        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProject.getSupportedCountryIds()).isEqualTo(DEFAULT_SUPPORTED_COUNTRY_IDS);
        assertThat(testProject.getSupportedLanguageIds()).isEqualTo(DEFAULT_SUPPORTED_LANGUAGE_IDS);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProject.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testProject.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testProject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProject.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testProject.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testProject.getTotalBudget()).isEqualTo(DEFAULT_TOTAL_BUDGET);
        assertThat(testProject.getTotalFunding()).isEqualTo(DEFAULT_TOTAL_FUNDING);
        assertThat(testProject.getTotalBudgetForEun()).isEqualTo(DEFAULT_TOTAL_BUDGET_FOR_EUN);
        assertThat(testProject.getTotalFundingForEun()).isEqualTo(DEFAULT_TOTAL_FUNDING_FOR_EUN);
        assertThat(testProject.getFundingValue()).isEqualTo(DEFAULT_FUNDING_VALUE);
        assertThat(testProject.getPercentageOfFunding()).isEqualTo(DEFAULT_PERCENTAGE_OF_FUNDING);
        assertThat(testProject.getPercentageOfIndirectCosts()).isEqualTo(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS);
        assertThat(testProject.getPercentageOfFundingForEun()).isEqualTo(DEFAULT_PERCENTAGE_OF_FUNDING_FOR_EUN);
        assertThat(testProject.getPercentageOfIndirectCostsForEun()).isEqualTo(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN);
        assertThat(testProject.getFundingExtraComment()).isEqualTo(DEFAULT_FUNDING_EXTRA_COMMENT);
        assertThat(testProject.getProgramme()).isEqualTo(DEFAULT_PROGRAMME);
        assertThat(testProject.getEuDg()).isEqualTo(DEFAULT_EU_DG);
        assertThat(testProject.getRoleOfEun()).isEqualTo(DEFAULT_ROLE_OF_EUN);
        assertThat(testProject.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testProject.getMainTasks()).isEqualTo(DEFAULT_MAIN_TASKS);
        assertThat(testProject.getExpectedOutcomes()).isEqualTo(DEFAULT_EXPECTED_OUTCOMES);
        assertThat(testProject.getEuCallReference()).isEqualTo(DEFAULT_EU_CALL_REFERENCE);
        assertThat(testProject.getEuProjectReference()).isEqualTo(DEFAULT_EU_PROJECT_REFERENCE);
        assertThat(testProject.getEuCallDeadline()).isEqualTo(DEFAULT_EU_CALL_DEADLINE);
        assertThat(testProject.getProjectManager()).isEqualTo(DEFAULT_PROJECT_MANAGER);
        assertThat(testProject.getReferenceNumberOfProject()).isEqualTo(DEFAULT_REFERENCE_NUMBER_OF_PROJECT);
        assertThat(testProject.getEunTeam()).isEqualTo(DEFAULT_EUN_TEAM);
        assertThat(testProject.getSysCreatTimestamp()).isEqualTo(DEFAULT_SYS_CREAT_TIMESTAMP);
        assertThat(testProject.getSysCreatIpAddress()).isEqualTo(DEFAULT_SYS_CREAT_IP_ADDRESS);
        assertThat(testProject.getSysModifTimestamp()).isEqualTo(DEFAULT_SYS_MODIF_TIMESTAMP);
        assertThat(testProject.getSysModifIpAddress()).isEqualTo(DEFAULT_SYS_MODIF_IP_ADDRESS);
    }

    @Test
    @Transactional
    void createProjectWithExistingId() throws Exception {
        // Create the Project with an existing ID
        project.setId(1L);
        ProjectDTO projectDTO = projectMapper.toDto(project);

        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setStatus(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setName(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setDescription(null);

        // Create the Project, which fails.
        ProjectDTO projectDTO = projectMapper.toDto(project);

        restProjectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].supportedCountryIds").value(hasItem(DEFAULT_SUPPORTED_COUNTRY_IDS)))
            .andExpect(jsonPath("$.[*].supportedLanguageIds").value(hasItem(DEFAULT_SUPPORTED_LANGUAGE_IDS)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].totalBudget").value(hasItem(DEFAULT_TOTAL_BUDGET.intValue())))
            .andExpect(jsonPath("$.[*].totalFunding").value(hasItem(DEFAULT_TOTAL_FUNDING.intValue())))
            .andExpect(jsonPath("$.[*].totalBudgetForEun").value(hasItem(DEFAULT_TOTAL_BUDGET_FOR_EUN.intValue())))
            .andExpect(jsonPath("$.[*].totalFundingForEun").value(hasItem(DEFAULT_TOTAL_FUNDING_FOR_EUN.intValue())))
            .andExpect(jsonPath("$.[*].fundingValue").value(hasItem(DEFAULT_FUNDING_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].percentageOfFunding").value(hasItem(DEFAULT_PERCENTAGE_OF_FUNDING.intValue())))
            .andExpect(jsonPath("$.[*].percentageOfIndirectCosts").value(hasItem(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS.intValue())))
            .andExpect(jsonPath("$.[*].percentageOfFundingForEun").value(hasItem(DEFAULT_PERCENTAGE_OF_FUNDING_FOR_EUN.intValue())))
            .andExpect(
                jsonPath("$.[*].percentageOfIndirectCostsForEun").value(hasItem(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN.intValue()))
            )
            .andExpect(jsonPath("$.[*].fundingExtraComment").value(hasItem(DEFAULT_FUNDING_EXTRA_COMMENT)))
            .andExpect(jsonPath("$.[*].programme").value(hasItem(DEFAULT_PROGRAMME)))
            .andExpect(jsonPath("$.[*].euDg").value(hasItem(DEFAULT_EU_DG)))
            .andExpect(jsonPath("$.[*].roleOfEun").value(hasItem(DEFAULT_ROLE_OF_EUN)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)))
            .andExpect(jsonPath("$.[*].mainTasks").value(hasItem(DEFAULT_MAIN_TASKS)))
            .andExpect(jsonPath("$.[*].expectedOutcomes").value(hasItem(DEFAULT_EXPECTED_OUTCOMES)))
            .andExpect(jsonPath("$.[*].euCallReference").value(hasItem(DEFAULT_EU_CALL_REFERENCE)))
            .andExpect(jsonPath("$.[*].euProjectReference").value(hasItem(DEFAULT_EU_PROJECT_REFERENCE)))
            .andExpect(jsonPath("$.[*].euCallDeadline").value(hasItem(DEFAULT_EU_CALL_DEADLINE)))
            .andExpect(jsonPath("$.[*].projectManager").value(hasItem(DEFAULT_PROJECT_MANAGER)))
            .andExpect(jsonPath("$.[*].referenceNumberOfProject").value(hasItem(DEFAULT_REFERENCE_NUMBER_OF_PROJECT)))
            .andExpect(jsonPath("$.[*].eunTeam").value(hasItem(DEFAULT_EUN_TEAM)))
            .andExpect(jsonPath("$.[*].sysCreatTimestamp").value(hasItem(DEFAULT_SYS_CREAT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].sysCreatIpAddress").value(hasItem(DEFAULT_SYS_CREAT_IP_ADDRESS)))
            .andExpect(jsonPath("$.[*].sysModifTimestamp").value(hasItem(DEFAULT_SYS_MODIF_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].sysModifIpAddress").value(hasItem(DEFAULT_SYS_MODIF_IP_ADDRESS)));
    }

    @Test
    @Transactional
    void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.supportedCountryIds").value(DEFAULT_SUPPORTED_COUNTRY_IDS))
            .andExpect(jsonPath("$.supportedLanguageIds").value(DEFAULT_SUPPORTED_LANGUAGE_IDS))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.totalBudget").value(DEFAULT_TOTAL_BUDGET.intValue()))
            .andExpect(jsonPath("$.totalFunding").value(DEFAULT_TOTAL_FUNDING.intValue()))
            .andExpect(jsonPath("$.totalBudgetForEun").value(DEFAULT_TOTAL_BUDGET_FOR_EUN.intValue()))
            .andExpect(jsonPath("$.totalFundingForEun").value(DEFAULT_TOTAL_FUNDING_FOR_EUN.intValue()))
            .andExpect(jsonPath("$.fundingValue").value(DEFAULT_FUNDING_VALUE.intValue()))
            .andExpect(jsonPath("$.percentageOfFunding").value(DEFAULT_PERCENTAGE_OF_FUNDING.intValue()))
            .andExpect(jsonPath("$.percentageOfIndirectCosts").value(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS.intValue()))
            .andExpect(jsonPath("$.percentageOfFundingForEun").value(DEFAULT_PERCENTAGE_OF_FUNDING_FOR_EUN.intValue()))
            .andExpect(jsonPath("$.percentageOfIndirectCostsForEun").value(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN.intValue()))
            .andExpect(jsonPath("$.fundingExtraComment").value(DEFAULT_FUNDING_EXTRA_COMMENT))
            .andExpect(jsonPath("$.programme").value(DEFAULT_PROGRAMME))
            .andExpect(jsonPath("$.euDg").value(DEFAULT_EU_DG))
            .andExpect(jsonPath("$.roleOfEun").value(DEFAULT_ROLE_OF_EUN))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY))
            .andExpect(jsonPath("$.mainTasks").value(DEFAULT_MAIN_TASKS))
            .andExpect(jsonPath("$.expectedOutcomes").value(DEFAULT_EXPECTED_OUTCOMES))
            .andExpect(jsonPath("$.euCallReference").value(DEFAULT_EU_CALL_REFERENCE))
            .andExpect(jsonPath("$.euProjectReference").value(DEFAULT_EU_PROJECT_REFERENCE))
            .andExpect(jsonPath("$.euCallDeadline").value(DEFAULT_EU_CALL_DEADLINE))
            .andExpect(jsonPath("$.projectManager").value(DEFAULT_PROJECT_MANAGER))
            .andExpect(jsonPath("$.referenceNumberOfProject").value(DEFAULT_REFERENCE_NUMBER_OF_PROJECT))
            .andExpect(jsonPath("$.eunTeam").value(DEFAULT_EUN_TEAM))
            .andExpect(jsonPath("$.sysCreatTimestamp").value(DEFAULT_SYS_CREAT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.sysCreatIpAddress").value(DEFAULT_SYS_CREAT_IP_ADDRESS))
            .andExpect(jsonPath("$.sysModifTimestamp").value(DEFAULT_SYS_MODIF_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.sysModifIpAddress").value(DEFAULT_SYS_MODIF_IP_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .status(UPDATED_STATUS)
            .supportedCountryIds(UPDATED_SUPPORTED_COUNTRY_IDS)
            .supportedLanguageIds(UPDATED_SUPPORTED_LANGUAGE_IDS)
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .period(UPDATED_PERIOD)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactName(UPDATED_CONTACT_NAME)
            .totalBudget(UPDATED_TOTAL_BUDGET)
            .totalFunding(UPDATED_TOTAL_FUNDING)
            .totalBudgetForEun(UPDATED_TOTAL_BUDGET_FOR_EUN)
            .totalFundingForEun(UPDATED_TOTAL_FUNDING_FOR_EUN)
            .fundingValue(UPDATED_FUNDING_VALUE)
            .percentageOfFunding(UPDATED_PERCENTAGE_OF_FUNDING)
            .percentageOfIndirectCosts(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS)
            .percentageOfFundingForEun(UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN)
            .percentageOfIndirectCostsForEun(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN)
            .fundingExtraComment(UPDATED_FUNDING_EXTRA_COMMENT)
            .programme(UPDATED_PROGRAMME)
            .euDg(UPDATED_EU_DG)
            .roleOfEun(UPDATED_ROLE_OF_EUN)
            .summary(UPDATED_SUMMARY)
            .mainTasks(UPDATED_MAIN_TASKS)
            .expectedOutcomes(UPDATED_EXPECTED_OUTCOMES)
            .euCallReference(UPDATED_EU_CALL_REFERENCE)
            .euProjectReference(UPDATED_EU_PROJECT_REFERENCE)
            .euCallDeadline(UPDATED_EU_CALL_DEADLINE)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .referenceNumberOfProject(UPDATED_REFERENCE_NUMBER_OF_PROJECT)
            .eunTeam(UPDATED_EUN_TEAM)
            .sysCreatTimestamp(UPDATED_SYS_CREAT_TIMESTAMP)
            .sysCreatIpAddress(UPDATED_SYS_CREAT_IP_ADDRESS)
            .sysModifTimestamp(UPDATED_SYS_MODIF_TIMESTAMP)
            .sysModifIpAddress(UPDATED_SYS_MODIF_IP_ADDRESS);
        ProjectDTO projectDTO = projectMapper.toDto(updatedProject);

        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProject.getSupportedCountryIds()).isEqualTo(UPDATED_SUPPORTED_COUNTRY_IDS);
        assertThat(testProject.getSupportedLanguageIds()).isEqualTo(UPDATED_SUPPORTED_LANGUAGE_IDS);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testProject.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProject.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testProject.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testProject.getTotalBudget()).isEqualTo(UPDATED_TOTAL_BUDGET);
        assertThat(testProject.getTotalFunding()).isEqualTo(UPDATED_TOTAL_FUNDING);
        assertThat(testProject.getTotalBudgetForEun()).isEqualTo(UPDATED_TOTAL_BUDGET_FOR_EUN);
        assertThat(testProject.getTotalFundingForEun()).isEqualTo(UPDATED_TOTAL_FUNDING_FOR_EUN);
        assertThat(testProject.getFundingValue()).isEqualTo(UPDATED_FUNDING_VALUE);
        assertThat(testProject.getPercentageOfFunding()).isEqualTo(UPDATED_PERCENTAGE_OF_FUNDING);
        assertThat(testProject.getPercentageOfIndirectCosts()).isEqualTo(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS);
        assertThat(testProject.getPercentageOfFundingForEun()).isEqualTo(UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN);
        assertThat(testProject.getPercentageOfIndirectCostsForEun()).isEqualTo(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN);
        assertThat(testProject.getFundingExtraComment()).isEqualTo(UPDATED_FUNDING_EXTRA_COMMENT);
        assertThat(testProject.getProgramme()).isEqualTo(UPDATED_PROGRAMME);
        assertThat(testProject.getEuDg()).isEqualTo(UPDATED_EU_DG);
        assertThat(testProject.getRoleOfEun()).isEqualTo(UPDATED_ROLE_OF_EUN);
        assertThat(testProject.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testProject.getMainTasks()).isEqualTo(UPDATED_MAIN_TASKS);
        assertThat(testProject.getExpectedOutcomes()).isEqualTo(UPDATED_EXPECTED_OUTCOMES);
        assertThat(testProject.getEuCallReference()).isEqualTo(UPDATED_EU_CALL_REFERENCE);
        assertThat(testProject.getEuProjectReference()).isEqualTo(UPDATED_EU_PROJECT_REFERENCE);
        assertThat(testProject.getEuCallDeadline()).isEqualTo(UPDATED_EU_CALL_DEADLINE);
        assertThat(testProject.getProjectManager()).isEqualTo(UPDATED_PROJECT_MANAGER);
        assertThat(testProject.getReferenceNumberOfProject()).isEqualTo(UPDATED_REFERENCE_NUMBER_OF_PROJECT);
        assertThat(testProject.getEunTeam()).isEqualTo(UPDATED_EUN_TEAM);
        assertThat(testProject.getSysCreatTimestamp()).isEqualTo(UPDATED_SYS_CREAT_TIMESTAMP);
        assertThat(testProject.getSysCreatIpAddress()).isEqualTo(UPDATED_SYS_CREAT_IP_ADDRESS);
        assertThat(testProject.getSysModifTimestamp()).isEqualTo(UPDATED_SYS_MODIF_TIMESTAMP);
        assertThat(testProject.getSysModifIpAddress()).isEqualTo(UPDATED_SYS_MODIF_IP_ADDRESS);
    }

    @Test
    @Transactional
    void putNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .status(UPDATED_STATUS)
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactName(UPDATED_CONTACT_NAME)
            .totalBudget(UPDATED_TOTAL_BUDGET)
            .totalFunding(UPDATED_TOTAL_FUNDING)
            .fundingValue(UPDATED_FUNDING_VALUE)
            .percentageOfFunding(UPDATED_PERCENTAGE_OF_FUNDING)
            .percentageOfIndirectCosts(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS)
            .percentageOfFundingForEun(UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN)
            .programme(UPDATED_PROGRAMME)
            .euDg(UPDATED_EU_DG)
            .mainTasks(UPDATED_MAIN_TASKS)
            .euCallReference(UPDATED_EU_CALL_REFERENCE)
            .euCallDeadline(UPDATED_EU_CALL_DEADLINE)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .referenceNumberOfProject(UPDATED_REFERENCE_NUMBER_OF_PROJECT)
            .sysCreatIpAddress(UPDATED_SYS_CREAT_IP_ADDRESS)
            .sysModifTimestamp(UPDATED_SYS_MODIF_TIMESTAMP);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProject.getSupportedCountryIds()).isEqualTo(DEFAULT_SUPPORTED_COUNTRY_IDS);
        assertThat(testProject.getSupportedLanguageIds()).isEqualTo(DEFAULT_SUPPORTED_LANGUAGE_IDS);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testProject.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProject.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testProject.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testProject.getTotalBudget()).isEqualTo(UPDATED_TOTAL_BUDGET);
        assertThat(testProject.getTotalFunding()).isEqualTo(UPDATED_TOTAL_FUNDING);
        assertThat(testProject.getTotalBudgetForEun()).isEqualTo(DEFAULT_TOTAL_BUDGET_FOR_EUN);
        assertThat(testProject.getTotalFundingForEun()).isEqualTo(DEFAULT_TOTAL_FUNDING_FOR_EUN);
        assertThat(testProject.getFundingValue()).isEqualTo(UPDATED_FUNDING_VALUE);
        assertThat(testProject.getPercentageOfFunding()).isEqualTo(UPDATED_PERCENTAGE_OF_FUNDING);
        assertThat(testProject.getPercentageOfIndirectCosts()).isEqualTo(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS);
        assertThat(testProject.getPercentageOfFundingForEun()).isEqualTo(UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN);
        assertThat(testProject.getPercentageOfIndirectCostsForEun()).isEqualTo(DEFAULT_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN);
        assertThat(testProject.getFundingExtraComment()).isEqualTo(DEFAULT_FUNDING_EXTRA_COMMENT);
        assertThat(testProject.getProgramme()).isEqualTo(UPDATED_PROGRAMME);
        assertThat(testProject.getEuDg()).isEqualTo(UPDATED_EU_DG);
        assertThat(testProject.getRoleOfEun()).isEqualTo(DEFAULT_ROLE_OF_EUN);
        assertThat(testProject.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testProject.getMainTasks()).isEqualTo(UPDATED_MAIN_TASKS);
        assertThat(testProject.getExpectedOutcomes()).isEqualTo(DEFAULT_EXPECTED_OUTCOMES);
        assertThat(testProject.getEuCallReference()).isEqualTo(UPDATED_EU_CALL_REFERENCE);
        assertThat(testProject.getEuProjectReference()).isEqualTo(DEFAULT_EU_PROJECT_REFERENCE);
        assertThat(testProject.getEuCallDeadline()).isEqualTo(UPDATED_EU_CALL_DEADLINE);
        assertThat(testProject.getProjectManager()).isEqualTo(UPDATED_PROJECT_MANAGER);
        assertThat(testProject.getReferenceNumberOfProject()).isEqualTo(UPDATED_REFERENCE_NUMBER_OF_PROJECT);
        assertThat(testProject.getEunTeam()).isEqualTo(DEFAULT_EUN_TEAM);
        assertThat(testProject.getSysCreatTimestamp()).isEqualTo(DEFAULT_SYS_CREAT_TIMESTAMP);
        assertThat(testProject.getSysCreatIpAddress()).isEqualTo(UPDATED_SYS_CREAT_IP_ADDRESS);
        assertThat(testProject.getSysModifTimestamp()).isEqualTo(UPDATED_SYS_MODIF_TIMESTAMP);
        assertThat(testProject.getSysModifIpAddress()).isEqualTo(DEFAULT_SYS_MODIF_IP_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateProjectWithPatch() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project using partial update
        Project partialUpdatedProject = new Project();
        partialUpdatedProject.setId(project.getId());

        partialUpdatedProject
            .status(UPDATED_STATUS)
            .supportedCountryIds(UPDATED_SUPPORTED_COUNTRY_IDS)
            .supportedLanguageIds(UPDATED_SUPPORTED_LANGUAGE_IDS)
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .period(UPDATED_PERIOD)
            .description(UPDATED_DESCRIPTION)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactName(UPDATED_CONTACT_NAME)
            .totalBudget(UPDATED_TOTAL_BUDGET)
            .totalFunding(UPDATED_TOTAL_FUNDING)
            .totalBudgetForEun(UPDATED_TOTAL_BUDGET_FOR_EUN)
            .totalFundingForEun(UPDATED_TOTAL_FUNDING_FOR_EUN)
            .fundingValue(UPDATED_FUNDING_VALUE)
            .percentageOfFunding(UPDATED_PERCENTAGE_OF_FUNDING)
            .percentageOfIndirectCosts(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS)
            .percentageOfFundingForEun(UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN)
            .percentageOfIndirectCostsForEun(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN)
            .fundingExtraComment(UPDATED_FUNDING_EXTRA_COMMENT)
            .programme(UPDATED_PROGRAMME)
            .euDg(UPDATED_EU_DG)
            .roleOfEun(UPDATED_ROLE_OF_EUN)
            .summary(UPDATED_SUMMARY)
            .mainTasks(UPDATED_MAIN_TASKS)
            .expectedOutcomes(UPDATED_EXPECTED_OUTCOMES)
            .euCallReference(UPDATED_EU_CALL_REFERENCE)
            .euProjectReference(UPDATED_EU_PROJECT_REFERENCE)
            .euCallDeadline(UPDATED_EU_CALL_DEADLINE)
            .projectManager(UPDATED_PROJECT_MANAGER)
            .referenceNumberOfProject(UPDATED_REFERENCE_NUMBER_OF_PROJECT)
            .eunTeam(UPDATED_EUN_TEAM)
            .sysCreatTimestamp(UPDATED_SYS_CREAT_TIMESTAMP)
            .sysCreatIpAddress(UPDATED_SYS_CREAT_IP_ADDRESS)
            .sysModifTimestamp(UPDATED_SYS_MODIF_TIMESTAMP)
            .sysModifIpAddress(UPDATED_SYS_MODIF_IP_ADDRESS);

        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProject))
            )
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProject.getSupportedCountryIds()).isEqualTo(UPDATED_SUPPORTED_COUNTRY_IDS);
        assertThat(testProject.getSupportedLanguageIds()).isEqualTo(UPDATED_SUPPORTED_LANGUAGE_IDS);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProject.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testProject.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testProject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProject.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testProject.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testProject.getTotalBudget()).isEqualTo(UPDATED_TOTAL_BUDGET);
        assertThat(testProject.getTotalFunding()).isEqualTo(UPDATED_TOTAL_FUNDING);
        assertThat(testProject.getTotalBudgetForEun()).isEqualTo(UPDATED_TOTAL_BUDGET_FOR_EUN);
        assertThat(testProject.getTotalFundingForEun()).isEqualTo(UPDATED_TOTAL_FUNDING_FOR_EUN);
        assertThat(testProject.getFundingValue()).isEqualTo(UPDATED_FUNDING_VALUE);
        assertThat(testProject.getPercentageOfFunding()).isEqualTo(UPDATED_PERCENTAGE_OF_FUNDING);
        assertThat(testProject.getPercentageOfIndirectCosts()).isEqualTo(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS);
        assertThat(testProject.getPercentageOfFundingForEun()).isEqualTo(UPDATED_PERCENTAGE_OF_FUNDING_FOR_EUN);
        assertThat(testProject.getPercentageOfIndirectCostsForEun()).isEqualTo(UPDATED_PERCENTAGE_OF_INDIRECT_COSTS_FOR_EUN);
        assertThat(testProject.getFundingExtraComment()).isEqualTo(UPDATED_FUNDING_EXTRA_COMMENT);
        assertThat(testProject.getProgramme()).isEqualTo(UPDATED_PROGRAMME);
        assertThat(testProject.getEuDg()).isEqualTo(UPDATED_EU_DG);
        assertThat(testProject.getRoleOfEun()).isEqualTo(UPDATED_ROLE_OF_EUN);
        assertThat(testProject.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testProject.getMainTasks()).isEqualTo(UPDATED_MAIN_TASKS);
        assertThat(testProject.getExpectedOutcomes()).isEqualTo(UPDATED_EXPECTED_OUTCOMES);
        assertThat(testProject.getEuCallReference()).isEqualTo(UPDATED_EU_CALL_REFERENCE);
        assertThat(testProject.getEuProjectReference()).isEqualTo(UPDATED_EU_PROJECT_REFERENCE);
        assertThat(testProject.getEuCallDeadline()).isEqualTo(UPDATED_EU_CALL_DEADLINE);
        assertThat(testProject.getProjectManager()).isEqualTo(UPDATED_PROJECT_MANAGER);
        assertThat(testProject.getReferenceNumberOfProject()).isEqualTo(UPDATED_REFERENCE_NUMBER_OF_PROJECT);
        assertThat(testProject.getEunTeam()).isEqualTo(UPDATED_EUN_TEAM);
        assertThat(testProject.getSysCreatTimestamp()).isEqualTo(UPDATED_SYS_CREAT_TIMESTAMP);
        assertThat(testProject.getSysCreatIpAddress()).isEqualTo(UPDATED_SYS_CREAT_IP_ADDRESS);
        assertThat(testProject.getSysModifTimestamp()).isEqualTo(UPDATED_SYS_MODIF_TIMESTAMP);
        assertThat(testProject.getSysModifIpAddress()).isEqualTo(UPDATED_SYS_MODIF_IP_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();
        project.setId(count.incrementAndGet());

        // Create the Project
        ProjectDTO projectDTO = projectMapper.toDto(project);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, project.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
