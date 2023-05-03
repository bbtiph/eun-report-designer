package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.EunTeamRepository;
import org.eun.back.service.EunTeamService;
import org.eun.back.service.dto.EunTeamDTO;
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
 * REST controller for managing {@link org.eun.back.domain.EunTeam}.
 */
@RestController
@RequestMapping("/api")
public class EunTeamResource {

    private final Logger log = LoggerFactory.getLogger(EunTeamResource.class);

    private static final String ENTITY_NAME = "eunTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EunTeamService eunTeamService;

    private final EunTeamRepository eunTeamRepository;

    public EunTeamResource(EunTeamService eunTeamService, EunTeamRepository eunTeamRepository) {
        this.eunTeamService = eunTeamService;
        this.eunTeamRepository = eunTeamRepository;
    }

    /**
     * {@code POST  /eun-teams} : Create a new eunTeam.
     *
     * @param eunTeamDTO the eunTeamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eunTeamDTO, or with status {@code 400 (Bad Request)} if the eunTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eun-teams")
    public ResponseEntity<EunTeamDTO> createEunTeam(@RequestBody EunTeamDTO eunTeamDTO) throws URISyntaxException {
        log.debug("REST request to save EunTeam : {}", eunTeamDTO);
        if (eunTeamDTO.getId() != null) {
            throw new BadRequestAlertException("A new eunTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EunTeamDTO result = eunTeamService.save(eunTeamDTO);
        return ResponseEntity
            .created(new URI("/api/eun-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eun-teams/:id} : Updates an existing eunTeam.
     *
     * @param id the id of the eunTeamDTO to save.
     * @param eunTeamDTO the eunTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eunTeamDTO,
     * or with status {@code 400 (Bad Request)} if the eunTeamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eunTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eun-teams/{id}")
    public ResponseEntity<EunTeamDTO> updateEunTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EunTeamDTO eunTeamDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EunTeam : {}, {}", id, eunTeamDTO);
        if (eunTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eunTeamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eunTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EunTeamDTO result = eunTeamService.update(eunTeamDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eunTeamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eun-teams/:id} : Partial updates given fields of an existing eunTeam, field will ignore if it is null
     *
     * @param id the id of the eunTeamDTO to save.
     * @param eunTeamDTO the eunTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eunTeamDTO,
     * or with status {@code 400 (Bad Request)} if the eunTeamDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eunTeamDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eunTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eun-teams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EunTeamDTO> partialUpdateEunTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EunTeamDTO eunTeamDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EunTeam partially : {}, {}", id, eunTeamDTO);
        if (eunTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eunTeamDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eunTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EunTeamDTO> result = eunTeamService.partialUpdate(eunTeamDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eunTeamDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /eun-teams} : get all the eunTeams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eunTeams in body.
     */
    @GetMapping("/eun-teams")
    public ResponseEntity<List<EunTeamDTO>> getAllEunTeams(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EunTeams");
        Page<EunTeamDTO> page = eunTeamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eun-teams/:id} : get the "id" eunTeam.
     *
     * @param id the id of the eunTeamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eunTeamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eun-teams/{id}")
    public ResponseEntity<EunTeamDTO> getEunTeam(@PathVariable Long id) {
        log.debug("REST request to get EunTeam : {}", id);
        Optional<EunTeamDTO> eunTeamDTO = eunTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eunTeamDTO);
    }

    /**
     * {@code DELETE  /eun-teams/:id} : delete the "id" eunTeam.
     *
     * @param id the id of the eunTeamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eun-teams/{id}")
    public ResponseEntity<Void> deleteEunTeam(@PathVariable Long id) {
        log.debug("REST request to delete EunTeam : {}", id);
        eunTeamService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
