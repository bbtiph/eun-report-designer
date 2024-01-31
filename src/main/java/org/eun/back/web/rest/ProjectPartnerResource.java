package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ProjectPartnerRepository;
import org.eun.back.service.ProjectPartnerService;
import org.eun.back.service.dto.ProjectPartnerDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.ProjectPartner}.
 */
@RestController
@RequestMapping("/api")
public class ProjectPartnerResource {

    private final Logger log = LoggerFactory.getLogger(ProjectPartnerResource.class);

    private static final String ENTITY_NAME = "projectPartner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectPartnerService projectPartnerService;

    private final ProjectPartnerRepository projectPartnerRepository;

    public ProjectPartnerResource(ProjectPartnerService projectPartnerService, ProjectPartnerRepository projectPartnerRepository) {
        this.projectPartnerService = projectPartnerService;
        this.projectPartnerRepository = projectPartnerRepository;
    }

    /**
     * {@code POST  /project-partners} : Create a new projectPartner.
     *
     * @param projectPartnerDTO the projectPartnerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectPartnerDTO, or with status {@code 400 (Bad Request)} if the projectPartner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-partners")
    public ResponseEntity<ProjectPartnerDTO> createProjectPartner(@RequestBody ProjectPartnerDTO projectPartnerDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectPartner : {}", projectPartnerDTO);
        if (projectPartnerDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectPartner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectPartnerDTO result = projectPartnerService.save(projectPartnerDTO);
        return ResponseEntity
            .created(new URI("/api/project-partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-partners/:id} : Updates an existing projectPartner.
     *
     * @param id the id of the projectPartnerDTO to save.
     * @param projectPartnerDTO the projectPartnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectPartnerDTO,
     * or with status {@code 400 (Bad Request)} if the projectPartnerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectPartnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-partners/{id}")
    public ResponseEntity<ProjectPartnerDTO> updateProjectPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectPartnerDTO projectPartnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectPartner : {}, {}", id, projectPartnerDTO);
        if (projectPartnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectPartnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectPartnerDTO result = projectPartnerService.update(projectPartnerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectPartnerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-partners/:id} : Partial updates given fields of an existing projectPartner, field will ignore if it is null
     *
     * @param id the id of the projectPartnerDTO to save.
     * @param projectPartnerDTO the projectPartnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectPartnerDTO,
     * or with status {@code 400 (Bad Request)} if the projectPartnerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectPartnerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectPartnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-partners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectPartnerDTO> partialUpdateProjectPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectPartnerDTO projectPartnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectPartner partially : {}, {}", id, projectPartnerDTO);
        if (projectPartnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectPartnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectPartnerDTO> result = projectPartnerService.partialUpdate(projectPartnerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectPartnerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-partners} : get all the projectPartners.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectPartners in body.
     */
    @GetMapping("/project-partners")
    public List<ProjectPartnerDTO> getAllProjectPartners() {
        log.debug("REST request to get all ProjectPartners");
        return projectPartnerService.findAll();
    }

    /**
     * {@code GET  /project-partners/:id} : get the "id" projectPartner.
     *
     * @param id the id of the projectPartnerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectPartnerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-partners/{id}")
    public ResponseEntity<ProjectPartnerDTO> getProjectPartner(@PathVariable Long id) {
        log.debug("REST request to get ProjectPartner : {}", id);
        Optional<ProjectPartnerDTO> projectPartnerDTO = projectPartnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectPartnerDTO);
    }

    /**
     * {@code DELETE  /project-partners/:id} : delete the "id" projectPartner.
     *
     * @param id the id of the projectPartnerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-partners/{id}")
    public ResponseEntity<Void> deleteProjectPartner(@PathVariable Long id) {
        log.debug("REST request to delete ProjectPartner : {}", id);
        projectPartnerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
