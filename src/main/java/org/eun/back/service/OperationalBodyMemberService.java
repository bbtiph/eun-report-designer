package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.OperationalBodyMemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.OperationalBodyMember}.
 */
public interface OperationalBodyMemberService {
    /**
     * Save a operationalBodyMember.
     *
     * @param operationalBodyMemberDTO the entity to save.
     * @return the persisted entity.
     */
    OperationalBodyMemberDTO save(OperationalBodyMemberDTO operationalBodyMemberDTO);

    /**
     * Updates a operationalBodyMember.
     *
     * @param operationalBodyMemberDTO the entity to update.
     * @return the persisted entity.
     */
    OperationalBodyMemberDTO update(OperationalBodyMemberDTO operationalBodyMemberDTO);

    /**
     * Partially updates a operationalBodyMember.
     *
     * @param operationalBodyMemberDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OperationalBodyMemberDTO> partialUpdate(OperationalBodyMemberDTO operationalBodyMemberDTO);

    /**
     * Get all the operationalBodyMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OperationalBodyMemberDTO> findAll(Pageable pageable);

    /**
     * Get the "id" operationalBodyMember.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OperationalBodyMemberDTO> findOne(Long id);

    /**
     * Delete the "id" operationalBodyMember.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
