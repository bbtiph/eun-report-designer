package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.EventReferencesParticipantsCategoryDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.EventReferencesParticipantsCategory}.
 */
public interface EventReferencesParticipantsCategoryService {
    /**
     * Save a eventReferencesParticipantsCategory.
     *
     * @param eventReferencesParticipantsCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    EventReferencesParticipantsCategoryDTO save(EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO);

    /**
     * Updates a eventReferencesParticipantsCategory.
     *
     * @param eventReferencesParticipantsCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    EventReferencesParticipantsCategoryDTO update(EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO);

    /**
     * Partially updates a eventReferencesParticipantsCategory.
     *
     * @param eventReferencesParticipantsCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventReferencesParticipantsCategoryDTO> partialUpdate(
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO
    );

    /**
     * Get all the eventReferencesParticipantsCategories.
     *
     * @return the list of entities.
     */
    List<EventReferencesParticipantsCategoryDTO> findAll();

    /**
     * Get the "id" eventReferencesParticipantsCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventReferencesParticipantsCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" eventReferencesParticipantsCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
