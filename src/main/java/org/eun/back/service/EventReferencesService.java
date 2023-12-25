package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.EventReferencesDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.EventReferences}.
 */
public interface EventReferencesService {
    /**
     * Save a eventReferences.
     *
     * @param eventReferencesDTO the entity to save.
     * @return the persisted entity.
     */
    EventReferencesDTO save(EventReferencesDTO eventReferencesDTO);

    /**
     * Updates a eventReferences.
     *
     * @param eventReferencesDTO the entity to update.
     * @return the persisted entity.
     */
    EventReferencesDTO update(EventReferencesDTO eventReferencesDTO);

    /**
     * Partially updates a eventReferences.
     *
     * @param eventReferencesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventReferencesDTO> partialUpdate(EventReferencesDTO eventReferencesDTO);

    /**
     * Get all the eventReferences.
     *
     * @return the list of entities.
     */
    List<EventReferencesDTO> findAll();

    /**
     * Get the "id" eventReferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventReferencesDTO> findOne(Long id);

    /**
     * Delete the "id" eventReferences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
