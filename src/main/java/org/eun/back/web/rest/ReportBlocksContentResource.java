package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ReportBlocksContentRepository;
import org.eun.back.service.ReportBlocksContentService;
import org.eun.back.service.dto.ReportBlocksContentDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.ReportBlocksContent}.
 */
@RestController
@RequestMapping("/api")
public class ReportBlocksContentResource {

    private final Logger log = LoggerFactory.getLogger(ReportBlocksContentResource.class);

    private static final String ENTITY_NAME = "reportBlocksContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportBlocksContentService reportBlocksContentService;

    private final ReportBlocksContentRepository reportBlocksContentRepository;

    public ReportBlocksContentResource(
        ReportBlocksContentService reportBlocksContentService,
        ReportBlocksContentRepository reportBlocksContentRepository
    ) {
        this.reportBlocksContentService = reportBlocksContentService;
        this.reportBlocksContentRepository = reportBlocksContentRepository;
    }

    /**
     * {@code POST  /report-blocks-contents} : Create a new reportBlocksContent.
     *
     * @param reportBlocksContentDTO the reportBlocksContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportBlocksContentDTO, or with status {@code 400 (Bad Request)} if the reportBlocksContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-blocks-contents")
    public ResponseEntity<ReportBlocksContentDTO> createReportBlocksContent(@RequestBody ReportBlocksContentDTO reportBlocksContentDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReportBlocksContent : {}", reportBlocksContentDTO);
        if (reportBlocksContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportBlocksContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportBlocksContentDTO result = reportBlocksContentService.save(reportBlocksContentDTO);
        return ResponseEntity
            .created(new URI("/api/report-blocks-contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-blocks-contents/:id} : Updates an existing reportBlocksContent.
     *
     * @param id the id of the reportBlocksContentDTO to save.
     * @param reportBlocksContentDTO the reportBlocksContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportBlocksContentDTO,
     * or with status {@code 400 (Bad Request)} if the reportBlocksContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportBlocksContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-blocks-contents/{id}")
    public ResponseEntity<ReportBlocksContentDTO> updateReportBlocksContent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportBlocksContentDTO reportBlocksContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReportBlocksContent : {}, {}", id, reportBlocksContentDTO);
        if (reportBlocksContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportBlocksContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportBlocksContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReportBlocksContentDTO result = reportBlocksContentService.update(reportBlocksContentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportBlocksContentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /report-blocks-contents/:id} : Partial updates given fields of an existing reportBlocksContent, field will ignore if it is null
     *
     * @param id the id of the reportBlocksContentDTO to save.
     * @param reportBlocksContentDTO the reportBlocksContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportBlocksContentDTO,
     * or with status {@code 400 (Bad Request)} if the reportBlocksContentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reportBlocksContentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reportBlocksContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/report-blocks-contents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReportBlocksContentDTO> partialUpdateReportBlocksContent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportBlocksContentDTO reportBlocksContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReportBlocksContent partially : {}, {}", id, reportBlocksContentDTO);
        if (reportBlocksContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportBlocksContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportBlocksContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReportBlocksContentDTO> result = reportBlocksContentService.partialUpdate(reportBlocksContentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportBlocksContentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /report-blocks-contents} : get all the reportBlocksContents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportBlocksContents in body.
     */
    @GetMapping("/report-blocks-contents")
    public List<ReportBlocksContentDTO> getAllReportBlocksContents() {
        log.debug("REST request to get all ReportBlocksContents");
        return reportBlocksContentService.findAll();
    }

    /**
     * {@code GET  /report-blocks-contents/:id} : get the "id" reportBlocksContent.
     *
     * @param id the id of the reportBlocksContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportBlocksContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-blocks-contents/{id}")
    public ResponseEntity<ReportBlocksContentDTO> getReportBlocksContent(@PathVariable Long id) {
        log.debug("REST request to get ReportBlocksContent : {}", id);
        Optional<ReportBlocksContentDTO> reportBlocksContentDTO = reportBlocksContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportBlocksContentDTO);
    }

    /**
     * {@code DELETE  /report-blocks-contents/:id} : delete the "id" reportBlocksContent.
     *
     * @param id the id of the reportBlocksContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-blocks-contents/{id}")
    public ResponseEntity<Void> deleteReportBlocksContent(@PathVariable Long id) {
        log.debug("REST request to delete ReportBlocksContent : {}", id);
        reportBlocksContentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
