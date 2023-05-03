package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.OperationalBodyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.OperationalBody}.
 */
public interface OperationalBodyService {
    /**
     * Save a operationalBody.
     *
     * @param operationalBodyDTO the entity to save.
     * @return the persisted entity.
     */
    OperationalBodyDTO save(OperationalBodyDTO operationalBodyDTO);

    /**
     * Updates a operationalBody.
     *
     * @param operationalBodyDTO the entity to update.
     * @return the persisted entity.
     */
    OperationalBodyDTO update(OperationalBodyDTO operationalBodyDTO);

    /**
     * Partially updates a operationalBody.
     *
     * @param operationalBodyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperationalBodyDTO> partialUpdate(OperationalBodyDTO operationalBodyDTO);

    /**
     * Get all the operationalBodies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OperationalBodyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" operationalBody.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperationalBodyDTO> findOne(Long id);

    /**
     * Delete the "id" operationalBody.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
