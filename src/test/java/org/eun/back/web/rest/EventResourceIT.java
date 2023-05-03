package org.eun.back.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.eun.back.IntegrationTest;
import org.eun.back.domain.Event;
import org.eun.back.repository.EventRepository;
import org.eun.back.service.dto.EventDTO;
import org.eun.back.service.mapper.EventMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_EUN_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_EUN_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_MODULES = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_MODULES = "BBBBBBBBBB";

    private static final Integer DEFAULT_COURSE_DURATION = 1;
    private static final Integer UPDATED_COURSE_DURATION = 2;

    private static final String DEFAULT_COURSE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MODULES = 1;
    private static final Integer UPDATED_MODULES = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ENGAGEMENT_RATE = 1;
    private static final Integer UPDATED_ENGAGEMENT_RATE = 2;

    private static final Integer DEFAULT_COMPLETION_RATE = 1;
    private static final Integer UPDATED_COMPLETION_RATE = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventMockMvc;

    private Event event;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .type(DEFAULT_TYPE)
            .location(DEFAULT_LOCATION)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .url(DEFAULT_URL)
            .eunContact(DEFAULT_EUN_CONTACT)
            .courseModules(DEFAULT_COURSE_MODULES)
            .courseDuration(DEFAULT_COURSE_DURATION)
            .courseType(DEFAULT_COURSE_TYPE)
            .modules(DEFAULT_MODULES)
            .status(DEFAULT_STATUS)
            .engagementRate(DEFAULT_ENGAGEMENT_RATE)
            .completionRate(DEFAULT_COMPLETION_RATE)
            .name(DEFAULT_NAME);
        return event;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .url(UPDATED_URL)
            .eunContact(UPDATED_EUN_CONTACT)
            .courseModules(UPDATED_COURSE_MODULES)
            .courseDuration(UPDATED_COURSE_DURATION)
            .courseType(UPDATED_COURSE_TYPE)
            .modules(UPDATED_MODULES)
            .status(UPDATED_STATUS)
            .engagementRate(UPDATED_ENGAGEMENT_RATE)
            .completionRate(UPDATED_COMPLETION_RATE)
            .name(UPDATED_NAME);
        return event;
    }

    @BeforeEach
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();
        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEvent.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEvent.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testEvent.getEunContact()).isEqualTo(DEFAULT_EUN_CONTACT);
        assertThat(testEvent.getCourseModules()).isEqualTo(DEFAULT_COURSE_MODULES);
        assertThat(testEvent.getCourseDuration()).isEqualTo(DEFAULT_COURSE_DURATION);
        assertThat(testEvent.getCourseType()).isEqualTo(DEFAULT_COURSE_TYPE);
        assertThat(testEvent.getModules()).isEqualTo(DEFAULT_MODULES);
        assertThat(testEvent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEvent.getEngagementRate()).isEqualTo(DEFAULT_ENGAGEMENT_RATE);
        assertThat(testEvent.getCompletionRate()).isEqualTo(DEFAULT_COMPLETION_RATE);
        assertThat(testEvent.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createEventWithExistingId() throws Exception {
        // Create the Event with an existing ID
        event.setId(1L);
        EventDTO eventDTO = eventMapper.toDto(event);

        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].eunContact").value(hasItem(DEFAULT_EUN_CONTACT)))
            .andExpect(jsonPath("$.[*].courseModules").value(hasItem(DEFAULT_COURSE_MODULES)))
            .andExpect(jsonPath("$.[*].courseDuration").value(hasItem(DEFAULT_COURSE_DURATION)))
            .andExpect(jsonPath("$.[*].courseType").value(hasItem(DEFAULT_COURSE_TYPE)))
            .andExpect(jsonPath("$.[*].modules").value(hasItem(DEFAULT_MODULES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].engagementRate").value(hasItem(DEFAULT_ENGAGEMENT_RATE)))
            .andExpect(jsonPath("$.[*].completionRate").value(hasItem(DEFAULT_COMPLETION_RATE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc
            .perform(get(ENTITY_API_URL_ID, event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.eunContact").value(DEFAULT_EUN_CONTACT))
            .andExpect(jsonPath("$.courseModules").value(DEFAULT_COURSE_MODULES))
            .andExpect(jsonPath("$.courseDuration").value(DEFAULT_COURSE_DURATION))
            .andExpect(jsonPath("$.courseType").value(DEFAULT_COURSE_TYPE))
            .andExpect(jsonPath("$.modules").value(DEFAULT_MODULES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.engagementRate").value(DEFAULT_ENGAGEMENT_RATE))
            .andExpect(jsonPath("$.completionRate").value(DEFAULT_COMPLETION_RATE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .url(UPDATED_URL)
            .eunContact(UPDATED_EUN_CONTACT)
            .courseModules(UPDATED_COURSE_MODULES)
            .courseDuration(UPDATED_COURSE_DURATION)
            .courseType(UPDATED_COURSE_TYPE)
            .modules(UPDATED_MODULES)
            .status(UPDATED_STATUS)
            .engagementRate(UPDATED_ENGAGEMENT_RATE)
            .completionRate(UPDATED_COMPLETION_RATE)
            .name(UPDATED_NAME);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEvent.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEvent.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testEvent.getEunContact()).isEqualTo(UPDATED_EUN_CONTACT);
        assertThat(testEvent.getCourseModules()).isEqualTo(UPDATED_COURSE_MODULES);
        assertThat(testEvent.getCourseDuration()).isEqualTo(UPDATED_COURSE_DURATION);
        assertThat(testEvent.getCourseType()).isEqualTo(UPDATED_COURSE_TYPE);
        assertThat(testEvent.getModules()).isEqualTo(UPDATED_MODULES);
        assertThat(testEvent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEvent.getEngagementRate()).isEqualTo(UPDATED_ENGAGEMENT_RATE);
        assertThat(testEvent.getCompletionRate()).isEqualTo(UPDATED_COMPLETION_RATE);
        assertThat(testEvent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .location(UPDATED_LOCATION)
            .eunContact(UPDATED_EUN_CONTACT)
            .modules(UPDATED_MODULES)
            .status(UPDATED_STATUS)
            .name(UPDATED_NAME);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEvent.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEvent.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEvent.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testEvent.getEunContact()).isEqualTo(UPDATED_EUN_CONTACT);
        assertThat(testEvent.getCourseModules()).isEqualTo(DEFAULT_COURSE_MODULES);
        assertThat(testEvent.getCourseDuration()).isEqualTo(DEFAULT_COURSE_DURATION);
        assertThat(testEvent.getCourseType()).isEqualTo(DEFAULT_COURSE_TYPE);
        assertThat(testEvent.getModules()).isEqualTo(UPDATED_MODULES);
        assertThat(testEvent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEvent.getEngagementRate()).isEqualTo(DEFAULT_ENGAGEMENT_RATE);
        assertThat(testEvent.getCompletionRate()).isEqualTo(DEFAULT_COMPLETION_RATE);
        assertThat(testEvent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEventWithPatch() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event using partial update
        Event partialUpdatedEvent = new Event();
        partialUpdatedEvent.setId(event.getId());

        partialUpdatedEvent
            .type(UPDATED_TYPE)
            .location(UPDATED_LOCATION)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .url(UPDATED_URL)
            .eunContact(UPDATED_EUN_CONTACT)
            .courseModules(UPDATED_COURSE_MODULES)
            .courseDuration(UPDATED_COURSE_DURATION)
            .courseType(UPDATED_COURSE_TYPE)
            .modules(UPDATED_MODULES)
            .status(UPDATED_STATUS)
            .engagementRate(UPDATED_ENGAGEMENT_RATE)
            .completionRate(UPDATED_COMPLETION_RATE)
            .name(UPDATED_NAME);

        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvent))
            )
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEvent.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEvent.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEvent.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testEvent.getEunContact()).isEqualTo(UPDATED_EUN_CONTACT);
        assertThat(testEvent.getCourseModules()).isEqualTo(UPDATED_COURSE_MODULES);
        assertThat(testEvent.getCourseDuration()).isEqualTo(UPDATED_COURSE_DURATION);
        assertThat(testEvent.getCourseType()).isEqualTo(UPDATED_COURSE_TYPE);
        assertThat(testEvent.getModules()).isEqualTo(UPDATED_MODULES);
        assertThat(testEvent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEvent.getEngagementRate()).isEqualTo(UPDATED_ENGAGEMENT_RATE);
        assertThat(testEvent.getCompletionRate()).isEqualTo(UPDATED_COMPLETION_RATE);
        assertThat(testEvent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();
        event.setId(count.incrementAndGet());

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, event.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
