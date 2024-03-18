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
import org.eun.back.domain.MOEParticipationReferences;
import org.eun.back.repository.MOEParticipationReferencesRepository;
import org.eun.back.service.dto.MOEParticipationReferencesDTO;
import org.eun.back.service.mapper.MOEParticipationReferencesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MOEParticipationReferencesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MOEParticipationReferencesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/moe-participation-references";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MOEParticipationReferencesRepository mOEParticipationReferencesRepository;

    @Autowired
    private MOEParticipationReferencesMapper mOEParticipationReferencesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMOEParticipationReferencesMockMvc;

    private MOEParticipationReferences mOEParticipationReferences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MOEParticipationReferences createEntity(EntityManager em) {
        MOEParticipationReferences mOEParticipationReferences = new MOEParticipationReferences()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return mOEParticipationReferences;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MOEParticipationReferences createUpdatedEntity(EntityManager em) {
        MOEParticipationReferences mOEParticipationReferences = new MOEParticipationReferences()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return mOEParticipationReferences;
    }

    @BeforeEach
    public void initTest() {
        mOEParticipationReferences = createEntity(em);
    }

    @Test
    @Transactional
    void createMOEParticipationReferences() throws Exception {
        int databaseSizeBeforeCreate = mOEParticipationReferencesRepository.findAll().size();
        // Create the MOEParticipationReferences
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);
        restMOEParticipationReferencesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeCreate + 1);
        MOEParticipationReferences testMOEParticipationReferences = mOEParticipationReferencesList.get(
            mOEParticipationReferencesList.size() - 1
        );
        assertThat(testMOEParticipationReferences.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMOEParticipationReferences.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMOEParticipationReferences.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMOEParticipationReferences.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testMOEParticipationReferences.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMOEParticipationReferences.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMOEParticipationReferences.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMOEParticipationReferences.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMOEParticipationReferences.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createMOEParticipationReferencesWithExistingId() throws Exception {
        // Create the MOEParticipationReferences with an existing ID
        mOEParticipationReferences.setId(1L);
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);

        int databaseSizeBeforeCreate = mOEParticipationReferencesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMOEParticipationReferencesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMOEParticipationReferences() throws Exception {
        // Initialize the database
        mOEParticipationReferencesRepository.saveAndFlush(mOEParticipationReferences);

        // Get all the mOEParticipationReferencesList
        restMOEParticipationReferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mOEParticipationReferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getMOEParticipationReferences() throws Exception {
        // Initialize the database
        mOEParticipationReferencesRepository.saveAndFlush(mOEParticipationReferences);

        // Get the mOEParticipationReferences
        restMOEParticipationReferencesMockMvc
            .perform(get(ENTITY_API_URL_ID, mOEParticipationReferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mOEParticipationReferences.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMOEParticipationReferences() throws Exception {
        // Get the mOEParticipationReferences
        restMOEParticipationReferencesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMOEParticipationReferences() throws Exception {
        // Initialize the database
        mOEParticipationReferencesRepository.saveAndFlush(mOEParticipationReferences);

        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();

        // Update the mOEParticipationReferences
        MOEParticipationReferences updatedMOEParticipationReferences = mOEParticipationReferencesRepository
            .findById(mOEParticipationReferences.getId())
            .get();
        // Disconnect from session so that the updates on updatedMOEParticipationReferences are not directly saved in db
        em.detach(updatedMOEParticipationReferences);
        updatedMOEParticipationReferences
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(
            updatedMOEParticipationReferences
        );

        restMOEParticipationReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mOEParticipationReferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isOk());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
        MOEParticipationReferences testMOEParticipationReferences = mOEParticipationReferencesList.get(
            mOEParticipationReferencesList.size() - 1
        );
        assertThat(testMOEParticipationReferences.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMOEParticipationReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMOEParticipationReferences.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMOEParticipationReferences.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMOEParticipationReferences.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMOEParticipationReferences.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMOEParticipationReferences.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMOEParticipationReferences.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMOEParticipationReferences.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMOEParticipationReferences() throws Exception {
        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();
        mOEParticipationReferences.setId(count.incrementAndGet());

        // Create the MOEParticipationReferences
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMOEParticipationReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mOEParticipationReferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMOEParticipationReferences() throws Exception {
        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();
        mOEParticipationReferences.setId(count.incrementAndGet());

        // Create the MOEParticipationReferences
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMOEParticipationReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMOEParticipationReferences() throws Exception {
        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();
        mOEParticipationReferences.setId(count.incrementAndGet());

        // Create the MOEParticipationReferences
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMOEParticipationReferencesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMOEParticipationReferencesWithPatch() throws Exception {
        // Initialize the database
        mOEParticipationReferencesRepository.saveAndFlush(mOEParticipationReferences);

        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();

        // Update the mOEParticipationReferences using partial update
        MOEParticipationReferences partialUpdatedMOEParticipationReferences = new MOEParticipationReferences();
        partialUpdatedMOEParticipationReferences.setId(mOEParticipationReferences.getId());

        partialUpdatedMOEParticipationReferences
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMOEParticipationReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMOEParticipationReferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMOEParticipationReferences))
            )
            .andExpect(status().isOk());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
        MOEParticipationReferences testMOEParticipationReferences = mOEParticipationReferencesList.get(
            mOEParticipationReferencesList.size() - 1
        );
        assertThat(testMOEParticipationReferences.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMOEParticipationReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMOEParticipationReferences.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMOEParticipationReferences.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMOEParticipationReferences.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMOEParticipationReferences.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMOEParticipationReferences.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMOEParticipationReferences.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMOEParticipationReferences.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMOEParticipationReferencesWithPatch() throws Exception {
        // Initialize the database
        mOEParticipationReferencesRepository.saveAndFlush(mOEParticipationReferences);

        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();

        // Update the mOEParticipationReferences using partial update
        MOEParticipationReferences partialUpdatedMOEParticipationReferences = new MOEParticipationReferences();
        partialUpdatedMOEParticipationReferences.setId(mOEParticipationReferences.getId());

        partialUpdatedMOEParticipationReferences
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMOEParticipationReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMOEParticipationReferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMOEParticipationReferences))
            )
            .andExpect(status().isOk());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
        MOEParticipationReferences testMOEParticipationReferences = mOEParticipationReferencesList.get(
            mOEParticipationReferencesList.size() - 1
        );
        assertThat(testMOEParticipationReferences.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMOEParticipationReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMOEParticipationReferences.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMOEParticipationReferences.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testMOEParticipationReferences.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMOEParticipationReferences.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMOEParticipationReferences.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMOEParticipationReferences.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMOEParticipationReferences.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMOEParticipationReferences() throws Exception {
        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();
        mOEParticipationReferences.setId(count.incrementAndGet());

        // Create the MOEParticipationReferences
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMOEParticipationReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mOEParticipationReferencesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMOEParticipationReferences() throws Exception {
        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();
        mOEParticipationReferences.setId(count.incrementAndGet());

        // Create the MOEParticipationReferences
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMOEParticipationReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMOEParticipationReferences() throws Exception {
        int databaseSizeBeforeUpdate = mOEParticipationReferencesRepository.findAll().size();
        mOEParticipationReferences.setId(count.incrementAndGet());

        // Create the MOEParticipationReferences
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO = mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMOEParticipationReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mOEParticipationReferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MOEParticipationReferences in the database
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMOEParticipationReferences() throws Exception {
        // Initialize the database
        mOEParticipationReferencesRepository.saveAndFlush(mOEParticipationReferences);

        int databaseSizeBeforeDelete = mOEParticipationReferencesRepository.findAll().size();

        // Delete the mOEParticipationReferences
        restMOEParticipationReferencesMockMvc
            .perform(delete(ENTITY_API_URL_ID, mOEParticipationReferences.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MOEParticipationReferences> mOEParticipationReferencesList = mOEParticipationReferencesRepository.findAll();
        assertThat(mOEParticipationReferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
