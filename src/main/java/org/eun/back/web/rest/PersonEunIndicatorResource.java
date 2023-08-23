package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.PersonEunIndicatorRepository;
import org.eun.back.service.PersonEunIndicatorService;
import org.eun.back.service.dto.PersonEunIndicatorDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.PersonEunIndicator}.
 */
@RestController
@RequestMapping("/api")
public class PersonEunIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(PersonEunIndicatorResource.class);

    private static final String ENTITY_NAME = "personEunIndicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonEunIndicatorService personEunIndicatorService;

    private final PersonEunIndicatorRepository personEunIndicatorRepository;

    public PersonEunIndicatorResource(
        PersonEunIndicatorService personEunIndicatorService,
        PersonEunIndicatorRepository personEunIndicatorRepository
    ) {
        this.personEunIndicatorService = personEunIndicatorService;
        this.personEunIndicatorRepository = personEunIndicatorRepository;
    }

    /**
     * {@code POST  /person-eun-indicators} : Create a new personEunIndicator.
     *
     * @param personEunIndicatorDTO the personEunIndicatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personEunIndicatorDTO, or with status {@code 400 (Bad Request)} if the personEunIndicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-eun-indicators")
    public ResponseEntity<PersonEunIndicatorDTO> createPersonEunIndicator(@RequestBody PersonEunIndicatorDTO personEunIndicatorDTO)
        throws URISyntaxException {
        log.debug("REST request to save PersonEunIndicator : {}", personEunIndicatorDTO);
        if (personEunIndicatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new personEunIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonEunIndicatorDTO result = personEunIndicatorService.save(personEunIndicatorDTO);
        return ResponseEntity
            .created(new URI("/api/person-eun-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-eun-indicators/:id} : Updates an existing personEunIndicator.
     *
     * @param id the id of the personEunIndicatorDTO to save.
     * @param personEunIndicatorDTO the personEunIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personEunIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the personEunIndicatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personEunIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-eun-indicators/{id}")
    public ResponseEntity<PersonEunIndicatorDTO> updatePersonEunIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonEunIndicatorDTO personEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonEunIndicator : {}, {}", id, personEunIndicatorDTO);
        if (personEunIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personEunIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personEunIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonEunIndicatorDTO result = personEunIndicatorService.update(personEunIndicatorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personEunIndicatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-eun-indicators/:id} : Partial updates given fields of an existing personEunIndicator, field will ignore if it is null
     *
     * @param id the id of the personEunIndicatorDTO to save.
     * @param personEunIndicatorDTO the personEunIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personEunIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the personEunIndicatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personEunIndicatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personEunIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-eun-indicators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonEunIndicatorDTO> partialUpdatePersonEunIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonEunIndicatorDTO personEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonEunIndicator partially : {}, {}", id, personEunIndicatorDTO);
        if (personEunIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personEunIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personEunIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonEunIndicatorDTO> result = personEunIndicatorService.partialUpdate(personEunIndicatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personEunIndicatorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /person-eun-indicators} : get all the personEunIndicators.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personEunIndicators in body.
     */
    @GetMapping("/person-eun-indicators")
    public List<PersonEunIndicatorDTO> getAllPersonEunIndicators() {
        log.debug("REST request to get all PersonEunIndicators");
        return personEunIndicatorService.findAll();
    }

    /**
     * {@code GET  /person-eun-indicators/:id} : get the "id" personEunIndicator.
     *
     * @param id the id of the personEunIndicatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personEunIndicatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-eun-indicators/{id}")
    public ResponseEntity<PersonEunIndicatorDTO> getPersonEunIndicator(@PathVariable Long id) {
        log.debug("REST request to get PersonEunIndicator : {}", id);
        Optional<PersonEunIndicatorDTO> personEunIndicatorDTO = personEunIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personEunIndicatorDTO);
    }

    /**
     * {@code DELETE  /person-eun-indicators/:id} : delete the "id" personEunIndicator.
     *
     * @param id the id of the personEunIndicatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-eun-indicators/{id}")
    public ResponseEntity<Void> deletePersonEunIndicator(@PathVariable Long id) {
        log.debug("REST request to delete PersonEunIndicator : {}", id);
        personEunIndicatorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
