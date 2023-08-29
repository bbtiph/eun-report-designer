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
import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.repository.OrganizationEunIndicatorRepository;
import org.eun.back.service.dto.OrganizationEunIndicatorDTO;
import org.eun.back.service.mapper.OrganizationEunIndicatorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganizationEunIndicatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationEunIndicatorResourceIT {

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

    private static final String ENTITY_API_URL = "/api/organization-eun-indicators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationEunIndicatorRepository organizationEunIndicatorRepository;

    @Autowired
    private OrganizationEunIndicatorMapper organizationEunIndicatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationEunIndicatorMockMvc;

    private OrganizationEunIndicator organizationEunIndicator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationEunIndicator createEntity(EntityManager em) {
        OrganizationEunIndicator organizationEunIndicator = new OrganizationEunIndicator()
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
        return organizationEunIndicator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationEunIndicator createUpdatedEntity(EntityManager em) {
        OrganizationEunIndicator organizationEunIndicator = new OrganizationEunIndicator()
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
        return organizationEunIndicator;
    }

    @BeforeEach
    public void initTest() {
        organizationEunIndicator = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganizationEunIndicator() throws Exception {
        int databaseSizeBeforeCreate = organizationEunIndicatorRepository.findAll().size();
        // Create the OrganizationEunIndicator
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);
        restOrganizationEunIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationEunIndicator testOrganizationEunIndicator = organizationEunIndicatorList.get(organizationEunIndicatorList.size() - 1);
        assertThat(testOrganizationEunIndicator.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testOrganizationEunIndicator.getnCount()).isEqualTo(DEFAULT_N_COUNT);
        assertThat(testOrganizationEunIndicator.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testOrganizationEunIndicator.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getProjectUrl()).isEqualTo(DEFAULT_PROJECT_URL);
        assertThat(testOrganizationEunIndicator.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testOrganizationEunIndicator.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testOrganizationEunIndicator.getReportsProjectId()).isEqualTo(DEFAULT_REPORTS_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrganizationEunIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrganizationEunIndicator.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrganizationEunIndicator.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createOrganizationEunIndicatorWithExistingId() throws Exception {
        // Create the OrganizationEunIndicator with an existing ID
        organizationEunIndicator.setId(1L);
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);

        int databaseSizeBeforeCreate = organizationEunIndicatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationEunIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganizationEunIndicators() throws Exception {
        // Initialize the database
        organizationEunIndicatorRepository.saveAndFlush(organizationEunIndicator);

        // Get all the organizationEunIndicatorList
        restOrganizationEunIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationEunIndicator.getId().intValue())))
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
    void getOrganizationEunIndicator() throws Exception {
        // Initialize the database
        organizationEunIndicatorRepository.saveAndFlush(organizationEunIndicator);

        // Get the organizationEunIndicator
        restOrganizationEunIndicatorMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationEunIndicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationEunIndicator.getId().intValue()))
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
    void getNonExistingOrganizationEunIndicator() throws Exception {
        // Get the organizationEunIndicator
        restOrganizationEunIndicatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationEunIndicator() throws Exception {
        // Initialize the database
        organizationEunIndicatorRepository.saveAndFlush(organizationEunIndicator);

        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();

        // Update the organizationEunIndicator
        OrganizationEunIndicator updatedOrganizationEunIndicator = organizationEunIndicatorRepository
            .findById(organizationEunIndicator.getId())
            .get();
        // Disconnect from session so that the updates on updatedOrganizationEunIndicator are not directly saved in db
        em.detach(updatedOrganizationEunIndicator);
        updatedOrganizationEunIndicator
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
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(updatedOrganizationEunIndicator);

        restOrganizationEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationEunIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        OrganizationEunIndicator testOrganizationEunIndicator = organizationEunIndicatorList.get(organizationEunIndicatorList.size() - 1);
        assertThat(testOrganizationEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testOrganizationEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testOrganizationEunIndicator.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testOrganizationEunIndicator.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getProjectUrl()).isEqualTo(UPDATED_PROJECT_URL);
        assertThat(testOrganizationEunIndicator.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testOrganizationEunIndicator.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testOrganizationEunIndicator.getReportsProjectId()).isEqualTo(UPDATED_REPORTS_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganizationEunIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganizationEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrganizationEunIndicator.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();
        organizationEunIndicator.setId(count.incrementAndGet());

        // Create the OrganizationEunIndicator
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationEunIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();
        organizationEunIndicator.setId(count.incrementAndGet());

        // Create the OrganizationEunIndicator
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();
        organizationEunIndicator.setId(count.incrementAndGet());

        // Create the OrganizationEunIndicator
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationEunIndicatorWithPatch() throws Exception {
        // Initialize the database
        organizationEunIndicatorRepository.saveAndFlush(organizationEunIndicator);

        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();

        // Update the organizationEunIndicator using partial update
        OrganizationEunIndicator partialUpdatedOrganizationEunIndicator = new OrganizationEunIndicator();
        partialUpdatedOrganizationEunIndicator.setId(organizationEunIndicator.getId());

        partialUpdatedOrganizationEunIndicator
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .projectUrl(UPDATED_PROJECT_URL)
            .countryName(UPDATED_COUNTRY_NAME)
            .reportsProjectId(UPDATED_REPORTS_PROJECT_ID)
            .createdBy(UPDATED_CREATED_BY);

        restOrganizationEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationEunIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationEunIndicator))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        OrganizationEunIndicator testOrganizationEunIndicator = organizationEunIndicatorList.get(organizationEunIndicatorList.size() - 1);
        assertThat(testOrganizationEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testOrganizationEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testOrganizationEunIndicator.getCountryId()).isEqualTo(DEFAULT_COUNTRY_ID);
        assertThat(testOrganizationEunIndicator.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getProjectUrl()).isEqualTo(UPDATED_PROJECT_URL);
        assertThat(testOrganizationEunIndicator.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testOrganizationEunIndicator.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testOrganizationEunIndicator.getReportsProjectId()).isEqualTo(UPDATED_REPORTS_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganizationEunIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrganizationEunIndicator.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrganizationEunIndicator.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationEunIndicatorWithPatch() throws Exception {
        // Initialize the database
        organizationEunIndicatorRepository.saveAndFlush(organizationEunIndicator);

        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();

        // Update the organizationEunIndicator using partial update
        OrganizationEunIndicator partialUpdatedOrganizationEunIndicator = new OrganizationEunIndicator();
        partialUpdatedOrganizationEunIndicator.setId(organizationEunIndicator.getId());

        partialUpdatedOrganizationEunIndicator
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

        restOrganizationEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationEunIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationEunIndicator))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        OrganizationEunIndicator testOrganizationEunIndicator = organizationEunIndicatorList.get(organizationEunIndicatorList.size() - 1);
        assertThat(testOrganizationEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testOrganizationEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testOrganizationEunIndicator.getCountryId()).isEqualTo(UPDATED_COUNTRY_ID);
        assertThat(testOrganizationEunIndicator.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getProjectUrl()).isEqualTo(UPDATED_PROJECT_URL);
        assertThat(testOrganizationEunIndicator.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testOrganizationEunIndicator.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testOrganizationEunIndicator.getReportsProjectId()).isEqualTo(UPDATED_REPORTS_PROJECT_ID);
        assertThat(testOrganizationEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganizationEunIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganizationEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrganizationEunIndicator.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();
        organizationEunIndicator.setId(count.incrementAndGet());

        // Create the OrganizationEunIndicator
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationEunIndicatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();
        organizationEunIndicator.setId(count.incrementAndGet());

        // Create the OrganizationEunIndicator
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = organizationEunIndicatorRepository.findAll().size();
        organizationEunIndicator.setId(count.incrementAndGet());

        // Create the OrganizationEunIndicator
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = organizationEunIndicatorMapper.toDto(organizationEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationEunIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationEunIndicator in the database
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationEunIndicator() throws Exception {
        // Initialize the database
        organizationEunIndicatorRepository.saveAndFlush(organizationEunIndicator);

        int databaseSizeBeforeDelete = organizationEunIndicatorRepository.findAll().size();

        // Delete the organizationEunIndicator
        restOrganizationEunIndicatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationEunIndicator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationEunIndicator> organizationEunIndicatorList = organizationEunIndicatorRepository.findAll();
        assertThat(organizationEunIndicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
