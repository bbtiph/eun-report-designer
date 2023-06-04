package org.eun.back.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.eun.back.IntegrationTest;
import org.eun.back.domain.ReportBlocksContent;
import org.eun.back.repository.ReportBlocksContentRepository;
import org.eun.back.service.dto.ReportBlocksContentDTO;
import org.eun.back.service.mapper.ReportBlocksContentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReportBlocksContentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReportBlocksContentResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIORITY_NUMBER = 1L;
    private static final Long UPDATED_PRIORITY_NUMBER = 2L;

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/report-blocks-contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReportBlocksContentRepository reportBlocksContentRepository;

    @Autowired
    private ReportBlocksContentMapper reportBlocksContentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportBlocksContentMockMvc;

    private ReportBlocksContent reportBlocksContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportBlocksContent createEntity(EntityManager em) {
        ReportBlocksContent reportBlocksContent = new ReportBlocksContent()
            .type(DEFAULT_TYPE)
            .priorityNumber(DEFAULT_PRIORITY_NUMBER)
            .template(DEFAULT_TEMPLATE)
            .isActive(DEFAULT_IS_ACTIVE);
        return reportBlocksContent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportBlocksContent createUpdatedEntity(EntityManager em) {
        ReportBlocksContent reportBlocksContent = new ReportBlocksContent()
            .type(UPDATED_TYPE)
            .priorityNumber(UPDATED_PRIORITY_NUMBER)
            .template(UPDATED_TEMPLATE)
            .isActive(UPDATED_IS_ACTIVE);
        return reportBlocksContent;
    }

    @BeforeEach
    public void initTest() {
        reportBlocksContent = createEntity(em);
    }

    @Test
    @Transactional
    void createReportBlocksContent() throws Exception {
        int databaseSizeBeforeCreate = reportBlocksContentRepository.findAll().size();
        // Create the ReportBlocksContent
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);
        restReportBlocksContentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeCreate + 1);
        ReportBlocksContent testReportBlocksContent = reportBlocksContentList.get(reportBlocksContentList.size() - 1);
        assertThat(testReportBlocksContent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReportBlocksContent.getPriorityNumber()).isEqualTo(DEFAULT_PRIORITY_NUMBER);
        assertThat(testReportBlocksContent.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
        assertThat(testReportBlocksContent.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createReportBlocksContentWithExistingId() throws Exception {
        // Create the ReportBlocksContent with an existing ID
        reportBlocksContent.setId(1L);
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);

        int databaseSizeBeforeCreate = reportBlocksContentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportBlocksContentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReportBlocksContents() throws Exception {
        // Initialize the database
        reportBlocksContentRepository.saveAndFlush(reportBlocksContent);

        // Get all the reportBlocksContentList
        restReportBlocksContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportBlocksContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].priorityNumber").value(hasItem(DEFAULT_PRIORITY_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getReportBlocksContent() throws Exception {
        // Initialize the database
        reportBlocksContentRepository.saveAndFlush(reportBlocksContent);

        // Get the reportBlocksContent
        restReportBlocksContentMockMvc
            .perform(get(ENTITY_API_URL_ID, reportBlocksContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportBlocksContent.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.priorityNumber").value(DEFAULT_PRIORITY_NUMBER.intValue()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingReportBlocksContent() throws Exception {
        // Get the reportBlocksContent
        restReportBlocksContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReportBlocksContent() throws Exception {
        // Initialize the database
        reportBlocksContentRepository.saveAndFlush(reportBlocksContent);

        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();

        // Update the reportBlocksContent
        ReportBlocksContent updatedReportBlocksContent = reportBlocksContentRepository.findById(reportBlocksContent.getId()).get();
        // Disconnect from session so that the updates on updatedReportBlocksContent are not directly saved in db
        em.detach(updatedReportBlocksContent);
        updatedReportBlocksContent
            .type(UPDATED_TYPE)
            .priorityNumber(UPDATED_PRIORITY_NUMBER)
            .template(UPDATED_TEMPLATE)
            .isActive(UPDATED_IS_ACTIVE);
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(updatedReportBlocksContent);

        restReportBlocksContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportBlocksContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocksContent testReportBlocksContent = reportBlocksContentList.get(reportBlocksContentList.size() - 1);
        assertThat(testReportBlocksContent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportBlocksContent.getPriorityNumber()).isEqualTo(UPDATED_PRIORITY_NUMBER);
        assertThat(testReportBlocksContent.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testReportBlocksContent.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingReportBlocksContent() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();
        reportBlocksContent.setId(count.incrementAndGet());

        // Create the ReportBlocksContent
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportBlocksContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportBlocksContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReportBlocksContent() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();
        reportBlocksContent.setId(count.incrementAndGet());

        // Create the ReportBlocksContent
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReportBlocksContent() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();
        reportBlocksContent.setId(count.incrementAndGet());

        // Create the ReportBlocksContent
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReportBlocksContentWithPatch() throws Exception {
        // Initialize the database
        reportBlocksContentRepository.saveAndFlush(reportBlocksContent);

        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();

        // Update the reportBlocksContent using partial update
        ReportBlocksContent partialUpdatedReportBlocksContent = new ReportBlocksContent();
        partialUpdatedReportBlocksContent.setId(reportBlocksContent.getId());

        partialUpdatedReportBlocksContent.type(UPDATED_TYPE).priorityNumber(UPDATED_PRIORITY_NUMBER);

        restReportBlocksContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportBlocksContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportBlocksContent))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocksContent testReportBlocksContent = reportBlocksContentList.get(reportBlocksContentList.size() - 1);
        assertThat(testReportBlocksContent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportBlocksContent.getPriorityNumber()).isEqualTo(UPDATED_PRIORITY_NUMBER);
        assertThat(testReportBlocksContent.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
        assertThat(testReportBlocksContent.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateReportBlocksContentWithPatch() throws Exception {
        // Initialize the database
        reportBlocksContentRepository.saveAndFlush(reportBlocksContent);

        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();

        // Update the reportBlocksContent using partial update
        ReportBlocksContent partialUpdatedReportBlocksContent = new ReportBlocksContent();
        partialUpdatedReportBlocksContent.setId(reportBlocksContent.getId());

        partialUpdatedReportBlocksContent
            .type(UPDATED_TYPE)
            .priorityNumber(UPDATED_PRIORITY_NUMBER)
            .template(UPDATED_TEMPLATE)
            .isActive(UPDATED_IS_ACTIVE);

        restReportBlocksContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportBlocksContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportBlocksContent))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocksContent testReportBlocksContent = reportBlocksContentList.get(reportBlocksContentList.size() - 1);
        assertThat(testReportBlocksContent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportBlocksContent.getPriorityNumber()).isEqualTo(UPDATED_PRIORITY_NUMBER);
        assertThat(testReportBlocksContent.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testReportBlocksContent.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingReportBlocksContent() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();
        reportBlocksContent.setId(count.incrementAndGet());

        // Create the ReportBlocksContent
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportBlocksContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reportBlocksContentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReportBlocksContent() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();
        reportBlocksContent.setId(count.incrementAndGet());

        // Create the ReportBlocksContent
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReportBlocksContent() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentRepository.findAll().size();
        reportBlocksContent.setId(count.incrementAndGet());

        // Create the ReportBlocksContent
        ReportBlocksContentDTO reportBlocksContentDTO = reportBlocksContentMapper.toDto(reportBlocksContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportBlocksContent in the database
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReportBlocksContent() throws Exception {
        // Initialize the database
        reportBlocksContentRepository.saveAndFlush(reportBlocksContent);

        int databaseSizeBeforeDelete = reportBlocksContentRepository.findAll().size();

        // Delete the reportBlocksContent
        restReportBlocksContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, reportBlocksContent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportBlocksContent> reportBlocksContentList = reportBlocksContentRepository.findAll();
        assertThat(reportBlocksContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
