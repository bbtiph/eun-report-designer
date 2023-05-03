package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.FundingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.Funding}.
 */
public interface FundingService {
    /**
     * Save a funding.
     *
     * @param fundingDTO the entity to save.
     * @return the persisted entity.
     */
    FundingDTO save(FundingDTO fundingDTO);

    /**
     * Updates a funding.
     *
     * @param fundingDTO the entity to update.
     * @return the persisted entity.
     */
    FundingDTO update(FundingDTO fundingDTO);

    /**
     * Partially updates a funding.
     *
     * @param fundingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FundingDTO> partialUpdate(FundingDTO fundingDTO);

    /**
     * Get all the fundings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FundingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" funding.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FundingDTO> findOne(Long id);

    /**
     * Delete the "id" funding.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
