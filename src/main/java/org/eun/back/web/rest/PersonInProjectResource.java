package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.PersonInProjectRepository;
import org.eun.back.service.PersonInProjectService;
import org.eun.back.service.dto.PersonInProjectDTO;
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
 * REST controller for managing {@link org.eun.back.domain.PersonInProject}.
 */
@RestController
@RequestMapping("/api")
public class PersonInProjectResource {

    private final Logger log = LoggerFactory.getLogger(PersonInProjectResource.class);

    private static final String ENTITY_NAME = "personInProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonInProjectService personInProjectService;

    private final PersonInProjectRepository personInProjectRepository;

    public PersonInProjectResource(PersonInProjectService personInProjectService, PersonInProjectRepository personInProjectRepository) {
        this.personInProjectService = personInProjectService;
        this.personInProjectRepository = personInProjectRepository;
    }

    /**
     * {@code POST  /person-in-projects} : Create a new personInProject.
     *
     * @param personInProjectDTO the personInProjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personInProjectDTO, or with status {@code 400 (Bad Request)} if the personInProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-in-projects")
    public ResponseEntity<PersonInProjectDTO> createPersonInProject(@RequestBody PersonInProjectDTO personInProjectDTO)
        throws URISyntaxException {
        log.debug("REST request to save PersonInProject : {}", personInProjectDTO);
        if (personInProjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new personInProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonInProjectDTO result = personInProjectService.save(personInProjectDTO);
        return ResponseEntity
            .created(new URI("/api/person-in-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-in-projects/:id} : Updates an existing personInProject.
     *
     * @param id the id of the personInProjectDTO to save.
     * @param personInProjectDTO the personInProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInProjectDTO,
     * or with status {@code 400 (Bad Request)} if the personInProjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personInProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-in-projects/{id}")
    public ResponseEntity<PersonInProjectDTO> updatePersonInProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonInProjectDTO personInProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonInProject : {}, {}", id, personInProjectDTO);
        if (personInProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personInProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personInProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonInProjectDTO result = personInProjectService.update(personInProjectDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personInProjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-in-projects/:id} : Partial updates given fields of an existing personInProject, field will ignore if it is null
     *
     * @param id the id of the personInProjectDTO to save.
     * @param personInProjectDTO the personInProjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInProjectDTO,
     * or with status {@code 400 (Bad Request)} if the personInProjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personInProjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personInProjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-in-projects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonInProjectDTO> partialUpdatePersonInProject(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonInProjectDTO personInProjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonInProject partially : {}, {}", id, personInProjectDTO);
        if (personInProjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personInProjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personInProjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonInProjectDTO> result = personInProjectService.partialUpdate(personInProjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personInProjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /person-in-projects} : get all the personInProjects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personInProjects in body.
     */
    @GetMapping("/person-in-projects")
    public ResponseEntity<List<PersonInProjectDTO>> getAllPersonInProjects(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PersonInProjects");
        Page<PersonInProjectDTO> page = personInProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-in-projects/:id} : get the "id" personInProject.
     *
     * @param id the id of the personInProjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personInProjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-in-projects/{id}")
    public ResponseEntity<PersonInProjectDTO> getPersonInProject(@PathVariable Long id) {
        log.debug("REST request to get PersonInProject : {}", id);
        Optional<PersonInProjectDTO> personInProjectDTO = personInProjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personInProjectDTO);
    }

    /**
     * {@code DELETE  /person-in-projects/:id} : delete the "id" personInProject.
     *
     * @param id the id of the personInProjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-in-projects/{id}")
    public ResponseEntity<Void> deletePersonInProject(@PathVariable Long id) {
        log.debug("REST request to delete PersonInProject : {}", id);
        personInProjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
