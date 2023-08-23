package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.OrganizationEunIndicatorRepository;
import org.eun.back.service.OrganizationEunIndicatorService;
import org.eun.back.service.dto.OrganizationEunIndicatorDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.OrganizationEunIndicator}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationEunIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationEunIndicatorResource.class);

    private static final String ENTITY_NAME = "organizationEunIndicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationEunIndicatorService organizationEunIndicatorService;

    private final OrganizationEunIndicatorRepository organizationEunIndicatorRepository;

    public OrganizationEunIndicatorResource(
        OrganizationEunIndicatorService organizationEunIndicatorService,
        OrganizationEunIndicatorRepository organizationEunIndicatorRepository
    ) {
        this.organizationEunIndicatorService = organizationEunIndicatorService;
        this.organizationEunIndicatorRepository = organizationEunIndicatorRepository;
    }

    /**
     * {@code POST  /organization-eun-indicators} : Create a new organizationEunIndicator.
     *
     * @param organizationEunIndicatorDTO the organizationEunIndicatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationEunIndicatorDTO, or with status {@code 400 (Bad Request)} if the organizationEunIndicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organization-eun-indicators")
    public ResponseEntity<OrganizationEunIndicatorDTO> createOrganizationEunIndicator(
        @RequestBody OrganizationEunIndicatorDTO organizationEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrganizationEunIndicator : {}", organizationEunIndicatorDTO);
        if (organizationEunIndicatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationEunIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationEunIndicatorDTO result = organizationEunIndicatorService.save(organizationEunIndicatorDTO);
        return ResponseEntity
            .created(new URI("/api/organization-eun-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organization-eun-indicators/:id} : Updates an existing organizationEunIndicator.
     *
     * @param id the id of the organizationEunIndicatorDTO to save.
     * @param organizationEunIndicatorDTO the organizationEunIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationEunIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the organizationEunIndicatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationEunIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organization-eun-indicators/{id}")
    public ResponseEntity<OrganizationEunIndicatorDTO> updateOrganizationEunIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationEunIndicatorDTO organizationEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationEunIndicator : {}, {}", id, organizationEunIndicatorDTO);
        if (organizationEunIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationEunIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationEunIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganizationEunIndicatorDTO result = organizationEunIndicatorService.update(organizationEunIndicatorDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationEunIndicatorDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /organization-eun-indicators/:id} : Partial updates given fields of an existing organizationEunIndicator, field will ignore if it is null
     *
     * @param id the id of the organizationEunIndicatorDTO to save.
     * @param organizationEunIndicatorDTO the organizationEunIndicatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationEunIndicatorDTO,
     * or with status {@code 400 (Bad Request)} if the organizationEunIndicatorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationEunIndicatorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationEunIndicatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organization-eun-indicators/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationEunIndicatorDTO> partialUpdateOrganizationEunIndicator(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationEunIndicatorDTO organizationEunIndicatorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationEunIndicator partially : {}, {}", id, organizationEunIndicatorDTO);
        if (organizationEunIndicatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationEunIndicatorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationEunIndicatorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationEunIndicatorDTO> result = organizationEunIndicatorService.partialUpdate(organizationEunIndicatorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationEunIndicatorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-eun-indicators} : get all the organizationEunIndicators.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationEunIndicators in body.
     */
    @GetMapping("/organization-eun-indicators")
    public List<OrganizationEunIndicatorDTO> getAllOrganizationEunIndicators() {
        log.debug("REST request to get all OrganizationEunIndicators");
        return organizationEunIndicatorService.findAll();
    }

    /**
     * {@code GET  /organization-eun-indicators/:id} : get the "id" organizationEunIndicator.
     *
     * @param id the id of the organizationEunIndicatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationEunIndicatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organization-eun-indicators/{id}")
    public ResponseEntity<OrganizationEunIndicatorDTO> getOrganizationEunIndicator(@PathVariable Long id) {
        log.debug("REST request to get OrganizationEunIndicator : {}", id);
        Optional<OrganizationEunIndicatorDTO> organizationEunIndicatorDTO = organizationEunIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationEunIndicatorDTO);
    }

    /**
     * {@code DELETE  /organization-eun-indicators/:id} : delete the "id" organizationEunIndicator.
     *
     * @param id the id of the organizationEunIndicatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organization-eun-indicators/{id}")
    public ResponseEntity<Void> deleteOrganizationEunIndicator(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationEunIndicator : {}", id);
        organizationEunIndicatorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
