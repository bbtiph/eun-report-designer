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
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.repository.WorkingGroupReferencesRepository;
import org.eun.back.service.criteria.WorkingGroupReferencesCriteria;
import org.eun.back.service.dto.WorkingGroupReferencesDTO;
import org.eun.back.service.mapper.WorkingGroupReferencesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorkingGroupReferencesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkingGroupReferencesResourceIT {

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_REPRESENTATIVE_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REPRESENTATIVE_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_REPRESENTATIVE_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REPRESENTATIVE_POSITION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COUNTRY_REPRESENTATIVE_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COUNTRY_REPRESENTATIVE_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COUNTRY_REPRESENTATIVE_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COUNTRY_REPRESENTATIVE_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EUN_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EUN_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EUN_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EUN_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_SHEET_NUM = 1L;
    private static final Long UPDATED_SHEET_NUM = 2L;
    private static final Long SMALLER_SHEET_NUM = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/working-group-references";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkingGroupReferencesRepository workingGroupReferencesRepository;

    @Autowired
    private WorkingGroupReferencesMapper workingGroupReferencesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkingGroupReferencesMockMvc;

    private WorkingGroupReferences workingGroupReferences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingGroupReferences createEntity(EntityManager em) {
        WorkingGroupReferences workingGroupReferences = new WorkingGroupReferences()
            .countryCode(DEFAULT_COUNTRY_CODE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .countryRepresentativeFirstName(DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME)
            .countryRepresentativeLastName(DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME)
            .countryRepresentativeMail(DEFAULT_COUNTRY_REPRESENTATIVE_MAIL)
            .countryRepresentativePosition(DEFAULT_COUNTRY_REPRESENTATIVE_POSITION)
            .countryRepresentativeStartDate(DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE)
            .countryRepresentativeEndDate(DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE)
            .countryRepresentativeMinistry(DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY)
            .countryRepresentativeDepartment(DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT)
            .contactEunFirstName(DEFAULT_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(DEFAULT_CONTACT_EUN_LAST_NAME)
            .type(DEFAULT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .sheetNum(DEFAULT_SHEET_NUM);
        return workingGroupReferences;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingGroupReferences createUpdatedEntity(EntityManager em) {
        WorkingGroupReferences workingGroupReferences = new WorkingGroupReferences()
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .countryRepresentativeFirstName(UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME)
            .countryRepresentativeLastName(UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME)
            .countryRepresentativeMail(UPDATED_COUNTRY_REPRESENTATIVE_MAIL)
            .countryRepresentativePosition(UPDATED_COUNTRY_REPRESENTATIVE_POSITION)
            .countryRepresentativeStartDate(UPDATED_COUNTRY_REPRESENTATIVE_START_DATE)
            .countryRepresentativeEndDate(UPDATED_COUNTRY_REPRESENTATIVE_END_DATE)
            .countryRepresentativeMinistry(UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY)
            .countryRepresentativeDepartment(UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT)
            .contactEunFirstName(UPDATED_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(UPDATED_CONTACT_EUN_LAST_NAME)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .sheetNum(UPDATED_SHEET_NUM);
        return workingGroupReferences;
    }

    @BeforeEach
    public void initTest() {
        workingGroupReferences = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkingGroupReferences() throws Exception {
        int databaseSizeBeforeCreate = workingGroupReferencesRepository.findAll().size();
        // Create the WorkingGroupReferences
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);
        restWorkingGroupReferencesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingGroupReferences testWorkingGroupReferences = workingGroupReferencesList.get(workingGroupReferencesList.size() - 1);
        assertThat(testWorkingGroupReferences.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testWorkingGroupReferences.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeFirstName()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeLastName()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMail()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_MAIL);
        assertThat(testWorkingGroupReferences.getCountryRepresentativePosition()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_POSITION);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeStartDate()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeEndDate()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMinistry()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeDepartment()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT);
        assertThat(testWorkingGroupReferences.getContactEunFirstName()).isEqualTo(DEFAULT_CONTACT_EUN_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getContactEunLastName()).isEqualTo(DEFAULT_CONTACT_EUN_LAST_NAME);
        assertThat(testWorkingGroupReferences.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWorkingGroupReferences.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testWorkingGroupReferences.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkingGroupReferences.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWorkingGroupReferences.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWorkingGroupReferences.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testWorkingGroupReferences.getSheetNum()).isEqualTo(DEFAULT_SHEET_NUM);
    }

    @Test
    @Transactional
    void createWorkingGroupReferencesWithExistingId() throws Exception {
        // Create the WorkingGroupReferences with an existing ID
        workingGroupReferences.setId(1L);
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);

        int databaseSizeBeforeCreate = workingGroupReferencesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingGroupReferencesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferences() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList
        restWorkingGroupReferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingGroupReferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryRepresentativeFirstName").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].countryRepresentativeLastName").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME)))
            .andExpect(jsonPath("$.[*].countryRepresentativeMail").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_MAIL)))
            .andExpect(jsonPath("$.[*].countryRepresentativePosition").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_POSITION)))
            .andExpect(
                jsonPath("$.[*].countryRepresentativeStartDate").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE.toString()))
            )
            .andExpect(jsonPath("$.[*].countryRepresentativeEndDate").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].countryRepresentativeMinistry").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY)))
            .andExpect(jsonPath("$.[*].countryRepresentativeDepartment").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].contactEunFirstName").value(hasItem(DEFAULT_CONTACT_EUN_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].contactEunLastName").value(hasItem(DEFAULT_CONTACT_EUN_LAST_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sheetNum").value(hasItem(DEFAULT_SHEET_NUM.intValue())));
    }

    @Test
    @Transactional
    void getWorkingGroupReferences() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get the workingGroupReferences
        restWorkingGroupReferencesMockMvc
            .perform(get(ENTITY_API_URL_ID, workingGroupReferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workingGroupReferences.getId().intValue()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.countryRepresentativeFirstName").value(DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME))
            .andExpect(jsonPath("$.countryRepresentativeLastName").value(DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME))
            .andExpect(jsonPath("$.countryRepresentativeMail").value(DEFAULT_COUNTRY_REPRESENTATIVE_MAIL))
            .andExpect(jsonPath("$.countryRepresentativePosition").value(DEFAULT_COUNTRY_REPRESENTATIVE_POSITION))
            .andExpect(jsonPath("$.countryRepresentativeStartDate").value(DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE.toString()))
            .andExpect(jsonPath("$.countryRepresentativeEndDate").value(DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE.toString()))
            .andExpect(jsonPath("$.countryRepresentativeMinistry").value(DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY))
            .andExpect(jsonPath("$.countryRepresentativeDepartment").value(DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT))
            .andExpect(jsonPath("$.contactEunFirstName").value(DEFAULT_CONTACT_EUN_FIRST_NAME))
            .andExpect(jsonPath("$.contactEunLastName").value(DEFAULT_CONTACT_EUN_LAST_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.sheetNum").value(DEFAULT_SHEET_NUM.intValue()));
    }

    @Test
    @Transactional
    void getWorkingGroupReferencesByIdFiltering() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        Long id = workingGroupReferences.getId();

        defaultWorkingGroupReferencesShouldBeFound("id.equals=" + id);
        defaultWorkingGroupReferencesShouldNotBeFound("id.notEquals=" + id);

        defaultWorkingGroupReferencesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkingGroupReferencesShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkingGroupReferencesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkingGroupReferencesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryCode equals to DEFAULT_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldBeFound("countryCode.equals=" + DEFAULT_COUNTRY_CODE);

        // Get all the workingGroupReferencesList where countryCode equals to UPDATED_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldNotBeFound("countryCode.equals=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryCode in DEFAULT_COUNTRY_CODE or UPDATED_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldBeFound("countryCode.in=" + DEFAULT_COUNTRY_CODE + "," + UPDATED_COUNTRY_CODE);

        // Get all the workingGroupReferencesList where countryCode equals to UPDATED_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldNotBeFound("countryCode.in=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryCode is not null
        defaultWorkingGroupReferencesShouldBeFound("countryCode.specified=true");

        // Get all the workingGroupReferencesList where countryCode is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryCode.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryCodeContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryCode contains DEFAULT_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldBeFound("countryCode.contains=" + DEFAULT_COUNTRY_CODE);

        // Get all the workingGroupReferencesList where countryCode contains UPDATED_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldNotBeFound("countryCode.contains=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryCodeNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryCode does not contain DEFAULT_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldNotBeFound("countryCode.doesNotContain=" + DEFAULT_COUNTRY_CODE);

        // Get all the workingGroupReferencesList where countryCode does not contain UPDATED_COUNTRY_CODE
        defaultWorkingGroupReferencesShouldBeFound("countryCode.doesNotContain=" + UPDATED_COUNTRY_CODE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryName equals to DEFAULT_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryName.equals=" + DEFAULT_COUNTRY_NAME);

        // Get all the workingGroupReferencesList where countryName equals to UPDATED_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryName.equals=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryNameIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryName in DEFAULT_COUNTRY_NAME or UPDATED_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryName.in=" + DEFAULT_COUNTRY_NAME + "," + UPDATED_COUNTRY_NAME);

        // Get all the workingGroupReferencesList where countryName equals to UPDATED_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryName.in=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryName is not null
        defaultWorkingGroupReferencesShouldBeFound("countryName.specified=true");

        // Get all the workingGroupReferencesList where countryName is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryNameContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryName contains DEFAULT_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryName.contains=" + DEFAULT_COUNTRY_NAME);

        // Get all the workingGroupReferencesList where countryName contains UPDATED_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryName.contains=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryNameNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryName does not contain DEFAULT_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryName.doesNotContain=" + DEFAULT_COUNTRY_NAME);

        // Get all the workingGroupReferencesList where countryName does not contain UPDATED_COUNTRY_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryName.doesNotContain=" + UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName equals to DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeFirstName.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME);

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName equals to UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeFirstName.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName in DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME or UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeFirstName.in=" +
            DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME +
            "," +
            UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        );

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName equals to UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeFirstName.in=" + UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeFirstName.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeFirstName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeFirstNameContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName contains DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeFirstName.contains=" + DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME);

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName contains UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeFirstName.contains=" + UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName does not contain DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeFirstName.doesNotContain=" + DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME
        );

        // Get all the workingGroupReferencesList where countryRepresentativeFirstName does not contain UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeFirstName.doesNotContain=" + UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeLastName equals to DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeLastName.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME);

        // Get all the workingGroupReferencesList where countryRepresentativeLastName equals to UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeLastName.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeLastName in DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME or UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeLastName.in=" + DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME + "," + UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME
        );

        // Get all the workingGroupReferencesList where countryRepresentativeLastName equals to UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeLastName.in=" + UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeLastName is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeLastName.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativeLastName is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeLastName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeLastNameContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeLastName contains DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeLastName.contains=" + DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME);

        // Get all the workingGroupReferencesList where countryRepresentativeLastName contains UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeLastName.contains=" + UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeLastName does not contain DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeLastName.doesNotContain=" + DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME
        );

        // Get all the workingGroupReferencesList where countryRepresentativeLastName does not contain UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeLastName.doesNotContain=" + UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMailIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMail equals to DEFAULT_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeMail.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_MAIL);

        // Get all the workingGroupReferencesList where countryRepresentativeMail equals to UPDATED_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMail.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_MAIL);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMailIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMail in DEFAULT_COUNTRY_REPRESENTATIVE_MAIL or UPDATED_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeMail.in=" + DEFAULT_COUNTRY_REPRESENTATIVE_MAIL + "," + UPDATED_COUNTRY_REPRESENTATIVE_MAIL
        );

        // Get all the workingGroupReferencesList where countryRepresentativeMail equals to UPDATED_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMail.in=" + UPDATED_COUNTRY_REPRESENTATIVE_MAIL);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMail is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeMail.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativeMail is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMail.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMailContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMail contains DEFAULT_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeMail.contains=" + DEFAULT_COUNTRY_REPRESENTATIVE_MAIL);

        // Get all the workingGroupReferencesList where countryRepresentativeMail contains UPDATED_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMail.contains=" + UPDATED_COUNTRY_REPRESENTATIVE_MAIL);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMailNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMail does not contain DEFAULT_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMail.doesNotContain=" + DEFAULT_COUNTRY_REPRESENTATIVE_MAIL);

        // Get all the workingGroupReferencesList where countryRepresentativeMail does not contain UPDATED_COUNTRY_REPRESENTATIVE_MAIL
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeMail.doesNotContain=" + UPDATED_COUNTRY_REPRESENTATIVE_MAIL);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativePositionIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativePosition equals to DEFAULT_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativePosition.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_POSITION);

        // Get all the workingGroupReferencesList where countryRepresentativePosition equals to UPDATED_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativePosition.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_POSITION);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativePositionIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativePosition in DEFAULT_COUNTRY_REPRESENTATIVE_POSITION or UPDATED_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativePosition.in=" + DEFAULT_COUNTRY_REPRESENTATIVE_POSITION + "," + UPDATED_COUNTRY_REPRESENTATIVE_POSITION
        );

        // Get all the workingGroupReferencesList where countryRepresentativePosition equals to UPDATED_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativePosition.in=" + UPDATED_COUNTRY_REPRESENTATIVE_POSITION);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativePositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativePosition is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativePosition.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativePosition is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativePosition.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativePositionContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativePosition contains DEFAULT_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativePosition.contains=" + DEFAULT_COUNTRY_REPRESENTATIVE_POSITION);

        // Get all the workingGroupReferencesList where countryRepresentativePosition contains UPDATED_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativePosition.contains=" + UPDATED_COUNTRY_REPRESENTATIVE_POSITION);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativePositionNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativePosition does not contain DEFAULT_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativePosition.doesNotContain=" + DEFAULT_COUNTRY_REPRESENTATIVE_POSITION
        );

        // Get all the workingGroupReferencesList where countryRepresentativePosition does not contain UPDATED_COUNTRY_REPRESENTATIVE_POSITION
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativePosition.doesNotContain=" + UPDATED_COUNTRY_REPRESENTATIVE_POSITION
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate equals to DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeStartDate.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate equals to UPDATED_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeStartDate.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate in DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE or UPDATED_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeStartDate.in=" +
            DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE +
            "," +
            UPDATED_COUNTRY_REPRESENTATIVE_START_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate equals to UPDATED_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeStartDate.in=" + UPDATED_COUNTRY_REPRESENTATIVE_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeStartDate.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is greater than or equal to DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeStartDate.greaterThanOrEqual=" + DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is greater than or equal to UPDATED_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeStartDate.greaterThanOrEqual=" + UPDATED_COUNTRY_REPRESENTATIVE_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is less than or equal to DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeStartDate.lessThanOrEqual=" + DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is less than or equal to SMALLER_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeStartDate.lessThanOrEqual=" + SMALLER_COUNTRY_REPRESENTATIVE_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is less than DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeStartDate.lessThan=" + DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is less than UPDATED_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeStartDate.lessThan=" + UPDATED_COUNTRY_REPRESENTATIVE_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is greater than DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeStartDate.greaterThan=" + DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeStartDate is greater than SMALLER_COUNTRY_REPRESENTATIVE_START_DATE
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeStartDate.greaterThan=" + SMALLER_COUNTRY_REPRESENTATIVE_START_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate equals to DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeEndDate.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate equals to UPDATED_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeEndDate.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate in DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE or UPDATED_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeEndDate.in=" + DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE + "," + UPDATED_COUNTRY_REPRESENTATIVE_END_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate equals to UPDATED_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeEndDate.in=" + UPDATED_COUNTRY_REPRESENTATIVE_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeEndDate.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeEndDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is greater than or equal to DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeEndDate.greaterThanOrEqual=" + DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is greater than or equal to UPDATED_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeEndDate.greaterThanOrEqual=" + UPDATED_COUNTRY_REPRESENTATIVE_END_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is less than or equal to DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeEndDate.lessThanOrEqual=" + DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is less than or equal to SMALLER_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeEndDate.lessThanOrEqual=" + SMALLER_COUNTRY_REPRESENTATIVE_END_DATE
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is less than DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeEndDate.lessThan=" + DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is less than UPDATED_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeEndDate.lessThan=" + UPDATED_COUNTRY_REPRESENTATIVE_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is greater than DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeEndDate.greaterThan=" + DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE
        );

        // Get all the workingGroupReferencesList where countryRepresentativeEndDate is greater than SMALLER_COUNTRY_REPRESENTATIVE_END_DATE
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeEndDate.greaterThan=" + SMALLER_COUNTRY_REPRESENTATIVE_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMinistryIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry equals to DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeMinistry.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY);

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry equals to UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMinistry.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMinistryIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry in DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY or UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeMinistry.in=" + DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY + "," + UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY
        );

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry equals to UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMinistry.in=" + UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMinistryIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeMinistry.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMinistry.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMinistryContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry contains DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeMinistry.contains=" + DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY);

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry contains UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeMinistry.contains=" + UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeMinistryNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry does not contain DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeMinistry.doesNotContain=" + DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY
        );

        // Get all the workingGroupReferencesList where countryRepresentativeMinistry does not contain UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeMinistry.doesNotContain=" + UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment equals to DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeDepartment.equals=" + DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT);

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment equals to UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeDepartment.equals=" + UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment in DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT or UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeDepartment.in=" +
            DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT +
            "," +
            UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        );

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment equals to UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeDepartment.in=" + UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment is not null
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeDepartment.specified=true");

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment is null
        defaultWorkingGroupReferencesShouldNotBeFound("countryRepresentativeDepartment.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeDepartmentContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment contains DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldBeFound("countryRepresentativeDepartment.contains=" + DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT);

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment contains UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeDepartment.contains=" + UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCountryRepresentativeDepartmentNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment does not contain DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldNotBeFound(
            "countryRepresentativeDepartment.doesNotContain=" + DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT
        );

        // Get all the workingGroupReferencesList where countryRepresentativeDepartment does not contain UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        defaultWorkingGroupReferencesShouldBeFound(
            "countryRepresentativeDepartment.doesNotContain=" + UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT
        );
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunFirstName equals to DEFAULT_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound("contactEunFirstName.equals=" + DEFAULT_CONTACT_EUN_FIRST_NAME);

        // Get all the workingGroupReferencesList where contactEunFirstName equals to UPDATED_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunFirstName.equals=" + UPDATED_CONTACT_EUN_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunFirstName in DEFAULT_CONTACT_EUN_FIRST_NAME or UPDATED_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound(
            "contactEunFirstName.in=" + DEFAULT_CONTACT_EUN_FIRST_NAME + "," + UPDATED_CONTACT_EUN_FIRST_NAME
        );

        // Get all the workingGroupReferencesList where contactEunFirstName equals to UPDATED_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunFirstName.in=" + UPDATED_CONTACT_EUN_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunFirstName is not null
        defaultWorkingGroupReferencesShouldBeFound("contactEunFirstName.specified=true");

        // Get all the workingGroupReferencesList where contactEunFirstName is null
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunFirstName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunFirstNameContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunFirstName contains DEFAULT_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound("contactEunFirstName.contains=" + DEFAULT_CONTACT_EUN_FIRST_NAME);

        // Get all the workingGroupReferencesList where contactEunFirstName contains UPDATED_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunFirstName.contains=" + UPDATED_CONTACT_EUN_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunFirstName does not contain DEFAULT_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunFirstName.doesNotContain=" + DEFAULT_CONTACT_EUN_FIRST_NAME);

        // Get all the workingGroupReferencesList where contactEunFirstName does not contain UPDATED_CONTACT_EUN_FIRST_NAME
        defaultWorkingGroupReferencesShouldBeFound("contactEunFirstName.doesNotContain=" + UPDATED_CONTACT_EUN_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunLastName equals to DEFAULT_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound("contactEunLastName.equals=" + DEFAULT_CONTACT_EUN_LAST_NAME);

        // Get all the workingGroupReferencesList where contactEunLastName equals to UPDATED_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunLastName.equals=" + UPDATED_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunLastName in DEFAULT_CONTACT_EUN_LAST_NAME or UPDATED_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound(
            "contactEunLastName.in=" + DEFAULT_CONTACT_EUN_LAST_NAME + "," + UPDATED_CONTACT_EUN_LAST_NAME
        );

        // Get all the workingGroupReferencesList where contactEunLastName equals to UPDATED_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunLastName.in=" + UPDATED_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunLastName is not null
        defaultWorkingGroupReferencesShouldBeFound("contactEunLastName.specified=true");

        // Get all the workingGroupReferencesList where contactEunLastName is null
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunLastName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunLastNameContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunLastName contains DEFAULT_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound("contactEunLastName.contains=" + DEFAULT_CONTACT_EUN_LAST_NAME);

        // Get all the workingGroupReferencesList where contactEunLastName contains UPDATED_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunLastName.contains=" + UPDATED_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByContactEunLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where contactEunLastName does not contain DEFAULT_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldNotBeFound("contactEunLastName.doesNotContain=" + DEFAULT_CONTACT_EUN_LAST_NAME);

        // Get all the workingGroupReferencesList where contactEunLastName does not contain UPDATED_CONTACT_EUN_LAST_NAME
        defaultWorkingGroupReferencesShouldBeFound("contactEunLastName.doesNotContain=" + UPDATED_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where type equals to DEFAULT_TYPE
        defaultWorkingGroupReferencesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the workingGroupReferencesList where type equals to UPDATED_TYPE
        defaultWorkingGroupReferencesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultWorkingGroupReferencesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the workingGroupReferencesList where type equals to UPDATED_TYPE
        defaultWorkingGroupReferencesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where type is not null
        defaultWorkingGroupReferencesShouldBeFound("type.specified=true");

        // Get all the workingGroupReferencesList where type is null
        defaultWorkingGroupReferencesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByTypeContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where type contains DEFAULT_TYPE
        defaultWorkingGroupReferencesShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the workingGroupReferencesList where type contains UPDATED_TYPE
        defaultWorkingGroupReferencesShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where type does not contain DEFAULT_TYPE
        defaultWorkingGroupReferencesShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the workingGroupReferencesList where type does not contain UPDATED_TYPE
        defaultWorkingGroupReferencesShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where isActive equals to DEFAULT_IS_ACTIVE
        defaultWorkingGroupReferencesShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the workingGroupReferencesList where isActive equals to UPDATED_IS_ACTIVE
        defaultWorkingGroupReferencesShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultWorkingGroupReferencesShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the workingGroupReferencesList where isActive equals to UPDATED_IS_ACTIVE
        defaultWorkingGroupReferencesShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where isActive is not null
        defaultWorkingGroupReferencesShouldBeFound("isActive.specified=true");

        // Get all the workingGroupReferencesList where isActive is null
        defaultWorkingGroupReferencesShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdBy equals to DEFAULT_CREATED_BY
        defaultWorkingGroupReferencesShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the workingGroupReferencesList where createdBy equals to UPDATED_CREATED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultWorkingGroupReferencesShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the workingGroupReferencesList where createdBy equals to UPDATED_CREATED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdBy is not null
        defaultWorkingGroupReferencesShouldBeFound("createdBy.specified=true");

        // Get all the workingGroupReferencesList where createdBy is null
        defaultWorkingGroupReferencesShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdBy contains DEFAULT_CREATED_BY
        defaultWorkingGroupReferencesShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the workingGroupReferencesList where createdBy contains UPDATED_CREATED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdBy does not contain DEFAULT_CREATED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the workingGroupReferencesList where createdBy does not contain UPDATED_CREATED_BY
        defaultWorkingGroupReferencesShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workingGroupReferencesList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the workingGroupReferencesList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedBy is not null
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedBy.specified=true");

        // Get all the workingGroupReferencesList where lastModifiedBy is null
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workingGroupReferencesList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workingGroupReferencesList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdDate equals to DEFAULT_CREATED_DATE
        defaultWorkingGroupReferencesShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the workingGroupReferencesList where createdDate equals to UPDATED_CREATED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultWorkingGroupReferencesShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the workingGroupReferencesList where createdDate equals to UPDATED_CREATED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdDate is not null
        defaultWorkingGroupReferencesShouldBeFound("createdDate.specified=true");

        // Get all the workingGroupReferencesList where createdDate is null
        defaultWorkingGroupReferencesShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultWorkingGroupReferencesShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the workingGroupReferencesList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultWorkingGroupReferencesShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the workingGroupReferencesList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdDate is less than DEFAULT_CREATED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the workingGroupReferencesList where createdDate is less than UPDATED_CREATED_DATE
        defaultWorkingGroupReferencesShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the workingGroupReferencesList where createdDate is greater than SMALLER_CREATED_DATE
        defaultWorkingGroupReferencesShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the workingGroupReferencesList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the workingGroupReferencesList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedDate is not null
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedDate.specified=true");

        // Get all the workingGroupReferencesList where lastModifiedDate is null
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedDate is greater than or equal to DEFAULT_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedDate.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the workingGroupReferencesList where lastModifiedDate is greater than or equal to UPDATED_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedDate.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedDate is less than or equal to DEFAULT_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedDate.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the workingGroupReferencesList where lastModifiedDate is less than or equal to SMALLER_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedDate.lessThanOrEqual=" + SMALLER_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedDate is less than DEFAULT_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedDate.lessThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the workingGroupReferencesList where lastModifiedDate is less than UPDATED_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedDate.lessThan=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesByLastModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where lastModifiedDate is greater than DEFAULT_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldNotBeFound("lastModifiedDate.greaterThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the workingGroupReferencesList where lastModifiedDate is greater than SMALLER_LAST_MODIFIED_DATE
        defaultWorkingGroupReferencesShouldBeFound("lastModifiedDate.greaterThan=" + SMALLER_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesBySheetNumIsEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where sheetNum equals to DEFAULT_SHEET_NUM
        defaultWorkingGroupReferencesShouldBeFound("sheetNum.equals=" + DEFAULT_SHEET_NUM);

        // Get all the workingGroupReferencesList where sheetNum equals to UPDATED_SHEET_NUM
        defaultWorkingGroupReferencesShouldNotBeFound("sheetNum.equals=" + UPDATED_SHEET_NUM);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesBySheetNumIsInShouldWork() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where sheetNum in DEFAULT_SHEET_NUM or UPDATED_SHEET_NUM
        defaultWorkingGroupReferencesShouldBeFound("sheetNum.in=" + DEFAULT_SHEET_NUM + "," + UPDATED_SHEET_NUM);

        // Get all the workingGroupReferencesList where sheetNum equals to UPDATED_SHEET_NUM
        defaultWorkingGroupReferencesShouldNotBeFound("sheetNum.in=" + UPDATED_SHEET_NUM);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesBySheetNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where sheetNum is not null
        defaultWorkingGroupReferencesShouldBeFound("sheetNum.specified=true");

        // Get all the workingGroupReferencesList where sheetNum is null
        defaultWorkingGroupReferencesShouldNotBeFound("sheetNum.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesBySheetNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where sheetNum is greater than or equal to DEFAULT_SHEET_NUM
        defaultWorkingGroupReferencesShouldBeFound("sheetNum.greaterThanOrEqual=" + DEFAULT_SHEET_NUM);

        // Get all the workingGroupReferencesList where sheetNum is greater than or equal to UPDATED_SHEET_NUM
        defaultWorkingGroupReferencesShouldNotBeFound("sheetNum.greaterThanOrEqual=" + UPDATED_SHEET_NUM);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesBySheetNumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where sheetNum is less than or equal to DEFAULT_SHEET_NUM
        defaultWorkingGroupReferencesShouldBeFound("sheetNum.lessThanOrEqual=" + DEFAULT_SHEET_NUM);

        // Get all the workingGroupReferencesList where sheetNum is less than or equal to SMALLER_SHEET_NUM
        defaultWorkingGroupReferencesShouldNotBeFound("sheetNum.lessThanOrEqual=" + SMALLER_SHEET_NUM);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesBySheetNumIsLessThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where sheetNum is less than DEFAULT_SHEET_NUM
        defaultWorkingGroupReferencesShouldNotBeFound("sheetNum.lessThan=" + DEFAULT_SHEET_NUM);

        // Get all the workingGroupReferencesList where sheetNum is less than UPDATED_SHEET_NUM
        defaultWorkingGroupReferencesShouldBeFound("sheetNum.lessThan=" + UPDATED_SHEET_NUM);
    }

    @Test
    @Transactional
    void getAllWorkingGroupReferencesBySheetNumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        // Get all the workingGroupReferencesList where sheetNum is greater than DEFAULT_SHEET_NUM
        defaultWorkingGroupReferencesShouldNotBeFound("sheetNum.greaterThan=" + DEFAULT_SHEET_NUM);

        // Get all the workingGroupReferencesList where sheetNum is greater than SMALLER_SHEET_NUM
        defaultWorkingGroupReferencesShouldBeFound("sheetNum.greaterThan=" + SMALLER_SHEET_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkingGroupReferencesShouldBeFound(String filter) throws Exception {
        restWorkingGroupReferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingGroupReferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].countryRepresentativeFirstName").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].countryRepresentativeLastName").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_LAST_NAME)))
            .andExpect(jsonPath("$.[*].countryRepresentativeMail").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_MAIL)))
            .andExpect(jsonPath("$.[*].countryRepresentativePosition").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_POSITION)))
            .andExpect(
                jsonPath("$.[*].countryRepresentativeStartDate").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_START_DATE.toString()))
            )
            .andExpect(jsonPath("$.[*].countryRepresentativeEndDate").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].countryRepresentativeMinistry").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_MINISTRY)))
            .andExpect(jsonPath("$.[*].countryRepresentativeDepartment").value(hasItem(DEFAULT_COUNTRY_REPRESENTATIVE_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].contactEunFirstName").value(hasItem(DEFAULT_CONTACT_EUN_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].contactEunLastName").value(hasItem(DEFAULT_CONTACT_EUN_LAST_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sheetNum").value(hasItem(DEFAULT_SHEET_NUM.intValue())));

        // Check, that the count call also returns 1
        restWorkingGroupReferencesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkingGroupReferencesShouldNotBeFound(String filter) throws Exception {
        restWorkingGroupReferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkingGroupReferencesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkingGroupReferences() throws Exception {
        // Get the workingGroupReferences
        restWorkingGroupReferencesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkingGroupReferences() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();

        // Update the workingGroupReferences
        WorkingGroupReferences updatedWorkingGroupReferences = workingGroupReferencesRepository
            .findById(workingGroupReferences.getId())
            .get();
        // Disconnect from session so that the updates on updatedWorkingGroupReferences are not directly saved in db
        em.detach(updatedWorkingGroupReferences);
        updatedWorkingGroupReferences
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .countryRepresentativeFirstName(UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME)
            .countryRepresentativeLastName(UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME)
            .countryRepresentativeMail(UPDATED_COUNTRY_REPRESENTATIVE_MAIL)
            .countryRepresentativePosition(UPDATED_COUNTRY_REPRESENTATIVE_POSITION)
            .countryRepresentativeStartDate(UPDATED_COUNTRY_REPRESENTATIVE_START_DATE)
            .countryRepresentativeEndDate(UPDATED_COUNTRY_REPRESENTATIVE_END_DATE)
            .countryRepresentativeMinistry(UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY)
            .countryRepresentativeDepartment(UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT)
            .contactEunFirstName(UPDATED_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(UPDATED_CONTACT_EUN_LAST_NAME)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .sheetNum(UPDATED_SHEET_NUM);
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(updatedWorkingGroupReferences);

        restWorkingGroupReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingGroupReferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
        WorkingGroupReferences testWorkingGroupReferences = workingGroupReferencesList.get(workingGroupReferencesList.size() - 1);
        assertThat(testWorkingGroupReferences.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testWorkingGroupReferences.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeFirstName()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeLastName()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMail()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_MAIL);
        assertThat(testWorkingGroupReferences.getCountryRepresentativePosition()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_POSITION);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeStartDate()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_START_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeEndDate()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_END_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMinistry()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeDepartment()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT);
        assertThat(testWorkingGroupReferences.getContactEunFirstName()).isEqualTo(UPDATED_CONTACT_EUN_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getContactEunLastName()).isEqualTo(UPDATED_CONTACT_EUN_LAST_NAME);
        assertThat(testWorkingGroupReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWorkingGroupReferences.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWorkingGroupReferences.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkingGroupReferences.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkingGroupReferences.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingGroupReferences.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testWorkingGroupReferences.getSheetNum()).isEqualTo(UPDATED_SHEET_NUM);
    }

    @Test
    @Transactional
    void putNonExistingWorkingGroupReferences() throws Exception {
        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();
        workingGroupReferences.setId(count.incrementAndGet());

        // Create the WorkingGroupReferences
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingGroupReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingGroupReferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkingGroupReferences() throws Exception {
        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();
        workingGroupReferences.setId(count.incrementAndGet());

        // Create the WorkingGroupReferences
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingGroupReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkingGroupReferences() throws Exception {
        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();
        workingGroupReferences.setId(count.incrementAndGet());

        // Create the WorkingGroupReferences
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingGroupReferencesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkingGroupReferencesWithPatch() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();

        // Update the workingGroupReferences using partial update
        WorkingGroupReferences partialUpdatedWorkingGroupReferences = new WorkingGroupReferences();
        partialUpdatedWorkingGroupReferences.setId(workingGroupReferences.getId());

        partialUpdatedWorkingGroupReferences
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryRepresentativeLastName(UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME)
            .countryRepresentativeMail(UPDATED_COUNTRY_REPRESENTATIVE_MAIL)
            .countryRepresentativeStartDate(UPDATED_COUNTRY_REPRESENTATIVE_START_DATE)
            .countryRepresentativeMinistry(UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY)
            .countryRepresentativeDepartment(UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restWorkingGroupReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingGroupReferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingGroupReferences))
            )
            .andExpect(status().isOk());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
        WorkingGroupReferences testWorkingGroupReferences = workingGroupReferencesList.get(workingGroupReferencesList.size() - 1);
        assertThat(testWorkingGroupReferences.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testWorkingGroupReferences.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeFirstName()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeLastName()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMail()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_MAIL);
        assertThat(testWorkingGroupReferences.getCountryRepresentativePosition()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_POSITION);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeStartDate()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_START_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeEndDate()).isEqualTo(DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMinistry()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeDepartment()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT);
        assertThat(testWorkingGroupReferences.getContactEunFirstName()).isEqualTo(DEFAULT_CONTACT_EUN_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getContactEunLastName()).isEqualTo(DEFAULT_CONTACT_EUN_LAST_NAME);
        assertThat(testWorkingGroupReferences.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWorkingGroupReferences.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testWorkingGroupReferences.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkingGroupReferences.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWorkingGroupReferences.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingGroupReferences.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testWorkingGroupReferences.getSheetNum()).isEqualTo(DEFAULT_SHEET_NUM);
    }

    @Test
    @Transactional
    void fullUpdateWorkingGroupReferencesWithPatch() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();

        // Update the workingGroupReferences using partial update
        WorkingGroupReferences partialUpdatedWorkingGroupReferences = new WorkingGroupReferences();
        partialUpdatedWorkingGroupReferences.setId(workingGroupReferences.getId());

        partialUpdatedWorkingGroupReferences
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .countryRepresentativeFirstName(UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME)
            .countryRepresentativeLastName(UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME)
            .countryRepresentativeMail(UPDATED_COUNTRY_REPRESENTATIVE_MAIL)
            .countryRepresentativePosition(UPDATED_COUNTRY_REPRESENTATIVE_POSITION)
            .countryRepresentativeStartDate(UPDATED_COUNTRY_REPRESENTATIVE_START_DATE)
            .countryRepresentativeEndDate(UPDATED_COUNTRY_REPRESENTATIVE_END_DATE)
            .countryRepresentativeMinistry(UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY)
            .countryRepresentativeDepartment(UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT)
            .contactEunFirstName(UPDATED_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(UPDATED_CONTACT_EUN_LAST_NAME)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .sheetNum(UPDATED_SHEET_NUM);

        restWorkingGroupReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingGroupReferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingGroupReferences))
            )
            .andExpect(status().isOk());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
        WorkingGroupReferences testWorkingGroupReferences = workingGroupReferencesList.get(workingGroupReferencesList.size() - 1);
        assertThat(testWorkingGroupReferences.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testWorkingGroupReferences.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeFirstName()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeLastName()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_LAST_NAME);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMail()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_MAIL);
        assertThat(testWorkingGroupReferences.getCountryRepresentativePosition()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_POSITION);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeStartDate()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_START_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeEndDate()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_END_DATE);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeMinistry()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_MINISTRY);
        assertThat(testWorkingGroupReferences.getCountryRepresentativeDepartment()).isEqualTo(UPDATED_COUNTRY_REPRESENTATIVE_DEPARTMENT);
        assertThat(testWorkingGroupReferences.getContactEunFirstName()).isEqualTo(UPDATED_CONTACT_EUN_FIRST_NAME);
        assertThat(testWorkingGroupReferences.getContactEunLastName()).isEqualTo(UPDATED_CONTACT_EUN_LAST_NAME);
        assertThat(testWorkingGroupReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWorkingGroupReferences.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWorkingGroupReferences.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkingGroupReferences.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkingGroupReferences.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingGroupReferences.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testWorkingGroupReferences.getSheetNum()).isEqualTo(UPDATED_SHEET_NUM);
    }

    @Test
    @Transactional
    void patchNonExistingWorkingGroupReferences() throws Exception {
        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();
        workingGroupReferences.setId(count.incrementAndGet());

        // Create the WorkingGroupReferences
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingGroupReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workingGroupReferencesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkingGroupReferences() throws Exception {
        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();
        workingGroupReferences.setId(count.incrementAndGet());

        // Create the WorkingGroupReferences
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingGroupReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkingGroupReferences() throws Exception {
        int databaseSizeBeforeUpdate = workingGroupReferencesRepository.findAll().size();
        workingGroupReferences.setId(count.incrementAndGet());

        // Create the WorkingGroupReferences
        WorkingGroupReferencesDTO workingGroupReferencesDTO = workingGroupReferencesMapper.toDto(workingGroupReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingGroupReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingGroupReferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingGroupReferences in the database
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkingGroupReferences() throws Exception {
        // Initialize the database
        workingGroupReferencesRepository.saveAndFlush(workingGroupReferences);

        int databaseSizeBeforeDelete = workingGroupReferencesRepository.findAll().size();

        // Delete the workingGroupReferences
        restWorkingGroupReferencesMockMvc
            .perform(delete(ENTITY_API_URL_ID, workingGroupReferences.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingGroupReferences> workingGroupReferencesList = workingGroupReferencesRepository.findAll();
        assertThat(workingGroupReferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
