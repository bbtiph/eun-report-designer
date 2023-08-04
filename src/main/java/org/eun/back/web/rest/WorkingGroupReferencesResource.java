package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.WorkingGroupReferencesRepository;
import org.eun.back.service.WorkingGroupReferencesQueryService;
import org.eun.back.service.WorkingGroupReferencesService;
import org.eun.back.service.criteria.WorkingGroupReferencesCriteria;
import org.eun.back.service.dto.WorkingGroupReferencesDTO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.WorkingGroupReferences}.
 */
@RestController
@RequestMapping("/api")
public class WorkingGroupReferencesResource {

    private final Logger log = LoggerFactory.getLogger(WorkingGroupReferencesResource.class);

    private static final String ENTITY_NAME = "workingGroupReferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingGroupReferencesService workingGroupReferencesService;

    private final WorkingGroupReferencesRepository workingGroupReferencesRepository;

    private final WorkingGroupReferencesQueryService workingGroupReferencesQueryService;

    public WorkingGroupReferencesResource(
        WorkingGroupReferencesService workingGroupReferencesService,
        WorkingGroupReferencesRepository workingGroupReferencesRepository,
        WorkingGroupReferencesQueryService workingGroupReferencesQueryService
    ) {
        this.workingGroupReferencesService = workingGroupReferencesService;
        this.workingGroupReferencesRepository = workingGroupReferencesRepository;
        this.workingGroupReferencesQueryService = workingGroupReferencesQueryService;
    }

    /**
     * {@code POST  /working-group-references} : Create a new workingGroupReferences.
     *
     * @param workingGroupReferencesDTO the workingGroupReferencesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workingGroupReferencesDTO, or with status {@code 400 (Bad Request)} if the workingGroupReferences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/working-group-references")
    public ResponseEntity<WorkingGroupReferencesDTO> createWorkingGroupReferences(
        @RequestBody WorkingGroupReferencesDTO workingGroupReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save WorkingGroupReferences : {}", workingGroupReferencesDTO);
        if (workingGroupReferencesDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingGroupReferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkingGroupReferencesDTO result = workingGroupReferencesService.save(workingGroupReferencesDTO);
        return ResponseEntity
            .created(new URI("/api/working-group-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping(value = "/working-group-references/upload")
    public ResponseEntity<Void> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            workingGroupReferencesService.upload(file);
        } catch (Exception e) {
            return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, e.toString(), file.getName()))
                .build();
        }

        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, file.getName()))
            .build();
    }

    /**
     * {@code PUT  /working-group-references/:id} : Updates an existing workingGroupReferences.
     *
     * @param id the id of the workingGroupReferencesDTO to save.
     * @param workingGroupReferencesDTO the workingGroupReferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingGroupReferencesDTO,
     * or with status {@code 400 (Bad Request)} if the workingGroupReferencesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workingGroupReferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/working-group-references/{id}")
    public ResponseEntity<WorkingGroupReferencesDTO> updateWorkingGroupReferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkingGroupReferencesDTO workingGroupReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WorkingGroupReferences : {}, {}", id, workingGroupReferencesDTO);
        if (workingGroupReferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workingGroupReferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingGroupReferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkingGroupReferencesDTO result = workingGroupReferencesService.update(workingGroupReferencesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workingGroupReferencesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /working-group-references/:id} : Partial updates given fields of an existing workingGroupReferences, field will ignore if it is null
     *
     * @param id the id of the workingGroupReferencesDTO to save.
     * @param workingGroupReferencesDTO the workingGroupReferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingGroupReferencesDTO,
     * or with status {@code 400 (Bad Request)} if the workingGroupReferencesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workingGroupReferencesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workingGroupReferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/working-group-references/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkingGroupReferencesDTO> partialUpdateWorkingGroupReferences(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkingGroupReferencesDTO workingGroupReferencesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkingGroupReferences partially : {}, {}", id, workingGroupReferencesDTO);
        if (workingGroupReferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workingGroupReferencesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingGroupReferencesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkingGroupReferencesDTO> result = workingGroupReferencesService.partialUpdate(workingGroupReferencesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workingGroupReferencesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /working-group-references} : get all the workingGroupReferences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workingGroupReferences in body.
     */
    @GetMapping("/working-group-references")
    public ResponseEntity<List<WorkingGroupReferencesDTO>> getAllWorkingGroupReferences(
        WorkingGroupReferencesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get WorkingGroupReferences by criteria: {}", criteria);
        Page<WorkingGroupReferencesDTO> page = workingGroupReferencesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-group-references/count} : count all the workingGroupReferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/working-group-references/count")
    public ResponseEntity<Long> countWorkingGroupReferences(WorkingGroupReferencesCriteria criteria) {
        log.debug("REST request to count WorkingGroupReferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(workingGroupReferencesQueryService.countByCriteria(criteria));
    }

    @GetMapping("/working-group-references/by-country/{code}")
    public ResponseEntity<List<WorkingGroupReferencesDTO>> getAllWorkingGroupReferences(@PathVariable String code) {
        log.debug("REST request to get a page of WorkingGroupReferences");
        List<WorkingGroupReferencesDTO> res = workingGroupReferencesService.findAllByCountry(code);
        return ResponseEntity.ok().body(res);
    }

    /**
     * {@code GET  /working-group-references/:id} : get the "id" workingGroupReferences.
     *
     * @param id the id of the workingGroupReferencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workingGroupReferencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/working-group-references/{id}")
    public ResponseEntity<WorkingGroupReferencesDTO> getWorkingGroupReferences(@PathVariable Long id) {
        log.debug("REST request to get WorkingGroupReferences : {}", id);
        Optional<WorkingGroupReferencesDTO> workingGroupReferencesDTO = workingGroupReferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingGroupReferencesDTO);
    }

    /**
     * {@code DELETE  /working-group-references/:id} : delete the "id" workingGroupReferences.
     *
     * @param id the id of the workingGroupReferencesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/working-group-references/{id}")
    public ResponseEntity<Void> deleteWorkingGroupReferences(@PathVariable Long id) {
        log.debug("REST request to delete WorkingGroupReferences : {}", id);
        workingGroupReferencesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
