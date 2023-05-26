package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.MoeContactsRepository;
import org.eun.back.service.MoeContactsService;
import org.eun.back.service.dto.MoeContactsDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.MoeContacts}.
 */
@RestController
@RequestMapping("/api")
public class MoeContactsResource {

    private final Logger log = LoggerFactory.getLogger(MoeContactsResource.class);

    private static final String ENTITY_NAME = "moeContacts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoeContactsService moeContactsService;

    private final MoeContactsRepository moeContactsRepository;

    public MoeContactsResource(MoeContactsService moeContactsService, MoeContactsRepository moeContactsRepository) {
        this.moeContactsService = moeContactsService;
        this.moeContactsRepository = moeContactsRepository;
    }

    /**
     * {@code POST  /moe-contacts} : Create a new moeContacts.
     *
     * @param moeContactsDTO the moeContactsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moeContactsDTO, or with status {@code 400 (Bad Request)} if the moeContacts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moe-contacts")
    public ResponseEntity<MoeContactsDTO> createMoeContacts(@RequestBody MoeContactsDTO moeContactsDTO) throws URISyntaxException {
        log.debug("REST request to save MoeContacts : {}", moeContactsDTO);
        if (moeContactsDTO.getId() != null) {
            throw new BadRequestAlertException("A new moeContacts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoeContactsDTO result = moeContactsService.save(moeContactsDTO);
        return ResponseEntity
            .created(new URI("/api/moe-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping(value = "/moe-contacts/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            moeContactsService.upload(file);
        } catch (Exception e) {
            // Обработка ошибок при сохранении данных
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

        return ResponseEntity.ok("Success");
    }

    /**
     * {@code PUT  /moe-contacts/:id} : Updates an existing moeContacts.
     *
     * @param id the id of the moeContactsDTO to save.
     * @param moeContactsDTO the moeContactsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moeContactsDTO,
     * or with status {@code 400 (Bad Request)} if the moeContactsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moeContactsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moe-contacts/{id}")
    public ResponseEntity<MoeContactsDTO> updateMoeContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoeContactsDTO moeContactsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MoeContacts : {}, {}", id, moeContactsDTO);
        if (moeContactsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moeContactsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moeContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MoeContactsDTO result = moeContactsService.update(moeContactsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moeContactsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /moe-contacts/:id} : Partial updates given fields of an existing moeContacts, field will ignore if it is null
     *
     * @param id the id of the moeContactsDTO to save.
     * @param moeContactsDTO the moeContactsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moeContactsDTO,
     * or with status {@code 400 (Bad Request)} if the moeContactsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moeContactsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moeContactsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moe-contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MoeContactsDTO> partialUpdateMoeContacts(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MoeContactsDTO moeContactsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MoeContacts partially : {}, {}", id, moeContactsDTO);
        if (moeContactsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moeContactsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moeContactsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MoeContactsDTO> result = moeContactsService.partialUpdate(moeContactsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, moeContactsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /moe-contacts} : get all the moeContacts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moeContacts in body.
     */
    @GetMapping("/moe-contacts")
    public List<MoeContactsDTO> getAllMoeContacts() {
        log.debug("REST request to get all MoeContacts");
        return moeContactsService.findAll();
    }

    /**
     * {@code GET  /moe-contacts/:id} : get the "id" moeContacts.
     *
     * @param id the id of the moeContactsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moeContactsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moe-contacts/{id}")
    public ResponseEntity<MoeContactsDTO> getMoeContacts(@PathVariable Long id) {
        log.debug("REST request to get MoeContacts : {}", id);
        Optional<MoeContactsDTO> moeContactsDTO = moeContactsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moeContactsDTO);
    }

    /**
     * {@code DELETE  /moe-contacts/:id} : delete the "id" moeContacts.
     *
     * @param id the id of the moeContactsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moe-contacts/{id}")
    public ResponseEntity<Void> deleteMoeContacts(@PathVariable Long id) {
        log.debug("REST request to delete MoeContacts : {}", id);
        moeContactsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
