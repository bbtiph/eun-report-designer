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
import org.eun.back.domain.ReportTemplate;
import org.eun.back.repository.ReportTemplateRepository;
import org.eun.back.service.dto.ReportTemplateDTO;
import org.eun.back.service.mapper.ReportTemplateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ReportTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReportTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/report-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;

    @Autowired
    private ReportTemplateMapper reportTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportTemplateMockMvc;

    private ReportTemplate reportTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportTemplate createEntity(EntityManager em) {
        ReportTemplate reportTemplate = new ReportTemplate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return reportTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportTemplate createUpdatedEntity(EntityManager em) {
        ReportTemplate reportTemplate = new ReportTemplate()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return reportTemplate;
    }

    @BeforeEach
    public void initTest() {
        reportTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createReportTemplate() throws Exception {
        int databaseSizeBeforeCreate = reportTemplateRepository.findAll().size();
        // Create the ReportTemplate
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);
        restReportTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ReportTemplate testReportTemplate = reportTemplateList.get(reportTemplateList.size() - 1);
        assertThat(testReportTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReportTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReportTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReportTemplate.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testReportTemplate.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testReportTemplate.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testReportTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testReportTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testReportTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReportTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createReportTemplateWithExistingId() throws Exception {
        // Create the ReportTemplate with an existing ID
        reportTemplate.setId(1L);
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);

        int databaseSizeBeforeCreate = reportTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReportTemplates() throws Exception {
        // Initialize the database
        reportTemplateRepository.saveAndFlush(reportTemplate);

        // Get all the reportTemplateList
        restReportTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getReportTemplate() throws Exception {
        // Initialize the database
        reportTemplateRepository.saveAndFlush(reportTemplate);

        // Get the reportTemplate
        restReportTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, reportTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReportTemplate() throws Exception {
        // Get the reportTemplate
        restReportTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReportTemplate() throws Exception {
        // Initialize the database
        reportTemplateRepository.saveAndFlush(reportTemplate);

        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();

        // Update the reportTemplate
        ReportTemplate updatedReportTemplate = reportTemplateRepository.findById(reportTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedReportTemplate are not directly saved in db
        em.detach(updatedReportTemplate);
        updatedReportTemplate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(updatedReportTemplate);

        restReportTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
        ReportTemplate testReportTemplate = reportTemplateList.get(reportTemplateList.size() - 1);
        assertThat(testReportTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReportTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReportTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportTemplate.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReportTemplate.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testReportTemplate.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testReportTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReportTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReportTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReportTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingReportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();
        reportTemplate.setId(count.incrementAndGet());

        // Create the ReportTemplate
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();
        reportTemplate.setId(count.incrementAndGet());

        // Create the ReportTemplate
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();
        reportTemplate.setId(count.incrementAndGet());

        // Create the ReportTemplate
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportTemplateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReportTemplateWithPatch() throws Exception {
        // Initialize the database
        reportTemplateRepository.saveAndFlush(reportTemplate);

        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();

        // Update the reportTemplate using partial update
        ReportTemplate partialUpdatedReportTemplate = new ReportTemplate();
        partialUpdatedReportTemplate.setId(reportTemplate.getId());

        partialUpdatedReportTemplate
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restReportTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
        ReportTemplate testReportTemplate = reportTemplateList.get(reportTemplateList.size() - 1);
        assertThat(testReportTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReportTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReportTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportTemplate.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testReportTemplate.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testReportTemplate.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testReportTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReportTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReportTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReportTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateReportTemplateWithPatch() throws Exception {
        // Initialize the database
        reportTemplateRepository.saveAndFlush(reportTemplate);

        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();

        // Update the reportTemplate using partial update
        ReportTemplate partialUpdatedReportTemplate = new ReportTemplate();
        partialUpdatedReportTemplate.setId(reportTemplate.getId());

        partialUpdatedReportTemplate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restReportTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
        ReportTemplate testReportTemplate = reportTemplateList.get(reportTemplateList.size() - 1);
        assertThat(testReportTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReportTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReportTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportTemplate.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReportTemplate.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testReportTemplate.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testReportTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testReportTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testReportTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReportTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingReportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();
        reportTemplate.setId(count.incrementAndGet());

        // Create the ReportTemplate
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reportTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();
        reportTemplate.setId(count.incrementAndGet());

        // Create the ReportTemplate
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReportTemplate() throws Exception {
        int databaseSizeBeforeUpdate = reportTemplateRepository.findAll().size();
        reportTemplate.setId(count.incrementAndGet());

        // Create the ReportTemplate
        ReportTemplateDTO reportTemplateDTO = reportTemplateMapper.toDto(reportTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportTemplate in the database
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReportTemplate() throws Exception {
        // Initialize the database
        reportTemplateRepository.saveAndFlush(reportTemplate);

        int databaseSizeBeforeDelete = reportTemplateRepository.findAll().size();

        // Delete the reportTemplate
        restReportTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, reportTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportTemplate> reportTemplateList = reportTemplateRepository.findAll();
        assertThat(reportTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
