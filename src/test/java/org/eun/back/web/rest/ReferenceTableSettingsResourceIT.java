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
import org.eun.back.domain.ReferenceTableSettings;
import org.eun.back.repository.ReferenceTableSettingsRepository;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.eun.back.service.mapper.ReferenceTableSettingsMapper;
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
 * Integration tests for the {@link ReferenceTableSettingsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReferenceTableSettingsResourceIT {

    private static final String DEFAULT_REF_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_REF_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMNS = "AAAAAAAAAA";
    private static final String UPDATED_COLUMNS = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMNS_OF_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_COLUMNS_OF_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ACTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/reference-table-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReferenceTableSettingsRepository referenceTableSettingsRepository;

    @Autowired
    private ReferenceTableSettingsMapper referenceTableSettingsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReferenceTableSettingsMockMvc;

    private ReferenceTableSettings referenceTableSettings;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceTableSettings createEntity(EntityManager em) {
        ReferenceTableSettings referenceTableSettings = new ReferenceTableSettings()
            .refTable(DEFAULT_REF_TABLE)
            .displayName(DEFAULT_DISPLAY_NAME)
            .columns(DEFAULT_COLUMNS)
            .columnsOfTemplate(DEFAULT_COLUMNS_OF_TEMPLATE)
            .actions(DEFAULT_ACTIONS)
            .path(DEFAULT_PATH)
            .isActive(DEFAULT_IS_ACTIVE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return referenceTableSettings;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceTableSettings createUpdatedEntity(EntityManager em) {
        ReferenceTableSettings referenceTableSettings = new ReferenceTableSettings()
            .refTable(UPDATED_REF_TABLE)
            .displayName(UPDATED_DISPLAY_NAME)
            .columns(UPDATED_COLUMNS)
            .columnsOfTemplate(UPDATED_COLUMNS_OF_TEMPLATE)
            .actions(UPDATED_ACTIONS)
            .path(UPDATED_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        return referenceTableSettings;
    }

    @BeforeEach
    public void initTest() {
        referenceTableSettings = createEntity(em);
    }

    @Test
    @Transactional
    void createReferenceTableSettings() throws Exception {
        int databaseSizeBeforeCreate = referenceTableSettingsRepository.findAll().size();
        // Create the ReferenceTableSettings
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);
        restReferenceTableSettingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        ReferenceTableSettings testReferenceTableSettings = referenceTableSettingsList.get(referenceTableSettingsList.size() - 1);
        assertThat(testReferenceTableSettings.getRefTable()).isEqualTo(DEFAULT_REF_TABLE);
        assertThat(testReferenceTableSettings.getDisplayName()).isEqualTo(DEFAULT_DISPLAY_NAME);
        assertThat(testReferenceTableSettings.getColumns()).isEqualTo(DEFAULT_COLUMNS);
        assertThat(testReferenceTableSettings.getColumnsOfTemplate()).isEqualTo(DEFAULT_COLUMNS_OF_TEMPLATE);
        assertThat(testReferenceTableSettings.getActions()).isEqualTo(DEFAULT_ACTIONS);
        assertThat(testReferenceTableSettings.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testReferenceTableSettings.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testReferenceTableSettings.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testReferenceTableSettings.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createReferenceTableSettingsWithExistingId() throws Exception {
        // Create the ReferenceTableSettings with an existing ID
        referenceTableSettings.setId(1L);
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);

        int databaseSizeBeforeCreate = referenceTableSettingsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenceTableSettingsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReferenceTableSettings() throws Exception {
        // Initialize the database
        referenceTableSettingsRepository.saveAndFlush(referenceTableSettings);

        // Get all the referenceTableSettingsList
        restReferenceTableSettingsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceTableSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].refTable").value(hasItem(DEFAULT_REF_TABLE)))
            .andExpect(jsonPath("$.[*].displayName").value(hasItem(DEFAULT_DISPLAY_NAME)))
            .andExpect(jsonPath("$.[*].columns").value(hasItem(DEFAULT_COLUMNS)))
            .andExpect(jsonPath("$.[*].columnsOfTemplate").value(hasItem(DEFAULT_COLUMNS_OF_TEMPLATE)))
            .andExpect(jsonPath("$.[*].actions").value(hasItem(DEFAULT_ACTIONS)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    void getReferenceTableSettings() throws Exception {
        // Initialize the database
        referenceTableSettingsRepository.saveAndFlush(referenceTableSettings);

        // Get the referenceTableSettings
        restReferenceTableSettingsMockMvc
            .perform(get(ENTITY_API_URL_ID, referenceTableSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(referenceTableSettings.getId().intValue()))
            .andExpect(jsonPath("$.refTable").value(DEFAULT_REF_TABLE))
            .andExpect(jsonPath("$.displayName").value(DEFAULT_DISPLAY_NAME))
            .andExpect(jsonPath("$.columns").value(DEFAULT_COLUMNS))
            .andExpect(jsonPath("$.columnsOfTemplate").value(DEFAULT_COLUMNS_OF_TEMPLATE))
            .andExpect(jsonPath("$.actions").value(DEFAULT_ACTIONS))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    void getNonExistingReferenceTableSettings() throws Exception {
        // Get the referenceTableSettings
        restReferenceTableSettingsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReferenceTableSettings() throws Exception {
        // Initialize the database
        referenceTableSettingsRepository.saveAndFlush(referenceTableSettings);

        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();

        // Update the referenceTableSettings
        ReferenceTableSettings updatedReferenceTableSettings = referenceTableSettingsRepository
            .findById(referenceTableSettings.getId())
            .get();
        // Disconnect from session so that the updates on updatedReferenceTableSettings are not directly saved in db
        em.detach(updatedReferenceTableSettings);
        updatedReferenceTableSettings
            .refTable(UPDATED_REF_TABLE)
            .displayName(UPDATED_DISPLAY_NAME)
            .columns(UPDATED_COLUMNS)
            .columnsOfTemplate(UPDATED_COLUMNS_OF_TEMPLATE)
            .actions(UPDATED_ACTIONS)
            .path(UPDATED_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(updatedReferenceTableSettings);

        restReferenceTableSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, referenceTableSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
        ReferenceTableSettings testReferenceTableSettings = referenceTableSettingsList.get(referenceTableSettingsList.size() - 1);
        assertThat(testReferenceTableSettings.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testReferenceTableSettings.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testReferenceTableSettings.getColumns()).isEqualTo(UPDATED_COLUMNS);
        assertThat(testReferenceTableSettings.getColumnsOfTemplate()).isEqualTo(UPDATED_COLUMNS_OF_TEMPLATE);
        assertThat(testReferenceTableSettings.getActions()).isEqualTo(UPDATED_ACTIONS);
        assertThat(testReferenceTableSettings.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testReferenceTableSettings.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReferenceTableSettings.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testReferenceTableSettings.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingReferenceTableSettings() throws Exception {
        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();
        referenceTableSettings.setId(count.incrementAndGet());

        // Create the ReferenceTableSettings
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceTableSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, referenceTableSettingsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReferenceTableSettings() throws Exception {
        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();
        referenceTableSettings.setId(count.incrementAndGet());

        // Create the ReferenceTableSettings
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceTableSettingsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReferenceTableSettings() throws Exception {
        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();
        referenceTableSettings.setId(count.incrementAndGet());

        // Create the ReferenceTableSettings
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceTableSettingsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReferenceTableSettingsWithPatch() throws Exception {
        // Initialize the database
        referenceTableSettingsRepository.saveAndFlush(referenceTableSettings);

        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();

        // Update the referenceTableSettings using partial update
        ReferenceTableSettings partialUpdatedReferenceTableSettings = new ReferenceTableSettings();
        partialUpdatedReferenceTableSettings.setId(referenceTableSettings.getId());

        partialUpdatedReferenceTableSettings.refTable(UPDATED_REF_TABLE).displayName(UPDATED_DISPLAY_NAME).isActive(UPDATED_IS_ACTIVE);

        restReferenceTableSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReferenceTableSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReferenceTableSettings))
            )
            .andExpect(status().isOk());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
        ReferenceTableSettings testReferenceTableSettings = referenceTableSettingsList.get(referenceTableSettingsList.size() - 1);
        assertThat(testReferenceTableSettings.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testReferenceTableSettings.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testReferenceTableSettings.getColumns()).isEqualTo(DEFAULT_COLUMNS);
        assertThat(testReferenceTableSettings.getColumnsOfTemplate()).isEqualTo(DEFAULT_COLUMNS_OF_TEMPLATE);
        assertThat(testReferenceTableSettings.getActions()).isEqualTo(DEFAULT_ACTIONS);
        assertThat(testReferenceTableSettings.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testReferenceTableSettings.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReferenceTableSettings.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testReferenceTableSettings.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateReferenceTableSettingsWithPatch() throws Exception {
        // Initialize the database
        referenceTableSettingsRepository.saveAndFlush(referenceTableSettings);

        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();

        // Update the referenceTableSettings using partial update
        ReferenceTableSettings partialUpdatedReferenceTableSettings = new ReferenceTableSettings();
        partialUpdatedReferenceTableSettings.setId(referenceTableSettings.getId());

        partialUpdatedReferenceTableSettings
            .refTable(UPDATED_REF_TABLE)
            .displayName(UPDATED_DISPLAY_NAME)
            .columns(UPDATED_COLUMNS)
            .columnsOfTemplate(UPDATED_COLUMNS_OF_TEMPLATE)
            .actions(UPDATED_ACTIONS)
            .path(UPDATED_PATH)
            .isActive(UPDATED_IS_ACTIVE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);

        restReferenceTableSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReferenceTableSettings.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReferenceTableSettings))
            )
            .andExpect(status().isOk());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
        ReferenceTableSettings testReferenceTableSettings = referenceTableSettingsList.get(referenceTableSettingsList.size() - 1);
        assertThat(testReferenceTableSettings.getRefTable()).isEqualTo(UPDATED_REF_TABLE);
        assertThat(testReferenceTableSettings.getDisplayName()).isEqualTo(UPDATED_DISPLAY_NAME);
        assertThat(testReferenceTableSettings.getColumns()).isEqualTo(UPDATED_COLUMNS);
        assertThat(testReferenceTableSettings.getColumnsOfTemplate()).isEqualTo(UPDATED_COLUMNS_OF_TEMPLATE);
        assertThat(testReferenceTableSettings.getActions()).isEqualTo(UPDATED_ACTIONS);
        assertThat(testReferenceTableSettings.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testReferenceTableSettings.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testReferenceTableSettings.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testReferenceTableSettings.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingReferenceTableSettings() throws Exception {
        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();
        referenceTableSettings.setId(count.incrementAndGet());

        // Create the ReferenceTableSettings
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceTableSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, referenceTableSettingsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReferenceTableSettings() throws Exception {
        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();
        referenceTableSettings.setId(count.incrementAndGet());

        // Create the ReferenceTableSettings
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceTableSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReferenceTableSettings() throws Exception {
        int databaseSizeBeforeUpdate = referenceTableSettingsRepository.findAll().size();
        referenceTableSettings.setId(count.incrementAndGet());

        // Create the ReferenceTableSettings
        ReferenceTableSettingsDTO referenceTableSettingsDTO = referenceTableSettingsMapper.toDto(referenceTableSettings);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceTableSettingsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(referenceTableSettingsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReferenceTableSettings in the database
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReferenceTableSettings() throws Exception {
        // Initialize the database
        referenceTableSettingsRepository.saveAndFlush(referenceTableSettings);

        int databaseSizeBeforeDelete = referenceTableSettingsRepository.findAll().size();

        // Delete the referenceTableSettings
        restReferenceTableSettingsMockMvc
            .perform(delete(ENTITY_API_URL_ID, referenceTableSettings.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReferenceTableSettings> referenceTableSettingsList = referenceTableSettingsRepository.findAll();
        assertThat(referenceTableSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
