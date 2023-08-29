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
import org.eun.back.domain.PersonEunIndicator;
import org.eun.back.repository.PersonEunIndicatorRepository;
import org.eun.back.service.dto.PersonEunIndicatorDTO;
import org.eun.back.service.mapper.PersonEunIndicatorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonEunIndicatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonEunIndicatorResourceIT {

    private static final Long DEFAULT_PERIOD = 1L;
    private static final Long UPDATED_PERIOD = 2L;

    private static final Long DEFAULT_N_COUNT = 1L;
    private static final Long UPDATED_N_COUNT = 2L;

    private static final Long DEFAULT_COUNTRY_ID = 1L;
    private static final Long UPDATED_COUNTRY_ID = 2L;

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long UPDATED_PROJECT_ID = 2L;

    private static final String DEFAULT_PROJECT_URL = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_REPORTS_PROJECT_ID = 1L;
    private static final Long UPDATED_REPORTS_PROJECT_ID = 2L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/person-eun-indicators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonEunIndicatorRepository personEunIndicatorRepository;

    @Autowired
    private PersonEunIndicatorMapper personEunIndicatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonEunIndicatorMockMvc;

    private PersonEunIndicator personEunIndicator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonEunIndicator createEntity(EntityManager em) {
        PersonEunIndicator personEunIndicator = new PersonEunIndicator()
            .period(DEFAULT_PERIOD)
            .nCount(DEFAULT_N_COUNT)
            .countryId(DEFAULT_COUNTRY_ID)
            .projectId(DEFAULT_PROJECT_ID)
            .projectUrl(DEFAULT_PROJECT_URL)
            .countryName(DEFAULT_COUNTRY_NAME)
            .projectName(DEFAULT_PROJECT_NAME)
            .reportsProjectId(DEFAULT_REPORTS_PROJECT_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return personEunIndicator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonEunIndicator createUpdatedEntity(EntityManager em) {
        PersonEunIndicator personEunIndicator = new PersonEunIndicator()
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .countryId(UPDATED_COUNTRY_ID)
            .projectId(UPDATED_PROJECT_ID)
            .projectUrl(UPDATED_PROJECT_URL)
            .countryName(UPDATED_COUNTRY_NAME)
            .projectName(UPDATED_PROJECT_NAME)
            .reportsProjectId(UPDATED_REPORTS_PROJECT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return personEunIndicator;
    }

    @BeforeEach
    public void initTest() {
        personEunIndicator = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonEunIndicator() throws Exception {
        int databaseSizeBeforeCreate = personEunIndicatorRepository.findAll().size();
        // Create the PersonEunIndicator
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);
        restPersonEunIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeCreate + 1);
        PersonEunIndicator testPersonEunIndicator = personEunIndicatorList.get(personEunIndicatorList.size() - 1);
        assertThat(testPersonEunIndicator.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testPersonEunIndicator.getnCount()).isEqualTo(DEFAULT_N_COUNT);
        assertThat(testPersonEunIndicator.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testPersonEunIndicator.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testPersonEunIndicator.getProjectUrl()).isEqualTo(DEFAULT_PROJECT_URL);
        assertThat(testPersonEunIndicator.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testPersonEunIndicator.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testPersonEunIndicator.getReportsProjectId()).isEqualTo(DEFAULT_REPORTS_PROJECT_ID);
        assertThat(testPersonEunIndicator.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPersonEunIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPersonEunIndicator.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPersonEunIndicator.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPersonEunIndicatorWithExistingId() throws Exception {
        // Create the PersonEunIndicator with an existing ID
        personEunIndicator.setId(1L);
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);

        int databaseSizeBeforeCreate = personEunIndicatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonEunIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonEunIndicators() throws Exception {
        // Initialize the database
        personEunIndicatorRepository.saveAndFlush(personEunIndicator);

        // Get all the personEunIndicatorList
        restPersonEunIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personEunIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].nCount").value(hasItem(DEFAULT_N_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].countryId").value(hasItem(DEFAULT_COUNTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].projectUrl").value(hasItem(DEFAULT_PROJECT_URL)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].reportsProjectId").value(hasItem(DEFAULT_REPORTS_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPersonEunIndicator() throws Exception {
        // Initialize the database
        personEunIndicatorRepository.saveAndFlush(personEunIndicator);

        // Get the personEunIndicator
        restPersonEunIndicatorMockMvc
            .perform(get(ENTITY_API_URL_ID, personEunIndicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personEunIndicator.getId().intValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.intValue()))
            .andExpect(jsonPath("$.nCount").value(DEFAULT_N_COUNT.intValue()))
            .andExpect(jsonPath("$.countryId").value(DEFAULT_COUNTRY_ID.intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.projectUrl").value(DEFAULT_PROJECT_URL))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.reportsProjectId").value(DEFAULT_REPORTS_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPersonEunIndicator() throws Exception {
        // Get the personEunIndicator
        restPersonEunIndicatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonEunIndicator() throws Exception {
        // Initialize the database
        personEunIndicatorRepository.saveAndFlush(personEunIndicator);

        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();

        // Update the personEunIndicator
        PersonEunIndicator updatedPersonEunIndicator = personEunIndicatorRepository.findById(personEunIndicator.getId()).get();
        // Disconnect from session so that the updates on updatedPersonEunIndicator are not directly saved in db
        em.detach(updatedPersonEunIndicator);
        updatedPersonEunIndicator
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .countryId(UPDATED_COUNTRY_ID)
            .projectId(UPDATED_PROJECT_ID)
            .projectUrl(UPDATED_PROJECT_URL)
            .countryName(UPDATED_COUNTRY_NAME)
            .projectName(UPDATED_PROJECT_NAME)
            .reportsProjectId(UPDATED_REPORTS_PROJECT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(updatedPersonEunIndicator);

        restPersonEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personEunIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        PersonEunIndicator testPersonEunIndicator = personEunIndicatorList.get(personEunIndicatorList.size() - 1);
        assertThat(testPersonEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testPersonEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testPersonEunIndicator.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testPersonEunIndicator.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testPersonEunIndicator.getProjectUrl()).isEqualTo(UPDATED_PROJECT_URL);
        assertThat(testPersonEunIndicator.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testPersonEunIndicator.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testPersonEunIndicator.getReportsProjectId()).isEqualTo(UPDATED_REPORTS_PROJECT_ID);
        assertThat(testPersonEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPersonEunIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPersonEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPersonEunIndicator.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPersonEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();
        personEunIndicator.setId(count.incrementAndGet());

        // Create the PersonEunIndicator
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personEunIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();
        personEunIndicator.setId(count.incrementAndGet());

        // Create the PersonEunIndicator
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();
        personEunIndicator.setId(count.incrementAndGet());

        // Create the PersonEunIndicator
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonEunIndicatorWithPatch() throws Exception {
        // Initialize the database
        personEunIndicatorRepository.saveAndFlush(personEunIndicator);

        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();

        // Update the personEunIndicator using partial update
        PersonEunIndicator partialUpdatedPersonEunIndicator = new PersonEunIndicator();
        partialUpdatedPersonEunIndicator.setId(personEunIndicator.getId());

        partialUpdatedPersonEunIndicator
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .countryId(UPDATED_COUNTRY_ID)
            .countryName(UPDATED_COUNTRY_NAME)
            .reportsProjectId(UPDATED_REPORTS_PROJECT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restPersonEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonEunIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonEunIndicator))
            )
            .andExpect(status().isOk());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        PersonEunIndicator testPersonEunIndicator = personEunIndicatorList.get(personEunIndicatorList.size() - 1);
        assertThat(testPersonEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testPersonEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testPersonEunIndicator.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testPersonEunIndicator.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testPersonEunIndicator.getProjectUrl()).isEqualTo(DEFAULT_PROJECT_URL);
        assertThat(testPersonEunIndicator.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testPersonEunIndicator.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testPersonEunIndicator.getReportsProjectId()).isEqualTo(UPDATED_REPORTS_PROJECT_ID);
        assertThat(testPersonEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPersonEunIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPersonEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPersonEunIndicator.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePersonEunIndicatorWithPatch() throws Exception {
        // Initialize the database
        personEunIndicatorRepository.saveAndFlush(personEunIndicator);

        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();

        // Update the personEunIndicator using partial update
        PersonEunIndicator partialUpdatedPersonEunIndicator = new PersonEunIndicator();
        partialUpdatedPersonEunIndicator.setId(personEunIndicator.getId());

        partialUpdatedPersonEunIndicator
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .countryId(UPDATED_COUNTRY_ID)
            .projectId(UPDATED_PROJECT_ID)
            .projectUrl(UPDATED_PROJECT_URL)
            .countryName(UPDATED_COUNTRY_NAME)
            .projectName(UPDATED_PROJECT_NAME)
            .reportsProjectId(UPDATED_REPORTS_PROJECT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPersonEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonEunIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonEunIndicator))
            )
            .andExpect(status().isOk());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        PersonEunIndicator testPersonEunIndicator = personEunIndicatorList.get(personEunIndicatorList.size() - 1);
        assertThat(testPersonEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testPersonEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testPersonEunIndicator.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testPersonEunIndicator.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testPersonEunIndicator.getProjectUrl()).isEqualTo(UPDATED_PROJECT_URL);
        assertThat(testPersonEunIndicator.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testPersonEunIndicator.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testPersonEunIndicator.getReportsProjectId()).isEqualTo(UPDATED_REPORTS_PROJECT_ID);
        assertThat(testPersonEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPersonEunIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPersonEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPersonEunIndicator.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPersonEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();
        personEunIndicator.setId(count.incrementAndGet());

        // Create the PersonEunIndicator
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personEunIndicatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();
        personEunIndicator.setId(count.incrementAndGet());

        // Create the PersonEunIndicator
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = personEunIndicatorRepository.findAll().size();
        personEunIndicator.setId(count.incrementAndGet());

        // Create the PersonEunIndicator
        PersonEunIndicatorDTO personEunIndicatorDTO = personEunIndicatorMapper.toDto(personEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personEunIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonEunIndicator in the database
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonEunIndicator() throws Exception {
        // Initialize the database
        personEunIndicatorRepository.saveAndFlush(personEunIndicator);

        int databaseSizeBeforeDelete = personEunIndicatorRepository.findAll().size();

        // Delete the personEunIndicator
        restPersonEunIndicatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, personEunIndicator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonEunIndicator> personEunIndicatorList = personEunIndicatorRepository.findAll();
        assertThat(personEunIndicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
