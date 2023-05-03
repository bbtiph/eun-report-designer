package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.EventParticipantRepository;
import org.eun.back.service.EventParticipantService;
import org.eun.back.service.dto.EventParticipantDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.EventParticipant}.
 */
@RestController
@RequestMapping("/api")
public class EventParticipantResource {

    private final Logger log = LoggerFactory.getLogger(EventParticipantResource.class);

    private static final String ENTITY_NAME = "eventParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventParticipantService eventParticipantService;

    private final EventParticipantRepository eventParticipantRepository;

    public EventParticipantResource(
        EventParticipantService eventParticipantService,
        EventParticipantRepository eventParticipantRepository
    ) {
        this.eventParticipantService = eventParticipantService;
        this.eventParticipantRepository = eventParticipantRepository;
    }

    /**
     * {@code POST  /event-participants} : Create a new eventParticipant.
     *
     * @param eventParticipantDTO the eventParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventParticipantDTO, or with status {@code 400 (Bad Request)} if the eventParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-participants")
    public ResponseEntity<EventParticipantDTO> createEventParticipant(@RequestBody EventParticipantDTO eventParticipantDTO)
        throws URISyntaxException {
        log.debug("REST request to save EventParticipant : {}", eventParticipantDTO);
        if (eventParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventParticipantDTO result = eventParticipantService.save(eventParticipantDTO);
        return ResponseEntity
            .created(new URI("/api/event-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-participants/:id} : Updates an existing eventParticipant.
     *
     * @param id the id of the eventParticipantDTO to save.
     * @param eventParticipantDTO the eventParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the eventParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-participants/{id}")
    public ResponseEntity<EventParticipantDTO> updateEventParticipant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventParticipantDTO eventParticipantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventParticipant : {}, {}", id, eventParticipantDTO);
        if (eventParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventParticipantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventParticipantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventParticipantDTO result = eventParticipantService.update(eventParticipantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-participants/:id} : Partial updates given fields of an existing eventParticipant, field will ignore if it is null
     *
     * @param id the id of the eventParticipantDTO to save.
     * @param eventParticipantDTO the eventParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the eventParticipantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventParticipantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-participants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventParticipantDTO> partialUpdateEventParticipant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventParticipantDTO eventParticipantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventParticipant partially : {}, {}", id, eventParticipantDTO);
        if (eventParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventParticipantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventParticipantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventParticipantDTO> result = eventParticipantService.partialUpdate(eventParticipantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventParticipantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-participants} : get all the eventParticipants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventParticipants in body.
     */
    @GetMapping("/event-participants")
    public ResponseEntity<List<EventParticipantDTO>> getAllEventParticipants(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EventParticipants");
        Page<EventParticipantDTO> page = eventParticipantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-participants/:id} : get the "id" eventParticipant.
     *
     * @param id the id of the eventParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-participants/{id}")
    public ResponseEntity<EventParticipantDTO> getEventParticipant(@PathVariable Long id) {
        log.debug("REST request to get EventParticipant : {}", id);
        Optional<EventParticipantDTO> eventParticipantDTO = eventParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventParticipantDTO);
    }

    /**
     * {@code DELETE  /event-participants/:id} : delete the "id" eventParticipant.
     *
     * @param id the id of the eventParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-participants/{id}")
    public ResponseEntity<Void> deleteEventParticipant(@PathVariable Long id) {
        log.debug("REST request to delete EventParticipant : {}", id);
        eventParticipantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
