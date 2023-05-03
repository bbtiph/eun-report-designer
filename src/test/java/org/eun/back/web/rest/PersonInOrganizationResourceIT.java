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
import org.eun.back.domain.PersonInOrganization;
import org.eun.back.repository.PersonInOrganizationRepository;
import org.eun.back.service.dto.PersonInOrganizationDTO;
import org.eun.back.service.mapper.PersonInOrganizationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonInOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonInOrganizationResourceIT {

    private static final String DEFAULT_ROLE_IN_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_IN_ORGANIZATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/person-in-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonInOrganizationRepository personInOrganizationRepository;

    @Autowired
    private PersonInOrganizationMapper personInOrganizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonInOrganizationMockMvc;

    private PersonInOrganization personInOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInOrganization createEntity(EntityManager em) {
        PersonInOrganization personInOrganization = new PersonInOrganization().roleInOrganization(DEFAULT_ROLE_IN_ORGANIZATION);
        return personInOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInOrganization createUpdatedEntity(EntityManager em) {
        PersonInOrganization personInOrganization = new PersonInOrganization().roleInOrganization(UPDATED_ROLE_IN_ORGANIZATION);
        return personInOrganization;
    }

    @BeforeEach
    public void initTest() {
        personInOrganization = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonInOrganization() throws Exception {
        int databaseSizeBeforeCreate = personInOrganizationRepository.findAll().size();
        // Create the PersonInOrganization
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);
        restPersonInOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        PersonInOrganization testPersonInOrganization = personInOrganizationList.get(personInOrganizationList.size() - 1);
        assertThat(testPersonInOrganization.getRoleInOrganization()).isEqualTo(DEFAULT_ROLE_IN_ORGANIZATION);
    }

    @Test
    @Transactional
    void createPersonInOrganizationWithExistingId() throws Exception {
        // Create the PersonInOrganization with an existing ID
        personInOrganization.setId(1L);
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);

        int databaseSizeBeforeCreate = personInOrganizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonInOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonInOrganizations() throws Exception {
        // Initialize the database
        personInOrganizationRepository.saveAndFlush(personInOrganization);

        // Get all the personInOrganizationList
        restPersonInOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleInOrganization").value(hasItem(DEFAULT_ROLE_IN_ORGANIZATION)));
    }

    @Test
    @Transactional
    void getPersonInOrganization() throws Exception {
        // Initialize the database
        personInOrganizationRepository.saveAndFlush(personInOrganization);

        // Get the personInOrganization
        restPersonInOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, personInOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personInOrganization.getId().intValue()))
            .andExpect(jsonPath("$.roleInOrganization").value(DEFAULT_ROLE_IN_ORGANIZATION));
    }

    @Test
    @Transactional
    void getNonExistingPersonInOrganization() throws Exception {
        // Get the personInOrganization
        restPersonInOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPersonInOrganization() throws Exception {
        // Initialize the database
        personInOrganizationRepository.saveAndFlush(personInOrganization);

        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();

        // Update the personInOrganization
        PersonInOrganization updatedPersonInOrganization = personInOrganizationRepository.findById(personInOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedPersonInOrganization are not directly saved in db
        em.detach(updatedPersonInOrganization);
        updatedPersonInOrganization.roleInOrganization(UPDATED_ROLE_IN_ORGANIZATION);
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(updatedPersonInOrganization);

        restPersonInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personInOrganizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
        PersonInOrganization testPersonInOrganization = personInOrganizationList.get(personInOrganizationList.size() - 1);
        assertThat(testPersonInOrganization.getRoleInOrganization()).isEqualTo(UPDATED_ROLE_IN_ORGANIZATION);
    }

    @Test
    @Transactional
    void putNonExistingPersonInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();
        personInOrganization.setId(count.incrementAndGet());

        // Create the PersonInOrganization
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personInOrganizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();
        personInOrganization.setId(count.incrementAndGet());

        // Create the PersonInOrganization
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();
        personInOrganization.setId(count.incrementAndGet());

        // Create the PersonInOrganization
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonInOrganizationWithPatch() throws Exception {
        // Initialize the database
        personInOrganizationRepository.saveAndFlush(personInOrganization);

        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();

        // Update the personInOrganization using partial update
        PersonInOrganization partialUpdatedPersonInOrganization = new PersonInOrganization();
        partialUpdatedPersonInOrganization.setId(personInOrganization.getId());

        restPersonInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonInOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonInOrganization))
            )
            .andExpect(status().isOk());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
        PersonInOrganization testPersonInOrganization = personInOrganizationList.get(personInOrganizationList.size() - 1);
        assertThat(testPersonInOrganization.getRoleInOrganization()).isEqualTo(DEFAULT_ROLE_IN_ORGANIZATION);
    }

    @Test
    @Transactional
    void fullUpdatePersonInOrganizationWithPatch() throws Exception {
        // Initialize the database
        personInOrganizationRepository.saveAndFlush(personInOrganization);

        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();

        // Update the personInOrganization using partial update
        PersonInOrganization partialUpdatedPersonInOrganization = new PersonInOrganization();
        partialUpdatedPersonInOrganization.setId(personInOrganization.getId());

        partialUpdatedPersonInOrganization.roleInOrganization(UPDATED_ROLE_IN_ORGANIZATION);

        restPersonInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonInOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonInOrganization))
            )
            .andExpect(status().isOk());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
        PersonInOrganization testPersonInOrganization = personInOrganizationList.get(personInOrganizationList.size() - 1);
        assertThat(testPersonInOrganization.getRoleInOrganization()).isEqualTo(UPDATED_ROLE_IN_ORGANIZATION);
    }

    @Test
    @Transactional
    void patchNonExistingPersonInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();
        personInOrganization.setId(count.incrementAndGet());

        // Create the PersonInOrganization
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personInOrganizationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();
        personInOrganization.setId(count.incrementAndGet());

        // Create the PersonInOrganization
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = personInOrganizationRepository.findAll().size();
        personInOrganization.setId(count.incrementAndGet());

        // Create the PersonInOrganization
        PersonInOrganizationDTO personInOrganizationDTO = personInOrganizationMapper.toDto(personInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personInOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonInOrganization in the database
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonInOrganization() throws Exception {
        // Initialize the database
        personInOrganizationRepository.saveAndFlush(personInOrganization);

        int databaseSizeBeforeDelete = personInOrganizationRepository.findAll().size();

        // Delete the personInOrganization
        restPersonInOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, personInOrganization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonInOrganization> personInOrganizationList = personInOrganizationRepository.findAll();
        assertThat(personInOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
