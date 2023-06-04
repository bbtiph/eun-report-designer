package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ReportBlocksContentDataRepository;
import org.eun.back.service.ReportBlocksContentDataService;
import org.eun.back.service.dto.ReportBlocksContentDataDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.ReportBlocksContentData}.
 */
@RestController
@RequestMapping("/api")
public class ReportBlocksContentDataResource {

    private final Logger log = LoggerFactory.getLogger(ReportBlocksContentDataResource.class);

    private static final String ENTITY_NAME = "reportBlocksContentData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportBlocksContentDataService reportBlocksContentDataService;

    private final ReportBlocksContentDataRepository reportBlocksContentDataRepository;

    public ReportBlocksContentDataResource(
        ReportBlocksContentDataService reportBlocksContentDataService,
        ReportBlocksContentDataRepository reportBlocksContentDataRepository
    ) {
        this.reportBlocksContentDataService = reportBlocksContentDataService;
        this.reportBlocksContentDataRepository = reportBlocksContentDataRepository;
    }

    /**
     * {@code POST  /report-blocks-content-data} : Create a new reportBlocksContentData.
     *
     * @param reportBlocksContentDataDTO the reportBlocksContentDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportBlocksContentDataDTO, or with status {@code 400 (Bad Request)} if the reportBlocksContentData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-blocks-content-data")
    public ResponseEntity<ReportBlocksContentDataDTO> createReportBlocksContentData(
        @RequestBody ReportBlocksContentDataDTO reportBlocksContentDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ReportBlocksContentData : {}", reportBlocksContentDataDTO);
        if (reportBlocksContentDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportBlocksContentData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportBlocksContentDataDTO result = reportBlocksContentDataService.save(reportBlocksContentDataDTO);
        return ResponseEntity
            .created(new URI("/api/report-blocks-content-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-blocks-content-data/:id} : Updates an existing reportBlocksContentData.
     *
     * @param id the id of the reportBlocksContentDataDTO to save.
     * @param reportBlocksContentDataDTO the reportBlocksContentDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportBlocksContentDataDTO,
     * or with status {@code 400 (Bad Request)} if the reportBlocksContentDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportBlocksContentDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-blocks-content-data/{id}")
    public ResponseEntity<ReportBlocksContentDataDTO> updateReportBlocksContentData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportBlocksContentDataDTO reportBlocksContentDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReportBlocksContentData : {}, {}", id, reportBlocksContentDataDTO);
        if (reportBlocksContentDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportBlocksContentDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportBlocksContentDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReportBlocksContentDataDTO result = reportBlocksContentDataService.update(reportBlocksContentDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportBlocksContentDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /report-blocks-content-data/:id} : Partial updates given fields of an existing reportBlocksContentData, field will ignore if it is null
     *
     * @param id the id of the reportBlocksContentDataDTO to save.
     * @param reportBlocksContentDataDTO the reportBlocksContentDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportBlocksContentDataDTO,
     * or with status {@code 400 (Bad Request)} if the reportBlocksContentDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reportBlocksContentDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reportBlocksContentDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/report-blocks-content-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReportBlocksContentDataDTO> partialUpdateReportBlocksContentData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportBlocksContentDataDTO reportBlocksContentDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReportBlocksContentData partially : {}, {}", id, reportBlocksContentDataDTO);
        if (reportBlocksContentDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportBlocksContentDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportBlocksContentDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReportBlocksContentDataDTO> result = reportBlocksContentDataService.partialUpdate(reportBlocksContentDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportBlocksContentDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /report-blocks-content-data} : get all the reportBlocksContentData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportBlocksContentData in body.
     */
    @GetMapping("/report-blocks-content-data")
    public List<ReportBlocksContentDataDTO> getAllReportBlocksContentData() {
        log.debug("REST request to get all ReportBlocksContentData");
        return reportBlocksContentDataService.findAll();
    }

    /**
     * {@code GET  /report-blocks-content-data/:id} : get the "id" reportBlocksContentData.
     *
     * @param id the id of the reportBlocksContentDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportBlocksContentDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-blocks-content-data/{id}")
    public ResponseEntity<ReportBlocksContentDataDTO> getReportBlocksContentData(@PathVariable Long id) {
        log.debug("REST request to get ReportBlocksContentData : {}", id);
        Optional<ReportBlocksContentDataDTO> reportBlocksContentDataDTO = reportBlocksContentDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportBlocksContentDataDTO);
    }

    /**
     * {@code DELETE  /report-blocks-content-data/:id} : delete the "id" reportBlocksContentData.
     *
     * @param id the id of the reportBlocksContentDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-blocks-content-data/{id}")
    public ResponseEntity<Void> deleteReportBlocksContentData(@PathVariable Long id) {
        log.debug("REST request to delete ReportBlocksContentData : {}", id);
        reportBlocksContentDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
