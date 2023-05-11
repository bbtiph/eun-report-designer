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
import org.eun.back.domain.Privilege;
import org.eun.back.repository.PrivilegeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrivilegeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrivilegeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/privileges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrivilegeMockMvc;

    private Privilege privilege;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Privilege createEntity(EntityManager em) {
        Privilege privilege = new Privilege().name(DEFAULT_NAME);
        return privilege;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Privilege createUpdatedEntity(EntityManager em) {
        Privilege privilege = new Privilege().name(UPDATED_NAME);
        return privilege;
    }

    @BeforeEach
    public void initTest() {
        privilege = createEntity(em);
    }

    @Test
    @Transactional
    void createPrivilege() throws Exception {
        int databaseSizeBeforeCreate = privilegeRepository.findAll().size();
        // Create the Privilege
        restPrivilegeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privilege)))
            .andExpect(status().isCreated());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeCreate + 1);
        Privilege testPrivilege = privilegeList.get(privilegeList.size() - 1);
        assertThat(testPrivilege.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createPrivilegeWithExistingId() throws Exception {
        // Create the Privilege with an existing ID
        privilege.setId(1L);

        int databaseSizeBeforeCreate = privilegeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivilegeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privilege)))
            .andExpect(status().isBadRequest());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrivileges() throws Exception {
        // Initialize the database
        privilegeRepository.saveAndFlush(privilege);

        // Get all the privilegeList
        restPrivilegeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privilege.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getPrivilege() throws Exception {
        // Initialize the database
        privilegeRepository.saveAndFlush(privilege);

        // Get the privilege
        restPrivilegeMockMvc
            .perform(get(ENTITY_API_URL_ID, privilege.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(privilege.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPrivilege() throws Exception {
        // Get the privilege
        restPrivilegeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrivilege() throws Exception {
        // Initialize the database
        privilegeRepository.saveAndFlush(privilege);

        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();

        // Update the privilege
        Privilege updatedPrivilege = privilegeRepository.findById(privilege.getId()).get();
        // Disconnect from session so that the updates on updatedPrivilege are not directly saved in db
        em.detach(updatedPrivilege);
        updatedPrivilege.name(UPDATED_NAME);

        restPrivilegeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrivilege.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrivilege))
            )
            .andExpect(status().isOk());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
        Privilege testPrivilege = privilegeList.get(privilegeList.size() - 1);
        assertThat(testPrivilege.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPrivilege() throws Exception {
        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();
        privilege.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivilegeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, privilege.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privilege))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrivilege() throws Exception {
        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();
        privilege.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilegeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(privilege))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrivilege() throws Exception {
        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();
        privilege.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilegeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(privilege)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrivilegeWithPatch() throws Exception {
        // Initialize the database
        privilegeRepository.saveAndFlush(privilege);

        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();

        // Update the privilege using partial update
        Privilege partialUpdatedPrivilege = new Privilege();
        partialUpdatedPrivilege.setId(privilege.getId());

        partialUpdatedPrivilege.name(UPDATED_NAME);

        restPrivilegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivilege.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivilege))
            )
            .andExpect(status().isOk());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
        Privilege testPrivilege = privilegeList.get(privilegeList.size() - 1);
        assertThat(testPrivilege.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePrivilegeWithPatch() throws Exception {
        // Initialize the database
        privilegeRepository.saveAndFlush(privilege);

        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();

        // Update the privilege using partial update
        Privilege partialUpdatedPrivilege = new Privilege();
        partialUpdatedPrivilege.setId(privilege.getId());

        partialUpdatedPrivilege.name(UPDATED_NAME);

        restPrivilegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrivilege.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrivilege))
            )
            .andExpect(status().isOk());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
        Privilege testPrivilege = privilegeList.get(privilegeList.size() - 1);
        assertThat(testPrivilege.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPrivilege() throws Exception {
        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();
        privilege.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivilegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, privilege.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privilege))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrivilege() throws Exception {
        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();
        privilege.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(privilege))
            )
            .andExpect(status().isBadRequest());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrivilege() throws Exception {
        int databaseSizeBeforeUpdate = privilegeRepository.findAll().size();
        privilege.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrivilegeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(privilege))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Privilege in the database
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrivilege() throws Exception {
        // Initialize the database
        privilegeRepository.saveAndFlush(privilege);

        int databaseSizeBeforeDelete = privilegeRepository.findAll().size();

        // Delete the privilege
        restPrivilegeMockMvc
            .perform(delete(ENTITY_API_URL_ID, privilege.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Privilege> privilegeList = privilegeRepository.findAll();
        assertThat(privilegeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
