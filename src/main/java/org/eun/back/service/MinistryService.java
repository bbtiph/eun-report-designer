package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.MinistryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.Ministry}.
 */
public interface MinistryService {
    /**
     * Save a ministry.
     *
     * @param ministryDTO the entity to save.
     * @return the persisted entity.
     */
    MinistryDTO save(MinistryDTO ministryDTO);

    /**
     * Updates a ministry.
     *
     * @param ministryDTO the entity to update.
     * @return the persisted entity.
     */
    MinistryDTO update(MinistryDTO ministryDTO);

    /**
     * Partially updates a ministry.
     *
     * @param ministryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MinistryDTO> partialUpdate(MinistryDTO ministryDTO);

    /**
     * Get all the ministries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MinistryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ministry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MinistryDTO> findOne(Long id);

    /**
     * Delete the "id" ministry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
