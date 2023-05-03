package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.FundingRepository;
import org.eun.back.service.FundingService;
import org.eun.back.service.dto.FundingDTO;
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
 * REST controller for managing {@link org.eun.back.domain.Funding}.
 */
@RestController
@RequestMapping("/api")
public class FundingResource {

    private final Logger log = LoggerFactory.getLogger(FundingResource.class);

    private static final String ENTITY_NAME = "funding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FundingService fundingService;

    private final FundingRepository fundingRepository;

    public FundingResource(FundingService fundingService, FundingRepository fundingRepository) {
        this.fundingService = fundingService;
        this.fundingRepository = fundingRepository;
    }

    /**
     * {@code POST  /fundings} : Create a new funding.
     *
     * @param fundingDTO the fundingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fundingDTO, or with status {@code 400 (Bad Request)} if the funding has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fundings")
    public ResponseEntity<FundingDTO> createFunding(@RequestBody FundingDTO fundingDTO) throws URISyntaxException {
        log.debug("REST request to save Funding : {}", fundingDTO);
        if (fundingDTO.getId() != null) {
            throw new BadRequestAlertException("A new funding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FundingDTO result = fundingService.save(fundingDTO);
        return ResponseEntity
            .created(new URI("/api/fundings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fundings/:id} : Updates an existing funding.
     *
     * @param id the id of the fundingDTO to save.
     * @param fundingDTO the fundingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundingDTO,
     * or with status {@code 400 (Bad Request)} if the fundingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fundingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fundings/{id}")
    public ResponseEntity<FundingDTO> updateFunding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FundingDTO fundingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Funding : {}, {}", id, fundingDTO);
        if (fundingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FundingDTO result = fundingService.update(fundingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fundingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fundings/:id} : Partial updates given fields of an existing funding, field will ignore if it is null
     *
     * @param id the id of the fundingDTO to save.
     * @param fundingDTO the fundingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fundingDTO,
     * or with status {@code 400 (Bad Request)} if the fundingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fundingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fundingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fundings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FundingDTO> partialUpdateFunding(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FundingDTO fundingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Funding partially : {}, {}", id, fundingDTO);
        if (fundingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fundingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fundingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FundingDTO> result = fundingService.partialUpdate(fundingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fundingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fundings} : get all the fundings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fundings in body.
     */
    @GetMapping("/fundings")
    public ResponseEntity<List<FundingDTO>> getAllFundings(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Fundings");
        Page<FundingDTO> page = fundingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fundings/:id} : get the "id" funding.
     *
     * @param id the id of the fundingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fundingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fundings/{id}")
    public ResponseEntity<FundingDTO> getFunding(@PathVariable Long id) {
        log.debug("REST request to get Funding : {}", id);
        Optional<FundingDTO> fundingDTO = fundingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fundingDTO);
    }

    /**
     * {@code DELETE  /fundings/:id} : delete the "id" funding.
     *
     * @param id the id of the fundingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fundings/{id}")
    public ResponseEntity<Void> deleteFunding(@PathVariable Long id) {
        log.debug("REST request to delete Funding : {}", id);
        fundingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
