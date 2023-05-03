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
import org.eun.back.domain.EventParticipant;
import org.eun.back.repository.EventParticipantRepository;
import org.eun.back.service.dto.EventParticipantDTO;
import org.eun.back.service.mapper.EventParticipantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventParticipantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventParticipantResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/event-participants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventParticipantRepository eventParticipantRepository;

    @Autowired
    private EventParticipantMapper eventParticipantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventParticipantMockMvc;

    private EventParticipant eventParticipant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventParticipant createEntity(EntityManager em) {
        EventParticipant eventParticipant = new EventParticipant().type(DEFAULT_TYPE);
        return eventParticipant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventParticipant createUpdatedEntity(EntityManager em) {
        EventParticipant eventParticipant = new EventParticipant().type(UPDATED_TYPE);
        return eventParticipant;
    }

    @BeforeEach
    public void initTest() {
        eventParticipant = createEntity(em);
    }

    @Test
    @Transactional
    void createEventParticipant() throws Exception {
        int databaseSizeBeforeCreate = eventParticipantRepository.findAll().size();
        // Create the EventParticipant
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);
        restEventParticipantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeCreate + 1);
        EventParticipant testEventParticipant = eventParticipantList.get(eventParticipantList.size() - 1);
        assertThat(testEventParticipant.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createEventParticipantWithExistingId() throws Exception {
        // Create the EventParticipant with an existing ID
        eventParticipant.setId(1L);
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);

        int databaseSizeBeforeCreate = eventParticipantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventParticipantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventParticipants() throws Exception {
        // Initialize the database
        eventParticipantRepository.saveAndFlush(eventParticipant);

        // Get all the eventParticipantList
        restEventParticipantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventParticipant.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getEventParticipant() throws Exception {
        // Initialize the database
        eventParticipantRepository.saveAndFlush(eventParticipant);

        // Get the eventParticipant
        restEventParticipantMockMvc
            .perform(get(ENTITY_API_URL_ID, eventParticipant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventParticipant.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingEventParticipant() throws Exception {
        // Get the eventParticipant
        restEventParticipantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventParticipant() throws Exception {
        // Initialize the database
        eventParticipantRepository.saveAndFlush(eventParticipant);

        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();

        // Update the eventParticipant
        EventParticipant updatedEventParticipant = eventParticipantRepository.findById(eventParticipant.getId()).get();
        // Disconnect from session so that the updates on updatedEventParticipant are not directly saved in db
        em.detach(updatedEventParticipant);
        updatedEventParticipant.type(UPDATED_TYPE);
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(updatedEventParticipant);

        restEventParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventParticipantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
        EventParticipant testEventParticipant = eventParticipantList.get(eventParticipantList.size() - 1);
        assertThat(testEventParticipant.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEventParticipant() throws Exception {
        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();
        eventParticipant.setId(count.incrementAndGet());

        // Create the EventParticipant
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventParticipantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventParticipant() throws Exception {
        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();
        eventParticipant.setId(count.incrementAndGet());

        // Create the EventParticipant
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventParticipantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventParticipant() throws Exception {
        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();
        eventParticipant.setId(count.incrementAndGet());

        // Create the EventParticipant
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventParticipantMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventParticipantWithPatch() throws Exception {
        // Initialize the database
        eventParticipantRepository.saveAndFlush(eventParticipant);

        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();

        // Update the eventParticipant using partial update
        EventParticipant partialUpdatedEventParticipant = new EventParticipant();
        partialUpdatedEventParticipant.setId(eventParticipant.getId());

        partialUpdatedEventParticipant.type(UPDATED_TYPE);

        restEventParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventParticipant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventParticipant))
            )
            .andExpect(status().isOk());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
        EventParticipant testEventParticipant = eventParticipantList.get(eventParticipantList.size() - 1);
        assertThat(testEventParticipant.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEventParticipantWithPatch() throws Exception {
        // Initialize the database
        eventParticipantRepository.saveAndFlush(eventParticipant);

        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();

        // Update the eventParticipant using partial update
        EventParticipant partialUpdatedEventParticipant = new EventParticipant();
        partialUpdatedEventParticipant.setId(eventParticipant.getId());

        partialUpdatedEventParticipant.type(UPDATED_TYPE);

        restEventParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventParticipant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventParticipant))
            )
            .andExpect(status().isOk());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
        EventParticipant testEventParticipant = eventParticipantList.get(eventParticipantList.size() - 1);
        assertThat(testEventParticipant.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEventParticipant() throws Exception {
        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();
        eventParticipant.setId(count.incrementAndGet());

        // Create the EventParticipant
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventParticipantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventParticipant() throws Exception {
        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();
        eventParticipant.setId(count.incrementAndGet());

        // Create the EventParticipant
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventParticipant() throws Exception {
        int databaseSizeBeforeUpdate = eventParticipantRepository.findAll().size();
        eventParticipant.setId(count.incrementAndGet());

        // Create the EventParticipant
        EventParticipantDTO eventParticipantDTO = eventParticipantMapper.toDto(eventParticipant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventParticipantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventParticipantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventParticipant in the database
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventParticipant() throws Exception {
        // Initialize the database
        eventParticipantRepository.saveAndFlush(eventParticipant);

        int databaseSizeBeforeDelete = eventParticipantRepository.findAll().size();

        // Delete the eventParticipant
        restEventParticipantMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventParticipant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventParticipant> eventParticipantList = eventParticipantRepository.findAll();
        assertThat(eventParticipantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
