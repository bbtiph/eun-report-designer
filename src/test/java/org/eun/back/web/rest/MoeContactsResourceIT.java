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
import org.eun.back.domain.MoeContacts;
import org.eun.back.repository.MoeContactsRepository;
import org.eun.back.service.dto.MoeContactsDTO;
import org.eun.back.service.mapper.MoeContactsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MoeContactsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoeContactsResourceIT {

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_MINISTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MINISTRY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MINISTRY_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MINISTRY_ENGLISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EUN_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EUN_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EUN_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EUN_LAST_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/moe-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MoeContactsRepository moeContactsRepository;

    @Autowired
    private MoeContactsMapper moeContactsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoeContactsMockMvc;

    private MoeContacts moeContacts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoeContacts createEntity(EntityManager em) {
        MoeContacts moeContacts = new MoeContacts()
            .countryCode(DEFAULT_COUNTRY_CODE)
            .countryName(DEFAULT_COUNTRY_NAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .ministryName(DEFAULT_MINISTRY_NAME)
            .ministryEnglishName(DEFAULT_MINISTRY_ENGLISH_NAME)
            .postalAddress(DEFAULT_POSTAL_ADDRESS)
            .invoicingAddress(DEFAULT_INVOICING_ADDRESS)
            .shippingAddress(DEFAULT_SHIPPING_ADDRESS)
            .contactEunFirstName(DEFAULT_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(DEFAULT_CONTACT_EUN_LAST_NAME);
        return moeContacts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MoeContacts createUpdatedEntity(EntityManager em) {
        MoeContacts moeContacts = new MoeContacts()
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .ministryName(UPDATED_MINISTRY_NAME)
            .ministryEnglishName(UPDATED_MINISTRY_ENGLISH_NAME)
            .postalAddress(UPDATED_POSTAL_ADDRESS)
            .invoicingAddress(UPDATED_INVOICING_ADDRESS)
            .shippingAddress(UPDATED_SHIPPING_ADDRESS)
            .contactEunFirstName(UPDATED_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(UPDATED_CONTACT_EUN_LAST_NAME);
        return moeContacts;
    }

    @BeforeEach
    public void initTest() {
        moeContacts = createEntity(em);
    }

    @Test
    @Transactional
    void createMoeContacts() throws Exception {
        int databaseSizeBeforeCreate = moeContactsRepository.findAll().size();
        // Create the MoeContacts
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);
        restMoeContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeCreate + 1);
        MoeContacts testMoeContacts = moeContactsList.get(moeContactsList.size() - 1);
        assertThat(testMoeContacts.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testMoeContacts.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testMoeContacts.getActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMoeContacts.getMinistryName()).isEqualTo(DEFAULT_MINISTRY_NAME);
        assertThat(testMoeContacts.getMinistryEnglishName()).isEqualTo(DEFAULT_MINISTRY_ENGLISH_NAME);
        assertThat(testMoeContacts.getPostalAddress()).isEqualTo(DEFAULT_POSTAL_ADDRESS);
        assertThat(testMoeContacts.getInvoicingAddress()).isEqualTo(DEFAULT_INVOICING_ADDRESS);
        assertThat(testMoeContacts.getShippingAddress()).isEqualTo(DEFAULT_SHIPPING_ADDRESS);
        assertThat(testMoeContacts.getContactEunFirstName()).isEqualTo(DEFAULT_CONTACT_EUN_FIRST_NAME);
        assertThat(testMoeContacts.getContactEunLastName()).isEqualTo(DEFAULT_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void createMoeContactsWithExistingId() throws Exception {
        // Create the MoeContacts with an existing ID
        moeContacts.setId(1L);
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);

        int databaseSizeBeforeCreate = moeContactsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoeContactsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMoeContacts() throws Exception {
        // Initialize the database
        moeContactsRepository.saveAndFlush(moeContacts);

        // Get all the moeContactsList
        restMoeContactsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moeContacts.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].ministryName").value(hasItem(DEFAULT_MINISTRY_NAME)))
            .andExpect(jsonPath("$.[*].ministryEnglishName").value(hasItem(DEFAULT_MINISTRY_ENGLISH_NAME)))
            .andExpect(jsonPath("$.[*].postalAddress").value(hasItem(DEFAULT_POSTAL_ADDRESS)))
            .andExpect(jsonPath("$.[*].invoicingAddress").value(hasItem(DEFAULT_INVOICING_ADDRESS)))
            .andExpect(jsonPath("$.[*].shippingAddress").value(hasItem(DEFAULT_SHIPPING_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactEunFirstName").value(hasItem(DEFAULT_CONTACT_EUN_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].contactEunLastName").value(hasItem(DEFAULT_CONTACT_EUN_LAST_NAME)));
    }

    @Test
    @Transactional
    void getMoeContacts() throws Exception {
        // Initialize the database
        moeContactsRepository.saveAndFlush(moeContacts);

        // Get the moeContacts
        restMoeContactsMockMvc
            .perform(get(ENTITY_API_URL_ID, moeContacts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moeContacts.getId().intValue()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.ministryName").value(DEFAULT_MINISTRY_NAME))
            .andExpect(jsonPath("$.ministryEnglishName").value(DEFAULT_MINISTRY_ENGLISH_NAME))
            .andExpect(jsonPath("$.postalAddress").value(DEFAULT_POSTAL_ADDRESS))
            .andExpect(jsonPath("$.invoicingAddress").value(DEFAULT_INVOICING_ADDRESS))
            .andExpect(jsonPath("$.shippingAddress").value(DEFAULT_SHIPPING_ADDRESS))
            .andExpect(jsonPath("$.contactEunFirstName").value(DEFAULT_CONTACT_EUN_FIRST_NAME))
            .andExpect(jsonPath("$.contactEunLastName").value(DEFAULT_CONTACT_EUN_LAST_NAME));
    }

    @Test
    @Transactional
    void getNonExistingMoeContacts() throws Exception {
        // Get the moeContacts
        restMoeContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMoeContacts() throws Exception {
        // Initialize the database
        moeContactsRepository.saveAndFlush(moeContacts);

        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();

        // Update the moeContacts
        MoeContacts updatedMoeContacts = moeContactsRepository.findById(moeContacts.getId()).get();
        // Disconnect from session so that the updates on updatedMoeContacts are not directly saved in db
        em.detach(updatedMoeContacts);
        updatedMoeContacts
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .ministryName(UPDATED_MINISTRY_NAME)
            .ministryEnglishName(UPDATED_MINISTRY_ENGLISH_NAME)
            .postalAddress(UPDATED_POSTAL_ADDRESS)
            .invoicingAddress(UPDATED_INVOICING_ADDRESS)
            .shippingAddress(UPDATED_SHIPPING_ADDRESS)
            .contactEunFirstName(UPDATED_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(UPDATED_CONTACT_EUN_LAST_NAME);
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(updatedMoeContacts);

        restMoeContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moeContactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isOk());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
        MoeContacts testMoeContacts = moeContactsList.get(moeContactsList.size() - 1);
        assertThat(testMoeContacts.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testMoeContacts.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testMoeContacts.getActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMoeContacts.getMinistryName()).isEqualTo(UPDATED_MINISTRY_NAME);
        assertThat(testMoeContacts.getMinistryEnglishName()).isEqualTo(UPDATED_MINISTRY_ENGLISH_NAME);
        assertThat(testMoeContacts.getPostalAddress()).isEqualTo(UPDATED_POSTAL_ADDRESS);
        assertThat(testMoeContacts.getInvoicingAddress()).isEqualTo(UPDATED_INVOICING_ADDRESS);
        assertThat(testMoeContacts.getShippingAddress()).isEqualTo(UPDATED_SHIPPING_ADDRESS);
        assertThat(testMoeContacts.getContactEunFirstName()).isEqualTo(UPDATED_CONTACT_EUN_FIRST_NAME);
        assertThat(testMoeContacts.getContactEunLastName()).isEqualTo(UPDATED_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void putNonExistingMoeContacts() throws Exception {
        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();
        moeContacts.setId(count.incrementAndGet());

        // Create the MoeContacts
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoeContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moeContactsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoeContacts() throws Exception {
        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();
        moeContacts.setId(count.incrementAndGet());

        // Create the MoeContacts
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoeContactsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoeContacts() throws Exception {
        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();
        moeContacts.setId(count.incrementAndGet());

        // Create the MoeContacts
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoeContactsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moeContactsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoeContactsWithPatch() throws Exception {
        // Initialize the database
        moeContactsRepository.saveAndFlush(moeContacts);

        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();

        // Update the moeContacts using partial update
        MoeContacts partialUpdatedMoeContacts = new MoeContacts();
        partialUpdatedMoeContacts.setId(moeContacts.getId());

        partialUpdatedMoeContacts
            .countryName(UPDATED_COUNTRY_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .ministryName(UPDATED_MINISTRY_NAME)
            .ministryEnglishName(UPDATED_MINISTRY_ENGLISH_NAME)
            .postalAddress(UPDATED_POSTAL_ADDRESS);

        restMoeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoeContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoeContacts))
            )
            .andExpect(status().isOk());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
        MoeContacts testMoeContacts = moeContactsList.get(moeContactsList.size() - 1);
        assertThat(testMoeContacts.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testMoeContacts.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testMoeContacts.getActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMoeContacts.getMinistryName()).isEqualTo(UPDATED_MINISTRY_NAME);
        assertThat(testMoeContacts.getMinistryEnglishName()).isEqualTo(UPDATED_MINISTRY_ENGLISH_NAME);
        assertThat(testMoeContacts.getPostalAddress()).isEqualTo(UPDATED_POSTAL_ADDRESS);
        assertThat(testMoeContacts.getInvoicingAddress()).isEqualTo(DEFAULT_INVOICING_ADDRESS);
        assertThat(testMoeContacts.getShippingAddress()).isEqualTo(DEFAULT_SHIPPING_ADDRESS);
        assertThat(testMoeContacts.getContactEunFirstName()).isEqualTo(DEFAULT_CONTACT_EUN_FIRST_NAME);
        assertThat(testMoeContacts.getContactEunLastName()).isEqualTo(DEFAULT_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void fullUpdateMoeContactsWithPatch() throws Exception {
        // Initialize the database
        moeContactsRepository.saveAndFlush(moeContacts);

        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();

        // Update the moeContacts using partial update
        MoeContacts partialUpdatedMoeContacts = new MoeContacts();
        partialUpdatedMoeContacts.setId(moeContacts.getId());

        partialUpdatedMoeContacts
            .countryCode(UPDATED_COUNTRY_CODE)
            .countryName(UPDATED_COUNTRY_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .ministryName(UPDATED_MINISTRY_NAME)
            .ministryEnglishName(UPDATED_MINISTRY_ENGLISH_NAME)
            .postalAddress(UPDATED_POSTAL_ADDRESS)
            .invoicingAddress(UPDATED_INVOICING_ADDRESS)
            .shippingAddress(UPDATED_SHIPPING_ADDRESS)
            .contactEunFirstName(UPDATED_CONTACT_EUN_FIRST_NAME)
            .contactEunLastName(UPDATED_CONTACT_EUN_LAST_NAME);

        restMoeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoeContacts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoeContacts))
            )
            .andExpect(status().isOk());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
        MoeContacts testMoeContacts = moeContactsList.get(moeContactsList.size() - 1);
        assertThat(testMoeContacts.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testMoeContacts.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testMoeContacts.getActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMoeContacts.getMinistryName()).isEqualTo(UPDATED_MINISTRY_NAME);
        assertThat(testMoeContacts.getMinistryEnglishName()).isEqualTo(UPDATED_MINISTRY_ENGLISH_NAME);
        assertThat(testMoeContacts.getPostalAddress()).isEqualTo(UPDATED_POSTAL_ADDRESS);
        assertThat(testMoeContacts.getInvoicingAddress()).isEqualTo(UPDATED_INVOICING_ADDRESS);
        assertThat(testMoeContacts.getShippingAddress()).isEqualTo(UPDATED_SHIPPING_ADDRESS);
        assertThat(testMoeContacts.getContactEunFirstName()).isEqualTo(UPDATED_CONTACT_EUN_FIRST_NAME);
        assertThat(testMoeContacts.getContactEunLastName()).isEqualTo(UPDATED_CONTACT_EUN_LAST_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingMoeContacts() throws Exception {
        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();
        moeContacts.setId(count.incrementAndGet());

        // Create the MoeContacts
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moeContactsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoeContacts() throws Exception {
        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();
        moeContacts.setId(count.incrementAndGet());

        // Create the MoeContacts
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoeContacts() throws Exception {
        int databaseSizeBeforeUpdate = moeContactsRepository.findAll().size();
        moeContacts.setId(count.incrementAndGet());

        // Create the MoeContacts
        MoeContactsDTO moeContactsDTO = moeContactsMapper.toDto(moeContacts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoeContactsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(moeContactsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MoeContacts in the database
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoeContacts() throws Exception {
        // Initialize the database
        moeContactsRepository.saveAndFlush(moeContacts);

        int databaseSizeBeforeDelete = moeContactsRepository.findAll().size();

        // Delete the moeContacts
        restMoeContactsMockMvc
            .perform(delete(ENTITY_API_URL_ID, moeContacts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MoeContacts> moeContactsList = moeContactsRepository.findAll();
        assertThat(moeContactsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
