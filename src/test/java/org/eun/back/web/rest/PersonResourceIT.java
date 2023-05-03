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
import org.eun.back.domain.Person;
import org.eun.back.domain.enumeration.GdprStatus;
import org.eun.back.repository.PersonRepository;
import org.eun.back.service.dto.PersonDTO;
import org.eun.back.service.mapper.PersonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final Long DEFAULT_EUN_DB_ID = 1L;
    private static final Long UPDATED_EUN_DB_ID = 2L;

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SALUTATION = 1;
    private static final Integer UPDATED_SALUTATION = 2;

    private static final String DEFAULT_MAIN_CONTRACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_CONTRACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_CONTRACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_CONTRACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_MOTHER_TONGUE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_MOTHER_TONGUE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_OTHER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIAL_NETWORK_CONTACTS = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_NETWORK_CONTACTS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final GdprStatus DEFAULT_GDPR_STATUS = GdprStatus.IDENTIFIABLE;
    private static final GdprStatus UPDATED_GDPR_STATUS = GdprStatus.USER_REQUESTED_ANON;

    private static final LocalDate DEFAULT_LAST_LOGIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_LOGIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .eunDbId(DEFAULT_EUN_DB_ID)
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .salutation(DEFAULT_SALUTATION)
            .mainContractEmail(DEFAULT_MAIN_CONTRACT_EMAIL)
            .extraContractEmail(DEFAULT_EXTRA_CONTRACT_EMAIL)
            .languageMotherTongue(DEFAULT_LANGUAGE_MOTHER_TONGUE)
            .languageOther(DEFAULT_LANGUAGE_OTHER)
            .description(DEFAULT_DESCRIPTION)
            .website(DEFAULT_WEBSITE)
            .mobile(DEFAULT_MOBILE)
            .phone(DEFAULT_PHONE)
            .socialNetworkContacts(DEFAULT_SOCIAL_NETWORK_CONTACTS)
            .status(DEFAULT_STATUS)
            .gdprStatus(DEFAULT_GDPR_STATUS)
            .lastLoginDate(DEFAULT_LAST_LOGIN_DATE);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .eunDbId(UPDATED_EUN_DB_ID)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .salutation(UPDATED_SALUTATION)
            .mainContractEmail(UPDATED_MAIN_CONTRACT_EMAIL)
            .extraContractEmail(UPDATED_EXTRA_CONTRACT_EMAIL)
            .languageMotherTongue(UPDATED_LANGUAGE_MOTHER_TONGUE)
            .languageOther(UPDATED_LANGUAGE_OTHER)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .mobile(UPDATED_MOBILE)
            .phone(UPDATED_PHONE)
            .socialNetworkContacts(UPDATED_SOCIAL_NETWORK_CONTACTS)
            .status(UPDATED_STATUS)
            .gdprStatus(UPDATED_GDPR_STATUS)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();
        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getEunDbId()).isEqualTo(DEFAULT_EUN_DB_ID);
        assertThat(testPerson.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPerson.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testPerson.getMainContractEmail()).isEqualTo(DEFAULT_MAIN_CONTRACT_EMAIL);
        assertThat(testPerson.getExtraContractEmail()).isEqualTo(DEFAULT_EXTRA_CONTRACT_EMAIL);
        assertThat(testPerson.getLanguageMotherTongue()).isEqualTo(DEFAULT_LANGUAGE_MOTHER_TONGUE);
        assertThat(testPerson.getLanguageOther()).isEqualTo(DEFAULT_LANGUAGE_OTHER);
        assertThat(testPerson.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPerson.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testPerson.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPerson.getSocialNetworkContacts()).isEqualTo(DEFAULT_SOCIAL_NETWORK_CONTACTS);
        assertThat(testPerson.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerson.getGdprStatus()).isEqualTo(DEFAULT_GDPR_STATUS);
        assertThat(testPerson.getLastLoginDate()).isEqualTo(DEFAULT_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].eunDbId").value(hasItem(DEFAULT_EUN_DB_ID.intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].mainContractEmail").value(hasItem(DEFAULT_MAIN_CONTRACT_EMAIL)))
            .andExpect(jsonPath("$.[*].extraContractEmail").value(hasItem(DEFAULT_EXTRA_CONTRACT_EMAIL)))
            .andExpect(jsonPath("$.[*].languageMotherTongue").value(hasItem(DEFAULT_LANGUAGE_MOTHER_TONGUE)))
            .andExpect(jsonPath("$.[*].languageOther").value(hasItem(DEFAULT_LANGUAGE_OTHER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].socialNetworkContacts").value(hasItem(DEFAULT_SOCIAL_NETWORK_CONTACTS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].gdprStatus").value(hasItem(DEFAULT_GDPR_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastLoginDate").value(hasItem(DEFAULT_LAST_LOGIN_DATE.toString())));
    }

    @Test
    @Transactional
    void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.eunDbId").value(DEFAULT_EUN_DB_ID.intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION))
            .andExpect(jsonPath("$.mainContractEmail").value(DEFAULT_MAIN_CONTRACT_EMAIL))
            .andExpect(jsonPath("$.extraContractEmail").value(DEFAULT_EXTRA_CONTRACT_EMAIL))
            .andExpect(jsonPath("$.languageMotherTongue").value(DEFAULT_LANGUAGE_MOTHER_TONGUE))
            .andExpect(jsonPath("$.languageOther").value(DEFAULT_LANGUAGE_OTHER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.socialNetworkContacts").value(DEFAULT_SOCIAL_NETWORK_CONTACTS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.gdprStatus").value(DEFAULT_GDPR_STATUS.toString()))
            .andExpect(jsonPath("$.lastLoginDate").value(DEFAULT_LAST_LOGIN_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .eunDbId(UPDATED_EUN_DB_ID)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .salutation(UPDATED_SALUTATION)
            .mainContractEmail(UPDATED_MAIN_CONTRACT_EMAIL)
            .extraContractEmail(UPDATED_EXTRA_CONTRACT_EMAIL)
            .languageMotherTongue(UPDATED_LANGUAGE_MOTHER_TONGUE)
            .languageOther(UPDATED_LANGUAGE_OTHER)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .mobile(UPDATED_MOBILE)
            .phone(UPDATED_PHONE)
            .socialNetworkContacts(UPDATED_SOCIAL_NETWORK_CONTACTS)
            .status(UPDATED_STATUS)
            .gdprStatus(UPDATED_GDPR_STATUS)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getEunDbId()).isEqualTo(UPDATED_EUN_DB_ID);
        assertThat(testPerson.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPerson.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testPerson.getMainContractEmail()).isEqualTo(UPDATED_MAIN_CONTRACT_EMAIL);
        assertThat(testPerson.getExtraContractEmail()).isEqualTo(UPDATED_EXTRA_CONTRACT_EMAIL);
        assertThat(testPerson.getLanguageMotherTongue()).isEqualTo(UPDATED_LANGUAGE_MOTHER_TONGUE);
        assertThat(testPerson.getLanguageOther()).isEqualTo(UPDATED_LANGUAGE_OTHER);
        assertThat(testPerson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPerson.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testPerson.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPerson.getSocialNetworkContacts()).isEqualTo(UPDATED_SOCIAL_NETWORK_CONTACTS);
        assertThat(testPerson.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerson.getGdprStatus()).isEqualTo(UPDATED_GDPR_STATUS);
        assertThat(testPerson.getLastLoginDate()).isEqualTo(UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .lastname(UPDATED_LASTNAME)
            .languageOther(UPDATED_LANGUAGE_OTHER)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .mobile(UPDATED_MOBILE)
            .phone(UPDATED_PHONE)
            .socialNetworkContacts(UPDATED_SOCIAL_NETWORK_CONTACTS);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getEunDbId()).isEqualTo(DEFAULT_EUN_DB_ID);
        assertThat(testPerson.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPerson.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testPerson.getMainContractEmail()).isEqualTo(DEFAULT_MAIN_CONTRACT_EMAIL);
        assertThat(testPerson.getExtraContractEmail()).isEqualTo(DEFAULT_EXTRA_CONTRACT_EMAIL);
        assertThat(testPerson.getLanguageMotherTongue()).isEqualTo(DEFAULT_LANGUAGE_MOTHER_TONGUE);
        assertThat(testPerson.getLanguageOther()).isEqualTo(UPDATED_LANGUAGE_OTHER);
        assertThat(testPerson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPerson.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testPerson.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPerson.getSocialNetworkContacts()).isEqualTo(UPDATED_SOCIAL_NETWORK_CONTACTS);
        assertThat(testPerson.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPerson.getGdprStatus()).isEqualTo(DEFAULT_GDPR_STATUS);
        assertThat(testPerson.getLastLoginDate()).isEqualTo(DEFAULT_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .eunDbId(UPDATED_EUN_DB_ID)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .salutation(UPDATED_SALUTATION)
            .mainContractEmail(UPDATED_MAIN_CONTRACT_EMAIL)
            .extraContractEmail(UPDATED_EXTRA_CONTRACT_EMAIL)
            .languageMotherTongue(UPDATED_LANGUAGE_MOTHER_TONGUE)
            .languageOther(UPDATED_LANGUAGE_OTHER)
            .description(UPDATED_DESCRIPTION)
            .website(UPDATED_WEBSITE)
            .mobile(UPDATED_MOBILE)
            .phone(UPDATED_PHONE)
            .socialNetworkContacts(UPDATED_SOCIAL_NETWORK_CONTACTS)
            .status(UPDATED_STATUS)
            .gdprStatus(UPDATED_GDPR_STATUS)
            .lastLoginDate(UPDATED_LAST_LOGIN_DATE);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getEunDbId()).isEqualTo(UPDATED_EUN_DB_ID);
        assertThat(testPerson.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPerson.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testPerson.getMainContractEmail()).isEqualTo(UPDATED_MAIN_CONTRACT_EMAIL);
        assertThat(testPerson.getExtraContractEmail()).isEqualTo(UPDATED_EXTRA_CONTRACT_EMAIL);
        assertThat(testPerson.getLanguageMotherTongue()).isEqualTo(UPDATED_LANGUAGE_MOTHER_TONGUE);
        assertThat(testPerson.getLanguageOther()).isEqualTo(UPDATED_LANGUAGE_OTHER);
        assertThat(testPerson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPerson.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testPerson.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPerson.getSocialNetworkContacts()).isEqualTo(UPDATED_SOCIAL_NETWORK_CONTACTS);
        assertThat(testPerson.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPerson.getGdprStatus()).isEqualTo(UPDATED_GDPR_STATUS);
        assertThat(testPerson.getLastLoginDate()).isEqualTo(UPDATED_LAST_LOGIN_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
