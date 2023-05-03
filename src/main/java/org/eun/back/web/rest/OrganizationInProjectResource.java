package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.OrganizationInProjectRepository;
import org.eun.back.service.OrganizationInProjectService;
import org.eun.back.service.dto.OrganizationInProjectDTO;
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
 * REST controller for managing {@link org.eun.back.domain.OrganizationInProject}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationInProjectResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationInProjectResource.class);

    private static final String ENTITY_NAME = "organizationInProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationInProjectService organizationInProjectService;

    private final OrganizationInProjectRepository organizationInProjectRepository;

    public OrganizationInProjectResource(
        OrganizationInProjectService organizationInProjectService,
        OrganizationInProjectRepository organizationInProjectRepository
    ) {
        this.organizationInProjectService = organizationInProjectService;
        this.organizationInProjectRepository = organizationInProjectRepository;
    }

    /**
     * {@code POST  /organization-in-projects} : Create a new organizationInProject.
     *
     * @param organizationInProjectDTO the organizationInProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationInProjectDTO, or with status {@code 400 (Bad Request)} if the organizationInProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organization-in-projects")
    public ResponseEntity<OrganizationInProjectDTO> createOrganizationInProject(
        @RequestBody OrganizationInProjectDTO organizationInProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrganizationInProject : {}", organizationInProjectDTO);
        if (organizationInProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationInProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationInProjectDTO result = organizationInProjectService.save(organizationInProjectDTO);
        return ResponseEntity
            .created(new URI("/api/organization-in-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organization-in-projects/:id} : Updates an existing organizationInProject.
     *
     * @param id the id of the organizationInProjectDTO to save.
     * @param organizationInProjectDTO the organizationInProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationInProjectDTO,
     * or with status {@code 400 (Bad Request)} if the organizationInProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationInProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organization-in-projects/{id}")
    public ResponseEntity<OrganizationInProjectDTO> updateOrganizationInProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationInProjectDTO organizationInProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationInProject : {}, {}", id, organizationInProjectDTO);
        if (organizationInProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationInProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationInProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganizationInProjectDTO result = organizationInProjectService.update(organizationInProjectDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationInProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organization-in-projects/:id} : Partial updates given fields of an existing organizationInProject, field will ignore if it is null
     *
     * @param id the id of the organizationInProjectDTO to save.
     * @param organizationInProjectDTO the organizationInProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationInProjectDTO,
     * or with status {@code 400 (Bad Request)} if the organizationInProjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationInProjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationInProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organization-in-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationInProjectDTO> partialUpdateOrganizationInProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrganizationInProjectDTO organizationInProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationInProject partially : {}, {}", id, organizationInProjectDTO);
        if (organizationInProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationInProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationInProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationInProjectDTO> result = organizationInProjectService.partialUpdate(organizationInProjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationInProjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-in-projects} : get all the organizationInProjects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationInProjects in body.
     */
    @GetMapping("/organization-in-projects")
    public ResponseEntity<List<OrganizationInProjectDTO>> getAllOrganizationInProjects(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OrganizationInProjects");
        Page<OrganizationInProjectDTO> page = organizationInProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-in-projects/:id} : get the "id" organizationInProject.
     *
     * @param id the id of the organizationInProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationInProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organization-in-projects/{id}")
    public ResponseEntity<OrganizationInProjectDTO> getOrganizationInProject(@PathVariable Long id) {
        log.debug("REST request to get OrganizationInProject : {}", id);
        Optional<OrganizationInProjectDTO> organizationInProjectDTO = organizationInProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationInProjectDTO);
    }

    /**
     * {@code DELETE  /organization-in-projects/:id} : delete the "id" organizationInProject.
     *
     * @param id the id of the organizationInProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organization-in-projects/{id}")
    public ResponseEntity<Void> deleteOrganizationInProject(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationInProject : {}", id);
        organizationInProjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
