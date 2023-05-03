package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.EunTeamMemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.EunTeamMember}.
 */
public interface EunTeamMemberService {
    /**
     * Save a eunTeamMember.
     *
     * @param eunTeamMemberDTO the entity to save.
     * @return the persisted entity.
     */
    EunTeamMemberDTO save(EunTeamMemberDTO eunTeamMemberDTO);

    /**
     * Updates a eunTeamMember.
     *
     * @param eunTeamMemberDTO the entity to update.
     * @return the persisted entity.
     */
    EunTeamMemberDTO update(EunTeamMemberDTO eunTeamMemberDTO);

    /**
     * Partially updates a eunTeamMember.
     *
     * @param eunTeamMemberDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EunTeamMemberDTO> partialUpdate(EunTeamMemberDTO eunTeamMemberDTO);

    /**
     * Get all the eunTeamMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EunTeamMemberDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eunTeamMember.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EunTeamMemberDTO> findOne(Long id);

    /**
     * Delete the "id" eunTeamMember.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
