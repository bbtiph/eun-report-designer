package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.EventInOrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.EventInOrganization}.
 */
public interface EventInOrganizationService {
    /**
     * Save a eventInOrganization.
     *
     * @param eventInOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    EventInOrganizationDTO save(EventInOrganizationDTO eventInOrganizationDTO);

    /**
     * Updates a eventInOrganization.
     *
     * @param eventInOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    EventInOrganizationDTO update(EventInOrganizationDTO eventInOrganizationDTO);

    /**
     * Partially updates a eventInOrganization.
     *
     * @param eventInOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventInOrganizationDTO> partialUpdate(EventInOrganizationDTO eventInOrganizationDTO);

    /**
     * Get all the eventInOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventInOrganizationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventInOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventInOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" eventInOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
