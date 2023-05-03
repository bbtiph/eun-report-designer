package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.repository.OperationalBodyMemberRepository;
import org.eun.back.service.OperationalBodyMemberService;
import org.eun.back.service.dto.OperationalBodyMemberDTO;
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
 * REST controller for managing {@link org.eun.back.domain.OperationalBodyMember}.
 */
@RestController
@RequestMapping("/api")
public class OperationalBodyMemberResource {

    private final Logger log = LoggerFactory.getLogger(OperationalBodyMemberResource.class);

    private static final String ENTITY_NAME = "operationalBodyMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationalBodyMemberService operationalBodyMemberService;

    private final OperationalBodyMemberRepository operationalBodyMemberRepository;

    public OperationalBodyMemberResource(
        OperationalBodyMemberService operationalBodyMemberService,
        OperationalBodyMemberRepository operationalBodyMemberRepository
    ) {
        this.operationalBodyMemberService = operationalBodyMemberService;
        this.operationalBodyMemberRepository = operationalBodyMemberRepository;
    }

    /**
     * {@code POST  /operational-body-members} : Create a new operationalBodyMember.
     *
     * @param operationalBodyMemberDTO the operationalBodyMemberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationalBodyMemberDTO, or with status {@code 400 (Bad Request)} if the operationalBodyMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operational-body-members")
    public ResponseEntity<OperationalBodyMemberDTO> createOperationalBodyMember(
        @RequestBody OperationalBodyMemberDTO operationalBodyMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OperationalBodyMember : {}", operationalBodyMemberDTO);
        if (operationalBodyMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new operationalBodyMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationalBodyMemberDTO result = operationalBodyMemberService.save(operationalBodyMemberDTO);
        return ResponseEntity
            .created(new URI("/api/operational-body-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operational-body-members/:id} : Updates an existing operationalBodyMember.
     *
     * @param id the id of the operationalBodyMemberDTO to save.
     * @param operationalBodyMemberDTO the operationalBodyMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationalBodyMemberDTO,
     * or with status {@code 400 (Bad Request)} if the operationalBodyMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationalBodyMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operational-body-members/{id}")
    public ResponseEntity<OperationalBodyMemberDTO> updateOperationalBodyMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationalBodyMemberDTO operationalBodyMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OperationalBodyMember : {}, {}", id, operationalBodyMemberDTO);
        if (operationalBodyMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationalBodyMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationalBodyMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperationalBodyMemberDTO result = operationalBodyMemberService.update(operationalBodyMemberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operationalBodyMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /operational-body-members/:id} : Partial updates given fields of an existing operationalBodyMember, field will ignore if it is null
     *
     * @param id the id of the operationalBodyMemberDTO to save.
     * @param operationalBodyMemberDTO the operationalBodyMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationalBodyMemberDTO,
     * or with status {@code 400 (Bad Request)} if the operationalBodyMemberDTO is not valid,
     * or with status {@code 404 (Not Found)} if the operationalBodyMemberDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the operationalBodyMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operational-body-members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OperationalBodyMemberDTO> partialUpdateOperationalBodyMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationalBodyMemberDTO operationalBodyMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperationalBodyMember partially : {}, {}", id, operationalBodyMemberDTO);
        if (operationalBodyMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationalBodyMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationalBodyMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperationalBodyMemberDTO> result = operationalBodyMemberService.partialUpdate(operationalBodyMemberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, operationalBodyMemberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /operational-body-members} : get all the operationalBodyMembers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationalBodyMembers in body.
     */
    @GetMapping("/operational-body-members")
    public ResponseEntity<List<OperationalBodyMemberDTO>> getAllOperationalBodyMembers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OperationalBodyMembers");
        Page<OperationalBodyMemberDTO> page = operationalBodyMemberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /operational-body-members/:id} : get the "id" operationalBodyMember.
     *
     * @param id the id of the operationalBodyMemberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationalBodyMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operational-body-members/{id}")
    public ResponseEntity<OperationalBodyMemberDTO> getOperationalBodyMember(@PathVariable Long id) {
        log.debug("REST request to get OperationalBodyMember : {}", id);
        Optional<OperationalBodyMemberDTO> operationalBodyMemberDTO = operationalBodyMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operationalBodyMemberDTO);
    }

    /**
     * {@code DELETE  /operational-body-members/:id} : delete the "id" operationalBodyMember.
     *
     * @param id the id of the operationalBodyMemberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operational-body-members/{id}")
    public ResponseEntity<Void> deleteOperationalBodyMember(@PathVariable Long id) {
        log.debug("REST request to delete OperationalBodyMember : {}", id);
        operationalBodyMemberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
