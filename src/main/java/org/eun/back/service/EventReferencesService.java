package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.EventReferencesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * Get all the eventReferences with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventReferencesDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" eventReferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventReferencesDTO> findOne(Long id);

    Optional<EventReferencesDTO> findOneByCountryId(Long id, Long countryId);

    /**
     * Delete the "id" eventReferences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
