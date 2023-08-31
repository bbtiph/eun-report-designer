package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ReportTemplateRepository;
import org.eun.back.service.ReportTemplateService;
import org.eun.back.service.dto.ReportTemplateDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.ReportTemplate}.
 */
@RestController
@RequestMapping("/api")
public class ReportTemplateResource {

    private final Logger log = LoggerFactory.getLogger(ReportTemplateResource.class);

    private static final String ENTITY_NAME = "reportTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReportTemplateService reportTemplateService;

    private final ReportTemplateRepository reportTemplateRepository;

    public ReportTemplateResource(ReportTemplateService reportTemplateService, ReportTemplateRepository reportTemplateRepository) {
        this.reportTemplateService = reportTemplateService;
        this.reportTemplateRepository = reportTemplateRepository;
    }

    /**
     * {@code POST  /report-templates} : Create a new reportTemplate.
     *
     * @param reportTemplateDTO the reportTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reportTemplateDTO, or with status {@code 400 (Bad Request)} if the reportTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/report-templates")
    public ResponseEntity<ReportTemplateDTO> createReportTemplate(@RequestBody ReportTemplateDTO reportTemplateDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReportTemplate : {}", reportTemplateDTO);
        if (reportTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new reportTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReportTemplateDTO result = reportTemplateService.save(reportTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/report-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /report-templates/:id} : Updates an existing reportTemplate.
     *
     * @param id the id of the reportTemplateDTO to save.
     * @param reportTemplateDTO the reportTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the reportTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reportTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/report-templates/{id}")
    public ResponseEntity<ReportTemplateDTO> updateReportTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportTemplateDTO reportTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReportTemplate : {}, {}", id, reportTemplateDTO);
        if (reportTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReportTemplateDTO result = reportTemplateService.update(reportTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /report-templates/:id} : Partial updates given fields of an existing reportTemplate, field will ignore if it is null
     *
     * @param id the id of the reportTemplateDTO to save.
     * @param reportTemplateDTO the reportTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reportTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the reportTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reportTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reportTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/report-templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReportTemplateDTO> partialUpdateReportTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportTemplateDTO reportTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReportTemplate partially : {}, {}", id, reportTemplateDTO);
        if (reportTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reportTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reportTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReportTemplateDTO> result = reportTemplateService.partialUpdate(reportTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reportTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /report-templates} : get all the reportTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reportTemplates in body.
     */
    @GetMapping("/report-templates")
    public List<ReportTemplateDTO> getAllReportTemplates() {
        log.debug("REST request to get all ReportTemplates");
        return reportTemplateService.findAll();
    }

    /**
     * {@code GET  /report-templates/:id} : get the "id" reportTemplate.
     *
     * @param id the id of the reportTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reportTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/report-templates/{id}")
    public ResponseEntity<ReportTemplateDTO> getReportTemplate(@PathVariable Long id) {
        log.debug("REST request to get ReportTemplate : {}", id);
        Optional<ReportTemplateDTO> reportTemplateDTO = reportTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reportTemplateDTO);
    }

    /**
     * {@code DELETE  /report-templates/:id} : delete the "id" reportTemplate.
     *
     * @param id the id of the reportTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/report-templates/{id}")
    public ResponseEntity<Void> deleteReportTemplate(@PathVariable Long id) {
        log.debug("REST request to delete ReportTemplate : {}", id);
        reportTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
