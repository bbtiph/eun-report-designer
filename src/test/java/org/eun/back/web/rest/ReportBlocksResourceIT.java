package org.eun.back.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.eun.back.IntegrationTest;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.repository.ReportBlocksRepository;
import org.eun.back.service.ReportBlocksService;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.eun.back.service.mapper.ReportBlocksMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReportBlocksResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ReportBlocksResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIORITY_NUMBER = 1L;
    private static final Long UPDATED_PRIORITY_NUMBER = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SQL_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_SQL_SCRIPT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/report-blocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReportBlocksRepository reportBlocksRepository;

    @Mock
    private ReportBlocksRepository reportBlocksRepositoryMock;

    @Autowired
    private ReportBlocksMapper reportBlocksMapper;

    @Mock
    private ReportBlocksService reportBlocksServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReportBlocksMockMvc;

    private ReportBlocks reportBlocks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportBlocks createEntity(EntityManager em) {
        ReportBlocks reportBlocks = new ReportBlocks()
            .countryName(DEFAULT_COUNTRY_NAME)
            .priorityNumber(DEFAULT_PRIORITY_NUMBER)
            .content(DEFAULT_CONTENT)
            .isActive(DEFAULT_IS_ACTIVE)
            .type(DEFAULT_TYPE)
            .sqlScript(DEFAULT_SQL_SCRIPT);
        return reportBlocks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportBlocks createUpdatedEntity(EntityManager em) {
        ReportBlocks reportBlocks = new ReportBlocks()
            .countryName(UPDATED_COUNTRY_NAME)
            .priorityNumber(UPDATED_PRIORITY_NUMBER)
            .content(UPDATED_CONTENT)
            .isActive(UPDATED_IS_ACTIVE)
            .type(UPDATED_TYPE)
            .sqlScript(UPDATED_SQL_SCRIPT);
        return reportBlocks;
    }

    @BeforeEach
    public void initTest() {
        reportBlocks = createEntity(em);
    }

    @Test
    @Transactional
    void createReportBlocks() throws Exception {
        int databaseSizeBeforeCreate = reportBlocksRepository.findAll().size();
        // Create the ReportBlocks
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);
        restReportBlocksMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeCreate + 1);
        ReportBlocks testReportBlocks = reportBlocksList.get(reportBlocksList.size() - 1);
        assertThat(testReportBlocks.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testReportBlocks.getPriorityNumber()).isEqualTo(DEFAULT_PRIORITY_NUMBER);
        assertThat(testReportBlocks.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testReportBlocks.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testReportBlocks.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReportBlocks.getSqlScript()).isEqualTo(DEFAULT_SQL_SCRIPT);
    }

    @Test
    @Transactional
    void createReportBlocksWithExistingId() throws Exception {
        // Create the ReportBlocks with an existing ID
        reportBlocks.setId(1L);
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);

        int databaseSizeBeforeCreate = reportBlocksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportBlocksMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReportBlocks() throws Exception {
        // Initialize the database
        reportBlocksRepository.saveAndFlush(reportBlocks);

        // Get all the reportBlocksList
        restReportBlocksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportBlocks.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].priorityNumber").value(hasItem(DEFAULT_PRIORITY_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].sqlScript").value(hasItem(DEFAULT_SQL_SCRIPT)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReportBlocksWithEagerRelationshipsIsEnabled() throws Exception {
        when(reportBlocksServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReportBlocksMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(reportBlocksServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllReportBlocksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(reportBlocksServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restReportBlocksMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(reportBlocksRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getReportBlocks() throws Exception {
        // Initialize the database
        reportBlocksRepository.saveAndFlush(reportBlocks);

        // Get the reportBlocks
        restReportBlocksMockMvc
            .perform(get(ENTITY_API_URL_ID, reportBlocks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reportBlocks.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.priorityNumber").value(DEFAULT_PRIORITY_NUMBER.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.sqlScript").value(DEFAULT_SQL_SCRIPT));
    }

    @Test
    @Transactional
    void getNonExistingReportBlocks() throws Exception {
        // Get the reportBlocks
        restReportBlocksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReportBlocks() throws Exception {
        // Initialize the database
        reportBlocksRepository.saveAndFlush(reportBlocks);

        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();

        // Update the reportBlocks
        ReportBlocks updatedReportBlocks = reportBlocksRepository.findById(reportBlocks.getId()).get();
        // Disconnect from session so that the updates on updatedReportBlocks are not directly saved in db
        em.detach(updatedReportBlocks);
        updatedReportBlocks
            .countryName(UPDATED_COUNTRY_NAME)
            .priorityNumber(UPDATED_PRIORITY_NUMBER)
            .content(UPDATED_CONTENT)
            .isActive(UPDATED_IS_ACTIVE)
            .type(UPDATED_TYPE)
            .sqlScript(UPDATED_SQL_SCRIPT);
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(updatedReportBlocks);

        restReportBlocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportBlocksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocks testReportBlocks = reportBlocksList.get(reportBlocksList.size() - 1);
        assertThat(testReportBlocks.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testReportBlocks.getPriorityNumber()).isEqualTo(UPDATED_PRIORITY_NUMBER);
        assertThat(testReportBlocks.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testReportBlocks.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReportBlocks.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportBlocks.getSqlScript()).isEqualTo(UPDATED_SQL_SCRIPT);
    }

    @Test
    @Transactional
    void putNonExistingReportBlocks() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();
        reportBlocks.setId(count.incrementAndGet());

        // Create the ReportBlocks
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportBlocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reportBlocksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReportBlocks() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();
        reportBlocks.setId(count.incrementAndGet());

        // Create the ReportBlocks
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReportBlocks() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();
        reportBlocks.setId(count.incrementAndGet());

        // Create the ReportBlocks
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReportBlocksWithPatch() throws Exception {
        // Initialize the database
        reportBlocksRepository.saveAndFlush(reportBlocks);

        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();

        // Update the reportBlocks using partial update
        ReportBlocks partialUpdatedReportBlocks = new ReportBlocks();
        partialUpdatedReportBlocks.setId(reportBlocks.getId());

        partialUpdatedReportBlocks.priorityNumber(UPDATED_PRIORITY_NUMBER).isActive(UPDATED_IS_ACTIVE).sqlScript(UPDATED_SQL_SCRIPT);

        restReportBlocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportBlocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportBlocks))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocks testReportBlocks = reportBlocksList.get(reportBlocksList.size() - 1);
        assertThat(testReportBlocks.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testReportBlocks.getPriorityNumber()).isEqualTo(UPDATED_PRIORITY_NUMBER);
        assertThat(testReportBlocks.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testReportBlocks.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReportBlocks.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReportBlocks.getSqlScript()).isEqualTo(UPDATED_SQL_SCRIPT);
    }

    @Test
    @Transactional
    void fullUpdateReportBlocksWithPatch() throws Exception {
        // Initialize the database
        reportBlocksRepository.saveAndFlush(reportBlocks);

        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();

        // Update the reportBlocks using partial update
        ReportBlocks partialUpdatedReportBlocks = new ReportBlocks();
        partialUpdatedReportBlocks.setId(reportBlocks.getId());

        partialUpdatedReportBlocks
            .countryName(UPDATED_COUNTRY_NAME)
            .priorityNumber(UPDATED_PRIORITY_NUMBER)
            .content(UPDATED_CONTENT)
            .isActive(UPDATED_IS_ACTIVE)
            .type(UPDATED_TYPE)
            .sqlScript(UPDATED_SQL_SCRIPT);

        restReportBlocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReportBlocks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReportBlocks))
            )
            .andExpect(status().isOk());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
        ReportBlocks testReportBlocks = reportBlocksList.get(reportBlocksList.size() - 1);
        assertThat(testReportBlocks.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testReportBlocks.getPriorityNumber()).isEqualTo(UPDATED_PRIORITY_NUMBER);
        assertThat(testReportBlocks.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testReportBlocks.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReportBlocks.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReportBlocks.getSqlScript()).isEqualTo(UPDATED_SQL_SCRIPT);
    }

    @Test
    @Transactional
    void patchNonExistingReportBlocks() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();
        reportBlocks.setId(count.incrementAndGet());

        // Create the ReportBlocks
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportBlocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reportBlocksDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReportBlocks() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();
        reportBlocks.setId(count.incrementAndGet());

        // Create the ReportBlocks
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReportBlocks() throws Exception {
        int databaseSizeBeforeUpdate = reportBlocksRepository.findAll().size();
        reportBlocks.setId(count.incrementAndGet());

        // Create the ReportBlocks
        ReportBlocksDTO reportBlocksDTO = reportBlocksMapper.toDto(reportBlocks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReportBlocksMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reportBlocksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReportBlocks in the database
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReportBlocks() throws Exception {
        // Initialize the database
        reportBlocksRepository.saveAndFlush(reportBlocks);

        int databaseSizeBeforeDelete = reportBlocksRepository.findAll().size();

        // Delete the reportBlocks
        restReportBlocksMockMvc
            .perform(delete(ENTITY_API_URL_ID, reportBlocks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReportBlocks> reportBlocksList = reportBlocksRepository.findAll();
        assertThat(reportBlocksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
