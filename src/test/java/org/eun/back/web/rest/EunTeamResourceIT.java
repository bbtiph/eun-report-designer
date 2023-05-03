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
import org.eun.back.domain.EunTeam;
import org.eun.back.repository.EunTeamRepository;
import org.eun.back.service.dto.EunTeamDTO;
import org.eun.back.service.mapper.EunTeamMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EunTeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EunTeamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/eun-teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EunTeamRepository eunTeamRepository;

    @Autowired
    private EunTeamMapper eunTeamMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEunTeamMockMvc;

    private EunTeam eunTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EunTeam createEntity(EntityManager em) {
        EunTeam eunTeam = new EunTeam().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return eunTeam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EunTeam createUpdatedEntity(EntityManager em) {
        EunTeam eunTeam = new EunTeam().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return eunTeam;
    }

    @BeforeEach
    public void initTest() {
        eunTeam = createEntity(em);
    }

    @Test
    @Transactional
    void createEunTeam() throws Exception {
        int databaseSizeBeforeCreate = eunTeamRepository.findAll().size();
        // Create the EunTeam
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);
        restEunTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eunTeamDTO)))
            .andExpect(status().isCreated());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeCreate + 1);
        EunTeam testEunTeam = eunTeamList.get(eunTeamList.size() - 1);
        assertThat(testEunTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEunTeam.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createEunTeamWithExistingId() throws Exception {
        // Create the EunTeam with an existing ID
        eunTeam.setId(1L);
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);

        int databaseSizeBeforeCreate = eunTeamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEunTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eunTeamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEunTeams() throws Exception {
        // Initialize the database
        eunTeamRepository.saveAndFlush(eunTeam);

        // Get all the eunTeamList
        restEunTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eunTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getEunTeam() throws Exception {
        // Initialize the database
        eunTeamRepository.saveAndFlush(eunTeam);

        // Get the eunTeam
        restEunTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, eunTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eunTeam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingEunTeam() throws Exception {
        // Get the eunTeam
        restEunTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEunTeam() throws Exception {
        // Initialize the database
        eunTeamRepository.saveAndFlush(eunTeam);

        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();

        // Update the eunTeam
        EunTeam updatedEunTeam = eunTeamRepository.findById(eunTeam.getId()).get();
        // Disconnect from session so that the updates on updatedEunTeam are not directly saved in db
        em.detach(updatedEunTeam);
        updatedEunTeam.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(updatedEunTeam);

        restEunTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eunTeamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamDTO))
            )
            .andExpect(status().isOk());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
        EunTeam testEunTeam = eunTeamList.get(eunTeamList.size() - 1);
        assertThat(testEunTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEunTeam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingEunTeam() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();
        eunTeam.setId(count.incrementAndGet());

        // Create the EunTeam
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEunTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eunTeamDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEunTeam() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();
        eunTeam.setId(count.incrementAndGet());

        // Create the EunTeam
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEunTeam() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();
        eunTeam.setId(count.incrementAndGet());

        // Create the EunTeam
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eunTeamDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEunTeamWithPatch() throws Exception {
        // Initialize the database
        eunTeamRepository.saveAndFlush(eunTeam);

        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();

        // Update the eunTeam using partial update
        EunTeam partialUpdatedEunTeam = new EunTeam();
        partialUpdatedEunTeam.setId(eunTeam.getId());

        partialUpdatedEunTeam.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restEunTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEunTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEunTeam))
            )
            .andExpect(status().isOk());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
        EunTeam testEunTeam = eunTeamList.get(eunTeamList.size() - 1);
        assertThat(testEunTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEunTeam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateEunTeamWithPatch() throws Exception {
        // Initialize the database
        eunTeamRepository.saveAndFlush(eunTeam);

        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();

        // Update the eunTeam using partial update
        EunTeam partialUpdatedEunTeam = new EunTeam();
        partialUpdatedEunTeam.setId(eunTeam.getId());

        partialUpdatedEunTeam.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restEunTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEunTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEunTeam))
            )
            .andExpect(status().isOk());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
        EunTeam testEunTeam = eunTeamList.get(eunTeamList.size() - 1);
        assertThat(testEunTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEunTeam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingEunTeam() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();
        eunTeam.setId(count.incrementAndGet());

        // Create the EunTeam
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEunTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eunTeamDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEunTeam() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();
        eunTeam.setId(count.incrementAndGet());

        // Create the EunTeam
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEunTeam() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamRepository.findAll().size();
        eunTeam.setId(count.incrementAndGet());

        // Create the EunTeam
        EunTeamDTO eunTeamDTO = eunTeamMapper.toDto(eunTeam);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eunTeamDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EunTeam in the database
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEunTeam() throws Exception {
        // Initialize the database
        eunTeamRepository.saveAndFlush(eunTeam);

        int databaseSizeBeforeDelete = eunTeamRepository.findAll().size();

        // Delete the eunTeam
        restEunTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, eunTeam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EunTeam> eunTeamList = eunTeamRepository.findAll();
        assertThat(eunTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
