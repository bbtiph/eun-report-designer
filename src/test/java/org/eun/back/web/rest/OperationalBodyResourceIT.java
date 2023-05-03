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
import org.eun.back.domain.OperationalBody;
import org.eun.back.repository.OperationalBodyRepository;
import org.eun.back.service.dto.OperationalBodyDTO;
import org.eun.back.service.mapper.OperationalBodyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OperationalBodyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperationalBodyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/operational-bodies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperationalBodyRepository operationalBodyRepository;

    @Autowired
    private OperationalBodyMapper operationalBodyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationalBodyMockMvc;

    private OperationalBody operationalBody;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationalBody createEntity(EntityManager em) {
        OperationalBody operationalBody = new OperationalBody()
            .name(DEFAULT_NAME)
            .acronym(DEFAULT_ACRONYM)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS);
        return operationalBody;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationalBody createUpdatedEntity(EntityManager em) {
        OperationalBody operationalBody = new OperationalBody()
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);
        return operationalBody;
    }

    @BeforeEach
    public void initTest() {
        operationalBody = createEntity(em);
    }

    @Test
    @Transactional
    void createOperationalBody() throws Exception {
        int databaseSizeBeforeCreate = operationalBodyRepository.findAll().size();
        // Create the OperationalBody
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);
        restOperationalBodyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeCreate + 1);
        OperationalBody testOperationalBody = operationalBodyList.get(operationalBodyList.size() - 1);
        assertThat(testOperationalBody.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOperationalBody.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testOperationalBody.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOperationalBody.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOperationalBody.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createOperationalBodyWithExistingId() throws Exception {
        // Create the OperationalBody with an existing ID
        operationalBody.setId(1L);
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        int databaseSizeBeforeCreate = operationalBodyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationalBodyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationalBodyRepository.findAll().size();
        // set the field null
        operationalBody.setName(null);

        // Create the OperationalBody, which fails.
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        restOperationalBodyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isBadRequest());

        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOperationalBodies() throws Exception {
        // Initialize the database
        operationalBodyRepository.saveAndFlush(operationalBody);

        // Get all the operationalBodyList
        restOperationalBodyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationalBody.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getOperationalBody() throws Exception {
        // Initialize the database
        operationalBodyRepository.saveAndFlush(operationalBody);

        // Get the operationalBody
        restOperationalBodyMockMvc
            .perform(get(ENTITY_API_URL_ID, operationalBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operationalBody.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingOperationalBody() throws Exception {
        // Get the operationalBody
        restOperationalBodyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOperationalBody() throws Exception {
        // Initialize the database
        operationalBodyRepository.saveAndFlush(operationalBody);

        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();

        // Update the operationalBody
        OperationalBody updatedOperationalBody = operationalBodyRepository.findById(operationalBody.getId()).get();
        // Disconnect from session so that the updates on updatedOperationalBody are not directly saved in db
        em.detach(updatedOperationalBody);
        updatedOperationalBody
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(updatedOperationalBody);

        restOperationalBodyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationalBodyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isOk());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
        OperationalBody testOperationalBody = operationalBodyList.get(operationalBodyList.size() - 1);
        assertThat(testOperationalBody.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOperationalBody.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testOperationalBody.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOperationalBody.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOperationalBody.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOperationalBody() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();
        operationalBody.setId(count.incrementAndGet());

        // Create the OperationalBody
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationalBodyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationalBodyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperationalBody() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();
        operationalBody.setId(count.incrementAndGet());

        // Create the OperationalBody
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperationalBody() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();
        operationalBody.setId(count.incrementAndGet());

        // Create the OperationalBody
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperationalBodyWithPatch() throws Exception {
        // Initialize the database
        operationalBodyRepository.saveAndFlush(operationalBody);

        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();

        // Update the operationalBody using partial update
        OperationalBody partialUpdatedOperationalBody = new OperationalBody();
        partialUpdatedOperationalBody.setId(operationalBody.getId());

        partialUpdatedOperationalBody.type(UPDATED_TYPE).status(UPDATED_STATUS);

        restOperationalBodyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationalBody.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationalBody))
            )
            .andExpect(status().isOk());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
        OperationalBody testOperationalBody = operationalBodyList.get(operationalBodyList.size() - 1);
        assertThat(testOperationalBody.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOperationalBody.getAcronym()).isEqualTo(DEFAULT_ACRONYM);
        assertThat(testOperationalBody.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOperationalBody.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOperationalBody.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOperationalBodyWithPatch() throws Exception {
        // Initialize the database
        operationalBodyRepository.saveAndFlush(operationalBody);

        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();

        // Update the operationalBody using partial update
        OperationalBody partialUpdatedOperationalBody = new OperationalBody();
        partialUpdatedOperationalBody.setId(operationalBody.getId());

        partialUpdatedOperationalBody
            .name(UPDATED_NAME)
            .acronym(UPDATED_ACRONYM)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);

        restOperationalBodyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationalBody.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationalBody))
            )
            .andExpect(status().isOk());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
        OperationalBody testOperationalBody = operationalBodyList.get(operationalBodyList.size() - 1);
        assertThat(testOperationalBody.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOperationalBody.getAcronym()).isEqualTo(UPDATED_ACRONYM);
        assertThat(testOperationalBody.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOperationalBody.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOperationalBody.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOperationalBody() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();
        operationalBody.setId(count.incrementAndGet());

        // Create the OperationalBody
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationalBodyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operationalBodyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperationalBody() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();
        operationalBody.setId(count.incrementAndGet());

        // Create the OperationalBody
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperationalBody() throws Exception {
        int databaseSizeBeforeUpdate = operationalBodyRepository.findAll().size();
        operationalBody.setId(count.incrementAndGet());

        // Create the OperationalBody
        OperationalBodyDTO operationalBodyDTO = operationalBodyMapper.toDto(operationalBody);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationalBodyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationalBodyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationalBody in the database
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperationalBody() throws Exception {
        // Initialize the database
        operationalBodyRepository.saveAndFlush(operationalBody);

        int databaseSizeBeforeDelete = operationalBodyRepository.findAll().size();

        // Delete the operationalBody
        restOperationalBodyMockMvc
            .perform(delete(ENTITY_API_URL_ID, operationalBody.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationalBody> operationalBodyList = operationalBodyRepository.findAll();
        assertThat(operationalBodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
