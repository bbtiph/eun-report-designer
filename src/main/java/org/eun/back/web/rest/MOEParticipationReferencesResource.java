package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.MOEParticipationReferencesRepository;
import org.eun.back.service.MOEParticipationReferencesService;
import org.eun.back.service.dto.MOEParticipationReferencesDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.MOEParticipationReferences}.
 */
@RestController
@RequestMapping("/api")
public class MOEParticipationReferencesResource {

    private final Logger log = LoggerFactory.getLogger(MOEParticipationReferencesResource.class);

    private static final String ENTITY_NAME = "mOEParticipationReferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MOEParticipationReferencesService mOEParticipationReferencesService;

    private final MOEParticipationReferencesRepository mOEParticipationReferencesRepository;

    public MOEParticipationReferencesResource(
        MOEParticipationReferencesService mOEParticipationReferencesService,
        MOEParticipationReferencesRepository mOEParticipationReferencesRepository
    ) {
        this.mOEParticipationReferencesService = mOEParticipationReferencesService;
        this.mOEParticipationReferencesRepository = mOEParticipationReferencesRepository;
    }

    /**
     * {@code POST  /moe-participation-references} : Create a new mOEParticipationReferences.
     *
     * @param mOEParticipationReferencesDTO the mOEParticipationReferencesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mOEParticipationReferencesDTO, or with status {@code 400 (Bad Request)} if the mOEParticipationReferences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moe-participation-references")
    public ResponseEntity<MOEParticipationReferencesDTO> createMOEParticipationReferences(
        @RequestBody MOEParticipationReferencesDTO mOEParticipationReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MOEParticipationReferences : {}", mOEParticipationReferencesDTO);
        if (mOEParticipationReferencesDTO.getId() != null) {
            throw new BadRequestAlertException("A new mOEParticipationReferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MOEParticipationReferencesDTO result = mOEParticipationReferencesService.save(mOEParticipationReferencesDTO);
        return ResponseEntity
            .created(new URI("/api/moe-participation-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moe-participation-references/:id} : Updates an existing mOEParticipationReferences.
     *
     * @param id the id of the mOEParticipationReferencesDTO to save.
     * @param mOEParticipationReferencesDTO the mOEParticipationReferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mOEParticipationReferencesDTO,
     * or with status {@code 400 (Bad Request)} if the mOEParticipationReferencesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mOEParticipationReferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moe-participation-references/{id}")
    public ResponseEntity<MOEParticipationReferencesDTO> updateMOEParticipationReferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MOEParticipationReferencesDTO mOEParticipationReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MOEParticipationReferences : {}, {}", id, mOEParticipationReferencesDTO);
        if (mOEParticipationReferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mOEParticipationReferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mOEParticipationReferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MOEParticipationReferencesDTO result = mOEParticipationReferencesService.update(mOEParticipationReferencesDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mOEParticipationReferencesDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /moe-participation-references/:id} : Partial updates given fields of an existing mOEParticipationReferences, field will ignore if it is null
     *
     * @param id the id of the mOEParticipationReferencesDTO to save.
     * @param mOEParticipationReferencesDTO the mOEParticipationReferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mOEParticipationReferencesDTO,
     * or with status {@code 400 (Bad Request)} if the mOEParticipationReferencesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mOEParticipationReferencesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mOEParticipationReferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moe-participation-references/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MOEParticipationReferencesDTO> partialUpdateMOEParticipationReferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MOEParticipationReferencesDTO mOEParticipationReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MOEParticipationReferences partially : {}, {}", id, mOEParticipationReferencesDTO);
        if (mOEParticipationReferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mOEParticipationReferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mOEParticipationReferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MOEParticipationReferencesDTO> result = mOEParticipationReferencesService.partialUpdate(mOEParticipationReferencesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mOEParticipationReferencesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /moe-participation-references} : get all the mOEParticipationReferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mOEParticipationReferences in body.
     */
    @GetMapping("/moe-participation-references")
    public List<MOEParticipationReferencesDTO> getAllMOEParticipationReferences() {
        log.debug("REST request to get all MOEParticipationReferences");
        return mOEParticipationReferencesService.findAll();
    }

    /**
     * {@code GET  /moe-participation-references/:id} : get the "id" mOEParticipationReferences.
     *
     * @param id the id of the mOEParticipationReferencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mOEParticipationReferencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moe-participation-references/{id}")
    public ResponseEntity<MOEParticipationReferencesDTO> getMOEParticipationReferences(@PathVariable Long id) {
        log.debug("REST request to get MOEParticipationReferences : {}", id);
        Optional<MOEParticipationReferencesDTO> mOEParticipationReferencesDTO = mOEParticipationReferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mOEParticipationReferencesDTO);
    }

    /**
     * {@code DELETE  /moe-participation-references/:id} : delete the "id" mOEParticipationReferences.
     *
     * @param id the id of the mOEParticipationReferencesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moe-participation-references/{id}")
    public ResponseEntity<Void> deleteMOEParticipationReferences(@PathVariable Long id) {
        log.debug("REST request to delete MOEParticipationReferences : {}", id);
        mOEParticipationReferencesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
