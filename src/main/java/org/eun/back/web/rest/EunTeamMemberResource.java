package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.EunTeamMemberRepository;
import org.eun.back.service.EunTeamMemberService;
import org.eun.back.service.dto.EunTeamMemberDTO;
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
 * REST controller for managing {@link org.eun.back.domain.EunTeamMember}.
 */
@RestController
@RequestMapping("/api")
public class EunTeamMemberResource {

    private final Logger log = LoggerFactory.getLogger(EunTeamMemberResource.class);

    private static final String ENTITY_NAME = "eunTeamMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EunTeamMemberService eunTeamMemberService;

    private final EunTeamMemberRepository eunTeamMemberRepository;

    public EunTeamMemberResource(EunTeamMemberService eunTeamMemberService, EunTeamMemberRepository eunTeamMemberRepository) {
        this.eunTeamMemberService = eunTeamMemberService;
        this.eunTeamMemberRepository = eunTeamMemberRepository;
    }

    /**
     * {@code POST  /eun-team-members} : Create a new eunTeamMember.
     *
     * @param eunTeamMemberDTO the eunTeamMemberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eunTeamMemberDTO, or with status {@code 400 (Bad Request)} if the eunTeamMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eun-team-members")
    public ResponseEntity<EunTeamMemberDTO> createEunTeamMember(@RequestBody EunTeamMemberDTO eunTeamMemberDTO) throws URISyntaxException {
        log.debug("REST request to save EunTeamMember : {}", eunTeamMemberDTO);
        if (eunTeamMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new eunTeamMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EunTeamMemberDTO result = eunTeamMemberService.save(eunTeamMemberDTO);
        return ResponseEntity
            .created(new URI("/api/eun-team-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eun-team-members/:id} : Updates an existing eunTeamMember.
     *
     * @param id the id of the eunTeamMemberDTO to save.
     * @param eunTeamMemberDTO the eunTeamMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eunTeamMemberDTO,
     * or with status {@code 400 (Bad Request)} if the eunTeamMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eunTeamMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eun-team-members/{id}")
    public ResponseEntity<EunTeamMemberDTO> updateEunTeamMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EunTeamMemberDTO eunTeamMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EunTeamMember : {}, {}", id, eunTeamMemberDTO);
        if (eunTeamMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eunTeamMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eunTeamMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EunTeamMemberDTO result = eunTeamMemberService.update(eunTeamMemberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eunTeamMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eun-team-members/:id} : Partial updates given fields of an existing eunTeamMember, field will ignore if it is null
     *
     * @param id the id of the eunTeamMemberDTO to save.
     * @param eunTeamMemberDTO the eunTeamMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eunTeamMemberDTO,
     * or with status {@code 400 (Bad Request)} if the eunTeamMemberDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eunTeamMemberDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eunTeamMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eun-team-members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EunTeamMemberDTO> partialUpdateEunTeamMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EunTeamMemberDTO eunTeamMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EunTeamMember partially : {}, {}", id, eunTeamMemberDTO);
        if (eunTeamMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eunTeamMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eunTeamMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EunTeamMemberDTO> result = eunTeamMemberService.partialUpdate(eunTeamMemberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eunTeamMemberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /eun-team-members} : get all the eunTeamMembers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eunTeamMembers in body.
     */
    @GetMapping("/eun-team-members")
    public ResponseEntity<List<EunTeamMemberDTO>> getAllEunTeamMembers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EunTeamMembers");
        Page<EunTeamMemberDTO> page = eunTeamMemberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eun-team-members/:id} : get the "id" eunTeamMember.
     *
     * @param id the id of the eunTeamMemberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eunTeamMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eun-team-members/{id}")
    public ResponseEntity<EunTeamMemberDTO> getEunTeamMember(@PathVariable Long id) {
        log.debug("REST request to get EunTeamMember : {}", id);
        Optional<EunTeamMemberDTO> eunTeamMemberDTO = eunTeamMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eunTeamMemberDTO);
    }

    /**
     * {@code DELETE  /eun-team-members/:id} : delete the "id" eunTeamMember.
     *
     * @param id the id of the eunTeamMemberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eun-team-members/{id}")
    public ResponseEntity<Void> deleteEunTeamMember(@PathVariable Long id) {
        log.debug("REST request to delete EunTeamMember : {}", id);
        eunTeamMemberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
