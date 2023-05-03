package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.EunTeamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.EunTeam}.
 */
public interface EunTeamService {
    /**
     * Save a eunTeam.
     *
     * @param eunTeamDTO the entity to save.
     * @return the persisted entity.
     */
    EunTeamDTO save(EunTeamDTO eunTeamDTO);

    /**
     * Updates a eunTeam.
     *
     * @param eunTeamDTO the entity to update.
     * @return the persisted entity.
     */
    EunTeamDTO update(EunTeamDTO eunTeamDTO);

    /**
     * Partially updates a eunTeam.
     *
     * @param eunTeamDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EunTeamDTO> partialUpdate(EunTeamDTO eunTeamDTO);

    /**
     * Get all the eunTeams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EunTeamDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eunTeam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EunTeamDTO> findOne(Long id);

    /**
     * Delete the "id" eunTeam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
