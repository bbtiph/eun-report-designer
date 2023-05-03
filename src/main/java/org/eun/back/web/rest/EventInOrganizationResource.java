package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.EventInOrganizationRepository;
import org.eun.back.service.EventInOrganizationService;
import org.eun.back.service.dto.EventInOrganizationDTO;
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
 * REST controller for managing {@link org.eun.back.domain.EventInOrganization}.
 */
@RestController
@RequestMapping("/api")
public class EventInOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(EventInOrganizationResource.class);

    private static final String ENTITY_NAME = "eventInOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventInOrganizationService eventInOrganizationService;

    private final EventInOrganizationRepository eventInOrganizationRepository;

    public EventInOrganizationResource(
        EventInOrganizationService eventInOrganizationService,
        EventInOrganizationRepository eventInOrganizationRepository
    ) {
        this.eventInOrganizationService = eventInOrganizationService;
        this.eventInOrganizationRepository = eventInOrganizationRepository;
    }

    /**
     * {@code POST  /event-in-organizations} : Create a new eventInOrganization.
     *
     * @param eventInOrganizationDTO the eventInOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventInOrganizationDTO, or with status {@code 400 (Bad Request)} if the eventInOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-in-organizations")
    public ResponseEntity<EventInOrganizationDTO> createEventInOrganization(@RequestBody EventInOrganizationDTO eventInOrganizationDTO)
        throws URISyntaxException {
        log.debug("REST request to save EventInOrganization : {}", eventInOrganizationDTO);
        if (eventInOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventInOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventInOrganizationDTO result = eventInOrganizationService.save(eventInOrganizationDTO);
        return ResponseEntity
            .created(new URI("/api/event-in-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-in-organizations/:id} : Updates an existing eventInOrganization.
     *
     * @param id the id of the eventInOrganizationDTO to save.
     * @param eventInOrganizationDTO the eventInOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventInOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the eventInOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventInOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-in-organizations/{id}")
    public ResponseEntity<EventInOrganizationDTO> updateEventInOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventInOrganizationDTO eventInOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventInOrganization : {}, {}", id, eventInOrganizationDTO);
        if (eventInOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventInOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventInOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventInOrganizationDTO result = eventInOrganizationService.update(eventInOrganizationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventInOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-in-organizations/:id} : Partial updates given fields of an existing eventInOrganization, field will ignore if it is null
     *
     * @param id the id of the eventInOrganizationDTO to save.
     * @param eventInOrganizationDTO the eventInOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventInOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the eventInOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventInOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventInOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-in-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventInOrganizationDTO> partialUpdateEventInOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventInOrganizationDTO eventInOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventInOrganization partially : {}, {}", id, eventInOrganizationDTO);
        if (eventInOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventInOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventInOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventInOrganizationDTO> result = eventInOrganizationService.partialUpdate(eventInOrganizationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventInOrganizationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-in-organizations} : get all the eventInOrganizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventInOrganizations in body.
     */
    @GetMapping("/event-in-organizations")
    public ResponseEntity<List<EventInOrganizationDTO>> getAllEventInOrganizations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EventInOrganizations");
        Page<EventInOrganizationDTO> page = eventInOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-in-organizations/:id} : get the "id" eventInOrganization.
     *
     * @param id the id of the eventInOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventInOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-in-organizations/{id}")
    public ResponseEntity<EventInOrganizationDTO> getEventInOrganization(@PathVariable Long id) {
        log.debug("REST request to get EventInOrganization : {}", id);
        Optional<EventInOrganizationDTO> eventInOrganizationDTO = eventInOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventInOrganizationDTO);
    }

    /**
     * {@code DELETE  /event-in-organizations/:id} : delete the "id" eventInOrganization.
     *
     * @param id the id of the eventInOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-in-organizations/{id}")
    public ResponseEntity<Void> deleteEventInOrganization(@PathVariable Long id) {
        log.debug("REST request to delete EventInOrganization : {}", id);
        eventInOrganizationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
