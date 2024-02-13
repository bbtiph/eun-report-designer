package org.eun.back.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.eun.back.domain.ParticipantsEunIndicator;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.ParticipantsEunIndicatorDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.ParticipantsEunIndicator}.
 */
public interface ParticipantsEunIndicatorService {
    /**
     * Save a participantsEunIndicator.
     *
     * @param participantsEunIndicatorDTO the entity to save.
     * @return the persisted entity.
     */
    ParticipantsEunIndicatorDTO save(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO);

    ParticipantsEunIndicatorDTO save(ParticipantsEunIndicator participantsEunIndicator);

    /**
     * Updates a participantsEunIndicator.
     *
     * @param participantsEunIndicatorDTO the entity to update.
     * @return the persisted entity.
     */
    ParticipantsEunIndicatorDTO update(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO);

    /**
     * Partially updates a participantsEunIndicator.
     *
     * @param participantsEunIndicatorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParticipantsEunIndicatorDTO> partialUpdate(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO);

    /**
     * Get all the participantsEunIndicators.
     *
     * @return the list of entities.
     */
    List<ParticipantsEunIndicatorDTO> findAll();

    List<ParticipantsEunIndicatorDTO> findAllByCountryId(String countryCode);

    /**
     * Get the "id" participantsEunIndicator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParticipantsEunIndicatorDTO> findOne(Long id);

    /**
     * Delete the "id" participantsEunIndicator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Indicator<?> getIndicator(Map<String, String> params, Long reportId);
}
