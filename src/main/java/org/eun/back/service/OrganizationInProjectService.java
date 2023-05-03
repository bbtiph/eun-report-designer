package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.OrganizationInProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.OrganizationInProject}.
 */
public interface OrganizationInProjectService {
    /**
     * Save a organizationInProject.
     *
     * @param organizationInProjectDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationInProjectDTO save(OrganizationInProjectDTO organizationInProjectDTO);

    /**
     * Updates a organizationInProject.
     *
     * @param organizationInProjectDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationInProjectDTO update(OrganizationInProjectDTO organizationInProjectDTO);

    /**
     * Partially updates a organizationInProject.
     *
     * @param organizationInProjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationInProjectDTO> partialUpdate(OrganizationInProjectDTO organizationInProjectDTO);

    /**
     * Get all the organizationInProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationInProjectDTO> findAll(Pageable pageable);

    /**
     * Get the "id" organizationInProject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationInProjectDTO> findOne(Long id);

    /**
     * Delete the "id" organizationInProject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
