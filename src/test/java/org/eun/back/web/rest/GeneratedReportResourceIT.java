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
import org.eun.back.domain.GeneratedReport;
import org.eun.back.repository.GeneratedReportRepository;
import org.eun.back.service.dto.GeneratedReportDTO;
import org.eun.back.service.mapper.GeneratedReportMapper;
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
 * Integration tests for the {@link GeneratedReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeneratedReportResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_BODY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/generated-reports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GeneratedReportRepository generatedReportRepository;

    @Autowired
    private GeneratedReportMapper generatedReportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeneratedReportMockMvc;

    private GeneratedReport generatedReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneratedReport createEntity(EntityManager em) {
        GeneratedReport generatedReport = new GeneratedReport()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .requestBody(DEFAULT_REQUEST_BODY)
            .isActive(DEFAULT_IS_ACTIVE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return generatedReport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneratedReport createUpdatedEntity(EntityManager em) {
        GeneratedReport generatedReport = new GeneratedReport()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .requestBody(UPDATED_REQUEST_BODY)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return generatedReport;
    }

    @BeforeEach
    public void initTest() {
        generatedReport = createEntity(em);
    }

    @Test
    @Transactional
    void createGeneratedReport() throws Exception {
        int databaseSizeBeforeCreate = generatedReportRepository.findAll().size();
        // Create the GeneratedReport
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);
        restGeneratedReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeCreate + 1);
        GeneratedReport testGeneratedReport = generatedReportList.get(generatedReportList.size() - 1);
        assertThat(testGeneratedReport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeneratedReport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGeneratedReport.getRequestBody()).isEqualTo(DEFAULT_REQUEST_BODY);
        assertThat(testGeneratedReport.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testGeneratedReport.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testGeneratedReport.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testGeneratedReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGeneratedReport.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testGeneratedReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGeneratedReport.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createGeneratedReportWithExistingId() throws Exception {
        // Create the GeneratedReport with an existing ID
        generatedReport.setId(1L);
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);

        int databaseSizeBeforeCreate = generatedReportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneratedReportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGeneratedReports() throws Exception {
        // Initialize the database
        generatedReportRepository.saveAndFlush(generatedReport);

        // Get all the generatedReportList
        restGeneratedReportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generatedReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY)))
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
    void getGeneratedReport() throws Exception {
        // Initialize the database
        generatedReportRepository.saveAndFlush(generatedReport);

        // Get the generatedReport
        restGeneratedReportMockMvc
            .perform(get(ENTITY_API_URL_ID, generatedReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(generatedReport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.requestBody").value(DEFAULT_REQUEST_BODY))
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
    void getNonExistingGeneratedReport() throws Exception {
        // Get the generatedReport
        restGeneratedReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGeneratedReport() throws Exception {
        // Initialize the database
        generatedReportRepository.saveAndFlush(generatedReport);

        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();

        // Update the generatedReport
        GeneratedReport updatedGeneratedReport = generatedReportRepository.findById(generatedReport.getId()).get();
        // Disconnect from session so that the updates on updatedGeneratedReport are not directly saved in db
        em.detach(updatedGeneratedReport);
        updatedGeneratedReport
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .requestBody(UPDATED_REQUEST_BODY)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(updatedGeneratedReport);

        restGeneratedReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generatedReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isOk());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
        GeneratedReport testGeneratedReport = generatedReportList.get(generatedReportList.size() - 1);
        assertThat(testGeneratedReport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeneratedReport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGeneratedReport.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testGeneratedReport.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGeneratedReport.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testGeneratedReport.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testGeneratedReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGeneratedReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGeneratedReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGeneratedReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingGeneratedReport() throws Exception {
        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();
        generatedReport.setId(count.incrementAndGet());

        // Create the GeneratedReport
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneratedReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generatedReportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeneratedReport() throws Exception {
        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();
        generatedReport.setId(count.incrementAndGet());

        // Create the GeneratedReport
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneratedReportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeneratedReport() throws Exception {
        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();
        generatedReport.setId(count.incrementAndGet());

        // Create the GeneratedReport
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneratedReportMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeneratedReportWithPatch() throws Exception {
        // Initialize the database
        generatedReportRepository.saveAndFlush(generatedReport);

        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();

        // Update the generatedReport using partial update
        GeneratedReport partialUpdatedGeneratedReport = new GeneratedReport();
        partialUpdatedGeneratedReport.setId(generatedReport.getId());

        partialUpdatedGeneratedReport
            .description(UPDATED_DESCRIPTION)
            .requestBody(UPDATED_REQUEST_BODY)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restGeneratedReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneratedReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneratedReport))
            )
            .andExpect(status().isOk());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
        GeneratedReport testGeneratedReport = generatedReportList.get(generatedReportList.size() - 1);
        assertThat(testGeneratedReport.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeneratedReport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGeneratedReport.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testGeneratedReport.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGeneratedReport.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testGeneratedReport.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testGeneratedReport.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGeneratedReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGeneratedReport.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testGeneratedReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateGeneratedReportWithPatch() throws Exception {
        // Initialize the database
        generatedReportRepository.saveAndFlush(generatedReport);

        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();

        // Update the generatedReport using partial update
        GeneratedReport partialUpdatedGeneratedReport = new GeneratedReport();
        partialUpdatedGeneratedReport.setId(generatedReport.getId());

        partialUpdatedGeneratedReport
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .requestBody(UPDATED_REQUEST_BODY)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restGeneratedReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneratedReport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneratedReport))
            )
            .andExpect(status().isOk());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
        GeneratedReport testGeneratedReport = generatedReportList.get(generatedReportList.size() - 1);
        assertThat(testGeneratedReport.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeneratedReport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGeneratedReport.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testGeneratedReport.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGeneratedReport.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testGeneratedReport.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testGeneratedReport.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGeneratedReport.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGeneratedReport.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testGeneratedReport.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingGeneratedReport() throws Exception {
        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();
        generatedReport.setId(count.incrementAndGet());

        // Create the GeneratedReport
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneratedReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, generatedReportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeneratedReport() throws Exception {
        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();
        generatedReport.setId(count.incrementAndGet());

        // Create the GeneratedReport
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneratedReportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeneratedReport() throws Exception {
        int databaseSizeBeforeUpdate = generatedReportRepository.findAll().size();
        generatedReport.setId(count.incrementAndGet());

        // Create the GeneratedReport
        GeneratedReportDTO generatedReportDTO = generatedReportMapper.toDto(generatedReport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneratedReportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generatedReportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeneratedReport in the database
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeneratedReport() throws Exception {
        // Initialize the database
        generatedReportRepository.saveAndFlush(generatedReport);

        int databaseSizeBeforeDelete = generatedReportRepository.findAll().size();

        // Delete the generatedReport
        restGeneratedReportMockMvc
            .perform(delete(ENTITY_API_URL_ID, generatedReport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeneratedReport> generatedReportList = generatedReportRepository.findAll();
        assertThat(generatedReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
