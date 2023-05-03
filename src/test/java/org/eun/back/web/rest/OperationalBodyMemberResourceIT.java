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
import org.eun.back.domain.OperationalBodyMember;
import org.eun.back.repository.OperationalBodyMemberRepository;
import org.eun.back.service.dto.OperationalBodyMemberDTO;
import org.eun.back.service.mapper.OperationalBodyMemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OperationalBodyMemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperationalBodyMemberResourceIT {

    private static final Long DEFAULT_PERSON_ID = 1L;
    private static final Long UPDATED_PERSON_ID = 2L;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_EUN_CONTACT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_EUN_CONTACT_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EUN_CONTACT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_EUN_CONTACT_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_COOPERATION_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_COOPERATION_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/operational-body-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperationalBodyMemberRepository operationalBodyMemberRepository;

    @Autowired
    private OperationalBodyMemberMapper operationalBodyMemberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationalBodyMemberMockMvc;

    private OperationalBodyMember operationalBodyMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationalBodyMember createEntity(EntityManager em) {
        OperationalBodyMember operationalBodyMember = new OperationalBodyMember()
            .personId(DEFAULT_PERSON_ID)
            .position(DEFAULT_POSITION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .department(DEFAULT_DEPARTMENT)
            .eunContactFirstname(DEFAULT_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(DEFAULT_EUN_CONTACT_LASTNAME)
            .cooperationField(DEFAULT_COOPERATION_FIELD)
            .status(DEFAULT_STATUS);
        return operationalBodyMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationalBodyMember createUpdatedEntity(EntityManager em) {
        OperationalBodyMember operationalBodyMember = new OperationalBodyMember()
            .personId(UPDATED_PERSON_ID)
            .position(UPDATED_POSITION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .department(UPDATED_DEPARTMENT)
            .eunContactFirstname(UPDATED_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(UPDATED_EUN_CONTACT_LASTNAME)
            .cooperationField(UPDATED_COOPERATION_FIELD)
            .status(UPDATED_STATUS);
        return operationalBodyMember;
    }

    @BeforeEach
    public void initTest() {
        operationalBodyMember = createEntity(em);
    }

    @Test
    @Transactional
    void createOperationalBodyMember() throws Exception {
        int databaseSizeBeforeCreate = operationalBodyMemberRepository.findAll().size();
        // Create the OperationalBodyMember
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);
        restOperationalBodyMemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeCreate + 1);
        OperationalBodyMember testOperationalBodyMember = operationalBodyMemberList.get(operationalBodyMemberList.size() - 1);
        assertThat(testOperationalBodyMember.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testOperationalBodyMember.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testOperationalBodyMember.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOperationalBodyMember.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testOperationalBodyMember.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testOperationalBodyMember.getEunContactFirstname()).isEqualTo(DEFAULT_EUN_CONTACT_FIRSTNAME);
        assertThat(testOperationalBodyMember.getEunContactLastname()).isEqualTo(DEFAULT_EUN_CONTACT_LASTNAME);
        assertThat(testOperationalBodyMember.getCooperationField()).isEqualTo(DEFAULT_COOPERATION_FIELD);
        assertThat(testOperationalBodyMember.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createOperationalBodyMemberWithExistingId() throws Exception {
        // Create the OperationalBodyMember with an existing ID
        operationalBodyMember.setId(1L);
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);

        int databaseSizeBeforeCreate = operationalBodyMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationalBodyMemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperationalBodyMembers() throws Exception {
        // Initialize the database
        operationalBodyMemberRepository.saveAndFlush(operationalBodyMember);

        // Get all the operationalBodyMemberList
        restOperationalBodyMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationalBodyMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].eunContactFirstname").value(hasItem(DEFAULT_EUN_CONTACT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].eunContactLastname").value(hasItem(DEFAULT_EUN_CONTACT_LASTNAME)))
            .andExpect(jsonPath("$.[*].cooperationField").value(hasItem(DEFAULT_COOPERATION_FIELD)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getOperationalBodyMember() throws Exception {
        // Initialize the database
        operationalBodyMemberRepository.saveAndFlush(operationalBodyMember);

        // Get the operationalBodyMember
        restOperationalBodyMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, operationalBodyMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operationalBodyMember.getId().intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.eunContactFirstname").value(DEFAULT_EUN_CONTACT_FIRSTNAME))
            .andExpect(jsonPath("$.eunContactLastname").value(DEFAULT_EUN_CONTACT_LASTNAME))
            .andExpect(jsonPath("$.cooperationField").value(DEFAULT_COOPERATION_FIELD))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingOperationalBodyMember() throws Exception {
        // Get the operationalBodyMember
        restOperationalBodyMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperationalBodyMember() throws Exception {
        // Initialize the database
        operationalBodyMemberRepository.saveAndFlush(operationalBodyMember);

        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();

        // Update the operationalBodyMember
        OperationalBodyMember updatedOperationalBodyMember = operationalBodyMemberRepository.findById(operationalBodyMember.getId()).get();
        // Disconnect from session so that the updates on updatedOperationalBodyMember are not directly saved in db
        em.detach(updatedOperationalBodyMember);
        updatedOperationalBodyMember
            .personId(UPDATED_PERSON_ID)
            .position(UPDATED_POSITION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .department(UPDATED_DEPARTMENT)
            .eunContactFirstname(UPDATED_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(UPDATED_EUN_CONTACT_LASTNAME)
            .cooperationField(UPDATED_COOPERATION_FIELD)
            .status(UPDATED_STATUS);
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(updatedOperationalBodyMember);

        restOperationalBodyMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationalBodyMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isOk());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
        OperationalBodyMember testOperationalBodyMember = operationalBodyMemberList.get(operationalBodyMemberList.size() - 1);
        assertThat(testOperationalBodyMember.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testOperationalBodyMember.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testOperationalBodyMember.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOperationalBodyMember.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOperationalBodyMember.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testOperationalBodyMember.getEunContactFirstname()).isEqualTo(UPDATED_EUN_CONTACT_FIRSTNAME);
        assertThat(testOperationalBodyMember.getEunContactLastname()).isEqualTo(UPDATED_EUN_CONTACT_LASTNAME);
        assertThat(testOperationalBodyMember.getCooperationField()).isEqualTo(UPDATED_COOPERATION_FIELD);
        assertThat(testOperationalBodyMember.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOperationalBodyMember() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();
        operationalBodyMember.setId(count.incrementAndGet());

        // Create the OperationalBodyMember
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationalBodyMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationalBodyMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperationalBodyMember() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();
        operationalBodyMember.setId(count.incrementAndGet());

        // Create the OperationalBodyMember
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperationalBodyMember() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();
        operationalBodyMember.setId(count.incrementAndGet());

        // Create the OperationalBodyMember
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMemberMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperationalBodyMemberWithPatch() throws Exception {
        // Initialize the database
        operationalBodyMemberRepository.saveAndFlush(operationalBodyMember);

        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();

        // Update the operationalBodyMember using partial update
        OperationalBodyMember partialUpdatedOperationalBodyMember = new OperationalBodyMember();
        partialUpdatedOperationalBodyMember.setId(operationalBodyMember.getId());

        restOperationalBodyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationalBodyMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationalBodyMember))
            )
            .andExpect(status().isOk());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
        OperationalBodyMember testOperationalBodyMember = operationalBodyMemberList.get(operationalBodyMemberList.size() - 1);
        assertThat(testOperationalBodyMember.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testOperationalBodyMember.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testOperationalBodyMember.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOperationalBodyMember.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testOperationalBodyMember.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testOperationalBodyMember.getEunContactFirstname()).isEqualTo(DEFAULT_EUN_CONTACT_FIRSTNAME);
        assertThat(testOperationalBodyMember.getEunContactLastname()).isEqualTo(DEFAULT_EUN_CONTACT_LASTNAME);
        assertThat(testOperationalBodyMember.getCooperationField()).isEqualTo(DEFAULT_COOPERATION_FIELD);
        assertThat(testOperationalBodyMember.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOperationalBodyMemberWithPatch() throws Exception {
        // Initialize the database
        operationalBodyMemberRepository.saveAndFlush(operationalBodyMember);

        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();

        // Update the operationalBodyMember using partial update
        OperationalBodyMember partialUpdatedOperationalBodyMember = new OperationalBodyMember();
        partialUpdatedOperationalBodyMember.setId(operationalBodyMember.getId());

        partialUpdatedOperationalBodyMember
            .personId(UPDATED_PERSON_ID)
            .position(UPDATED_POSITION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .department(UPDATED_DEPARTMENT)
            .eunContactFirstname(UPDATED_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(UPDATED_EUN_CONTACT_LASTNAME)
            .cooperationField(UPDATED_COOPERATION_FIELD)
            .status(UPDATED_STATUS);

        restOperationalBodyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationalBodyMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationalBodyMember))
            )
            .andExpect(status().isOk());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
        OperationalBodyMember testOperationalBodyMember = operationalBodyMemberList.get(operationalBodyMemberList.size() - 1);
        assertThat(testOperationalBodyMember.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testOperationalBodyMember.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testOperationalBodyMember.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOperationalBodyMember.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOperationalBodyMember.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testOperationalBodyMember.getEunContactFirstname()).isEqualTo(UPDATED_EUN_CONTACT_FIRSTNAME);
        assertThat(testOperationalBodyMember.getEunContactLastname()).isEqualTo(UPDATED_EUN_CONTACT_LASTNAME);
        assertThat(testOperationalBodyMember.getCooperationField()).isEqualTo(UPDATED_COOPERATION_FIELD);
        assertThat(testOperationalBodyMember.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOperationalBodyMember() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();
        operationalBodyMember.setId(count.incrementAndGet());

        // Create the OperationalBodyMember
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationalBodyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operationalBodyMemberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperationalBodyMember() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();
        operationalBodyMember.setId(count.incrementAndGet());

        // Create the OperationalBodyMember
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperationalBodyMember() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyMemberRepository.findAll().size();
        operationalBodyMember.setId(count.incrementAndGet());

        // Create the OperationalBodyMember
        OperationalBodyMemberDTO operationalBodyMemberDTO = operationalBodyMemberMapper.toDto(operationalBodyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationalBodyMember in the database
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperationalBodyMember() throws Exception {
        // Initialize the database
        operationalBodyMemberRepository.saveAndFlush(operationalBodyMember);

        int databaseSizeBeforeDelete = operationalBodyMemberRepository.findAll().size();

        // Delete the operationalBodyMember
        restOperationalBodyMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, operationalBodyMember.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationalBodyMember> operationalBodyMemberList = operationalBodyMemberRepository.findAll();
        assertThat(operationalBodyMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
