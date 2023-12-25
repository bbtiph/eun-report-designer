package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.EventReferencesParticipantsCategoryRepository;
import org.eun.back.service.EventReferencesParticipantsCategoryService;
import org.eun.back.service.dto.EventReferencesParticipantsCategoryDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.EventReferencesParticipantsCategory}.
 */
@RestController
@RequestMapping("/api")
public class EventReferencesParticipantsCategoryResource {

    private final Logger log = LoggerFactory.getLogger(EventReferencesParticipantsCategoryResource.class);

    private static final String ENTITY_NAME = "eventReferencesParticipantsCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventReferencesParticipantsCategoryService eventReferencesParticipantsCategoryService;

    private final EventReferencesParticipantsCategoryRepository eventReferencesParticipantsCategoryRepository;

    public EventReferencesParticipantsCategoryResource(
        EventReferencesParticipantsCategoryService eventReferencesParticipantsCategoryService,
        EventReferencesParticipantsCategoryRepository eventReferencesParticipantsCategoryRepository
    ) {
        this.eventReferencesParticipantsCategoryService = eventReferencesParticipantsCategoryService;
        this.eventReferencesParticipantsCategoryRepository = eventReferencesParticipantsCategoryRepository;
    }

    /**
     * {@code POST  /event-references-participants-categories} : Create a new eventReferencesParticipantsCategory.
     *
     * @param eventReferencesParticipantsCategoryDTO the eventReferencesParticipantsCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventReferencesParticipantsCategoryDTO, or with status {@code 400 (Bad Request)} if the eventReferencesParticipantsCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-references-participants-categories")
    public ResponseEntity<EventReferencesParticipantsCategoryDTO> createEventReferencesParticipantsCategory(
        @RequestBody EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EventReferencesParticipantsCategory : {}", eventReferencesParticipantsCategoryDTO);
        if (eventReferencesParticipantsCategoryDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new eventReferencesParticipantsCategory cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        EventReferencesParticipantsCategoryDTO result = eventReferencesParticipantsCategoryService.save(
            eventReferencesParticipantsCategoryDTO
        );
        return ResponseEntity
            .created(new URI("/api/event-references-participants-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-references-participants-categories/:id} : Updates an existing eventReferencesParticipantsCategory.
     *
     * @param id the id of the eventReferencesParticipantsCategoryDTO to save.
     * @param eventReferencesParticipantsCategoryDTO the eventReferencesParticipantsCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReferencesParticipantsCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the eventReferencesParticipantsCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventReferencesParticipantsCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-references-participants-categories/{id}")
    public ResponseEntity<EventReferencesParticipantsCategoryDTO> updateEventReferencesParticipantsCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventReferencesParticipantsCategory : {}, {}", id, eventReferencesParticipantsCategoryDTO);
        if (eventReferencesParticipantsCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReferencesParticipantsCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReferencesParticipantsCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventReferencesParticipantsCategoryDTO result = eventReferencesParticipantsCategoryService.update(
            eventReferencesParticipantsCategoryDTO
        );
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    false,
                    ENTITY_NAME,
                    eventReferencesParticipantsCategoryDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /event-references-participants-categories/:id} : Partial updates given fields of an existing eventReferencesParticipantsCategory, field will ignore if it is null
     *
     * @param id the id of the eventReferencesParticipantsCategoryDTO to save.
     * @param eventReferencesParticipantsCategoryDTO the eventReferencesParticipantsCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventReferencesParticipantsCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the eventReferencesParticipantsCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventReferencesParticipantsCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventReferencesParticipantsCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/event-references-participants-categories/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<EventReferencesParticipantsCategoryDTO> partialUpdateEventReferencesParticipantsCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update EventReferencesParticipantsCategory partially : {}, {}",
            id,
            eventReferencesParticipantsCategoryDTO
        );
        if (eventReferencesParticipantsCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventReferencesParticipantsCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventReferencesParticipantsCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventReferencesParticipantsCategoryDTO> result = eventReferencesParticipantsCategoryService.partialUpdate(
            eventReferencesParticipantsCategoryDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                false,
                ENTITY_NAME,
                eventReferencesParticipantsCategoryDTO.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /event-references-participants-categories} : get all the eventReferencesParticipantsCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventReferencesParticipantsCategories in body.
     */
    @GetMapping("/event-references-participants-categories")
    public List<EventReferencesParticipantsCategoryDTO> getAllEventReferencesParticipantsCategories() {
        log.debug("REST request to get all EventReferencesParticipantsCategories");
        return eventReferencesParticipantsCategoryService.findAll();
    }

    /**
     * {@code GET  /event-references-participants-categories/:id} : get the "id" eventReferencesParticipantsCategory.
     *
     * @param id the id of the eventReferencesParticipantsCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventReferencesParticipantsCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-references-participants-categories/{id}")
    public ResponseEntity<EventReferencesParticipantsCategoryDTO> getEventReferencesParticipantsCategory(@PathVariable Long id) {
        log.debug("REST request to get EventReferencesParticipantsCategory : {}", id);
        Optional<EventReferencesParticipantsCategoryDTO> eventReferencesParticipantsCategoryDTO = eventReferencesParticipantsCategoryService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(eventReferencesParticipantsCategoryDTO);
    }

    /**
     * {@code DELETE  /event-references-participants-categories/:id} : delete the "id" eventReferencesParticipantsCategory.
     *
     * @param id the id of the eventReferencesParticipantsCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-references-participants-categories/{id}")
    public ResponseEntity<Void> deleteEventReferencesParticipantsCategory(@PathVariable Long id) {
        log.debug("REST request to delete EventReferencesParticipantsCategory : {}", id);
        eventReferencesParticipantsCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
