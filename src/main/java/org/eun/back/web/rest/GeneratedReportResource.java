package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.GeneratedReportRepository;
import org.eun.back.service.GeneratedReportService;
import org.eun.back.service.dto.GeneratedReportDTO;
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
 * REST controller for managing {@link org.eun.back.domain.GeneratedReport}.
 */
@RestController
@RequestMapping("/api")
public class GeneratedReportResource {

    private final Logger log = LoggerFactory.getLogger(GeneratedReportResource.class);

    private static final String ENTITY_NAME = "generatedReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeneratedReportService generatedReportService;

    private final GeneratedReportRepository generatedReportRepository;

    public GeneratedReportResource(GeneratedReportService generatedReportService, GeneratedReportRepository generatedReportRepository) {
        this.generatedReportService = generatedReportService;
        this.generatedReportRepository = generatedReportRepository;
    }

    /**
     * {@code POST  /generated-reports} : Create a new generatedReport.
     *
     * @param generatedReportDTO the generatedReportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new generatedReportDTO, or with status {@code 400 (Bad Request)} if the generatedReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/generated-reports")
    public ResponseEntity<GeneratedReportDTO> createGeneratedReport(@RequestBody GeneratedReportDTO generatedReportDTO)
        throws URISyntaxException {
        log.debug("REST request to save GeneratedReport : {}", generatedReportDTO);
        if (generatedReportDTO.getId() != null) {
            throw new BadRequestAlertException("A new generatedReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneratedReportDTO result = generatedReportService.save(generatedReportDTO);
        return ResponseEntity
            .created(new URI("/api/generated-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /generated-reports/:id} : Updates an existing generatedReport.
     *
     * @param id the id of the generatedReportDTO to save.
     * @param generatedReportDTO the generatedReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generatedReportDTO,
     * or with status {@code 400 (Bad Request)} if the generatedReportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the generatedReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/generated-reports/{id}")
    public ResponseEntity<GeneratedReportDTO> updateGeneratedReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GeneratedReportDTO generatedReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GeneratedReport : {}, {}", id, generatedReportDTO);
        if (generatedReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generatedReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generatedReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GeneratedReportDTO result = generatedReportService.update(generatedReportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generatedReportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /generated-reports/:id} : Partial updates given fields of an existing generatedReport, field will ignore if it is null
     *
     * @param id the id of the generatedReportDTO to save.
     * @param generatedReportDTO the generatedReportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generatedReportDTO,
     * or with status {@code 400 (Bad Request)} if the generatedReportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the generatedReportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the generatedReportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/generated-reports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GeneratedReportDTO> partialUpdateGeneratedReport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GeneratedReportDTO generatedReportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GeneratedReport partially : {}, {}", id, generatedReportDTO);
        if (generatedReportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generatedReportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generatedReportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GeneratedReportDTO> result = generatedReportService.partialUpdate(generatedReportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generatedReportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /generated-reports} : get all the generatedReports.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of generatedReports in body.
     */
    @GetMapping("/generated-reports")
    public ResponseEntity<List<GeneratedReportDTO>> getAllGeneratedReports(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of GeneratedReports");
        Page<GeneratedReportDTO> page = generatedReportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /generated-reports/:id} : get the "id" generatedReport.
     *
     * @param id the id of the generatedReportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the generatedReportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/generated-reports/{id}")
    public ResponseEntity<GeneratedReportDTO> getGeneratedReport(@PathVariable Long id) {
        log.debug("REST request to get GeneratedReport : {}", id);
        Optional<GeneratedReportDTO> generatedReportDTO = generatedReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(generatedReportDTO);
    }

    /**
     * {@code DELETE  /generated-reports/:id} : delete the "id" generatedReport.
     *
     * @param id the id of the generatedReportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/generated-reports/{id}")
    public ResponseEntity<Void> deleteGeneratedReport(@PathVariable Long id) {
        log.debug("REST request to delete GeneratedReport : {}", id);
        generatedReportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
