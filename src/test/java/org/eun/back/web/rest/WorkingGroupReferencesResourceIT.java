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

    private static final LocalDate DEFAULT_COUNTRY_REPRESENTATIVE_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COUNTRY_REPRESENTATIVE_END_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

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
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
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
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
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
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
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
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
