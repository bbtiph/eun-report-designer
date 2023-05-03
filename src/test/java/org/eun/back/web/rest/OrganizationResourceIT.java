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
import org.eun.back.domain.Organization;
import org.eun.back.domain.enumeration.OrgStatus;
import org.eun.back.repository.OrganizationRepository;
import org.eun.back.service.dto.OrganizationDTO;
import org.eun.back.service.mapper.OrganizationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationResourceIT {

    private static final Long DEFAULT_EUN_DB_ID = 1L;
    private static final Long UPDATED_EUN_DB_ID = 2L;

    private static final OrgStatus DEFAULT_STATUS = OrgStatus.ACTIVE;
    private static final OrgStatus UPDATED_STATUS = OrgStatus.MERGED;

    private static final String DEFAULT_OFFICIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OFFICIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_LATITUDE = 1;
    private static final Integer UPDATED_LATITUDE = 2;

    private static final Integer DEFAULT_LONGITUDE = 1;
    private static final Integer UPDATED_LONGITUDE = 2;

    private static final Integer DEFAULT_MAX_AGE = 1;
    private static final Integer UPDATED_MAX_AGE = 2;

    private static final Integer DEFAULT_MIN_AGE = 1;
    private static final Integer UPDATED_MIN_AGE = 2;

    private static final Integer DEFAULT_AREA = 1;
    private static final Integer UPDATED_AREA = 2;

    private static final String DEFAULT_SPECIALIZATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER_OF_STUDENTS = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_OF_STUDENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HARDWARE_USED = false;
    private static final Boolean UPDATED_HARDWARE_USED = true;

    private static final Boolean DEFAULT_ICT_USED = false;
    private static final Boolean UPDATED_ICT_USED = true;

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .eunDbId(DEFAULT_EUN_DB_ID)
            .status(DEFAULT_STATUS)
            .officialName(DEFAULT_OFFICIAL_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .address(DEFAULT_ADDRESS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .maxAge(DEFAULT_MAX_AGE)
            .minAge(DEFAULT_MIN_AGE)
            .area(DEFAULT_AREA)
            .specialization(DEFAULT_SPECIALIZATION)
            .numberOfStudents(DEFAULT_NUMBER_OF_STUDENTS)
            .hardwareUsed(DEFAULT_HARDWARE_USED)
            .ictUsed(DEFAULT_ICT_USED)
            .website(DEFAULT_WEBSITE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .telephone(DEFAULT_TELEPHONE)
            .fax(DEFAULT_FAX)
            .email(DEFAULT_EMAIL)
            .organisationNumber(DEFAULT_ORGANISATION_NUMBER);
        return organization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity(EntityManager em) {
        Organization organization = new Organization()
            .eunDbId(UPDATED_EUN_DB_ID)
            .status(UPDATED_STATUS)
            .officialName(UPDATED_OFFICIAL_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .maxAge(UPDATED_MAX_AGE)
            .minAge(UPDATED_MIN_AGE)
            .area(UPDATED_AREA)
            .specialization(UPDATED_SPECIALIZATION)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS)
            .hardwareUsed(UPDATED_HARDWARE_USED)
            .ictUsed(UPDATED_ICT_USED)
            .website(UPDATED_WEBSITE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .organisationNumber(UPDATED_ORGANISATION_NUMBER);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();
        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);
        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getEunDbId()).isEqualTo(DEFAULT_EUN_DB_ID);
        assertThat(testOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganization.getOfficialName()).isEqualTo(DEFAULT_OFFICIAL_NAME);
        assertThat(testOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganization.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrganization.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrganization.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testOrganization.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testOrganization.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
        assertThat(testOrganization.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testOrganization.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testOrganization.getSpecialization()).isEqualTo(DEFAULT_SPECIALIZATION);
        assertThat(testOrganization.getNumberOfStudents()).isEqualTo(DEFAULT_NUMBER_OF_STUDENTS);
        assertThat(testOrganization.getHardwareUsed()).isEqualTo(DEFAULT_HARDWARE_USED);
        assertThat(testOrganization.getIctUsed()).isEqualTo(DEFAULT_ICT_USED);
        assertThat(testOrganization.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrganization.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testOrganization.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testOrganization.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testOrganization.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganization.getOrganisationNumber()).isEqualTo(DEFAULT_ORGANISATION_NUMBER);
    }

    @Test
    @Transactional
    void createOrganizationWithExistingId() throws Exception {
        // Create the Organization with an existing ID
        organization.setId(1L);
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setStatus(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOfficialNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setOfficialName(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].eunDbId").value(hasItem(DEFAULT_EUN_DB_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].officialName").value(hasItem(DEFAULT_OFFICIAL_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE)))
            .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].specialization").value(hasItem(DEFAULT_SPECIALIZATION)))
            .andExpect(jsonPath("$.[*].numberOfStudents").value(hasItem(DEFAULT_NUMBER_OF_STUDENTS)))
            .andExpect(jsonPath("$.[*].hardwareUsed").value(hasItem(DEFAULT_HARDWARE_USED.booleanValue())))
            .andExpect(jsonPath("$.[*].ictUsed").value(hasItem(DEFAULT_ICT_USED.booleanValue())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].organisationNumber").value(hasItem(DEFAULT_ORGANISATION_NUMBER)));
    }

    @Test
    @Transactional
    void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.eunDbId").value(DEFAULT_EUN_DB_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.officialName").value(DEFAULT_OFFICIAL_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.maxAge").value(DEFAULT_MAX_AGE))
            .andExpect(jsonPath("$.minAge").value(DEFAULT_MIN_AGE))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.specialization").value(DEFAULT_SPECIALIZATION))
            .andExpect(jsonPath("$.numberOfStudents").value(DEFAULT_NUMBER_OF_STUDENTS))
            .andExpect(jsonPath("$.hardwareUsed").value(DEFAULT_HARDWARE_USED.booleanValue()))
            .andExpect(jsonPath("$.ictUsed").value(DEFAULT_ICT_USED.booleanValue()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.organisationNumber").value(DEFAULT_ORGANISATION_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .eunDbId(UPDATED_EUN_DB_ID)
            .status(UPDATED_STATUS)
            .officialName(UPDATED_OFFICIAL_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .maxAge(UPDATED_MAX_AGE)
            .minAge(UPDATED_MIN_AGE)
            .area(UPDATED_AREA)
            .specialization(UPDATED_SPECIALIZATION)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS)
            .hardwareUsed(UPDATED_HARDWARE_USED)
            .ictUsed(UPDATED_ICT_USED)
            .website(UPDATED_WEBSITE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .organisationNumber(UPDATED_ORGANISATION_NUMBER);
        OrganizationDTO organizationDTO = organizationMapper.toDto(updatedOrganization);

        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getEunDbId()).isEqualTo(UPDATED_EUN_DB_ID);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getOfficialName()).isEqualTo(UPDATED_OFFICIAL_NAME);
        assertThat(testOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrganization.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrganization.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testOrganization.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testOrganization.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testOrganization.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testOrganization.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testOrganization.getSpecialization()).isEqualTo(UPDATED_SPECIALIZATION);
        assertThat(testOrganization.getNumberOfStudents()).isEqualTo(UPDATED_NUMBER_OF_STUDENTS);
        assertThat(testOrganization.getHardwareUsed()).isEqualTo(UPDATED_HARDWARE_USED);
        assertThat(testOrganization.getIctUsed()).isEqualTo(UPDATED_ICT_USED);
        assertThat(testOrganization.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganization.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testOrganization.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testOrganization.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testOrganization.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganization.getOrganisationNumber()).isEqualTo(UPDATED_ORGANISATION_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .eunDbId(UPDATED_EUN_DB_ID)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .longitude(UPDATED_LONGITUDE)
            .maxAge(UPDATED_MAX_AGE)
            .area(UPDATED_AREA)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS)
            .ictUsed(UPDATED_ICT_USED)
            .fax(UPDATED_FAX)
            .organisationNumber(UPDATED_ORGANISATION_NUMBER);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getEunDbId()).isEqualTo(UPDATED_EUN_DB_ID);
        assertThat(testOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganization.getOfficialName()).isEqualTo(DEFAULT_OFFICIAL_NAME);
        assertThat(testOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrganization.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrganization.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testOrganization.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testOrganization.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testOrganization.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testOrganization.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testOrganization.getSpecialization()).isEqualTo(DEFAULT_SPECIALIZATION);
        assertThat(testOrganization.getNumberOfStudents()).isEqualTo(UPDATED_NUMBER_OF_STUDENTS);
        assertThat(testOrganization.getHardwareUsed()).isEqualTo(DEFAULT_HARDWARE_USED);
        assertThat(testOrganization.getIctUsed()).isEqualTo(UPDATED_ICT_USED);
        assertThat(testOrganization.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrganization.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testOrganization.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testOrganization.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testOrganization.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganization.getOrganisationNumber()).isEqualTo(UPDATED_ORGANISATION_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .eunDbId(UPDATED_EUN_DB_ID)
            .status(UPDATED_STATUS)
            .officialName(UPDATED_OFFICIAL_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .maxAge(UPDATED_MAX_AGE)
            .minAge(UPDATED_MIN_AGE)
            .area(UPDATED_AREA)
            .specialization(UPDATED_SPECIALIZATION)
            .numberOfStudents(UPDATED_NUMBER_OF_STUDENTS)
            .hardwareUsed(UPDATED_HARDWARE_USED)
            .ictUsed(UPDATED_ICT_USED)
            .website(UPDATED_WEBSITE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .telephone(UPDATED_TELEPHONE)
            .fax(UPDATED_FAX)
            .email(UPDATED_EMAIL)
            .organisationNumber(UPDATED_ORGANISATION_NUMBER);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getEunDbId()).isEqualTo(UPDATED_EUN_DB_ID);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getOfficialName()).isEqualTo(UPDATED_OFFICIAL_NAME);
        assertThat(testOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrganization.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrganization.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testOrganization.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testOrganization.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testOrganization.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testOrganization.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testOrganization.getSpecialization()).isEqualTo(UPDATED_SPECIALIZATION);
        assertThat(testOrganization.getNumberOfStudents()).isEqualTo(UPDATED_NUMBER_OF_STUDENTS);
        assertThat(testOrganization.getHardwareUsed()).isEqualTo(UPDATED_HARDWARE_USED);
        assertThat(testOrganization.getIctUsed()).isEqualTo(UPDATED_ICT_USED);
        assertThat(testOrganization.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganization.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testOrganization.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testOrganization.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testOrganization.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganization.getOrganisationNumber()).isEqualTo(UPDATED_ORGANISATION_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
