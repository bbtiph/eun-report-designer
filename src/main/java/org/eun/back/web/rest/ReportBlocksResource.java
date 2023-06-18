package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ReportBlocksRepository;
import org.eun.back.service.ReportBlocksService;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.ReportBlocks}.
 */
@RestController
@RequestMapping("/api")
public class ReportBlocksResource {

    private final Logger log = LoggerFactory.getLogger(ReportBlocksResource.class);

    private static final String ENTITY_NAME = "reportBlocks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportBlocksService reportBlocksService;

    private final ReportBlocksRepository reportBlocksRepository;

    public ReportBlocksResource(ReportBlocksService reportBlocksService, ReportBlocksRepository reportBlocksRepository) {
        this.reportBlocksService = reportBlocksService;
        this.reportBlocksRepository = reportBlocksRepository;
    }

    /**
     * {@code POST  /report-blocks} : Create a new reportBlocks.
     *
     * @param reportBlocksDTO the reportBlocksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportBlocksDTO, or with status {@code 400 (Bad Request)} if the reportBlocks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-blocks")
    public ResponseEntity<ReportBlocksDTO> createReportBlocks(@RequestBody ReportBlocksDTO reportBlocksDTO) throws URISyntaxException {
        log.debug("REST request to save ReportBlocks : {}", reportBlocksDTO);
        if (reportBlocksDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportBlocks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportBlocksDTO result = reportBlocksService.save(reportBlocksDTO);
        return ResponseEntity
            .created(new URI("/api/report-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-blocks/:id} : Updates an existing reportBlocks.
     *
     * @param id the id of the reportBlocksDTO to save.
     * @param reportBlocksDTO the reportBlocksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportBlocksDTO,
     * or with status {@code 400 (Bad Request)} if the reportBlocksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportBlocksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-blocks/{id}")
    public ResponseEntity<ReportBlocksDTO> updateReportBlocks(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportBlocksDTO reportBlocksDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReportBlocks : {}, {}", id, reportBlocksDTO);
        if (reportBlocksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportBlocksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportBlocksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReportBlocksDTO result = reportBlocksService.update(reportBlocksDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportBlocksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /report-blocks/:id} : Partial updates given fields of an existing reportBlocks, field will ignore if it is null
     *
     * @param id the id of the reportBlocksDTO to save.
     * @param reportBlocksDTO the reportBlocksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportBlocksDTO,
     * or with status {@code 400 (Bad Request)} if the reportBlocksDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reportBlocksDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reportBlocksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/report-blocks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReportBlocksDTO> partialUpdateReportBlocks(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportBlocksDTO reportBlocksDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReportBlocks partially : {}, {}", id, reportBlocksDTO);
        if (reportBlocksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportBlocksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportBlocksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReportBlocksDTO> result = reportBlocksService.partialUpdate(reportBlocksDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportBlocksDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /report-blocks} : get all the reportBlocks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportBlocks in body.
     */
    @GetMapping("/report-blocks")
    public List<ReportBlocksDTO> getAllReportBlocks(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ReportBlocks");
        return reportBlocksService.findAll();
    }

    /**
     * {@code GET  /report-blocks} : get all the reportBlocks by report.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportBlocks in body.
     */
    @GetMapping("/report-blocks/report/{reportId}/{countryId}")
    public List<ReportBlocksDTO> getAllReportBlocksByReport(
        @PathVariable Long reportId,
        @PathVariable Long countryId,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all ReportBlocks");
        return reportBlocksService.findAllByReport(reportId, countryId);
    }

    /**
     * {@code GET  /report-blocks/:id} : get the "id" reportBlocks.
     *
     * @param id the id of the reportBlocksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportBlocksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-blocks/{id}")
    public ResponseEntity<ReportBlocksDTO> getReportBlocks(@PathVariable Long id) {
        log.debug("REST request to get ReportBlocks : {}", id);
        Optional<ReportBlocksDTO> reportBlocksDTO = reportBlocksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportBlocksDTO);
    }

    @GetMapping("/report-blocks/{id}/{countryId}")
    public ResponseEntity<ReportBlocksDTO> getReportBlocksWithCountry(@PathVariable Long id, @PathVariable Long countryId) {
        log.debug("REST request to get ReportBlocks : {}", id);
        Optional<ReportBlocksDTO> reportBlocksDTO = reportBlocksService.findOneWithCountry(id, countryId);
        return ResponseUtil.wrapOrNotFound(reportBlocksDTO);
    }

    /**
     * {@code DELETE  /report-blocks/:id} : delete the "id" reportBlocks.
     *
     * @param id the id of the reportBlocksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-blocks/{id}")
    public ResponseEntity<Void> deleteReportBlocks(@PathVariable Long id) {
        log.debug("REST request to delete ReportBlocks : {}", id);
        reportBlocksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
