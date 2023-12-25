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
import org.eun.back.domain.EventReferencesParticipantsCategory;
import org.eun.back.repository.EventReferencesParticipantsCategoryRepository;
import org.eun.back.service.dto.EventReferencesParticipantsCategoryDTO;
import org.eun.back.service.mapper.EventReferencesParticipantsCategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventReferencesParticipantsCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventReferencesParticipantsCategoryResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Long DEFAULT_PARTICIPANTS_COUNT = 1L;
    private static final Long UPDATED_PARTICIPANTS_COUNT = 2L;

    private static final String ENTITY_API_URL = "/api/event-references-participants-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventReferencesParticipantsCategoryRepository eventReferencesParticipantsCategoryRepository;

    @Autowired
    private EventReferencesParticipantsCategoryMapper eventReferencesParticipantsCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventReferencesParticipantsCategoryMockMvc;

    private EventReferencesParticipantsCategory eventReferencesParticipantsCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReferencesParticipantsCategory createEntity(EntityManager em) {
        EventReferencesParticipantsCategory eventReferencesParticipantsCategory = new EventReferencesParticipantsCategory()
            .category(DEFAULT_CATEGORY)
            .participantsCount(DEFAULT_PARTICIPANTS_COUNT);
        return eventReferencesParticipantsCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventReferencesParticipantsCategory createUpdatedEntity(EntityManager em) {
        EventReferencesParticipantsCategory eventReferencesParticipantsCategory = new EventReferencesParticipantsCategory()
            .category(UPDATED_CATEGORY)
            .participantsCount(UPDATED_PARTICIPANTS_COUNT);
        return eventReferencesParticipantsCategory;
    }

    @BeforeEach
    public void initTest() {
        eventReferencesParticipantsCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createEventReferencesParticipantsCategory() throws Exception {
        int databaseSizeBeforeCreate = eventReferencesParticipantsCategoryRepository.findAll().size();
        // Create the EventReferencesParticipantsCategory
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        EventReferencesParticipantsCategory testEventReferencesParticipantsCategory = eventReferencesParticipantsCategoryList.get(
            eventReferencesParticipantsCategoryList.size() - 1
        );
        assertThat(testEventReferencesParticipantsCategory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEventReferencesParticipantsCategory.getParticipantsCount()).isEqualTo(DEFAULT_PARTICIPANTS_COUNT);
    }

    @Test
    @Transactional
    void createEventReferencesParticipantsCategoryWithExistingId() throws Exception {
        // Create the EventReferencesParticipantsCategory with an existing ID
        eventReferencesParticipantsCategory.setId(1L);
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );

        int databaseSizeBeforeCreate = eventReferencesParticipantsCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventReferencesParticipantsCategories() throws Exception {
        // Initialize the database
        eventReferencesParticipantsCategoryRepository.saveAndFlush(eventReferencesParticipantsCategory);

        // Get all the eventReferencesParticipantsCategoryList
        restEventReferencesParticipantsCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventReferencesParticipantsCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].participantsCount").value(hasItem(DEFAULT_PARTICIPANTS_COUNT.intValue())));
    }

    @Test
    @Transactional
    void getEventReferencesParticipantsCategory() throws Exception {
        // Initialize the database
        eventReferencesParticipantsCategoryRepository.saveAndFlush(eventReferencesParticipantsCategory);

        // Get the eventReferencesParticipantsCategory
        restEventReferencesParticipantsCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, eventReferencesParticipantsCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventReferencesParticipantsCategory.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.participantsCount").value(DEFAULT_PARTICIPANTS_COUNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventReferencesParticipantsCategory() throws Exception {
        // Get the eventReferencesParticipantsCategory
        restEventReferencesParticipantsCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventReferencesParticipantsCategory() throws Exception {
        // Initialize the database
        eventReferencesParticipantsCategoryRepository.saveAndFlush(eventReferencesParticipantsCategory);

        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();

        // Update the eventReferencesParticipantsCategory
        EventReferencesParticipantsCategory updatedEventReferencesParticipantsCategory = eventReferencesParticipantsCategoryRepository
            .findById(eventReferencesParticipantsCategory.getId())
            .get();
        // Disconnect from session so that the updates on updatedEventReferencesParticipantsCategory are not directly saved in db
        em.detach(updatedEventReferencesParticipantsCategory);
        updatedEventReferencesParticipantsCategory.category(UPDATED_CATEGORY).participantsCount(UPDATED_PARTICIPANTS_COUNT);
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            updatedEventReferencesParticipantsCategory
        );

        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReferencesParticipantsCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
        EventReferencesParticipantsCategory testEventReferencesParticipantsCategory = eventReferencesParticipantsCategoryList.get(
            eventReferencesParticipantsCategoryList.size() - 1
        );
        assertThat(testEventReferencesParticipantsCategory.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEventReferencesParticipantsCategory.getParticipantsCount()).isEqualTo(UPDATED_PARTICIPANTS_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingEventReferencesParticipantsCategory() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();
        eventReferencesParticipantsCategory.setId(count.incrementAndGet());

        // Create the EventReferencesParticipantsCategory
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventReferencesParticipantsCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventReferencesParticipantsCategory() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();
        eventReferencesParticipantsCategory.setId(count.incrementAndGet());

        // Create the EventReferencesParticipantsCategory
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventReferencesParticipantsCategory() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();
        eventReferencesParticipantsCategory.setId(count.incrementAndGet());

        // Create the EventReferencesParticipantsCategory
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventReferencesParticipantsCategoryWithPatch() throws Exception {
        // Initialize the database
        eventReferencesParticipantsCategoryRepository.saveAndFlush(eventReferencesParticipantsCategory);

        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();

        // Update the eventReferencesParticipantsCategory using partial update
        EventReferencesParticipantsCategory partialUpdatedEventReferencesParticipantsCategory = new EventReferencesParticipantsCategory();
        partialUpdatedEventReferencesParticipantsCategory.setId(eventReferencesParticipantsCategory.getId());

        partialUpdatedEventReferencesParticipantsCategory.participantsCount(UPDATED_PARTICIPANTS_COUNT);

        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReferencesParticipantsCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReferencesParticipantsCategory))
            )
            .andExpect(status().isOk());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
        EventReferencesParticipantsCategory testEventReferencesParticipantsCategory = eventReferencesParticipantsCategoryList.get(
            eventReferencesParticipantsCategoryList.size() - 1
        );
        assertThat(testEventReferencesParticipantsCategory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEventReferencesParticipantsCategory.getParticipantsCount()).isEqualTo(UPDATED_PARTICIPANTS_COUNT);
    }

    @Test
    @Transactional
    void fullUpdateEventReferencesParticipantsCategoryWithPatch() throws Exception {
        // Initialize the database
        eventReferencesParticipantsCategoryRepository.saveAndFlush(eventReferencesParticipantsCategory);

        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();

        // Update the eventReferencesParticipantsCategory using partial update
        EventReferencesParticipantsCategory partialUpdatedEventReferencesParticipantsCategory = new EventReferencesParticipantsCategory();
        partialUpdatedEventReferencesParticipantsCategory.setId(eventReferencesParticipantsCategory.getId());

        partialUpdatedEventReferencesParticipantsCategory.category(UPDATED_CATEGORY).participantsCount(UPDATED_PARTICIPANTS_COUNT);

        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventReferencesParticipantsCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventReferencesParticipantsCategory))
            )
            .andExpect(status().isOk());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
        EventReferencesParticipantsCategory testEventReferencesParticipantsCategory = eventReferencesParticipantsCategoryList.get(
            eventReferencesParticipantsCategoryList.size() - 1
        );
        assertThat(testEventReferencesParticipantsCategory.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEventReferencesParticipantsCategory.getParticipantsCount()).isEqualTo(UPDATED_PARTICIPANTS_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingEventReferencesParticipantsCategory() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();
        eventReferencesParticipantsCategory.setId(count.incrementAndGet());

        // Create the EventReferencesParticipantsCategory
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventReferencesParticipantsCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventReferencesParticipantsCategory() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();
        eventReferencesParticipantsCategory.setId(count.incrementAndGet());

        // Create the EventReferencesParticipantsCategory
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventReferencesParticipantsCategory() throws Exception {
        int databaseSizeBeforeUpdate = eventReferencesParticipantsCategoryRepository.findAll().size();
        eventReferencesParticipantsCategory.setId(count.incrementAndGet());

        // Create the EventReferencesParticipantsCategory
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryMapper.toDto(
            eventReferencesParticipantsCategory
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventReferencesParticipantsCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventReferencesParticipantsCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventReferencesParticipantsCategory in the database
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventReferencesParticipantsCategory() throws Exception {
        // Initialize the database
        eventReferencesParticipantsCategoryRepository.saveAndFlush(eventReferencesParticipantsCategory);

        int databaseSizeBeforeDelete = eventReferencesParticipantsCategoryRepository.findAll().size();

        // Delete the eventReferencesParticipantsCategory
        restEventReferencesParticipantsCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventReferencesParticipantsCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventReferencesParticipantsCategory> eventReferencesParticipantsCategoryList = eventReferencesParticipantsCategoryRepository.findAll();
        assertThat(eventReferencesParticipantsCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
