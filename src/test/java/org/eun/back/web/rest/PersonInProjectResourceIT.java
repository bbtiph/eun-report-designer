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
import org.eun.back.domain.PersonInProject;
import org.eun.back.repository.PersonInProjectRepository;
import org.eun.back.service.dto.PersonInProjectDTO;
import org.eun.back.service.mapper.PersonInProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonInProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonInProjectResourceIT {

    private static final String DEFAULT_ROLE_IN_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_IN_PROJECT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/person-in-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonInProjectRepository personInProjectRepository;

    @Autowired
    private PersonInProjectMapper personInProjectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonInProjectMockMvc;

    private PersonInProject personInProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInProject createEntity(EntityManager em) {
        PersonInProject personInProject = new PersonInProject().roleInProject(DEFAULT_ROLE_IN_PROJECT);
        return personInProject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInProject createUpdatedEntity(EntityManager em) {
        PersonInProject personInProject = new PersonInProject().roleInProject(UPDATED_ROLE_IN_PROJECT);
        return personInProject;
    }

    @BeforeEach
    public void initTest() {
        personInProject = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonInProject() throws Exception {
        int databaseSizeBeforeCreate = personInProjectRepository.findAll().size();
        // Create the PersonInProject
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);
        restPersonInProjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeCreate + 1);
        PersonInProject testPersonInProject = personInProjectList.get(personInProjectList.size() - 1);
        assertThat(testPersonInProject.getRoleInProject()).isEqualTo(DEFAULT_ROLE_IN_PROJECT);
    }

    @Test
    @Transactional
    void createPersonInProjectWithExistingId() throws Exception {
        // Create the PersonInProject with an existing ID
        personInProject.setId(1L);
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);

        int databaseSizeBeforeCreate = personInProjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonInProjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonInProjects() throws Exception {
        // Initialize the database
        personInProjectRepository.saveAndFlush(personInProject);

        // Get all the personInProjectList
        restPersonInProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleInProject").value(hasItem(DEFAULT_ROLE_IN_PROJECT)));
    }

    @Test
    @Transactional
    void getPersonInProject() throws Exception {
        // Initialize the database
        personInProjectRepository.saveAndFlush(personInProject);

        // Get the personInProject
        restPersonInProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, personInProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personInProject.getId().intValue()))
            .andExpect(jsonPath("$.roleInProject").value(DEFAULT_ROLE_IN_PROJECT));
    }

    @Test
    @Transactional
    void getNonExistingPersonInProject() throws Exception {
        // Get the personInProject
        restPersonInProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonInProject() throws Exception {
        // Initialize the database
        personInProjectRepository.saveAndFlush(personInProject);

        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();

        // Update the personInProject
        PersonInProject updatedPersonInProject = personInProjectRepository.findById(personInProject.getId()).get();
        // Disconnect from session so that the updates on updatedPersonInProject are not directly saved in db
        em.detach(updatedPersonInProject);
        updatedPersonInProject.roleInProject(UPDATED_ROLE_IN_PROJECT);
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(updatedPersonInProject);

        restPersonInProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personInProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
        PersonInProject testPersonInProject = personInProjectList.get(personInProjectList.size() - 1);
        assertThat(testPersonInProject.getRoleInProject()).isEqualTo(UPDATED_ROLE_IN_PROJECT);
    }

    @Test
    @Transactional
    void putNonExistingPersonInProject() throws Exception {
        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();
        personInProject.setId(count.incrementAndGet());

        // Create the PersonInProject
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personInProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonInProject() throws Exception {
        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();
        personInProject.setId(count.incrementAndGet());

        // Create the PersonInProject
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonInProject() throws Exception {
        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();
        personInProject.setId(count.incrementAndGet());

        // Create the PersonInProject
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInProjectMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonInProjectWithPatch() throws Exception {
        // Initialize the database
        personInProjectRepository.saveAndFlush(personInProject);

        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();

        // Update the personInProject using partial update
        PersonInProject partialUpdatedPersonInProject = new PersonInProject();
        partialUpdatedPersonInProject.setId(personInProject.getId());

        partialUpdatedPersonInProject.roleInProject(UPDATED_ROLE_IN_PROJECT);

        restPersonInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonInProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonInProject))
            )
            .andExpect(status().isOk());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
        PersonInProject testPersonInProject = personInProjectList.get(personInProjectList.size() - 1);
        assertThat(testPersonInProject.getRoleInProject()).isEqualTo(UPDATED_ROLE_IN_PROJECT);
    }

    @Test
    @Transactional
    void fullUpdatePersonInProjectWithPatch() throws Exception {
        // Initialize the database
        personInProjectRepository.saveAndFlush(personInProject);

        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();

        // Update the personInProject using partial update
        PersonInProject partialUpdatedPersonInProject = new PersonInProject();
        partialUpdatedPersonInProject.setId(personInProject.getId());

        partialUpdatedPersonInProject.roleInProject(UPDATED_ROLE_IN_PROJECT);

        restPersonInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonInProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonInProject))
            )
            .andExpect(status().isOk());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
        PersonInProject testPersonInProject = personInProjectList.get(personInProjectList.size() - 1);
        assertThat(testPersonInProject.getRoleInProject()).isEqualTo(UPDATED_ROLE_IN_PROJECT);
    }

    @Test
    @Transactional
    void patchNonExistingPersonInProject() throws Exception {
        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();
        personInProject.setId(count.incrementAndGet());

        // Create the PersonInProject
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personInProjectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonInProject() throws Exception {
        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();
        personInProject.setId(count.incrementAndGet());

        // Create the PersonInProject
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonInProject() throws Exception {
        int databaseSizeBeforeUpdate = personInProjectRepository.findAll().size();
        personInProject.setId(count.incrementAndGet());

        // Create the PersonInProject
        PersonInProjectDTO personInProjectDTO = personInProjectMapper.toDto(personInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInProjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonInProject in the database
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonInProject() throws Exception {
        // Initialize the database
        personInProjectRepository.saveAndFlush(personInProject);

        int databaseSizeBeforeDelete = personInProjectRepository.findAll().size();

        // Delete the personInProject
        restPersonInProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, personInProject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonInProject> personInProjectList = personInProjectRepository.findAll();
        assertThat(personInProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
