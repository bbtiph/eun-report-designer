package org.eun.back.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.eun.back.IntegrationTest;
import org.eun.back.domain.EventReferences;
import org.eun.back.repository.EventReferencesRepository;
import org.eun.back.service.EventReferencesService;
import org.eun.back.service.dto.EventReferencesDTO;
import org.eun.back.service.mapper.EventReferencesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventReferencesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EventReferencesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/event-references";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventReferencesRepository eventReferencesRepository;

    @Mock
    private EventReferencesRepository eventReferencesRepositoryMock;

    @Autowired
    private EventReferencesMapper eventReferencesMapper;

    @Mock
    private EventReferencesService eventReferencesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventReferencesMockMvc;

    private EventReferences eventReferences;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReferences createEntity(EntityManager em) {
        EventReferences eventReferences = new EventReferences().name(DEFAULT_NAME).type(DEFAULT_TYPE).isActive(DEFAULT_IS_ACTIVE);
        return eventReferences;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReferences createUpdatedEntity(EntityManager em) {
        EventReferences eventReferences = new EventReferences().name(UPDATED_NAME).type(UPDATED_TYPE).isActive(UPDATED_IS_ACTIVE);
        return eventReferences;
    }

    @BeforeEach
    public void initTest() {
        eventReferences = createEntity(em);
    }

    @Test
    @Transactional
    void createEventReferences() throws Exception {
        int databaseSizeBeforeCreate = eventReferencesRepository.findAll().size();
        // Create the EventReferences
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);
        restEventReferencesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeCreate + 1);
        EventReferences testEventReferences = eventReferencesList.get(eventReferencesList.size() - 1);
        assertThat(testEventReferences.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEventReferences.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEventReferences.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createEventReferencesWithExistingId() throws Exception {
        // Create the EventReferences with an existing ID
        eventReferences.setId(1L);
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);

        int databaseSizeBeforeCreate = eventReferencesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventReferencesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventReferences() throws Exception {
        // Initialize the database
        eventReferencesRepository.saveAndFlush(eventReferences);

        // Get all the eventReferencesList
        restEventReferencesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventReferences.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventReferencesWithEagerRelationshipsIsEnabled() throws Exception {
        when(eventReferencesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventReferencesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(eventReferencesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventReferencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(eventReferencesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventReferencesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(eventReferencesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEventReferences() throws Exception {
        // Initialize the database
        eventReferencesRepository.saveAndFlush(eventReferences);

        // Get the eventReferences
        restEventReferencesMockMvc
            .perform(get(ENTITY_API_URL_ID, eventReferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventReferences.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventReferences() throws Exception {
        // Get the eventReferences
        restEventReferencesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventReferences() throws Exception {
        // Initialize the database
        eventReferencesRepository.saveAndFlush(eventReferences);

        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();

        // Update the eventReferences
        EventReferences updatedEventReferences = eventReferencesRepository.findById(eventReferences.getId()).get();
        // Disconnect from session so that the updates on updatedEventReferences are not directly saved in db
        em.detach(updatedEventReferences);
        updatedEventReferences.name(UPDATED_NAME).type(UPDATED_TYPE).isActive(UPDATED_IS_ACTIVE);
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(updatedEventReferences);

        restEventReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
        EventReferences testEventReferences = eventReferencesList.get(eventReferencesList.size() - 1);
        assertThat(testEventReferences.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEventReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEventReferences.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingEventReferences() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();
        eventReferences.setId(count.incrementAndGet());

        // Create the EventReferences
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReferencesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventReferences() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();
        eventReferences.setId(count.incrementAndGet());

        // Create the EventReferences
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventReferences() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();
        eventReferences.setId(count.incrementAndGet());

        // Create the EventReferences
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventReferencesWithPatch() throws Exception {
        // Initialize the database
        eventReferencesRepository.saveAndFlush(eventReferences);

        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();

        // Update the eventReferences using partial update
        EventReferences partialUpdatedEventReferences = new EventReferences();
        partialUpdatedEventReferences.setId(eventReferences.getId());

        partialUpdatedEventReferences.type(UPDATED_TYPE);

        restEventReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReferences))
            )
            .andExpect(status().isOk());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
        EventReferences testEventReferences = eventReferencesList.get(eventReferencesList.size() - 1);
        assertThat(testEventReferences.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEventReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEventReferences.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateEventReferencesWithPatch() throws Exception {
        // Initialize the database
        eventReferencesRepository.saveAndFlush(eventReferences);

        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();

        // Update the eventReferences using partial update
        EventReferences partialUpdatedEventReferences = new EventReferences();
        partialUpdatedEventReferences.setId(eventReferences.getId());

        partialUpdatedEventReferences.name(UPDATED_NAME).type(UPDATED_TYPE).isActive(UPDATED_IS_ACTIVE);

        restEventReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReferences.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReferences))
            )
            .andExpect(status().isOk());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
        EventReferences testEventReferences = eventReferencesList.get(eventReferencesList.size() - 1);
        assertThat(testEventReferences.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEventReferences.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEventReferences.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingEventReferences() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();
        eventReferences.setId(count.incrementAndGet());

        // Create the EventReferences
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventReferencesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventReferences() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();
        eventReferences.setId(count.incrementAndGet());

        // Create the EventReferences
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventReferences() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesRepository.findAll().size();
        eventReferences.setId(count.incrementAndGet());

        // Create the EventReferences
        EventReferencesDTO eventReferencesDTO = eventReferencesMapper.toDto(eventReferences);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReferences in the database
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventReferences() throws Exception {
        // Initialize the database
        eventReferencesRepository.saveAndFlush(eventReferences);

        int databaseSizeBeforeDelete = eventReferencesRepository.findAll().size();

        // Delete the eventReferences
        restEventReferencesMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventReferences.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventReferences> eventReferencesList = eventReferencesRepository.findAll();
        assertThat(eventReferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
