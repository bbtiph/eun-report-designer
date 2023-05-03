package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.PersonInOrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.PersonInOrganization}.
 */
public interface PersonInOrganizationService {
    /**
     * Save a personInOrganization.
     *
     * @param personInOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    PersonInOrganizationDTO save(PersonInOrganizationDTO personInOrganizationDTO);

    /**
     * Updates a personInOrganization.
     *
     * @param personInOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    PersonInOrganizationDTO update(PersonInOrganizationDTO personInOrganizationDTO);

    /**
     * Partially updates a personInOrganization.
     *
     * @param personInOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonInOrganizationDTO> partialUpdate(PersonInOrganizationDTO personInOrganizationDTO);

    /**
     * Get all the personInOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonInOrganizationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" personInOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonInOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" personInOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
