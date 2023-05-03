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
import org.eun.back.domain.EventInOrganization;
import org.eun.back.repository.EventInOrganizationRepository;
import org.eun.back.service.dto.EventInOrganizationDTO;
import org.eun.back.service.mapper.EventInOrganizationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventInOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventInOrganizationResourceIT {

    private static final String ENTITY_API_URL = "/api/event-in-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventInOrganizationRepository eventInOrganizationRepository;

    @Autowired
    private EventInOrganizationMapper eventInOrganizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventInOrganizationMockMvc;

    private EventInOrganization eventInOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventInOrganization createEntity(EntityManager em) {
        EventInOrganization eventInOrganization = new EventInOrganization();
        return eventInOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventInOrganization createUpdatedEntity(EntityManager em) {
        EventInOrganization eventInOrganization = new EventInOrganization();
        return eventInOrganization;
    }

    @BeforeEach
    public void initTest() {
        eventInOrganization = createEntity(em);
    }

    @Test
    @Transactional
    void createEventInOrganization() throws Exception {
        int databaseSizeBeforeCreate = eventInOrganizationRepository.findAll().size();
        // Create the EventInOrganization
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);
        restEventInOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        EventInOrganization testEventInOrganization = eventInOrganizationList.get(eventInOrganizationList.size() - 1);
    }

    @Test
    @Transactional
    void createEventInOrganizationWithExistingId() throws Exception {
        // Create the EventInOrganization with an existing ID
        eventInOrganization.setId(1L);
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);

        int databaseSizeBeforeCreate = eventInOrganizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventInOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventInOrganizations() throws Exception {
        // Initialize the database
        eventInOrganizationRepository.saveAndFlush(eventInOrganization);

        // Get all the eventInOrganizationList
        restEventInOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventInOrganization.getId().intValue())));
    }

    @Test
    @Transactional
    void getEventInOrganization() throws Exception {
        // Initialize the database
        eventInOrganizationRepository.saveAndFlush(eventInOrganization);

        // Get the eventInOrganization
        restEventInOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, eventInOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventInOrganization.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventInOrganization() throws Exception {
        // Get the eventInOrganization
        restEventInOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventInOrganization() throws Exception {
        // Initialize the database
        eventInOrganizationRepository.saveAndFlush(eventInOrganization);

        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();

        // Update the eventInOrganization
        EventInOrganization updatedEventInOrganization = eventInOrganizationRepository.findById(eventInOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedEventInOrganization are not directly saved in db
        em.detach(updatedEventInOrganization);
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(updatedEventInOrganization);

        restEventInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventInOrganizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
        EventInOrganization testEventInOrganization = eventInOrganizationList.get(eventInOrganizationList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingEventInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();
        eventInOrganization.setId(count.incrementAndGet());

        // Create the EventInOrganization
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventInOrganizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();
        eventInOrganization.setId(count.incrementAndGet());

        // Create the EventInOrganization
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();
        eventInOrganization.setId(count.incrementAndGet());

        // Create the EventInOrganization
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventInOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventInOrganizationWithPatch() throws Exception {
        // Initialize the database
        eventInOrganizationRepository.saveAndFlush(eventInOrganization);

        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();

        // Update the eventInOrganization using partial update
        EventInOrganization partialUpdatedEventInOrganization = new EventInOrganization();
        partialUpdatedEventInOrganization.setId(eventInOrganization.getId());

        restEventInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventInOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventInOrganization))
            )
            .andExpect(status().isOk());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
        EventInOrganization testEventInOrganization = eventInOrganizationList.get(eventInOrganizationList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateEventInOrganizationWithPatch() throws Exception {
        // Initialize the database
        eventInOrganizationRepository.saveAndFlush(eventInOrganization);

        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();

        // Update the eventInOrganization using partial update
        EventInOrganization partialUpdatedEventInOrganization = new EventInOrganization();
        partialUpdatedEventInOrganization.setId(eventInOrganization.getId());

        restEventInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventInOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventInOrganization))
            )
            .andExpect(status().isOk());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
        EventInOrganization testEventInOrganization = eventInOrganizationList.get(eventInOrganizationList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingEventInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();
        eventInOrganization.setId(count.incrementAndGet());

        // Create the EventInOrganization
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventInOrganizationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();
        eventInOrganization.setId(count.incrementAndGet());

        // Create the EventInOrganization
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventInOrganization() throws Exception {
        int databaseSizeBeforeUpdate = eventInOrganizationRepository.findAll().size();
        eventInOrganization.setId(count.incrementAndGet());

        // Create the EventInOrganization
        EventInOrganizationDTO eventInOrganizationDTO = eventInOrganizationMapper.toDto(eventInOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventInOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventInOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventInOrganization in the database
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventInOrganization() throws Exception {
        // Initialize the database
        eventInOrganizationRepository.saveAndFlush(eventInOrganization);

        int databaseSizeBeforeDelete = eventInOrganizationRepository.findAll().size();

        // Delete the eventInOrganization
        restEventInOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventInOrganization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventInOrganization> eventInOrganizationList = eventInOrganizationRepository.findAll();
        assertThat(eventInOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
