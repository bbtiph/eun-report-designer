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
import org.eun.back.domain.Funding;
import org.eun.back.repository.FundingRepository;
import org.eun.back.service.dto.FundingDTO;
import org.eun.back.service.mapper.FundingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FundingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FundingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fundings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FundingRepository fundingRepository;

    @Autowired
    private FundingMapper fundingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFundingMockMvc;

    private Funding funding;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funding createEntity(EntityManager em) {
        Funding funding = new Funding().name(DEFAULT_NAME).type(DEFAULT_TYPE).parentId(DEFAULT_PARENT_ID).description(DEFAULT_DESCRIPTION);
        return funding;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funding createUpdatedEntity(EntityManager em) {
        Funding funding = new Funding().name(UPDATED_NAME).type(UPDATED_TYPE).parentId(UPDATED_PARENT_ID).description(UPDATED_DESCRIPTION);
        return funding;
    }

    @BeforeEach
    public void initTest() {
        funding = createEntity(em);
    }

    @Test
    @Transactional
    void createFunding() throws Exception {
        int databaseSizeBeforeCreate = fundingRepository.findAll().size();
        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.toDto(funding);
        restFundingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundingDTO)))
            .andExpect(status().isCreated());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeCreate + 1);
        Funding testFunding = fundingList.get(fundingList.size() - 1);
        assertThat(testFunding.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFunding.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFunding.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testFunding.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFundingWithExistingId() throws Exception {
        // Create the Funding with an existing ID
        funding.setId(1L);
        FundingDTO fundingDTO = fundingMapper.toDto(funding);

        int databaseSizeBeforeCreate = fundingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFundingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFundings() throws Exception {
        // Initialize the database
        fundingRepository.saveAndFlush(funding);

        // Get all the fundingList
        restFundingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funding.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getFunding() throws Exception {
        // Initialize the database
        fundingRepository.saveAndFlush(funding);

        // Get the funding
        restFundingMockMvc
            .perform(get(ENTITY_API_URL_ID, funding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funding.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFunding() throws Exception {
        // Get the funding
        restFundingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFunding() throws Exception {
        // Initialize the database
        fundingRepository.saveAndFlush(funding);

        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();

        // Update the funding
        Funding updatedFunding = fundingRepository.findById(funding.getId()).get();
        // Disconnect from session so that the updates on updatedFunding are not directly saved in db
        em.detach(updatedFunding);
        updatedFunding.name(UPDATED_NAME).type(UPDATED_TYPE).parentId(UPDATED_PARENT_ID).description(UPDATED_DESCRIPTION);
        FundingDTO fundingDTO = fundingMapper.toDto(updatedFunding);

        restFundingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fundingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
        Funding testFunding = fundingList.get(fundingList.size() - 1);
        assertThat(testFunding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFunding.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFunding.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testFunding.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFunding() throws Exception {
        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();
        funding.setId(count.incrementAndGet());

        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.toDto(funding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fundingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFunding() throws Exception {
        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();
        funding.setId(count.incrementAndGet());

        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.toDto(funding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fundingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFunding() throws Exception {
        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();
        funding.setId(count.incrementAndGet());

        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.toDto(funding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fundingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFundingWithPatch() throws Exception {
        // Initialize the database
        fundingRepository.saveAndFlush(funding);

        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();

        // Update the funding using partial update
        Funding partialUpdatedFunding = new Funding();
        partialUpdatedFunding.setId(funding.getId());

        partialUpdatedFunding.type(UPDATED_TYPE).description(UPDATED_DESCRIPTION);

        restFundingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunding.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunding))
            )
            .andExpect(status().isOk());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
        Funding testFunding = fundingList.get(fundingList.size() - 1);
        assertThat(testFunding.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFunding.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFunding.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testFunding.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFundingWithPatch() throws Exception {
        // Initialize the database
        fundingRepository.saveAndFlush(funding);

        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();

        // Update the funding using partial update
        Funding partialUpdatedFunding = new Funding();
        partialUpdatedFunding.setId(funding.getId());

        partialUpdatedFunding.name(UPDATED_NAME).type(UPDATED_TYPE).parentId(UPDATED_PARENT_ID).description(UPDATED_DESCRIPTION);

        restFundingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFunding.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFunding))
            )
            .andExpect(status().isOk());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
        Funding testFunding = fundingList.get(fundingList.size() - 1);
        assertThat(testFunding.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFunding.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFunding.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testFunding.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFunding() throws Exception {
        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();
        funding.setId(count.incrementAndGet());

        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.toDto(funding);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFundingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fundingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFunding() throws Exception {
        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();
        funding.setId(count.incrementAndGet());

        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.toDto(funding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fundingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFunding() throws Exception {
        int databaseSizeBeforeUpdate = fundingRepository.findAll().size();
        funding.setId(count.incrementAndGet());

        // Create the Funding
        FundingDTO fundingDTO = fundingMapper.toDto(funding);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFundingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fundingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funding in the database
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFunding() throws Exception {
        // Initialize the database
        fundingRepository.saveAndFlush(funding);

        int databaseSizeBeforeDelete = fundingRepository.findAll().size();

        // Delete the funding
        restFundingMockMvc
            .perform(delete(ENTITY_API_URL_ID, funding.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funding> fundingList = fundingRepository.findAll();
        assertThat(fundingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
