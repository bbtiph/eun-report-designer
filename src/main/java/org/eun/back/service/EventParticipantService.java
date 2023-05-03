package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.EventParticipantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.EventParticipant}.
 */
public interface EventParticipantService {
    /**
     * Save a eventParticipant.
     *
     * @param eventParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    EventParticipantDTO save(EventParticipantDTO eventParticipantDTO);

    /**
     * Updates a eventParticipant.
     *
     * @param eventParticipantDTO the entity to update.
     * @return the persisted entity.
     */
    EventParticipantDTO update(EventParticipantDTO eventParticipantDTO);

    /**
     * Partially updates a eventParticipant.
     *
     * @param eventParticipantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventParticipantDTO> partialUpdate(EventParticipantDTO eventParticipantDTO);

    /**
     * Get all the eventParticipants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventParticipantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventParticipant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventParticipantDTO> findOne(Long id);

    /**
     * Delete the "id" eventParticipant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
