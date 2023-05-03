package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.OrganizationInMinistry}.
 */
public interface OrganizationInMinistryService {
    /**
     * Save a organizationInMinistry.
     *
     * @param organizationInMinistryDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationInMinistryDTO save(OrganizationInMinistryDTO organizationInMinistryDTO);

    /**
     * Updates a organizationInMinistry.
     *
     * @param organizationInMinistryDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationInMinistryDTO update(OrganizationInMinistryDTO organizationInMinistryDTO);

    /**
     * Partially updates a organizationInMinistry.
     *
     * @param organizationInMinistryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationInMinistryDTO> partialUpdate(OrganizationInMinistryDTO organizationInMinistryDTO);

    /**
     * Get all the organizationInMinistries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationInMinistryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" organizationInMinistry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationInMinistryDTO> findOne(Long id);

    /**
     * Delete the "id" organizationInMinistry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
