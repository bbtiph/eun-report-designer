package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.ReportBlocks}.
 */
public interface ReportBlocksService {
    /**
     * Save a reportBlocks.
     *
     * @param reportBlocksDTO the entity to save.
     * @return the persisted entity.
     */
    ReportBlocksDTO save(ReportBlocksDTO reportBlocksDTO);

    /**
     * Updates a reportBlocks.
     *
     * @param reportBlocksDTO the entity to update.
     * @return the persisted entity.
     */
    ReportBlocksDTO update(ReportBlocksDTO reportBlocksDTO);

    /**
     * Partially updates a reportBlocks.
     *
     * @param reportBlocksDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReportBlocksDTO> partialUpdate(ReportBlocksDTO reportBlocksDTO);

    /**
     * Get all the reportBlocks.
     *
     * @return the list of entities.
     */
    List<ReportBlocksDTO> findAll();

    /**
     * Get all the reportBlocks with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReportBlocksDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" reportBlocks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReportBlocksDTO> findOne(Long id);

    Optional<ReportBlocksDTO> findOneWithCountry(Long id, Long countryId);

    /**
     * Delete the "id" reportBlocks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
