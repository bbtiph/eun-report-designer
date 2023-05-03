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
import org.eun.back.domain.OrganizationInProject;
import org.eun.back.repository.OrganizationInProjectRepository;
import org.eun.back.service.dto.OrganizationInProjectDTO;
import org.eun.back.service.mapper.OrganizationInProjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganizationInProjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationInProjectResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_JOIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_JOIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_FUNDING_FOR_ORGANIZATION = 1;
    private static final Integer UPDATED_FUNDING_FOR_ORGANIZATION = 2;

    private static final Integer DEFAULT_PARTICIPATION_TO_MATCHING_FUNDING = 1;
    private static final Integer UPDATED_PARTICIPATION_TO_MATCHING_FUNDING = 2;

    private static final Boolean DEFAULT_SCHOOL_REGISTRATION_POSSIBLE = false;
    private static final Boolean UPDATED_SCHOOL_REGISTRATION_POSSIBLE = true;

    private static final Boolean DEFAULT_TEACHER_PARTICIPATION_POSSIBLE = false;
    private static final Boolean UPDATED_TEACHER_PARTICIPATION_POSSIBLE = true;

    private static final Boolean DEFAULT_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED = false;
    private static final Boolean UPDATED_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED = true;

    private static final Boolean DEFAULT_USERS_CAN_REGISTER_TO_PORTAL = false;
    private static final Boolean UPDATED_USERS_CAN_REGISTER_TO_PORTAL = true;

    private static final String ENTITY_API_URL = "/api/organization-in-projects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationInProjectRepository organizationInProjectRepository;

    @Autowired
    private OrganizationInProjectMapper organizationInProjectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationInProjectMockMvc;

    private OrganizationInProject organizationInProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationInProject createEntity(EntityManager em) {
        OrganizationInProject organizationInProject = new OrganizationInProject()
            .status(DEFAULT_STATUS)
            .joinDate(DEFAULT_JOIN_DATE)
            .fundingForOrganization(DEFAULT_FUNDING_FOR_ORGANIZATION)
            .participationToMatchingFunding(DEFAULT_PARTICIPATION_TO_MATCHING_FUNDING)
            .schoolRegistrationPossible(DEFAULT_SCHOOL_REGISTRATION_POSSIBLE)
            .teacherParticipationPossible(DEFAULT_TEACHER_PARTICIPATION_POSSIBLE)
            .ambassadorsPilotTeachersLeadingTeachersIdentified(DEFAULT_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED)
            .usersCanRegisterToPortal(DEFAULT_USERS_CAN_REGISTER_TO_PORTAL);
        return organizationInProject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationInProject createUpdatedEntity(EntityManager em) {
        OrganizationInProject organizationInProject = new OrganizationInProject()
            .status(UPDATED_STATUS)
            .joinDate(UPDATED_JOIN_DATE)
            .fundingForOrganization(UPDATED_FUNDING_FOR_ORGANIZATION)
            .participationToMatchingFunding(UPDATED_PARTICIPATION_TO_MATCHING_FUNDING)
            .schoolRegistrationPossible(UPDATED_SCHOOL_REGISTRATION_POSSIBLE)
            .teacherParticipationPossible(UPDATED_TEACHER_PARTICIPATION_POSSIBLE)
            .ambassadorsPilotTeachersLeadingTeachersIdentified(UPDATED_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED)
            .usersCanRegisterToPortal(UPDATED_USERS_CAN_REGISTER_TO_PORTAL);
        return organizationInProject;
    }

    @BeforeEach
    public void initTest() {
        organizationInProject = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganizationInProject() throws Exception {
        int databaseSizeBeforeCreate = organizationInProjectRepository.findAll().size();
        // Create the OrganizationInProject
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);
        restOrganizationInProjectMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationInProject testOrganizationInProject = organizationInProjectList.get(organizationInProjectList.size() - 1);
        assertThat(testOrganizationInProject.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganizationInProject.getJoinDate()).isEqualTo(DEFAULT_JOIN_DATE);
        assertThat(testOrganizationInProject.getFundingForOrganization()).isEqualTo(DEFAULT_FUNDING_FOR_ORGANIZATION);
        assertThat(testOrganizationInProject.getParticipationToMatchingFunding()).isEqualTo(DEFAULT_PARTICIPATION_TO_MATCHING_FUNDING);
        assertThat(testOrganizationInProject.getSchoolRegistrationPossible()).isEqualTo(DEFAULT_SCHOOL_REGISTRATION_POSSIBLE);
        assertThat(testOrganizationInProject.getTeacherParticipationPossible()).isEqualTo(DEFAULT_TEACHER_PARTICIPATION_POSSIBLE);
        assertThat(testOrganizationInProject.getAmbassadorsPilotTeachersLeadingTeachersIdentified())
            .isEqualTo(DEFAULT_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED);
        assertThat(testOrganizationInProject.getUsersCanRegisterToPortal()).isEqualTo(DEFAULT_USERS_CAN_REGISTER_TO_PORTAL);
    }

    @Test
    @Transactional
    void createOrganizationInProjectWithExistingId() throws Exception {
        // Create the OrganizationInProject with an existing ID
        organizationInProject.setId(1L);
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);

        int databaseSizeBeforeCreate = organizationInProjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationInProjectMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrganizationInProjects() throws Exception {
        // Initialize the database
        organizationInProjectRepository.saveAndFlush(organizationInProject);

        // Get all the organizationInProjectList
        restOrganizationInProjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationInProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].joinDate").value(hasItem(DEFAULT_JOIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].fundingForOrganization").value(hasItem(DEFAULT_FUNDING_FOR_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].participationToMatchingFunding").value(hasItem(DEFAULT_PARTICIPATION_TO_MATCHING_FUNDING)))
            .andExpect(jsonPath("$.[*].schoolRegistrationPossible").value(hasItem(DEFAULT_SCHOOL_REGISTRATION_POSSIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].teacherParticipationPossible").value(hasItem(DEFAULT_TEACHER_PARTICIPATION_POSSIBLE.booleanValue())))
            .andExpect(
                jsonPath("$.[*].ambassadorsPilotTeachersLeadingTeachersIdentified")
                    .value(hasItem(DEFAULT_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].usersCanRegisterToPortal").value(hasItem(DEFAULT_USERS_CAN_REGISTER_TO_PORTAL.booleanValue())));
    }

    @Test
    @Transactional
    void getOrganizationInProject() throws Exception {
        // Initialize the database
        organizationInProjectRepository.saveAndFlush(organizationInProject);

        // Get the organizationInProject
        restOrganizationInProjectMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationInProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationInProject.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.joinDate").value(DEFAULT_JOIN_DATE.toString()))
            .andExpect(jsonPath("$.fundingForOrganization").value(DEFAULT_FUNDING_FOR_ORGANIZATION))
            .andExpect(jsonPath("$.participationToMatchingFunding").value(DEFAULT_PARTICIPATION_TO_MATCHING_FUNDING))
            .andExpect(jsonPath("$.schoolRegistrationPossible").value(DEFAULT_SCHOOL_REGISTRATION_POSSIBLE.booleanValue()))
            .andExpect(jsonPath("$.teacherParticipationPossible").value(DEFAULT_TEACHER_PARTICIPATION_POSSIBLE.booleanValue()))
            .andExpect(
                jsonPath("$.ambassadorsPilotTeachersLeadingTeachersIdentified")
                    .value(DEFAULT_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED.booleanValue())
            )
            .andExpect(jsonPath("$.usersCanRegisterToPortal").value(DEFAULT_USERS_CAN_REGISTER_TO_PORTAL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationInProject() throws Exception {
        // Get the organizationInProject
        restOrganizationInProjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationInProject() throws Exception {
        // Initialize the database
        organizationInProjectRepository.saveAndFlush(organizationInProject);

        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();

        // Update the organizationInProject
        OrganizationInProject updatedOrganizationInProject = organizationInProjectRepository.findById(organizationInProject.getId()).get();
        // Disconnect from session so that the updates on updatedOrganizationInProject are not directly saved in db
        em.detach(updatedOrganizationInProject);
        updatedOrganizationInProject
            .status(UPDATED_STATUS)
            .joinDate(UPDATED_JOIN_DATE)
            .fundingForOrganization(UPDATED_FUNDING_FOR_ORGANIZATION)
            .participationToMatchingFunding(UPDATED_PARTICIPATION_TO_MATCHING_FUNDING)
            .schoolRegistrationPossible(UPDATED_SCHOOL_REGISTRATION_POSSIBLE)
            .teacherParticipationPossible(UPDATED_TEACHER_PARTICIPATION_POSSIBLE)
            .ambassadorsPilotTeachersLeadingTeachersIdentified(UPDATED_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED)
            .usersCanRegisterToPortal(UPDATED_USERS_CAN_REGISTER_TO_PORTAL);
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(updatedOrganizationInProject);

        restOrganizationInProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationInProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
        OrganizationInProject testOrganizationInProject = organizationInProjectList.get(organizationInProjectList.size() - 1);
        assertThat(testOrganizationInProject.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganizationInProject.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testOrganizationInProject.getFundingForOrganization()).isEqualTo(UPDATED_FUNDING_FOR_ORGANIZATION);
        assertThat(testOrganizationInProject.getParticipationToMatchingFunding()).isEqualTo(UPDATED_PARTICIPATION_TO_MATCHING_FUNDING);
        assertThat(testOrganizationInProject.getSchoolRegistrationPossible()).isEqualTo(UPDATED_SCHOOL_REGISTRATION_POSSIBLE);
        assertThat(testOrganizationInProject.getTeacherParticipationPossible()).isEqualTo(UPDATED_TEACHER_PARTICIPATION_POSSIBLE);
        assertThat(testOrganizationInProject.getAmbassadorsPilotTeachersLeadingTeachersIdentified())
            .isEqualTo(UPDATED_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED);
        assertThat(testOrganizationInProject.getUsersCanRegisterToPortal()).isEqualTo(UPDATED_USERS_CAN_REGISTER_TO_PORTAL);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationInProject() throws Exception {
        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();
        organizationInProject.setId(count.incrementAndGet());

        // Create the OrganizationInProject
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationInProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationInProjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationInProject() throws Exception {
        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();
        organizationInProject.setId(count.incrementAndGet());

        // Create the OrganizationInProject
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInProjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationInProject() throws Exception {
        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();
        organizationInProject.setId(count.incrementAndGet());

        // Create the OrganizationInProject
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInProjectMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationInProjectWithPatch() throws Exception {
        // Initialize the database
        organizationInProjectRepository.saveAndFlush(organizationInProject);

        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();

        // Update the organizationInProject using partial update
        OrganizationInProject partialUpdatedOrganizationInProject = new OrganizationInProject();
        partialUpdatedOrganizationInProject.setId(organizationInProject.getId());

        partialUpdatedOrganizationInProject
            .joinDate(UPDATED_JOIN_DATE)
            .fundingForOrganization(UPDATED_FUNDING_FOR_ORGANIZATION)
            .schoolRegistrationPossible(UPDATED_SCHOOL_REGISTRATION_POSSIBLE)
            .teacherParticipationPossible(UPDATED_TEACHER_PARTICIPATION_POSSIBLE);

        restOrganizationInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationInProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationInProject))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
        OrganizationInProject testOrganizationInProject = organizationInProjectList.get(organizationInProjectList.size() - 1);
        assertThat(testOrganizationInProject.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganizationInProject.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testOrganizationInProject.getFundingForOrganization()).isEqualTo(UPDATED_FUNDING_FOR_ORGANIZATION);
        assertThat(testOrganizationInProject.getParticipationToMatchingFunding()).isEqualTo(DEFAULT_PARTICIPATION_TO_MATCHING_FUNDING);
        assertThat(testOrganizationInProject.getSchoolRegistrationPossible()).isEqualTo(UPDATED_SCHOOL_REGISTRATION_POSSIBLE);
        assertThat(testOrganizationInProject.getTeacherParticipationPossible()).isEqualTo(UPDATED_TEACHER_PARTICIPATION_POSSIBLE);
        assertThat(testOrganizationInProject.getAmbassadorsPilotTeachersLeadingTeachersIdentified())
            .isEqualTo(DEFAULT_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED);
        assertThat(testOrganizationInProject.getUsersCanRegisterToPortal()).isEqualTo(DEFAULT_USERS_CAN_REGISTER_TO_PORTAL);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationInProjectWithPatch() throws Exception {
        // Initialize the database
        organizationInProjectRepository.saveAndFlush(organizationInProject);

        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();

        // Update the organizationInProject using partial update
        OrganizationInProject partialUpdatedOrganizationInProject = new OrganizationInProject();
        partialUpdatedOrganizationInProject.setId(organizationInProject.getId());

        partialUpdatedOrganizationInProject
            .status(UPDATED_STATUS)
            .joinDate(UPDATED_JOIN_DATE)
            .fundingForOrganization(UPDATED_FUNDING_FOR_ORGANIZATION)
            .participationToMatchingFunding(UPDATED_PARTICIPATION_TO_MATCHING_FUNDING)
            .schoolRegistrationPossible(UPDATED_SCHOOL_REGISTRATION_POSSIBLE)
            .teacherParticipationPossible(UPDATED_TEACHER_PARTICIPATION_POSSIBLE)
            .ambassadorsPilotTeachersLeadingTeachersIdentified(UPDATED_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED)
            .usersCanRegisterToPortal(UPDATED_USERS_CAN_REGISTER_TO_PORTAL);

        restOrganizationInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationInProject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganizationInProject))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
        OrganizationInProject testOrganizationInProject = organizationInProjectList.get(organizationInProjectList.size() - 1);
        assertThat(testOrganizationInProject.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganizationInProject.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testOrganizationInProject.getFundingForOrganization()).isEqualTo(UPDATED_FUNDING_FOR_ORGANIZATION);
        assertThat(testOrganizationInProject.getParticipationToMatchingFunding()).isEqualTo(UPDATED_PARTICIPATION_TO_MATCHING_FUNDING);
        assertThat(testOrganizationInProject.getSchoolRegistrationPossible()).isEqualTo(UPDATED_SCHOOL_REGISTRATION_POSSIBLE);
        assertThat(testOrganizationInProject.getTeacherParticipationPossible()).isEqualTo(UPDATED_TEACHER_PARTICIPATION_POSSIBLE);
        assertThat(testOrganizationInProject.getAmbassadorsPilotTeachersLeadingTeachersIdentified())
            .isEqualTo(UPDATED_AMBASSADORS_PILOT_TEACHERS_LEADING_TEACHERS_IDENTIFIED);
        assertThat(testOrganizationInProject.getUsersCanRegisterToPortal()).isEqualTo(UPDATED_USERS_CAN_REGISTER_TO_PORTAL);
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationInProject() throws Exception {
        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();
        organizationInProject.setId(count.incrementAndGet());

        // Create the OrganizationInProject
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationInProjectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationInProject() throws Exception {
        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();
        organizationInProject.setId(count.incrementAndGet());

        // Create the OrganizationInProject
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationInProject() throws Exception {
        int databaseSizeBeforeUpdate = organizationInProjectRepository.findAll().size();
        organizationInProject.setId(count.incrementAndGet());

        // Create the OrganizationInProject
        OrganizationInProjectDTO organizationInProjectDTO = organizationInProjectMapper.toDto(organizationInProject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationInProjectMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationInProjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationInProject in the database
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationInProject() throws Exception {
        // Initialize the database
        organizationInProjectRepository.saveAndFlush(organizationInProject);

        int databaseSizeBeforeDelete = organizationInProjectRepository.findAll().size();

        // Delete the organizationInProject
        restOrganizationInProjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationInProject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationInProject> organizationInProjectList = organizationInProjectRepository.findAll();
        assertThat(organizationInProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
