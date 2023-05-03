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
import org.eun.back.domain.EunTeamMember;
import org.eun.back.repository.EunTeamMemberRepository;
import org.eun.back.service.dto.EunTeamMemberDTO;
import org.eun.back.service.mapper.EunTeamMemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EunTeamMemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EunTeamMemberResourceIT {

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/eun-team-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EunTeamMemberRepository eunTeamMemberRepository;

    @Autowired
    private EunTeamMemberMapper eunTeamMemberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEunTeamMemberMockMvc;

    private EunTeamMember eunTeamMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EunTeamMember createEntity(EntityManager em) {
        EunTeamMember eunTeamMember = new EunTeamMember().role(DEFAULT_ROLE).status(DEFAULT_STATUS);
        return eunTeamMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EunTeamMember createUpdatedEntity(EntityManager em) {
        EunTeamMember eunTeamMember = new EunTeamMember().role(UPDATED_ROLE).status(UPDATED_STATUS);
        return eunTeamMember;
    }

    @BeforeEach
    public void initTest() {
        eunTeamMember = createEntity(em);
    }

    @Test
    @Transactional
    void createEunTeamMember() throws Exception {
        int databaseSizeBeforeCreate = eunTeamMemberRepository.findAll().size();
        // Create the EunTeamMember
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);
        restEunTeamMemberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeCreate + 1);
        EunTeamMember testEunTeamMember = eunTeamMemberList.get(eunTeamMemberList.size() - 1);
        assertThat(testEunTeamMember.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testEunTeamMember.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createEunTeamMemberWithExistingId() throws Exception {
        // Create the EunTeamMember with an existing ID
        eunTeamMember.setId(1L);
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);

        int databaseSizeBeforeCreate = eunTeamMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEunTeamMemberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEunTeamMembers() throws Exception {
        // Initialize the database
        eunTeamMemberRepository.saveAndFlush(eunTeamMember);

        // Get all the eunTeamMemberList
        restEunTeamMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eunTeamMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getEunTeamMember() throws Exception {
        // Initialize the database
        eunTeamMemberRepository.saveAndFlush(eunTeamMember);

        // Get the eunTeamMember
        restEunTeamMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, eunTeamMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eunTeamMember.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingEunTeamMember() throws Exception {
        // Get the eunTeamMember
        restEunTeamMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEunTeamMember() throws Exception {
        // Initialize the database
        eunTeamMemberRepository.saveAndFlush(eunTeamMember);

        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();

        // Update the eunTeamMember
        EunTeamMember updatedEunTeamMember = eunTeamMemberRepository.findById(eunTeamMember.getId()).get();
        // Disconnect from session so that the updates on updatedEunTeamMember are not directly saved in db
        em.detach(updatedEunTeamMember);
        updatedEunTeamMember.role(UPDATED_ROLE).status(UPDATED_STATUS);
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(updatedEunTeamMember);

        restEunTeamMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eunTeamMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isOk());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
        EunTeamMember testEunTeamMember = eunTeamMemberList.get(eunTeamMemberList.size() - 1);
        assertThat(testEunTeamMember.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testEunTeamMember.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingEunTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();
        eunTeamMember.setId(count.incrementAndGet());

        // Create the EunTeamMember
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEunTeamMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eunTeamMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEunTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();
        eunTeamMember.setId(count.incrementAndGet());

        // Create the EunTeamMember
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEunTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();
        eunTeamMember.setId(count.incrementAndGet());

        // Create the EunTeamMember
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMemberMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEunTeamMemberWithPatch() throws Exception {
        // Initialize the database
        eunTeamMemberRepository.saveAndFlush(eunTeamMember);

        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();

        // Update the eunTeamMember using partial update
        EunTeamMember partialUpdatedEunTeamMember = new EunTeamMember();
        partialUpdatedEunTeamMember.setId(eunTeamMember.getId());

        partialUpdatedEunTeamMember.status(UPDATED_STATUS);

        restEunTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEunTeamMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEunTeamMember))
            )
            .andExpect(status().isOk());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
        EunTeamMember testEunTeamMember = eunTeamMemberList.get(eunTeamMemberList.size() - 1);
        assertThat(testEunTeamMember.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testEunTeamMember.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateEunTeamMemberWithPatch() throws Exception {
        // Initialize the database
        eunTeamMemberRepository.saveAndFlush(eunTeamMember);

        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();

        // Update the eunTeamMember using partial update
        EunTeamMember partialUpdatedEunTeamMember = new EunTeamMember();
        partialUpdatedEunTeamMember.setId(eunTeamMember.getId());

        partialUpdatedEunTeamMember.role(UPDATED_ROLE).status(UPDATED_STATUS);

        restEunTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEunTeamMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEunTeamMember))
            )
            .andExpect(status().isOk());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
        EunTeamMember testEunTeamMember = eunTeamMemberList.get(eunTeamMemberList.size() - 1);
        assertThat(testEunTeamMember.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testEunTeamMember.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingEunTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();
        eunTeamMember.setId(count.incrementAndGet());

        // Create the EunTeamMember
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEunTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eunTeamMemberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEunTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();
        eunTeamMember.setId(count.incrementAndGet());

        // Create the EunTeamMember
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEunTeamMember() throws Exception {
        int databaseSizeBeforeUpdate = eunTeamMemberRepository.findAll().size();
        eunTeamMember.setId(count.incrementAndGet());

        // Create the EunTeamMember
        EunTeamMemberDTO eunTeamMemberDTO = eunTeamMemberMapper.toDto(eunTeamMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEunTeamMemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eunTeamMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EunTeamMember in the database
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEunTeamMember() throws Exception {
        // Initialize the database
        eunTeamMemberRepository.saveAndFlush(eunTeamMember);

        int databaseSizeBeforeDelete = eunTeamMemberRepository.findAll().size();

        // Delete the eunTeamMember
        restEunTeamMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, eunTeamMember.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EunTeamMember> eunTeamMemberList = eunTeamMemberRepository.findAll();
        assertThat(eunTeamMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
