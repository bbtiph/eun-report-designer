package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.CountriesRepository;
import org.eun.back.service.CountriesService;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.Countries}.
 */
@RestController
@RequestMapping("/api")
public class CountriesResource {

    private final Logger log = LoggerFactory.getLogger(CountriesResource.class);

    private static final String ENTITY_NAME = "countries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountriesService countriesService;

    private final CountriesRepository countriesRepository;

    public CountriesResource(CountriesService countriesService, CountriesRepository countriesRepository) {
        this.countriesService = countriesService;
        this.countriesRepository = countriesRepository;
    }

    /**
     * {@code POST  /countries} : Create a new countries.
     *
     * @param countriesDTO the countriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new countriesDTO, or with status {@code 400 (Bad Request)} if the countries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/countries")
    public ResponseEntity<CountriesDTO> createCountries(@RequestBody CountriesDTO countriesDTO) throws URISyntaxException {
        log.debug("REST request to save Countries : {}", countriesDTO);
        if (countriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new countries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CountriesDTO result = countriesService.save(countriesDTO);
        return ResponseEntity
            .created(new URI("/api/countries/" + result.getId()))
            .headers(
                HeaderUtil.createAlert(
                    applicationName,
                    "The country \"" + result.getCountryName() + "\" has been added.",
                    result.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PUT  /countries/:id} : Updates an existing countries.
     *
     * @param id the id of the countriesDTO to save.
     * @param countriesDTO the countriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countriesDTO,
     * or with status {@code 400 (Bad Request)} if the countriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the countriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/countries/{id}")
    public ResponseEntity<CountriesDTO> updateCountries(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CountriesDTO countriesDTO
    ) {
        log.debug("REST request to update Countries : {}, {}", id, countriesDTO);
        if (countriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CountriesDTO result = countriesService.update(countriesDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createAlert(
                    applicationName,
                    "The country \"" + result.getCountryName() + "\" has been updated.",
                    countriesDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /countries/:id} : Partial updates given fields of an existing countries, field will ignore if it is null
     *
     * @param id the id of the countriesDTO to save.
     * @param countriesDTO the countriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated countriesDTO,
     * or with status {@code 400 (Bad Request)} if the countriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the countriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the countriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/countries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CountriesDTO> partialUpdateCountries(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CountriesDTO countriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Countries partially : {}, {}", id, countriesDTO);
        if (countriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, countriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CountriesDTO> result = countriesService.partialUpdate(countriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, countriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /countries} : get all the countries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countries in body.
     */
    @GetMapping("/countries")
    public List<CountriesDTO> getAllCountries() {
        log.debug("REST request to get all Countries");
        return countriesService.findAll();
    }

    @GetMapping("/countries/by-report//{reportId}")
    public List<CountriesDTO> getAllCountriesByReport(@PathVariable Long reportId) {
        log.debug("REST request to get all Countries by report");
        if (reportId == 0) {
            return this.getAllCountries();
        }
        return countriesService.findAllByReport(reportId);
    }

    /**
     * {@code GET  /countries/:id} : get the "id" countries.
     *
     * @param id the id of the countriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the countriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/countries/{id}")
    public ResponseEntity<CountriesDTO> getCountries(@PathVariable Long id) {
        log.debug("REST request to get Countries : {}", id);
        Optional<CountriesDTO> countriesDTO = countriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(countriesDTO);
    }

    /**
     * {@code DELETE  /countries/:id} : delete the "id" countries.
     *
     * @param id the id of the countriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteCountries(@PathVariable Long id) {
        log.debug("REST request to delete Countries : {}", id);
        countriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
