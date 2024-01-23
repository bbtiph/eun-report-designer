package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.JobInfoRepository;
import org.eun.back.service.JobInfoService;
import org.eun.back.service.dto.JobInfoDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.JobInfo}.
 */
@RestController
@RequestMapping("/api")
public class JobInfoResource {

    private final Logger log = LoggerFactory.getLogger(JobInfoResource.class);

    private static final String ENTITY_NAME = "jobInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobInfoService jobInfoService;

    private final JobInfoRepository jobInfoRepository;

    public JobInfoResource(JobInfoService jobInfoService, JobInfoRepository jobInfoRepository) {
        this.jobInfoService = jobInfoService;
        this.jobInfoRepository = jobInfoRepository;
    }

    /**
     * {@code POST  /job-infos} : Create a new jobInfo.
     *
     * @param jobInfoDTO the jobInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobInfoDTO, or with status {@code 400 (Bad Request)} if the jobInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-infos")
    public ResponseEntity<JobInfoDTO> createJobInfo(@RequestBody JobInfoDTO jobInfoDTO) throws URISyntaxException {
        log.debug("REST request to save JobInfo : {}", jobInfoDTO);
        if (jobInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobInfoDTO result = jobInfoService.save(jobInfoDTO);
        return ResponseEntity
            .created(new URI("/api/job-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-infos/:id} : Updates an existing jobInfo.
     *
     * @param id the id of the jobInfoDTO to save.
     * @param jobInfoDTO the jobInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobInfoDTO,
     * or with status {@code 400 (Bad Request)} if the jobInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-infos/{id}")
    public ResponseEntity<JobInfoDTO> updateJobInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobInfoDTO jobInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JobInfo : {}, {}", id, jobInfoDTO);
        if (jobInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobInfoDTO result = jobInfoService.update(jobInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-infos/:id} : Partial updates given fields of an existing jobInfo, field will ignore if it is null
     *
     * @param id the id of the jobInfoDTO to save.
     * @param jobInfoDTO the jobInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobInfoDTO,
     * or with status {@code 400 (Bad Request)} if the jobInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jobInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobInfoDTO> partialUpdateJobInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobInfoDTO jobInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobInfo partially : {}, {}", id, jobInfoDTO);
        if (jobInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobInfoDTO> result = jobInfoService.partialUpdate(jobInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /job-infos} : get all the jobInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobInfos in body.
     */
    @GetMapping("/job-infos")
    public List<JobInfoDTO> getAllJobInfos() {
        log.debug("REST request to get all JobInfos");
        return jobInfoService.findAll();
    }

    /**
     * {@code GET  /job-infos/:id} : get the "id" jobInfo.
     *
     * @param id the id of the jobInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-infos/{id}")
    public ResponseEntity<JobInfoDTO> getJobInfo(@PathVariable Long id) {
        log.debug("REST request to get JobInfo : {}", id);
        Optional<JobInfoDTO> jobInfoDTO = jobInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobInfoDTO);
    }

    /**
     * {@code DELETE  /job-infos/:id} : delete the "id" jobInfo.
     *
     * @param id the id of the jobInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-infos/{id}")
    public ResponseEntity<Void> deleteJobInfo(@PathVariable Long id) {
        log.debug("REST request to delete JobInfo : {}", id);
        jobInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
