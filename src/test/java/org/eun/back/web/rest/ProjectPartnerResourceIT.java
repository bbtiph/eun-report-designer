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
import org.eun.back.domain.ProjectPartner;
import org.eun.back.repository.ProjectPartnerRepository;
import org.eun.back.service.dto.ProjectPartnerDTO;
import org.eun.back.service.mapper.ProjectPartnerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectPartnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectPartnerResourceIT {

    private static final String DEFAULT_ODATA_ETAG = "AAAAAAAAAA";
    private static final String UPDATED_ODATA_ETAG = "BBBBBBBBBB";

    private static final Long DEFAULT_NO = 1L;
    private static final Long UPDATED_NO = 2L;

    private static final String DEFAULT_JOB_NO = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NO = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PARTNER_AMOUNT = 1D;
    private static final Double UPDATED_PARTNER_AMOUNT = 2D;

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

    private static final String ENTITY_API_URL = "/api/project-partners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectPartnerRepository projectPartnerRepository;

    @Autowired
    private ProjectPartnerMapper projectPartnerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectPartnerMockMvc;

    private ProjectPartner projectPartner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectPartner createEntity(EntityManager em) {
        ProjectPartner projectPartner = new ProjectPartner()
            .odataEtag(DEFAULT_ODATA_ETAG)
            .no(DEFAULT_NO)
            .jobNo(DEFAULT_JOB_NO)
            .vendorCode(DEFAULT_VENDOR_CODE)
            .vendorName(DEFAULT_VENDOR_NAME)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .partnerAmount(DEFAULT_PARTNER_AMOUNT)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return projectPartner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectPartner createUpdatedEntity(EntityManager em) {
        ProjectPartner projectPartner = new ProjectPartner()
            .odataEtag(UPDATED_ODATA_ETAG)
            .no(UPDATED_NO)
            .jobNo(UPDATED_JOB_NO)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .partnerAmount(UPDATED_PARTNER_AMOUNT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return projectPartner;
    }

    @BeforeEach
    public void initTest() {
        projectPartner = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectPartner() throws Exception {
        int databaseSizeBeforeCreate = projectPartnerRepository.findAll().size();
        // Create the ProjectPartner
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);
        restProjectPartnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectPartner testProjectPartner = projectPartnerList.get(projectPartnerList.size() - 1);
        assertThat(testProjectPartner.getOdataEtag()).isEqualTo(DEFAULT_ODATA_ETAG);
        assertThat(testProjectPartner.getNo()).isEqualTo(DEFAULT_NO);
        assertThat(testProjectPartner.getJobNo()).isEqualTo(DEFAULT_JOB_NO);
        assertThat(testProjectPartner.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testProjectPartner.getVendorName()).isEqualTo(DEFAULT_VENDOR_NAME);
        assertThat(testProjectPartner.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testProjectPartner.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testProjectPartner.getPartnerAmount()).isEqualTo(DEFAULT_PARTNER_AMOUNT);
        assertThat(testProjectPartner.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testProjectPartner.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjectPartner.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProjectPartner.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProjectPartner.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProjectPartnerWithExistingId() throws Exception {
        // Create the ProjectPartner with an existing ID
        projectPartner.setId(1L);
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);

        int databaseSizeBeforeCreate = projectPartnerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectPartnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectPartners() throws Exception {
        // Initialize the database
        projectPartnerRepository.saveAndFlush(projectPartner);

        // Get all the projectPartnerList
        restProjectPartnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectPartner.getId().intValue())))
            .andExpect(jsonPath("$.[*].odataEtag").value(hasItem(DEFAULT_ODATA_ETAG)))
            .andExpect(jsonPath("$.[*].no").value(hasItem(DEFAULT_NO.intValue())))
            .andExpect(jsonPath("$.[*].jobNo").value(hasItem(DEFAULT_JOB_NO)))
            .andExpect(jsonPath("$.[*].vendorCode").value(hasItem(DEFAULT_VENDOR_CODE)))
            .andExpect(jsonPath("$.[*].vendorName").value(hasItem(DEFAULT_VENDOR_NAME)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].partnerAmount").value(hasItem(DEFAULT_PARTNER_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProjectPartner() throws Exception {
        // Initialize the database
        projectPartnerRepository.saveAndFlush(projectPartner);

        // Get the projectPartner
        restProjectPartnerMockMvc
            .perform(get(ENTITY_API_URL_ID, projectPartner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectPartner.getId().intValue()))
            .andExpect(jsonPath("$.odataEtag").value(DEFAULT_ODATA_ETAG))
            .andExpect(jsonPath("$.no").value(DEFAULT_NO.intValue()))
            .andExpect(jsonPath("$.jobNo").value(DEFAULT_JOB_NO))
            .andExpect(jsonPath("$.vendorCode").value(DEFAULT_VENDOR_CODE))
            .andExpect(jsonPath("$.vendorName").value(DEFAULT_VENDOR_NAME))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.partnerAmount").value(DEFAULT_PARTNER_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProjectPartner() throws Exception {
        // Get the projectPartner
        restProjectPartnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjectPartner() throws Exception {
        // Initialize the database
        projectPartnerRepository.saveAndFlush(projectPartner);

        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();

        // Update the projectPartner
        ProjectPartner updatedProjectPartner = projectPartnerRepository.findById(projectPartner.getId()).get();
        // Disconnect from session so that the updates on updatedProjectPartner are not directly saved in db
        em.detach(updatedProjectPartner);
        updatedProjectPartner
            .odataEtag(UPDATED_ODATA_ETAG)
            .no(UPDATED_NO)
            .jobNo(UPDATED_JOB_NO)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .partnerAmount(UPDATED_PARTNER_AMOUNT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(updatedProjectPartner);

        restProjectPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectPartnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
        ProjectPartner testProjectPartner = projectPartnerList.get(projectPartnerList.size() - 1);
        assertThat(testProjectPartner.getOdataEtag()).isEqualTo(UPDATED_ODATA_ETAG);
        assertThat(testProjectPartner.getNo()).isEqualTo(UPDATED_NO);
        assertThat(testProjectPartner.getJobNo()).isEqualTo(UPDATED_JOB_NO);
        assertThat(testProjectPartner.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testProjectPartner.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testProjectPartner.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testProjectPartner.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testProjectPartner.getPartnerAmount()).isEqualTo(UPDATED_PARTNER_AMOUNT);
        assertThat(testProjectPartner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testProjectPartner.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectPartner.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjectPartner.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProjectPartner.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProjectPartner() throws Exception {
        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();
        projectPartner.setId(count.incrementAndGet());

        // Create the ProjectPartner
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectPartnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectPartner() throws Exception {
        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();
        projectPartner.setId(count.incrementAndGet());

        // Create the ProjectPartner
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectPartner() throws Exception {
        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();
        projectPartner.setId(count.incrementAndGet());

        // Create the ProjectPartner
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPartnerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectPartnerWithPatch() throws Exception {
        // Initialize the database
        projectPartnerRepository.saveAndFlush(projectPartner);

        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();

        // Update the projectPartner using partial update
        ProjectPartner partialUpdatedProjectPartner = new ProjectPartner();
        partialUpdatedProjectPartner.setId(projectPartner.getId());

        partialUpdatedProjectPartner
            .odataEtag(UPDATED_ODATA_ETAG)
            .no(UPDATED_NO)
            .jobNo(UPDATED_JOB_NO)
            .vendorName(UPDATED_VENDOR_NAME)
            .countryName(UPDATED_COUNTRY_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProjectPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectPartner))
            )
            .andExpect(status().isOk());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
        ProjectPartner testProjectPartner = projectPartnerList.get(projectPartnerList.size() - 1);
        assertThat(testProjectPartner.getOdataEtag()).isEqualTo(UPDATED_ODATA_ETAG);
        assertThat(testProjectPartner.getNo()).isEqualTo(UPDATED_NO);
        assertThat(testProjectPartner.getJobNo()).isEqualTo(UPDATED_JOB_NO);
        assertThat(testProjectPartner.getVendorCode()).isEqualTo(DEFAULT_VENDOR_CODE);
        assertThat(testProjectPartner.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testProjectPartner.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testProjectPartner.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testProjectPartner.getPartnerAmount()).isEqualTo(DEFAULT_PARTNER_AMOUNT);
        assertThat(testProjectPartner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testProjectPartner.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectPartner.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjectPartner.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProjectPartner.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProjectPartnerWithPatch() throws Exception {
        // Initialize the database
        projectPartnerRepository.saveAndFlush(projectPartner);

        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();

        // Update the projectPartner using partial update
        ProjectPartner partialUpdatedProjectPartner = new ProjectPartner();
        partialUpdatedProjectPartner.setId(projectPartner.getId());

        partialUpdatedProjectPartner
            .odataEtag(UPDATED_ODATA_ETAG)
            .no(UPDATED_NO)
            .jobNo(UPDATED_JOB_NO)
            .vendorCode(UPDATED_VENDOR_CODE)
            .vendorName(UPDATED_VENDOR_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .partnerAmount(UPDATED_PARTNER_AMOUNT)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProjectPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectPartner))
            )
            .andExpect(status().isOk());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
        ProjectPartner testProjectPartner = projectPartnerList.get(projectPartnerList.size() - 1);
        assertThat(testProjectPartner.getOdataEtag()).isEqualTo(UPDATED_ODATA_ETAG);
        assertThat(testProjectPartner.getNo()).isEqualTo(UPDATED_NO);
        assertThat(testProjectPartner.getJobNo()).isEqualTo(UPDATED_JOB_NO);
        assertThat(testProjectPartner.getVendorCode()).isEqualTo(UPDATED_VENDOR_CODE);
        assertThat(testProjectPartner.getVendorName()).isEqualTo(UPDATED_VENDOR_NAME);
        assertThat(testProjectPartner.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testProjectPartner.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testProjectPartner.getPartnerAmount()).isEqualTo(UPDATED_PARTNER_AMOUNT);
        assertThat(testProjectPartner.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testProjectPartner.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectPartner.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProjectPartner.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProjectPartner.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProjectPartner() throws Exception {
        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();
        projectPartner.setId(count.incrementAndGet());

        // Create the ProjectPartner
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectPartnerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectPartner() throws Exception {
        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();
        projectPartner.setId(count.incrementAndGet());

        // Create the ProjectPartner
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectPartner() throws Exception {
        int databaseSizeBeforeUpdate = projectPartnerRepository.findAll().size();
        projectPartner.setId(count.incrementAndGet());

        // Create the ProjectPartner
        ProjectPartnerDTO projectPartnerDTO = projectPartnerMapper.toDto(projectPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectPartnerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectPartner in the database
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectPartner() throws Exception {
        // Initialize the database
        projectPartnerRepository.saveAndFlush(projectPartner);

        int databaseSizeBeforeDelete = projectPartnerRepository.findAll().size();

        // Delete the projectPartner
        restProjectPartnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectPartner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectPartner> projectPartnerList = projectPartnerRepository.findAll();
        assertThat(projectPartnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
