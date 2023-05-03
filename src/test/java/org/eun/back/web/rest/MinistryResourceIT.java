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
import org.eun.back.domain.Ministry;
import org.eun.back.repository.MinistryRepository;
import org.eun.back.service.dto.MinistryDTO;
import org.eun.back.service.mapper.MinistryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MinistryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MinistryResourceIT {

    private static final String DEFAULT_LANGUAGES = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGES = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_STEERCOM_MEMBER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STEERCOM_MEMBER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STEERCOM_MEMBER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_STEERCOM_MEMBER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_ADDRESS_REGION = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_ADDRESS_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_ADDRESS_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_ADDRESS_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_ADDRESS_CITY = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_ADDRESS_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_ADDRESS_STREET = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_ADDRESS_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EUN_CONTACT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_EUN_CONTACT_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EUN_CONTACT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_EUN_CONTACT_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EUN_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EUN_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FINANCIAL_CORRESPONDING_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_FINANCIAL_CORRESPONDING_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ministries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MinistryRepository ministryRepository;

    @Autowired
    private MinistryMapper ministryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMinistryMockMvc;

    private Ministry ministry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ministry createEntity(EntityManager em) {
        Ministry ministry = new Ministry()
            .languages(DEFAULT_LANGUAGES)
            .formalName(DEFAULT_FORMAL_NAME)
            .englishName(DEFAULT_ENGLISH_NAME)
            .acronym(DEFAULT_ACRONYM)
            .description(DEFAULT_DESCRIPTION)
            .website(DEFAULT_WEBSITE)
            .steercomMemberName(DEFAULT_STEERCOM_MEMBER_NAME)
            .steercomMemberEmail(DEFAULT_STEERCOM_MEMBER_EMAIL)
            .postalAddressRegion(DEFAULT_POSTAL_ADDRESS_REGION)
            .postalAddressPostalCode(DEFAULT_POSTAL_ADDRESS_POSTAL_CODE)
            .postalAddressCity(DEFAULT_POSTAL_ADDRESS_CITY)
            .postalAddressStreet(DEFAULT_POSTAL_ADDRESS_STREET)
            .status(DEFAULT_STATUS)
            .eunContactFirstname(DEFAULT_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(DEFAULT_EUN_CONTACT_LASTNAME)
            .eunContactEmail(DEFAULT_EUN_CONTACT_EMAIL)
            .invoicingAddress(DEFAULT_INVOICING_ADDRESS)
            .financialCorrespondingEmail(DEFAULT_FINANCIAL_CORRESPONDING_EMAIL);
        return ministry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ministry createUpdatedEntity(EntityManager em) {
        Ministry ministry = new Ministry()
            .languages(UPDATED_LANGUAGES)
            .formalName(UPDATED_FORMAL_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .steercomMemberName(UPDATED_STEERCOM_MEMBER_NAME)
            .steercomMemberEmail(UPDATED_STEERCOM_MEMBER_EMAIL)
            .postalAddressRegion(UPDATED_POSTAL_ADDRESS_REGION)
            .postalAddressPostalCode(UPDATED_POSTAL_ADDRESS_POSTAL_CODE)
            .postalAddressCity(UPDATED_POSTAL_ADDRESS_CITY)
            .postalAddressStreet(UPDATED_POSTAL_ADDRESS_STREET)
            .status(UPDATED_STATUS)
            .eunContactFirstname(UPDATED_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(UPDATED_EUN_CONTACT_LASTNAME)
            .eunContactEmail(UPDATED_EUN_CONTACT_EMAIL)
            .invoicingAddress(UPDATED_INVOICING_ADDRESS)
            .financialCorrespondingEmail(UPDATED_FINANCIAL_CORRESPONDING_EMAIL);
        return ministry;
    }

    @BeforeEach
    public void initTest() {
        ministry = createEntity(em);
    }

    @Test
    @Transactional
    void createMinistry() throws Exception {
        int databaseSizeBeforeCreate = ministryRepository.findAll().size();
        // Create the Ministry
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);
        restMinistryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ministryDTO)))
            .andExpect(status().isCreated());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeCreate + 1);
        Ministry testMinistry = ministryList.get(ministryList.size() - 1);
        assertThat(testMinistry.getLanguages()).isEqualTo(DEFAULT_LANGUAGES);
        assertThat(testMinistry.getFormalName()).isEqualTo(DEFAULT_FORMAL_NAME);
        assertThat(testMinistry.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
        assertThat(testMinistry.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testMinistry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMinistry.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testMinistry.getSteercomMemberName()).isEqualTo(DEFAULT_STEERCOM_MEMBER_NAME);
        assertThat(testMinistry.getSteercomMemberEmail()).isEqualTo(DEFAULT_STEERCOM_MEMBER_EMAIL);
        assertThat(testMinistry.getPostalAddressRegion()).isEqualTo(DEFAULT_POSTAL_ADDRESS_REGION);
        assertThat(testMinistry.getPostalAddressPostalCode()).isEqualTo(DEFAULT_POSTAL_ADDRESS_POSTAL_CODE);
        assertThat(testMinistry.getPostalAddressCity()).isEqualTo(DEFAULT_POSTAL_ADDRESS_CITY);
        assertThat(testMinistry.getPostalAddressStreet()).isEqualTo(DEFAULT_POSTAL_ADDRESS_STREET);
        assertThat(testMinistry.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMinistry.getEunContactFirstname()).isEqualTo(DEFAULT_EUN_CONTACT_FIRSTNAME);
        assertThat(testMinistry.getEunContactLastname()).isEqualTo(DEFAULT_EUN_CONTACT_LASTNAME);
        assertThat(testMinistry.getEunContactEmail()).isEqualTo(DEFAULT_EUN_CONTACT_EMAIL);
        assertThat(testMinistry.getInvoicingAddress()).isEqualTo(DEFAULT_INVOICING_ADDRESS);
        assertThat(testMinistry.getFinancialCorrespondingEmail()).isEqualTo(DEFAULT_FINANCIAL_CORRESPONDING_EMAIL);
    }

    @Test
    @Transactional
    void createMinistryWithExistingId() throws Exception {
        // Create the Ministry with an existing ID
        ministry.setId(1L);
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);

        int databaseSizeBeforeCreate = ministryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMinistryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ministryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMinistries() throws Exception {
        // Initialize the database
        ministryRepository.saveAndFlush(ministry);

        // Get all the ministryList
        restMinistryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ministry.getId().intValue())))
            .andExpect(jsonPath("$.[*].languages").value(hasItem(DEFAULT_LANGUAGES)))
            .andExpect(jsonPath("$.[*].formalName").value(hasItem(DEFAULT_FORMAL_NAME)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME)))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].steercomMemberName").value(hasItem(DEFAULT_STEERCOM_MEMBER_NAME)))
            .andExpect(jsonPath("$.[*].steercomMemberEmail").value(hasItem(DEFAULT_STEERCOM_MEMBER_EMAIL)))
            .andExpect(jsonPath("$.[*].postalAddressRegion").value(hasItem(DEFAULT_POSTAL_ADDRESS_REGION)))
            .andExpect(jsonPath("$.[*].postalAddressPostalCode").value(hasItem(DEFAULT_POSTAL_ADDRESS_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].postalAddressCity").value(hasItem(DEFAULT_POSTAL_ADDRESS_CITY)))
            .andExpect(jsonPath("$.[*].postalAddressStreet").value(hasItem(DEFAULT_POSTAL_ADDRESS_STREET)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].eunContactFirstname").value(hasItem(DEFAULT_EUN_CONTACT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].eunContactLastname").value(hasItem(DEFAULT_EUN_CONTACT_LASTNAME)))
            .andExpect(jsonPath("$.[*].eunContactEmail").value(hasItem(DEFAULT_EUN_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].invoicingAddress").value(hasItem(DEFAULT_INVOICING_ADDRESS)))
            .andExpect(jsonPath("$.[*].financialCorrespondingEmail").value(hasItem(DEFAULT_FINANCIAL_CORRESPONDING_EMAIL)));
    }

    @Test
    @Transactional
    void getMinistry() throws Exception {
        // Initialize the database
        ministryRepository.saveAndFlush(ministry);

        // Get the ministry
        restMinistryMockMvc
            .perform(get(ENTITY_API_URL_ID, ministry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ministry.getId().intValue()))
            .andExpect(jsonPath("$.languages").value(DEFAULT_LANGUAGES))
            .andExpect(jsonPath("$.formalName").value(DEFAULT_FORMAL_NAME))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.steercomMemberName").value(DEFAULT_STEERCOM_MEMBER_NAME))
            .andExpect(jsonPath("$.steercomMemberEmail").value(DEFAULT_STEERCOM_MEMBER_EMAIL))
            .andExpect(jsonPath("$.postalAddressRegion").value(DEFAULT_POSTAL_ADDRESS_REGION))
            .andExpect(jsonPath("$.postalAddressPostalCode").value(DEFAULT_POSTAL_ADDRESS_POSTAL_CODE))
            .andExpect(jsonPath("$.postalAddressCity").value(DEFAULT_POSTAL_ADDRESS_CITY))
            .andExpect(jsonPath("$.postalAddressStreet").value(DEFAULT_POSTAL_ADDRESS_STREET))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.eunContactFirstname").value(DEFAULT_EUN_CONTACT_FIRSTNAME))
            .andExpect(jsonPath("$.eunContactLastname").value(DEFAULT_EUN_CONTACT_LASTNAME))
            .andExpect(jsonPath("$.eunContactEmail").value(DEFAULT_EUN_CONTACT_EMAIL))
            .andExpect(jsonPath("$.invoicingAddress").value(DEFAULT_INVOICING_ADDRESS))
            .andExpect(jsonPath("$.financialCorrespondingEmail").value(DEFAULT_FINANCIAL_CORRESPONDING_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingMinistry() throws Exception {
        // Get the ministry
        restMinistryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMinistry() throws Exception {
        // Initialize the database
        ministryRepository.saveAndFlush(ministry);

        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();

        // Update the ministry
        Ministry updatedMinistry = ministryRepository.findById(ministry.getId()).get();
        // Disconnect from session so that the updates on updatedMinistry are not directly saved in db
        em.detach(updatedMinistry);
        updatedMinistry
            .languages(UPDATED_LANGUAGES)
            .formalName(UPDATED_FORMAL_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .steercomMemberName(UPDATED_STEERCOM_MEMBER_NAME)
            .steercomMemberEmail(UPDATED_STEERCOM_MEMBER_EMAIL)
            .postalAddressRegion(UPDATED_POSTAL_ADDRESS_REGION)
            .postalAddressPostalCode(UPDATED_POSTAL_ADDRESS_POSTAL_CODE)
            .postalAddressCity(UPDATED_POSTAL_ADDRESS_CITY)
            .postalAddressStreet(UPDATED_POSTAL_ADDRESS_STREET)
            .status(UPDATED_STATUS)
            .eunContactFirstname(UPDATED_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(UPDATED_EUN_CONTACT_LASTNAME)
            .eunContactEmail(UPDATED_EUN_CONTACT_EMAIL)
            .invoicingAddress(UPDATED_INVOICING_ADDRESS)
            .financialCorrespondingEmail(UPDATED_FINANCIAL_CORRESPONDING_EMAIL);
        MinistryDTO ministryDTO = ministryMapper.toDto(updatedMinistry);

        restMinistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ministryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ministryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
        Ministry testMinistry = ministryList.get(ministryList.size() - 1);
        assertThat(testMinistry.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testMinistry.getFormalName()).isEqualTo(UPDATED_FORMAL_NAME);
        assertThat(testMinistry.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
        assertThat(testMinistry.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testMinistry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMinistry.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testMinistry.getSteercomMemberName()).isEqualTo(UPDATED_STEERCOM_MEMBER_NAME);
        assertThat(testMinistry.getSteercomMemberEmail()).isEqualTo(UPDATED_STEERCOM_MEMBER_EMAIL);
        assertThat(testMinistry.getPostalAddressRegion()).isEqualTo(UPDATED_POSTAL_ADDRESS_REGION);
        assertThat(testMinistry.getPostalAddressPostalCode()).isEqualTo(UPDATED_POSTAL_ADDRESS_POSTAL_CODE);
        assertThat(testMinistry.getPostalAddressCity()).isEqualTo(UPDATED_POSTAL_ADDRESS_CITY);
        assertThat(testMinistry.getPostalAddressStreet()).isEqualTo(UPDATED_POSTAL_ADDRESS_STREET);
        assertThat(testMinistry.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMinistry.getEunContactFirstname()).isEqualTo(UPDATED_EUN_CONTACT_FIRSTNAME);
        assertThat(testMinistry.getEunContactLastname()).isEqualTo(UPDATED_EUN_CONTACT_LASTNAME);
        assertThat(testMinistry.getEunContactEmail()).isEqualTo(UPDATED_EUN_CONTACT_EMAIL);
        assertThat(testMinistry.getInvoicingAddress()).isEqualTo(UPDATED_INVOICING_ADDRESS);
        assertThat(testMinistry.getFinancialCorrespondingEmail()).isEqualTo(UPDATED_FINANCIAL_CORRESPONDING_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingMinistry() throws Exception {
        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();
        ministry.setId(count.incrementAndGet());

        // Create the Ministry
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMinistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ministryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ministryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMinistry() throws Exception {
        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();
        ministry.setId(count.incrementAndGet());

        // Create the Ministry
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMinistryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ministryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMinistry() throws Exception {
        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();
        ministry.setId(count.incrementAndGet());

        // Create the Ministry
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMinistryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ministryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMinistryWithPatch() throws Exception {
        // Initialize the database
        ministryRepository.saveAndFlush(ministry);

        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();

        // Update the ministry using partial update
        Ministry partialUpdatedMinistry = new Ministry();
        partialUpdatedMinistry.setId(ministry.getId());

        partialUpdatedMinistry
            .languages(UPDATED_LANGUAGES)
            .steercomMemberName(UPDATED_STEERCOM_MEMBER_NAME)
            .steercomMemberEmail(UPDATED_STEERCOM_MEMBER_EMAIL)
            .postalAddressPostalCode(UPDATED_POSTAL_ADDRESS_POSTAL_CODE)
            .eunContactFirstname(UPDATED_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(UPDATED_EUN_CONTACT_LASTNAME)
            .eunContactEmail(UPDATED_EUN_CONTACT_EMAIL)
            .invoicingAddress(UPDATED_INVOICING_ADDRESS);

        restMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMinistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMinistry))
            )
            .andExpect(status().isOk());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
        Ministry testMinistry = ministryList.get(ministryList.size() - 1);
        assertThat(testMinistry.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testMinistry.getFormalName()).isEqualTo(DEFAULT_FORMAL_NAME);
        assertThat(testMinistry.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
        assertThat(testMinistry.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testMinistry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMinistry.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testMinistry.getSteercomMemberName()).isEqualTo(UPDATED_STEERCOM_MEMBER_NAME);
        assertThat(testMinistry.getSteercomMemberEmail()).isEqualTo(UPDATED_STEERCOM_MEMBER_EMAIL);
        assertThat(testMinistry.getPostalAddressRegion()).isEqualTo(DEFAULT_POSTAL_ADDRESS_REGION);
        assertThat(testMinistry.getPostalAddressPostalCode()).isEqualTo(UPDATED_POSTAL_ADDRESS_POSTAL_CODE);
        assertThat(testMinistry.getPostalAddressCity()).isEqualTo(DEFAULT_POSTAL_ADDRESS_CITY);
        assertThat(testMinistry.getPostalAddressStreet()).isEqualTo(DEFAULT_POSTAL_ADDRESS_STREET);
        assertThat(testMinistry.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMinistry.getEunContactFirstname()).isEqualTo(UPDATED_EUN_CONTACT_FIRSTNAME);
        assertThat(testMinistry.getEunContactLastname()).isEqualTo(UPDATED_EUN_CONTACT_LASTNAME);
        assertThat(testMinistry.getEunContactEmail()).isEqualTo(UPDATED_EUN_CONTACT_EMAIL);
        assertThat(testMinistry.getInvoicingAddress()).isEqualTo(UPDATED_INVOICING_ADDRESS);
        assertThat(testMinistry.getFinancialCorrespondingEmail()).isEqualTo(DEFAULT_FINANCIAL_CORRESPONDING_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateMinistryWithPatch() throws Exception {
        // Initialize the database
        ministryRepository.saveAndFlush(ministry);

        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();

        // Update the ministry using partial update
        Ministry partialUpdatedMinistry = new Ministry();
        partialUpdatedMinistry.setId(ministry.getId());

        partialUpdatedMinistry
            .languages(UPDATED_LANGUAGES)
            .formalName(UPDATED_FORMAL_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .steercomMemberName(UPDATED_STEERCOM_MEMBER_NAME)
            .steercomMemberEmail(UPDATED_STEERCOM_MEMBER_EMAIL)
            .postalAddressRegion(UPDATED_POSTAL_ADDRESS_REGION)
            .postalAddressPostalCode(UPDATED_POSTAL_ADDRESS_POSTAL_CODE)
            .postalAddressCity(UPDATED_POSTAL_ADDRESS_CITY)
            .postalAddressStreet(UPDATED_POSTAL_ADDRESS_STREET)
            .status(UPDATED_STATUS)
            .eunContactFirstname(UPDATED_EUN_CONTACT_FIRSTNAME)
            .eunContactLastname(UPDATED_EUN_CONTACT_LASTNAME)
            .eunContactEmail(UPDATED_EUN_CONTACT_EMAIL)
            .invoicingAddress(UPDATED_INVOICING_ADDRESS)
            .financialCorrespondingEmail(UPDATED_FINANCIAL_CORRESPONDING_EMAIL);

        restMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMinistry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMinistry))
            )
            .andExpect(status().isOk());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
        Ministry testMinistry = ministryList.get(ministryList.size() - 1);
        assertThat(testMinistry.getLanguages()).isEqualTo(UPDATED_LANGUAGES);
        assertThat(testMinistry.getFormalName()).isEqualTo(UPDATED_FORMAL_NAME);
        assertThat(testMinistry.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
        assertThat(testMinistry.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testMinistry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMinistry.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testMinistry.getSteercomMemberName()).isEqualTo(UPDATED_STEERCOM_MEMBER_NAME);
        assertThat(testMinistry.getSteercomMemberEmail()).isEqualTo(UPDATED_STEERCOM_MEMBER_EMAIL);
        assertThat(testMinistry.getPostalAddressRegion()).isEqualTo(UPDATED_POSTAL_ADDRESS_REGION);
        assertThat(testMinistry.getPostalAddressPostalCode()).isEqualTo(UPDATED_POSTAL_ADDRESS_POSTAL_CODE);
        assertThat(testMinistry.getPostalAddressCity()).isEqualTo(UPDATED_POSTAL_ADDRESS_CITY);
        assertThat(testMinistry.getPostalAddressStreet()).isEqualTo(UPDATED_POSTAL_ADDRESS_STREET);
        assertThat(testMinistry.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMinistry.getEunContactFirstname()).isEqualTo(UPDATED_EUN_CONTACT_FIRSTNAME);
        assertThat(testMinistry.getEunContactLastname()).isEqualTo(UPDATED_EUN_CONTACT_LASTNAME);
        assertThat(testMinistry.getEunContactEmail()).isEqualTo(UPDATED_EUN_CONTACT_EMAIL);
        assertThat(testMinistry.getInvoicingAddress()).isEqualTo(UPDATED_INVOICING_ADDRESS);
        assertThat(testMinistry.getFinancialCorrespondingEmail()).isEqualTo(UPDATED_FINANCIAL_CORRESPONDING_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingMinistry() throws Exception {
        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();
        ministry.setId(count.incrementAndGet());

        // Create the Ministry
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ministryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ministryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMinistry() throws Exception {
        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();
        ministry.setId(count.incrementAndGet());

        // Create the Ministry
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ministryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMinistry() throws Exception {
        int databaseSizeBeforeUpdate = ministryRepository.findAll().size();
        ministry.setId(count.incrementAndGet());

        // Create the Ministry
        MinistryDTO ministryDTO = ministryMapper.toDto(ministry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMinistryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ministryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ministry in the database
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMinistry() throws Exception {
        // Initialize the database
        ministryRepository.saveAndFlush(ministry);

        int databaseSizeBeforeDelete = ministryRepository.findAll().size();

        // Delete the ministry
        restMinistryMockMvc
            .perform(delete(ENTITY_API_URL_ID, ministry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ministry> ministryList = ministryRepository.findAll();
        assertThat(ministryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
