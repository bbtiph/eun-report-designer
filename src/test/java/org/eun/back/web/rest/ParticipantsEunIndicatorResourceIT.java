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
import org.eun.back.domain.ParticipantsEunIndicator;
import org.eun.back.repository.ParticipantsEunIndicatorRepository;
import org.eun.back.service.dto.ParticipantsEunIndicatorDTO;
import org.eun.back.service.mapper.ParticipantsEunIndicatorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParticipantsEunIndicatorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipantsEunIndicatorResourceIT {

    private static final Long DEFAULT_PERIOD = 1L;
    private static final Long UPDATED_PERIOD = 2L;

    private static final Long DEFAULT_N_COUNT = 1L;
    private static final Long UPDATED_N_COUNT = 2L;

    private static final String DEFAULT_COURSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LAST_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/participants-eun-indicators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParticipantsEunIndicatorRepository participantsEunIndicatorRepository;

    @Autowired
    private ParticipantsEunIndicatorMapper participantsEunIndicatorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParticipantsEunIndicatorMockMvc;

    private ParticipantsEunIndicator participantsEunIndicator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParticipantsEunIndicator createEntity(EntityManager em) {
        ParticipantsEunIndicator participantsEunIndicator = new ParticipantsEunIndicator()
            .period(DEFAULT_PERIOD)
            .nCount(DEFAULT_N_COUNT)
            .courseId(DEFAULT_COURSE_ID)
            .courseName(DEFAULT_COURSE_NAME)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return participantsEunIndicator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParticipantsEunIndicator createUpdatedEntity(EntityManager em) {
        ParticipantsEunIndicator participantsEunIndicator = new ParticipantsEunIndicator()
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .courseId(UPDATED_COURSE_ID)
            .courseName(UPDATED_COURSE_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return participantsEunIndicator;
    }

    @BeforeEach
    public void initTest() {
        participantsEunIndicator = createEntity(em);
    }

    @Test
    @Transactional
    void createParticipantsEunIndicator() throws Exception {
        int databaseSizeBeforeCreate = participantsEunIndicatorRepository.findAll().size();
        // Create the ParticipantsEunIndicator
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);
        restParticipantsEunIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeCreate + 1);
        ParticipantsEunIndicator testParticipantsEunIndicator = participantsEunIndicatorList.get(participantsEunIndicatorList.size() - 1);
        assertThat(testParticipantsEunIndicator.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testParticipantsEunIndicator.getnCount()).isEqualTo(DEFAULT_N_COUNT);
        assertThat(testParticipantsEunIndicator.getCourseId()).isEqualTo(DEFAULT_COURSE_ID);
        assertThat(testParticipantsEunIndicator.getCourseName()).isEqualTo(DEFAULT_COURSE_NAME);
        assertThat(testParticipantsEunIndicator.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testParticipantsEunIndicator.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testParticipantsEunIndicator.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testParticipantsEunIndicator.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testParticipantsEunIndicator.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createParticipantsEunIndicatorWithExistingId() throws Exception {
        // Create the ParticipantsEunIndicator with an existing ID
        participantsEunIndicator.setId(1L);
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);

        int databaseSizeBeforeCreate = participantsEunIndicatorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantsEunIndicatorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllParticipantsEunIndicators() throws Exception {
        // Initialize the database
        participantsEunIndicatorRepository.saveAndFlush(participantsEunIndicator);

        // Get all the participantsEunIndicatorList
        restParticipantsEunIndicatorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participantsEunIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].nCount").value(hasItem(DEFAULT_N_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].courseId").value(hasItem(DEFAULT_COURSE_ID)))
            .andExpect(jsonPath("$.[*].courseName").value(hasItem(DEFAULT_COURSE_NAME)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getParticipantsEunIndicator() throws Exception {
        // Initialize the database
        participantsEunIndicatorRepository.saveAndFlush(participantsEunIndicator);

        // Get the participantsEunIndicator
        restParticipantsEunIndicatorMockMvc
            .perform(get(ENTITY_API_URL_ID, participantsEunIndicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participantsEunIndicator.getId().intValue()))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD.intValue()))
            .andExpect(jsonPath("$.nCount").value(DEFAULT_N_COUNT.intValue()))
            .andExpect(jsonPath("$.courseId").value(DEFAULT_COURSE_ID))
            .andExpect(jsonPath("$.courseName").value(DEFAULT_COURSE_NAME))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingParticipantsEunIndicator() throws Exception {
        // Get the participantsEunIndicator
        restParticipantsEunIndicatorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParticipantsEunIndicator() throws Exception {
        // Initialize the database
        participantsEunIndicatorRepository.saveAndFlush(participantsEunIndicator);

        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();

        // Update the participantsEunIndicator
        ParticipantsEunIndicator updatedParticipantsEunIndicator = participantsEunIndicatorRepository
            .findById(participantsEunIndicator.getId())
            .get();
        // Disconnect from session so that the updates on updatedParticipantsEunIndicator are not directly saved in db
        em.detach(updatedParticipantsEunIndicator);
        updatedParticipantsEunIndicator
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .courseId(UPDATED_COURSE_ID)
            .courseName(UPDATED_COURSE_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(updatedParticipantsEunIndicator);

        restParticipantsEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participantsEunIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        ParticipantsEunIndicator testParticipantsEunIndicator = participantsEunIndicatorList.get(participantsEunIndicatorList.size() - 1);
        assertThat(testParticipantsEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testParticipantsEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testParticipantsEunIndicator.getCourseId()).isEqualTo(UPDATED_COURSE_ID);
        assertThat(testParticipantsEunIndicator.getCourseName()).isEqualTo(UPDATED_COURSE_NAME);
        assertThat(testParticipantsEunIndicator.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testParticipantsEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testParticipantsEunIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testParticipantsEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testParticipantsEunIndicator.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingParticipantsEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();
        participantsEunIndicator.setId(count.incrementAndGet());

        // Create the ParticipantsEunIndicator
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantsEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participantsEunIndicatorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParticipantsEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();
        participantsEunIndicator.setId(count.incrementAndGet());

        // Create the ParticipantsEunIndicator
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParticipantsEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();
        participantsEunIndicator.setId(count.incrementAndGet());

        // Create the ParticipantsEunIndicator
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsEunIndicatorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParticipantsEunIndicatorWithPatch() throws Exception {
        // Initialize the database
        participantsEunIndicatorRepository.saveAndFlush(participantsEunIndicator);

        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();

        // Update the participantsEunIndicator using partial update
        ParticipantsEunIndicator partialUpdatedParticipantsEunIndicator = new ParticipantsEunIndicator();
        partialUpdatedParticipantsEunIndicator.setId(participantsEunIndicator.getId());

        partialUpdatedParticipantsEunIndicator
            .courseId(UPDATED_COURSE_ID)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restParticipantsEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipantsEunIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipantsEunIndicator))
            )
            .andExpect(status().isOk());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        ParticipantsEunIndicator testParticipantsEunIndicator = participantsEunIndicatorList.get(participantsEunIndicatorList.size() - 1);
        assertThat(testParticipantsEunIndicator.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testParticipantsEunIndicator.getnCount()).isEqualTo(DEFAULT_N_COUNT);
        assertThat(testParticipantsEunIndicator.getCourseId()).isEqualTo(UPDATED_COURSE_ID);
        assertThat(testParticipantsEunIndicator.getCourseName()).isEqualTo(DEFAULT_COURSE_NAME);
        assertThat(testParticipantsEunIndicator.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testParticipantsEunIndicator.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testParticipantsEunIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testParticipantsEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testParticipantsEunIndicator.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateParticipantsEunIndicatorWithPatch() throws Exception {
        // Initialize the database
        participantsEunIndicatorRepository.saveAndFlush(participantsEunIndicator);

        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();

        // Update the participantsEunIndicator using partial update
        ParticipantsEunIndicator partialUpdatedParticipantsEunIndicator = new ParticipantsEunIndicator();
        partialUpdatedParticipantsEunIndicator.setId(participantsEunIndicator.getId());

        partialUpdatedParticipantsEunIndicator
            .period(UPDATED_PERIOD)
            .nCount(UPDATED_N_COUNT)
            .courseId(UPDATED_COURSE_ID)
            .courseName(UPDATED_COURSE_NAME)
            .countryCode(UPDATED_COUNTRY_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restParticipantsEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipantsEunIndicator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipantsEunIndicator))
            )
            .andExpect(status().isOk());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
        ParticipantsEunIndicator testParticipantsEunIndicator = participantsEunIndicatorList.get(participantsEunIndicatorList.size() - 1);
        assertThat(testParticipantsEunIndicator.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testParticipantsEunIndicator.getnCount()).isEqualTo(UPDATED_N_COUNT);
        assertThat(testParticipantsEunIndicator.getCourseId()).isEqualTo(UPDATED_COURSE_ID);
        assertThat(testParticipantsEunIndicator.getCourseName()).isEqualTo(UPDATED_COURSE_NAME);
        assertThat(testParticipantsEunIndicator.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testParticipantsEunIndicator.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testParticipantsEunIndicator.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testParticipantsEunIndicator.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testParticipantsEunIndicator.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingParticipantsEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();
        participantsEunIndicator.setId(count.incrementAndGet());

        // Create the ParticipantsEunIndicator
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantsEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participantsEunIndicatorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParticipantsEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();
        participantsEunIndicator.setId(count.incrementAndGet());

        // Create the ParticipantsEunIndicator
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParticipantsEunIndicator() throws Exception {
        int databaseSizeBeforeUpdate = participantsEunIndicatorRepository.findAll().size();
        participantsEunIndicator.setId(count.incrementAndGet());

        // Create the ParticipantsEunIndicator
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = participantsEunIndicatorMapper.toDto(participantsEunIndicator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsEunIndicatorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participantsEunIndicatorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParticipantsEunIndicator in the database
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParticipantsEunIndicator() throws Exception {
        // Initialize the database
        participantsEunIndicatorRepository.saveAndFlush(participantsEunIndicator);

        int databaseSizeBeforeDelete = participantsEunIndicatorRepository.findAll().size();

        // Delete the participantsEunIndicator
        restParticipantsEunIndicatorMockMvc
            .perform(delete(ENTITY_API_URL_ID, participantsEunIndicator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParticipantsEunIndicator> participantsEunIndicatorList = participantsEunIndicatorRepository.findAll();
        assertThat(participantsEunIndicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
