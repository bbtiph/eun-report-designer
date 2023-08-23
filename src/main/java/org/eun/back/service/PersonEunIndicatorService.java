package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.PersonEunIndicatorDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.PersonEunIndicator}.
 */
public interface PersonEunIndicatorService {
    /**
     * Save a personEunIndicator.
     *
     * @param personEunIndicatorDTO the entity to save.
     * @return the persisted entity.
     */
    PersonEunIndicatorDTO save(PersonEunIndicatorDTO personEunIndicatorDTO);

    /**
     * Updates a personEunIndicator.
     *
     * @param personEunIndicatorDTO the entity to update.
     * @return the persisted entity.
     */
    PersonEunIndicatorDTO update(PersonEunIndicatorDTO personEunIndicatorDTO);

    /**
     * Partially updates a personEunIndicator.
     *
     * @param personEunIndicatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonEunIndicatorDTO> partialUpdate(PersonEunIndicatorDTO personEunIndicatorDTO);

    /**
     * Get all the personEunIndicators.
     *
     * @return the list of entities.
     */
    List<PersonEunIndicatorDTO> findAll();

    /**
     * Get the "id" personEunIndicator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonEunIndicatorDTO> findOne(Long id);

    /**
     * Delete the "id" personEunIndicator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Indicator<?> getIndicator(Long countryId, Long reportId);
}
