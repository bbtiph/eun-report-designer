package org.eun.back.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.ReferenceTableSettingsRepository;
import org.eun.back.service.ReferenceTableSettingsService;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.ReferenceTableSettings}.
 */
@RestController
@RequestMapping("/api")
public class ReferenceTableSettingsResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceTableSettingsResource.class);

    private static final String ENTITY_NAME = "referenceTableSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferenceTableSettingsService referenceTableSettingsService;

    private final ReferenceTableSettingsRepository referenceTableSettingsRepository;

    public ReferenceTableSettingsResource(
        ReferenceTableSettingsService referenceTableSettingsService,
        ReferenceTableSettingsRepository referenceTableSettingsRepository
    ) {
        this.referenceTableSettingsService = referenceTableSettingsService;
        this.referenceTableSettingsRepository = referenceTableSettingsRepository;
    }

    /**
     * {@code POST  /reference-table-settings} : Create a new referenceTableSettings.
     *
     * @param referenceTableSettingsDTO the referenceTableSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referenceTableSettingsDTO, or with status {@code 400 (Bad Request)} if the referenceTableSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reference-table-settings")
    public ResponseEntity<ReferenceTableSettingsDTO> createReferenceTableSettings(
        @RequestBody ReferenceTableSettingsDTO referenceTableSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ReferenceTableSettings : {}", referenceTableSettingsDTO);
        if (referenceTableSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new referenceTableSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferenceTableSettingsDTO result = referenceTableSettingsService.save(referenceTableSettingsDTO);
        return ResponseEntity
            .created(new URI("/api/reference-table-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reference-table-settings/:id} : Updates an existing referenceTableSettings.
     *
     * @param id the id of the referenceTableSettingsDTO to save.
     * @param referenceTableSettingsDTO the referenceTableSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referenceTableSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the referenceTableSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referenceTableSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reference-table-settings/{id}")
    public ResponseEntity<ReferenceTableSettingsDTO> updateReferenceTableSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferenceTableSettingsDTO referenceTableSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReferenceTableSettings : {}, {}", id, referenceTableSettingsDTO);
        if (referenceTableSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referenceTableSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referenceTableSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReferenceTableSettingsDTO result = referenceTableSettingsService.update(referenceTableSettingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, referenceTableSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reference-table-settings/:id} : Partial updates given fields of an existing referenceTableSettings, field will ignore if it is null
     *
     * @param id the id of the referenceTableSettingsDTO to save.
     * @param referenceTableSettingsDTO the referenceTableSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referenceTableSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the referenceTableSettingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the referenceTableSettingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the referenceTableSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reference-table-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReferenceTableSettingsDTO> partialUpdateReferenceTableSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferenceTableSettingsDTO referenceTableSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReferenceTableSettings partially : {}, {}", id, referenceTableSettingsDTO);
        if (referenceTableSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referenceTableSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referenceTableSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReferenceTableSettingsDTO> result = referenceTableSettingsService.partialUpdate(referenceTableSettingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, referenceTableSettingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reference-table-settings} : get all the referenceTableSettings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referenceTableSettings in body.
     */
    @GetMapping("/reference-table-settings/by-ref-table/data/{refTable}")
    public List<?> getAllReferenceTableSettingsData(@PathVariable String refTable) {
        log.debug("REST request to get all ReferenceTableSettings");
        return referenceTableSettingsService.findAllDataByRefTable(refTable);
    }

    @PostMapping("/reference-table-settings/by-ref-table/download/{refTable}")
    public ResponseEntity<byte[]> generateExcel(@PathVariable String refTable) throws IOException {
        byte[] generatedExcel = referenceTableSettingsService.download(refTable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=working_group_references.xlsx");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(generatedExcel);
    }

    @PostMapping(value = "/reference-table-settings/by-ref-table/upload/{refTable}")
    public ResponseEntity<Void> handleFileUpload(@PathVariable String refTable, @RequestParam("file") MultipartFile file) {
        try {
            referenceTableSettingsService.upload(file, refTable);
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

    @DeleteMapping("/reference-table-settings/by-ref-table/delete/{refTable}/{id}")
    public ResponseEntity<Void> deleteReferenceRowByRefTableAndId(@PathVariable String refTable, @PathVariable Long id) {
        referenceTableSettingsService.deleteReferenceRowByRefTableAndId(refTable, id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, refTable, id.toString()))
            .build();
    }

    @GetMapping("/reference-table-settings")
    public List<ReferenceTableSettingsDTO> getAllReferenceTableSettings() {
        log.debug("REST request to get all ReferenceTableSettings");
        return referenceTableSettingsService.findAll();
    }

    /**
     * {@code GET  /reference-table-settings/:id} : get the "id" referenceTableSettings.
     *
     * @param id the id of the referenceTableSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referenceTableSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reference-table-settings/{id}")
    public ResponseEntity<ReferenceTableSettingsDTO> getReferenceTableSettings(@PathVariable Long id) {
        log.debug("REST request to get ReferenceTableSettings : {}", id);
        Optional<ReferenceTableSettingsDTO> referenceTableSettingsDTO = referenceTableSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referenceTableSettingsDTO);
    }

    @GetMapping("/reference-table-settings/by-ref-table/{refTable}")
    public ResponseEntity<ReferenceTableSettingsDTO> getReferenceTableSettingsByName(@PathVariable String refTable) {
        log.debug("REST request to get ReferenceTableSettings by name : {}", refTable);
        Optional<ReferenceTableSettingsDTO> referenceTableSettingsDTO = referenceTableSettingsService.findOneByRefTable(refTable);
        return ResponseUtil.wrapOrNotFound(referenceTableSettingsDTO);
    }

    /**
     * {@code DELETE  /reference-table-settings/:id} : delete the "id" referenceTableSettings.
     *
     * @param id the id of the referenceTableSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reference-table-settings/{id}")
    public ResponseEntity<Void> deleteReferenceTableSettings(@PathVariable Long id) {
        log.debug("REST request to delete ReferenceTableSettings : {}", id);
        referenceTableSettingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
