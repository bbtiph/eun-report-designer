package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ReportBlocksRepository;
import org.eun.back.service.CountriesService;
import org.eun.back.service.ReportBlocksService;
import org.eun.back.service.dto.CountriesDTO;
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

    private final CountriesService countriesService;

    public ReportBlocksResource(
        ReportBlocksService reportBlocksService,
        ReportBlocksRepository reportBlocksRepository,
        CountriesService countriesService
    ) {
        this.reportBlocksService = reportBlocksService;
        this.reportBlocksRepository = reportBlocksRepository;
        this.countriesService = countriesService;
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
            .headers(
                HeaderUtil.createAlert(
                    applicationName,
                    "The report block  <b [style.font-weight]=\"'bold'\">\"" + result.getName() + "\"</b> has been created.",
                    result.getId().toString()
                )
            )
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
    @PutMapping("/report-blocks/{id}/{type}")
    public ResponseEntity<ReportBlocksDTO> updateReportBlocks(
        @RequestParam(value = "reportId", required = false) Long reportId,
        @RequestParam(value = "countryId", required = false) Long countryId,
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReportBlocksDTO reportBlocksDTO,
        @PathVariable String type
    ) {
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

        ReportBlocksDTO result = reportBlocksService.update(reportBlocksDTO, type, reportId);
        CountriesDTO countryDTO = countryId != null ? countriesService.findOne(countryId).get() : new CountriesDTO();
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createAlert(
                    applicationName,
                    type.equals("content") && countryDTO != null
                        ? "The information for <b [style.font-weight]=\"'bold'\">\"" +
                        countryDTO.getCountryName() +
                        "\" </b>has been updated."
                        : "The report block <b [style.font-weight]=\"'bold'\">\"" + result.getName() + "\"</b> has been updated.",
                    reportBlocksDTO.getId().toString()
                )
            )
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
            HeaderUtil.createAlert(
                applicationName,
                "The report block  <b [style.font-weight]=\"'bold'\">\"" + result.get().getName() + "\"</b> has been updated.",
                reportBlocksDTO.getId().toString()
            )
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
        return reportBlocksService.findAllByReportAndCountry(reportId, countryId);
    }

    @GetMapping("/report-blocks/external/{reportId}/{countryId}")
    public List<ReportBlocksDTO> getAllReportBlocksByReportForExternalServices(@PathVariable Long reportId, @PathVariable Long countryId) {
        log.debug("REST request to get all ReportBlocks");
        return reportBlocksService.findAllByReportAndCountryForExternalServices(reportId, countryId);
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
        ReportBlocksDTO result = reportBlocksService.findOne(id).get();
        reportBlocksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(
                HeaderUtil.createAlert(
                    applicationName,
                    "The report block  <b [style.font-weight]=\"'bold'\">\"" + result.getName() + "\"</b> has been deleted.",
                    id.toString()
                )
            )
            .build();
    }
}
