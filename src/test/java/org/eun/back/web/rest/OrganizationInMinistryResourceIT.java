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
import org.eun.back.domain.OrganizationInMinistry;
import org.eun.back.repository.OrganizationInMinistryRepository;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
import org.eun.back.service.mapper.OrganizationInMinistryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganizationInMinistryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationInMinistryResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organization-in-ministries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationInMinistryRepository organizationInMinistryRepository;

    @Autowired
    private OrganizationInMinistryMapper organizationInMinistryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationInMinistryMockMvc;

    private OrganizationInMinistry organizationInMinistry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationInMinistry createEntity(EntityManager em) {
        OrganizationInMinistry organizationInMinistry = new OrganizationInMinistry().status(DEFAULT_STATUS);
        return organizationInMinistry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationInMinistry createUpdatedEntity(EntityManager em) {
        OrganizationInMinistry organizationInMinistry = new OrganizationInMinistry().status(UPDATED_STATUS);
        return organizationInMinistry;
    }

    @BeforeEach
    public void initTest() {
        organizationInMinistry = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganizationInMinistry() throws Exception {
        int databaseSizeBeforeCreate = organizationInMinistryRepository.findAll().size();
        // Create the OrganizationInMinistry
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);
        restOrganizationInMinistryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationInMinistry testOrganizationInMinistry = organizationInMinistryList.get(organizationInMinistryList.size() - 1);
        assertThat(testOrganizationInMinistry.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createOrganizationInMinistryWithExistingId() throws Exception {
        // Create the OrganizationInMinistry with an existing ID
        organizationInMinistry.setId(1L);
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);

        int databaseSizeBeforeCreate = organizationInMinistryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationInMinistryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganizationInMinistries() throws Exception {
        // Initialize the database
        organizationInMinistryRepository.saveAndFlush(organizationInMinistry);

        // Get all the organizationInMinistryList
        restOrganizationInMinistryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationInMinistry.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getOrganizationInMinistry() throws Exception {
        // Initialize the database
        organizationInMinistryRepository.saveAndFlush(organizationInMinistry);

        // Get the organizationInMinistry
        restOrganizationInMinistryMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationInMinistry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationInMinistry.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationInMinistry() throws Exception {
        // Get the organizationInMinistry
        restOrganizationInMinistryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationInMinistry() throws Exception {
        // Initialize the database
        organizationInMinistryRepository.saveAndFlush(organizationInMinistry);

        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();

        // Update the organizationInMinistry
        OrganizationInMinistry updatedOrganizationInMinistry = organizationInMinistryRepository
            .findById(organizationInMinistry.getId())
            .get();
        // Disconnect from session so that the updates on updatedOrganizationInMinistry are not directly saved in db
        em.detach(updatedOrganizationInMinistry);
        updatedOrganizationInMinistry.status(UPDATED_STATUS);
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(updatedOrganizationInMinistry);

        restOrganizationInMinistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationInMinistryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
        OrganizationInMinistry testOrganizationInMinistry = organizationInMinistryList.get(organizationInMinistryList.size() - 1);
        assertThat(testOrganizationInMinistry.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationInMinistry() throws Exception {
        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();
        organizationInMinistry.setId(count.incrementAndGet());

        // Create the OrganizationInMinistry
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationInMinistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationInMinistryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationInMinistry() throws Exception {
        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();
        organizationInMinistry.setId(count.incrementAndGet());

        // Create the OrganizationInMinistry
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInMinistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationInMinistry() throws Exception {
        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();
        organizationInMinistry.setId(count.incrementAndGet());

        // Create the OrganizationInMinistry
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInMinistryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationInMinistryWithPatch() throws Exception {
        // Initialize the database
        organizationInMinistryRepository.saveAndFlush(organizationInMinistry);

        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();

        // Update the organizationInMinistry using partial update
        OrganizationInMinistry partialUpdatedOrganizationInMinistry = new OrganizationInMinistry();
        partialUpdatedOrganizationInMinistry.setId(organizationInMinistry.getId());

        partialUpdatedOrganizationInMinistry.status(UPDATED_STATUS);

        restOrganizationInMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationInMinistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationInMinistry))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
        OrganizationInMinistry testOrganizationInMinistry = organizationInMinistryList.get(organizationInMinistryList.size() - 1);
        assertThat(testOrganizationInMinistry.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationInMinistryWithPatch() throws Exception {
        // Initialize the database
        organizationInMinistryRepository.saveAndFlush(organizationInMinistry);

        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();

        // Update the organizationInMinistry using partial update
        OrganizationInMinistry partialUpdatedOrganizationInMinistry = new OrganizationInMinistry();
        partialUpdatedOrganizationInMinistry.setId(organizationInMinistry.getId());

        partialUpdatedOrganizationInMinistry.status(UPDATED_STATUS);

        restOrganizationInMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationInMinistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationInMinistry))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
        OrganizationInMinistry testOrganizationInMinistry = organizationInMinistryList.get(organizationInMinistryList.size() - 1);
        assertThat(testOrganizationInMinistry.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationInMinistry() throws Exception {
        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();
        organizationInMinistry.setId(count.incrementAndGet());

        // Create the OrganizationInMinistry
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationInMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationInMinistryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationInMinistry() throws Exception {
        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();
        organizationInMinistry.setId(count.incrementAndGet());

        // Create the OrganizationInMinistry
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationInMinistry() throws Exception {
        int databaseSizeBeforeUpdate = organizationInMinistryRepository.findAll().size();
        organizationInMinistry.setId(count.incrementAndGet());

        // Create the OrganizationInMinistry
        OrganizationInMinistryDTO organizationInMinistryDTO = organizationInMinistryMapper.toDto(organizationInMinistry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationInMinistryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationInMinistry in the database
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationInMinistry() throws Exception {
        // Initialize the database
        organizationInMinistryRepository.saveAndFlush(organizationInMinistry);

        int databaseSizeBeforeDelete = organizationInMinistryRepository.findAll().size();

        // Delete the organizationInMinistry
        restOrganizationInMinistryMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationInMinistry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationInMinistry> organizationInMinistryList = organizationInMinistryRepository.findAll();
        assertThat(organizationInMinistryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
