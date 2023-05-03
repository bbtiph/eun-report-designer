package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.OrganizationInMinistryRepository;
import org.eun.back.service.OrganizationInMinistryService;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
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
 * REST controller for managing {@link org.eun.back.domain.OrganizationInMinistry}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationInMinistryResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationInMinistryResource.class);

    private static final String ENTITY_NAME = "organizationInMinistry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationInMinistryService organizationInMinistryService;

    private final OrganizationInMinistryRepository organizationInMinistryRepository;

    public OrganizationInMinistryResource(
        OrganizationInMinistryService organizationInMinistryService,
        OrganizationInMinistryRepository organizationInMinistryRepository
    ) {
        this.organizationInMinistryService = organizationInMinistryService;
        this.organizationInMinistryRepository = organizationInMinistryRepository;
    }

    /**
     * {@code POST  /organization-in-ministries} : Create a new organizationInMinistry.
     *
     * @param organizationInMinistryDTO the organizationInMinistryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationInMinistryDTO, or with status {@code 400 (Bad Request)} if the organizationInMinistry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organization-in-ministries")
    public ResponseEntity<OrganizationInMinistryDTO> createOrganizationInMinistry(
        @RequestBody OrganizationInMinistryDTO organizationInMinistryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrganizationInMinistry : {}", organizationInMinistryDTO);
        if (organizationInMinistryDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationInMinistry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationInMinistryDTO result = organizationInMinistryService.save(organizationInMinistryDTO);
        return ResponseEntity
            .created(new URI("/api/organization-in-ministries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organization-in-ministries/:id} : Updates an existing organizationInMinistry.
     *
     * @param id the id of the organizationInMinistryDTO to save.
     * @param organizationInMinistryDTO the organizationInMinistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationInMinistryDTO,
     * or with status {@code 400 (Bad Request)} if the organizationInMinistryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationInMinistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organization-in-ministries/{id}")
    public ResponseEntity<OrganizationInMinistryDTO> updateOrganizationInMinistry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationInMinistryDTO organizationInMinistryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationInMinistry : {}, {}", id, organizationInMinistryDTO);
        if (organizationInMinistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationInMinistryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationInMinistryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganizationInMinistryDTO result = organizationInMinistryService.update(organizationInMinistryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationInMinistryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organization-in-ministries/:id} : Partial updates given fields of an existing organizationInMinistry, field will ignore if it is null
     *
     * @param id the id of the organizationInMinistryDTO to save.
     * @param organizationInMinistryDTO the organizationInMinistryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationInMinistryDTO,
     * or with status {@code 400 (Bad Request)} if the organizationInMinistryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationInMinistryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationInMinistryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organization-in-ministries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationInMinistryDTO> partialUpdateOrganizationInMinistry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationInMinistryDTO organizationInMinistryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationInMinistry partially : {}, {}", id, organizationInMinistryDTO);
        if (organizationInMinistryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationInMinistryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationInMinistryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationInMinistryDTO> result = organizationInMinistryService.partialUpdate(organizationInMinistryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationInMinistryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-in-ministries} : get all the organizationInMinistries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationInMinistries in body.
     */
    @GetMapping("/organization-in-ministries")
    public ResponseEntity<List<OrganizationInMinistryDTO>> getAllOrganizationInMinistries(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OrganizationInMinistries");
        Page<OrganizationInMinistryDTO> page = organizationInMinistryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-in-ministries/:id} : get the "id" organizationInMinistry.
     *
     * @param id the id of the organizationInMinistryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationInMinistryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organization-in-ministries/{id}")
    public ResponseEntity<OrganizationInMinistryDTO> getOrganizationInMinistry(@PathVariable Long id) {
        log.debug("REST request to get OrganizationInMinistry : {}", id);
        Optional<OrganizationInMinistryDTO> organizationInMinistryDTO = organizationInMinistryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationInMinistryDTO);
    }

    /**
     * {@code DELETE  /organization-in-ministries/:id} : delete the "id" organizationInMinistry.
     *
     * @param id the id of the organizationInMinistryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organization-in-ministries/{id}")
    public ResponseEntity<Void> deleteOrganizationInMinistry(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationInMinistry : {}", id);
        organizationInMinistryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
