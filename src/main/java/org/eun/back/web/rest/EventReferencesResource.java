package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.EventReferencesRepository;
import org.eun.back.service.EventReferencesService;
import org.eun.back.service.dto.EventReferencesDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.EventReferences}.
 */
@RestController
@RequestMapping("/api")
public class EventReferencesResource {

    private final Logger log = LoggerFactory.getLogger(EventReferencesResource.class);

    private static final String ENTITY_NAME = "eventReferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventReferencesService eventReferencesService;

    private final EventReferencesRepository eventReferencesRepository;

    public EventReferencesResource(EventReferencesService eventReferencesService, EventReferencesRepository eventReferencesRepository) {
        this.eventReferencesService = eventReferencesService;
        this.eventReferencesRepository = eventReferencesRepository;
    }

    /**
     * {@code POST  /event-references} : Create a new eventReferences.
     *
     * @param eventReferencesDTO the eventReferencesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventReferencesDTO, or with status {@code 400 (Bad Request)} if the eventReferences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-references")
    public ResponseEntity<EventReferencesDTO> createEventReferences(@RequestBody EventReferencesDTO eventReferencesDTO)
        throws URISyntaxException {
        log.debug("REST request to save EventReferences : {}", eventReferencesDTO);
        if (eventReferencesDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventReferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventReferencesDTO result = eventReferencesService.save(eventReferencesDTO);
        return ResponseEntity
            .created(new URI("/api/event-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-references/:id} : Updates an existing eventReferences.
     *
     * @param id the id of the eventReferencesDTO to save.
     * @param eventReferencesDTO the eventReferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReferencesDTO,
     * or with status {@code 400 (Bad Request)} if the eventReferencesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventReferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-references/{id}")
    public ResponseEntity<EventReferencesDTO> updateEventReferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReferencesDTO eventReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventReferences : {}, {}", id, eventReferencesDTO);
        if (eventReferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventReferencesDTO result = eventReferencesService.update(eventReferencesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReferencesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-references/:id} : Partial updates given fields of an existing eventReferences, field will ignore if it is null
     *
     * @param id the id of the eventReferencesDTO to save.
     * @param eventReferencesDTO the eventReferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReferencesDTO,
     * or with status {@code 400 (Bad Request)} if the eventReferencesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventReferencesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventReferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-references/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventReferencesDTO> partialUpdateEventReferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReferencesDTO eventReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventReferences partially : {}, {}", id, eventReferencesDTO);
        if (eventReferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventReferencesDTO> result = eventReferencesService.partialUpdate(eventReferencesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventReferencesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-references} : get all the eventReferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventReferences in body.
     */
    @GetMapping("/event-references")
    public List<EventReferencesDTO> getAllEventReferences() {
        log.debug("REST request to get all EventReferences");
        return eventReferencesService.findAll();
    }

    /**
     * {@code GET  /event-references/:id} : get the "id" eventReferences.
     *
     * @param id the id of the eventReferencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventReferencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-references/{id}")
    public ResponseEntity<EventReferencesDTO> getEventReferences(@PathVariable Long id) {
        log.debug("REST request to get EventReferences : {}", id);
        Optional<EventReferencesDTO> eventReferencesDTO = eventReferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventReferencesDTO);
    }

    /**
     * {@code DELETE  /event-references/:id} : delete the "id" eventReferences.
     *
     * @param id the id of the eventReferencesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-references/{id}")
    public ResponseEntity<Void> deleteEventReferences(@PathVariable Long id) {
        log.debug("REST request to delete EventReferences : {}", id);
        eventReferencesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
