package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.ProjectPartnerDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.ProjectPartner}.
 */
public interface ProjectPartnerService {
    /**
     * Save a projectPartner.
     *
     * @param projectPartnerDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectPartnerDTO save(ProjectPartnerDTO projectPartnerDTO);

    /**
     * Updates a projectPartner.
     *
     * @param projectPartnerDTO the entity to update.
     * @return the persisted entity.
     */
    ProjectPartnerDTO update(ProjectPartnerDTO projectPartnerDTO);

    /**
     * Partially updates a projectPartner.
     *
     * @param projectPartnerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectPartnerDTO> partialUpdate(ProjectPartnerDTO projectPartnerDTO);

    /**
     * Get all the projectPartners.
     *
     * @return the list of entities.
     */
    List<ProjectPartnerDTO> findAll();

    /**
     * Get the "id" projectPartner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectPartnerDTO> findOne(Long id);

    /**
     * Delete the "id" projectPartner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
