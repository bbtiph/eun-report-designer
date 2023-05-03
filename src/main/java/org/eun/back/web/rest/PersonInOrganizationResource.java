package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.PersonInOrganizationRepository;
import org.eun.back.service.PersonInOrganizationService;
import org.eun.back.service.dto.PersonInOrganizationDTO;
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
 * REST controller for managing {@link org.eun.back.domain.PersonInOrganization}.
 */
@RestController
@RequestMapping("/api")
public class PersonInOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(PersonInOrganizationResource.class);

    private static final String ENTITY_NAME = "personInOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonInOrganizationService personInOrganizationService;

    private final PersonInOrganizationRepository personInOrganizationRepository;

    public PersonInOrganizationResource(
        PersonInOrganizationService personInOrganizationService,
        PersonInOrganizationRepository personInOrganizationRepository
    ) {
        this.personInOrganizationService = personInOrganizationService;
        this.personInOrganizationRepository = personInOrganizationRepository;
    }

    /**
     * {@code POST  /person-in-organizations} : Create a new personInOrganization.
     *
     * @param personInOrganizationDTO the personInOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personInOrganizationDTO, or with status {@code 400 (Bad Request)} if the personInOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-in-organizations")
    public ResponseEntity<PersonInOrganizationDTO> createPersonInOrganization(@RequestBody PersonInOrganizationDTO personInOrganizationDTO)
        throws URISyntaxException {
        log.debug("REST request to save PersonInOrganization : {}", personInOrganizationDTO);
        if (personInOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new personInOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonInOrganizationDTO result = personInOrganizationService.save(personInOrganizationDTO);
        return ResponseEntity
            .created(new URI("/api/person-in-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-in-organizations/:id} : Updates an existing personInOrganization.
     *
     * @param id the id of the personInOrganizationDTO to save.
     * @param personInOrganizationDTO the personInOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the personInOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personInOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-in-organizations/{id}")
    public ResponseEntity<PersonInOrganizationDTO> updatePersonInOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonInOrganizationDTO personInOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonInOrganization : {}, {}", id, personInOrganizationDTO);
        if (personInOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personInOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personInOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonInOrganizationDTO result = personInOrganizationService.update(personInOrganizationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personInOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-in-organizations/:id} : Partial updates given fields of an existing personInOrganization, field will ignore if it is null
     *
     * @param id the id of the personInOrganizationDTO to save.
     * @param personInOrganizationDTO the personInOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the personInOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personInOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personInOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-in-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonInOrganizationDTO> partialUpdatePersonInOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonInOrganizationDTO personInOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonInOrganization partially : {}, {}", id, personInOrganizationDTO);
        if (personInOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personInOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personInOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonInOrganizationDTO> result = personInOrganizationService.partialUpdate(personInOrganizationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personInOrganizationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /person-in-organizations} : get all the personInOrganizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personInOrganizations in body.
     */
    @GetMapping("/person-in-organizations")
    public ResponseEntity<List<PersonInOrganizationDTO>> getAllPersonInOrganizations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PersonInOrganizations");
        Page<PersonInOrganizationDTO> page = personInOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-in-organizations/:id} : get the "id" personInOrganization.
     *
     * @param id the id of the personInOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personInOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-in-organizations/{id}")
    public ResponseEntity<PersonInOrganizationDTO> getPersonInOrganization(@PathVariable Long id) {
        log.debug("REST request to get PersonInOrganization : {}", id);
        Optional<PersonInOrganizationDTO> personInOrganizationDTO = personInOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personInOrganizationDTO);
    }

    /**
     * {@code DELETE  /person-in-organizations/:id} : delete the "id" personInOrganization.
     *
     * @param id the id of the personInOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-in-organizations/{id}")
    public ResponseEntity<Void> deletePersonInOrganization(@PathVariable Long id) {
        log.debug("REST request to delete PersonInOrganization : {}", id);
        personInOrganizationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
