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
import org.eun.back.domain.ReportBlocksContentData;
import org.eun.back.repository.ReportBlocksContentDataRepository;
import org.eun.back.service.dto.ReportBlocksContentDataDTO;
import org.eun.back.service.mapper.ReportBlocksContentDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReportBlocksContentDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReportBlocksContentDataResourceIT {

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/report-blocks-content-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReportBlocksContentDataRepository reportBlocksContentDataRepository;

    @Autowired
    private ReportBlocksContentDataMapper reportBlocksContentDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportBlocksContentDataMockMvc;

    private ReportBlocksContentData reportBlocksContentData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportBlocksContentData createEntity(EntityManager em) {
        ReportBlocksContentData reportBlocksContentData = new ReportBlocksContentData().data(DEFAULT_DATA);
        return reportBlocksContentData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportBlocksContentData createUpdatedEntity(EntityManager em) {
        ReportBlocksContentData reportBlocksContentData = new ReportBlocksContentData().data(UPDATED_DATA);
        return reportBlocksContentData;
    }

    @BeforeEach
    public void initTest() {
        reportBlocksContentData = createEntity(em);
    }

    @Test
    @Transactional
    void createReportBlocksContentData() throws Exception {
        int databaseSizeBeforeCreate = reportBlocksContentDataRepository.findAll().size();
        // Create the ReportBlocksContentData
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);
        restReportBlocksContentDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeCreate + 1);
        ReportBlocksContentData testReportBlocksContentData = reportBlocksContentDataList.get(reportBlocksContentDataList.size() - 1);
        assertThat(testReportBlocksContentData.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    void createReportBlocksContentDataWithExistingId() throws Exception {
        // Create the ReportBlocksContentData with an existing ID
        reportBlocksContentData.setId(1L);
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);

        int databaseSizeBeforeCreate = reportBlocksContentDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportBlocksContentDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReportBlocksContentData() throws Exception {
        // Initialize the database
        reportBlocksContentDataRepository.saveAndFlush(reportBlocksContentData);

        // Get all the reportBlocksContentDataList
        restReportBlocksContentDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportBlocksContentData.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    void getReportBlocksContentData() throws Exception {
        // Initialize the database
        reportBlocksContentDataRepository.saveAndFlush(reportBlocksContentData);

        // Get the reportBlocksContentData
        restReportBlocksContentDataMockMvc
            .perform(get(ENTITY_API_URL_ID, reportBlocksContentData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportBlocksContentData.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA));
    }

    @Test
    @Transactional
    void getNonExistingReportBlocksContentData() throws Exception {
        // Get the reportBlocksContentData
        restReportBlocksContentDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReportBlocksContentData() throws Exception {
        // Initialize the database
        reportBlocksContentDataRepository.saveAndFlush(reportBlocksContentData);

        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();

        // Update the reportBlocksContentData
        ReportBlocksContentData updatedReportBlocksContentData = reportBlocksContentDataRepository
            .findById(reportBlocksContentData.getId())
            .get();
        // Disconnect from session so that the updates on updatedReportBlocksContentData are not directly saved in db
        em.detach(updatedReportBlocksContentData);
        updatedReportBlocksContentData.data(UPDATED_DATA);
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(updatedReportBlocksContentData);

        restReportBlocksContentDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportBlocksContentDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocksContentData testReportBlocksContentData = reportBlocksContentDataList.get(reportBlocksContentDataList.size() - 1);
        assertThat(testReportBlocksContentData.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void putNonExistingReportBlocksContentData() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();
        reportBlocksContentData.setId(count.incrementAndGet());

        // Create the ReportBlocksContentData
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportBlocksContentDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportBlocksContentDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReportBlocksContentData() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();
        reportBlocksContentData.setId(count.incrementAndGet());

        // Create the ReportBlocksContentData
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReportBlocksContentData() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();
        reportBlocksContentData.setId(count.incrementAndGet());

        // Create the ReportBlocksContentData
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReportBlocksContentDataWithPatch() throws Exception {
        // Initialize the database
        reportBlocksContentDataRepository.saveAndFlush(reportBlocksContentData);

        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();

        // Update the reportBlocksContentData using partial update
        ReportBlocksContentData partialUpdatedReportBlocksContentData = new ReportBlocksContentData();
        partialUpdatedReportBlocksContentData.setId(reportBlocksContentData.getId());

        partialUpdatedReportBlocksContentData.data(UPDATED_DATA);

        restReportBlocksContentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportBlocksContentData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportBlocksContentData))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocksContentData testReportBlocksContentData = reportBlocksContentDataList.get(reportBlocksContentDataList.size() - 1);
        assertThat(testReportBlocksContentData.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void fullUpdateReportBlocksContentDataWithPatch() throws Exception {
        // Initialize the database
        reportBlocksContentDataRepository.saveAndFlush(reportBlocksContentData);

        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();

        // Update the reportBlocksContentData using partial update
        ReportBlocksContentData partialUpdatedReportBlocksContentData = new ReportBlocksContentData();
        partialUpdatedReportBlocksContentData.setId(reportBlocksContentData.getId());

        partialUpdatedReportBlocksContentData.data(UPDATED_DATA);

        restReportBlocksContentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportBlocksContentData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportBlocksContentData))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocksContentData testReportBlocksContentData = reportBlocksContentDataList.get(reportBlocksContentDataList.size() - 1);
        assertThat(testReportBlocksContentData.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingReportBlocksContentData() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();
        reportBlocksContentData.setId(count.incrementAndGet());

        // Create the ReportBlocksContentData
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportBlocksContentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reportBlocksContentDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReportBlocksContentData() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();
        reportBlocksContentData.setId(count.incrementAndGet());

        // Create the ReportBlocksContentData
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReportBlocksContentData() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksContentDataRepository.findAll().size();
        reportBlocksContentData.setId(count.incrementAndGet());

        // Create the ReportBlocksContentData
        ReportBlocksContentDataDTO reportBlocksContentDataDTO = reportBlocksContentDataMapper.toDto(reportBlocksContentData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksContentDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksContentDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportBlocksContentData in the database
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReportBlocksContentData() throws Exception {
        // Initialize the database
        reportBlocksContentDataRepository.saveAndFlush(reportBlocksContentData);

        int databaseSizeBeforeDelete = reportBlocksContentDataRepository.findAll().size();

        // Delete the reportBlocksContentData
        restReportBlocksContentDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, reportBlocksContentData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportBlocksContentData> reportBlocksContentDataList = reportBlocksContentDataRepository.findAll();
        assertThat(reportBlocksContentDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
