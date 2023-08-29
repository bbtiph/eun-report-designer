package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ParticipantsEunIndicatorRepository;
import org.eun.back.service.ParticipantsEunIndicatorService;
import org.eun.back.service.dto.ParticipantsEunIndicatorDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.ParticipantsEunIndicator}.
 */
@RestController
@RequestMapping("/api")
public class ParticipantsEunIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantsEunIndicatorResource.class);

    private static final String ENTITY_NAME = "participantsEunIndicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipantsEunIndicatorService participantsEunIndicatorService;

    private final ParticipantsEunIndicatorRepository participantsEunIndicatorRepository;

    public ParticipantsEunIndicatorResource(
        ParticipantsEunIndicatorService participantsEunIndicatorService,
        ParticipantsEunIndicatorRepository participantsEunIndicatorRepository
    ) {
        this.participantsEunIndicatorService = participantsEunIndicatorService;
        this.participantsEunIndicatorRepository = participantsEunIndicatorRepository;
    }

    /**
     * {@code POST  /participants-eun-indicators} : Create a new participantsEunIndicator.
     *
     * @param participantsEunIndicatorDTO the participantsEunIndicatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participantsEunIndicatorDTO, or with status {@code 400 (Bad Request)} if the participantsEunIndicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participants-eun-indicators")
    public ResponseEntity<ParticipantsEunIndicatorDTO> createParticipantsEunIndicator(
        @RequestBody ParticipantsEunIndicatorDTO participantsEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ParticipantsEunIndicator : {}", participantsEunIndicatorDTO);
        if (participantsEunIndicatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new participantsEunIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParticipantsEunIndicatorDTO result = participantsEunIndicatorService.save(participantsEunIndicatorDTO);
        return ResponseEntity
            .created(new URI("/api/participants-eun-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /participants-eun-indicators/:id} : Updates an existing participantsEunIndicator.
     *
     * @param id the id of the participantsEunIndicatorDTO to save.
     * @param participantsEunIndicatorDTO the participantsEunIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participantsEunIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the participantsEunIndicatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participantsEunIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participants-eun-indicators/{id}")
    public ResponseEntity<ParticipantsEunIndicatorDTO> updateParticipantsEunIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParticipantsEunIndicatorDTO participantsEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParticipantsEunIndicator : {}, {}", id, participantsEunIndicatorDTO);
        if (participantsEunIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participantsEunIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantsEunIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParticipantsEunIndicatorDTO result = participantsEunIndicatorService.update(participantsEunIndicatorDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, participantsEunIndicatorDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /participants-eun-indicators/:id} : Partial updates given fields of an existing participantsEunIndicator, field will ignore if it is null
     *
     * @param id the id of the participantsEunIndicatorDTO to save.
     * @param participantsEunIndicatorDTO the participantsEunIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participantsEunIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the participantsEunIndicatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the participantsEunIndicatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the participantsEunIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/participants-eun-indicators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParticipantsEunIndicatorDTO> partialUpdateParticipantsEunIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ParticipantsEunIndicatorDTO participantsEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParticipantsEunIndicator partially : {}, {}", id, participantsEunIndicatorDTO);
        if (participantsEunIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participantsEunIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantsEunIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParticipantsEunIndicatorDTO> result = participantsEunIndicatorService.partialUpdate(participantsEunIndicatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, participantsEunIndicatorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /participants-eun-indicators} : get all the participantsEunIndicators.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participantsEunIndicators in body.
     */
    @GetMapping("/participants-eun-indicators")
    public List<ParticipantsEunIndicatorDTO> getAllParticipantsEunIndicators() {
        log.debug("REST request to get all ParticipantsEunIndicators");
        return participantsEunIndicatorService.findAll();
    }

    /**
     * {@code GET  /participants-eun-indicators/:id} : get the "id" participantsEunIndicator.
     *
     * @param id the id of the participantsEunIndicatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participantsEunIndicatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participants-eun-indicators/{id}")
    public ResponseEntity<ParticipantsEunIndicatorDTO> getParticipantsEunIndicator(@PathVariable Long id) {
        log.debug("REST request to get ParticipantsEunIndicator : {}", id);
        Optional<ParticipantsEunIndicatorDTO> participantsEunIndicatorDTO = participantsEunIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participantsEunIndicatorDTO);
    }

    /**
     * {@code DELETE  /participants-eun-indicators/:id} : delete the "id" participantsEunIndicator.
     *
     * @param id the id of the participantsEunIndicatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participants-eun-indicators/{id}")
    public ResponseEntity<Void> deleteParticipantsEunIndicator(@PathVariable Long id) {
        log.debug("REST request to delete ParticipantsEunIndicator : {}", id);
        participantsEunIndicatorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
