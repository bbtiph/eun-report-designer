package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.ReferenceTableSettings}.
 */
public interface ReferenceTableSettingsService {
    /**
     * Save a referenceTableSettings.
     *
     * @param referenceTableSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    ReferenceTableSettingsDTO save(ReferenceTableSettingsDTO referenceTableSettingsDTO);

    /**
     * Updates a referenceTableSettings.
     *
     * @param referenceTableSettingsDTO the entity to update.
     * @return the persisted entity.
     */
    ReferenceTableSettingsDTO update(ReferenceTableSettingsDTO referenceTableSettingsDTO);

    /**
     * Partially updates a referenceTableSettings.
     *
     * @param referenceTableSettingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReferenceTableSettingsDTO> partialUpdate(ReferenceTableSettingsDTO referenceTableSettingsDTO);

    /**
     * Get all the referenceTableSettings.
     *
     * @return the list of entities.
     */
    List<ReferenceTableSettingsDTO> findAll();

    /**
     * Get the "id" referenceTableSettings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReferenceTableSettingsDTO> findOne(Long id);

    /**
     * Delete the "id" referenceTableSettings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
