package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.eun.back.repository.OperationalBodyRepository;
import org.eun.back.service.OperationalBodyService;
import org.eun.back.service.dto.OperationalBodyDTO;
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
 * REST controller for managing {@link org.eun.back.domain.OperationalBody}.
 */
@RestController
@RequestMapping("/api")
public class OperationalBodyResource {

    private final Logger log = LoggerFactory.getLogger(OperationalBodyResource.class);

    private static final String ENTITY_NAME = "operationalBody";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationalBodyService operationalBodyService;

    private final OperationalBodyRepository operationalBodyRepository;

    public OperationalBodyResource(OperationalBodyService operationalBodyService, OperationalBodyRepository operationalBodyRepository) {
        this.operationalBodyService = operationalBodyService;
        this.operationalBodyRepository = operationalBodyRepository;
    }

    /**
     * {@code POST  /operational-bodies} : Create a new operationalBody.
     *
     * @param operationalBodyDTO the operationalBodyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationalBodyDTO, or with status {@code 400 (Bad Request)} if the operationalBody has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operational-bodies")
    public ResponseEntity<OperationalBodyDTO> createOperationalBody(@Valid @RequestBody OperationalBodyDTO operationalBodyDTO)
        throws URISyntaxException {
        log.debug("REST request to save OperationalBody : {}", operationalBodyDTO);
        if (operationalBodyDTO.getId() != null) {
            throw new BadRequestAlertException("A new operationalBody cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationalBodyDTO result = operationalBodyService.save(operationalBodyDTO);
        return ResponseEntity
            .created(new URI("/api/operational-bodies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operational-bodies/:id} : Updates an existing operationalBody.
     *
     * @param id the id of the operationalBodyDTO to save.
     * @param operationalBodyDTO the operationalBodyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationalBodyDTO,
     * or with status {@code 400 (Bad Request)} if the operationalBodyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationalBodyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operational-bodies/{id}")
    public ResponseEntity<OperationalBodyDTO> updateOperationalBody(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OperationalBodyDTO operationalBodyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OperationalBody : {}, {}", id, operationalBodyDTO);
        if (operationalBodyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationalBodyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationalBodyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperationalBodyDTO result = operationalBodyService.update(operationalBodyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operationalBodyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /operational-bodies/:id} : Partial updates given fields of an existing operationalBody, field will ignore if it is null
     *
     * @param id the id of the operationalBodyDTO to save.
     * @param operationalBodyDTO the operationalBodyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationalBodyDTO,
     * or with status {@code 400 (Bad Request)} if the operationalBodyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the operationalBodyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the operationalBodyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operational-bodies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperationalBodyDTO> partialUpdateOperationalBody(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OperationalBodyDTO operationalBodyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperationalBody partially : {}, {}", id, operationalBodyDTO);
        if (operationalBodyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationalBodyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationalBodyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperationalBodyDTO> result = operationalBodyService.partialUpdate(operationalBodyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operationalBodyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /operational-bodies} : get all the operationalBodies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationalBodies in body.
     */
    @GetMapping("/operational-bodies")
    public ResponseEntity<List<OperationalBodyDTO>> getAllOperationalBodies(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OperationalBodies");
        Page<OperationalBodyDTO> page = operationalBodyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /operational-bodies/:id} : get the "id" operationalBody.
     *
     * @param id the id of the operationalBodyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationalBodyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operational-bodies/{id}")
    public ResponseEntity<OperationalBodyDTO> getOperationalBody(@PathVariable Long id) {
        log.debug("REST request to get OperationalBody : {}", id);
        Optional<OperationalBodyDTO> operationalBodyDTO = operationalBodyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationalBodyDTO);
    }

    /**
     * {@code DELETE  /operational-bodies/:id} : delete the "id" operationalBody.
     *
     * @param id the id of the operationalBodyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operational-bodies/{id}")
    public ResponseEntity<Void> deleteOperationalBody(@PathVariable Long id) {
        log.debug("REST request to delete OperationalBody : {}", id);
        operationalBodyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
