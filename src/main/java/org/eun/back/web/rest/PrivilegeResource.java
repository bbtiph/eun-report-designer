package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eun.back.domain.Privilege;
import org.eun.back.repository.PrivilegeRepository;
import org.eun.back.service.PrivilegeService;
import org.eun.back.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.Privilege}.
 */
@RestController
@RequestMapping("/api")
public class PrivilegeResource {

    private final Logger log = LoggerFactory.getLogger(PrivilegeResource.class);

    private static final String ENTITY_NAME = "privilege";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrivilegeService privilegeService;

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeResource(PrivilegeService privilegeService, PrivilegeRepository privilegeRepository) {
        this.privilegeService = privilegeService;
        this.privilegeRepository = privilegeRepository;
    }

    /**
     * {@code POST  /privileges} : Create a new privilege.
     *
     * @param privilege the privilege to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new privilege, or with status {@code 400 (Bad Request)} if the privilege has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/privileges")
    public ResponseEntity<Privilege> createPrivilege(@RequestBody Privilege privilege) throws URISyntaxException {
        log.debug("REST request to save Privilege : {}", privilege);
        if (privilege.getId() != null) {
            throw new BadRequestAlertException("A new privilege cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Privilege result = privilegeService.save(privilege);
        return ResponseEntity
            .created(new URI("/api/privileges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /privileges/:id} : Updates an existing privilege.
     *
     * @param id the id of the privilege to save.
     * @param privilege the privilege to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privilege,
     * or with status {@code 400 (Bad Request)} if the privilege is not valid,
     * or with status {@code 500 (Internal Server Error)} if the privilege couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/privileges/{id}")
    public ResponseEntity<Privilege> updatePrivilege(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Privilege privilege
    ) throws URISyntaxException {
        log.debug("REST request to update Privilege : {}, {}", id, privilege);
        if (privilege.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privilege.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privilegeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Privilege result = privilegeService.update(privilege);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, privilege.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /privileges/:id} : Partial updates given fields of an existing privilege, field will ignore if it is null
     *
     * @param id the id of the privilege to save.
     * @param privilege the privilege to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privilege,
     * or with status {@code 400 (Bad Request)} if the privilege is not valid,
     * or with status {@code 404 (Not Found)} if the privilege is not found,
     * or with status {@code 500 (Internal Server Error)} if the privilege couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/privileges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Privilege> partialUpdatePrivilege(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Privilege privilege
    ) throws URISyntaxException {
        log.debug("REST request to partial update Privilege partially : {}, {}", id, privilege);
        if (privilege.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, privilege.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!privilegeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Privilege> result = privilegeService.partialUpdate(privilege);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, privilege.getId().toString())
        );
    }

    /**
     * {@code GET  /privileges} : get all the privileges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of privileges in body.
     */
    @GetMapping("/privileges")
    public List<Privilege> getAllPrivileges() {
        log.debug("REST request to get all Privileges");
        return privilegeService.findAll();
    }

    /**
     * {@code GET  /privileges/:id} : get the "id" privilege.
     *
     * @param id the id of the privilege to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the privilege, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/privileges/{id}")
    public ResponseEntity<Privilege> getPrivilege(@PathVariable Long id) {
        log.debug("REST request to get Privilege : {}", id);
        Optional<Privilege> privilege = privilegeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(privilege);
    }

    /**
     * {@code DELETE  /privileges/:id} : delete the "id" privilege.
     *
     * @param id the id of the privilege to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/privileges/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        log.debug("REST request to delete Privilege : {}", id);
        privilegeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
