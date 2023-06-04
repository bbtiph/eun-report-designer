package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.ReportBlocksContentDataDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.ReportBlocksContentData}.
 */
public interface ReportBlocksContentDataService {
    /**
     * Save a reportBlocksContentData.
     *
     * @param reportBlocksContentDataDTO the entity to save.
     * @return the persisted entity.
     */
    ReportBlocksContentDataDTO save(ReportBlocksContentDataDTO reportBlocksContentDataDTO);

    /**
     * Updates a reportBlocksContentData.
     *
     * @param reportBlocksContentDataDTO the entity to update.
     * @return the persisted entity.
     */
    ReportBlocksContentDataDTO update(ReportBlocksContentDataDTO reportBlocksContentDataDTO);

    /**
     * Partially updates a reportBlocksContentData.
     *
     * @param reportBlocksContentDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReportBlocksContentDataDTO> partialUpdate(ReportBlocksContentDataDTO reportBlocksContentDataDTO);

    /**
     * Get all the reportBlocksContentData.
     *
     * @return the list of entities.
     */
    List<ReportBlocksContentDataDTO> findAll();

    /**
     * Get the "id" reportBlocksContentData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReportBlocksContentDataDTO> findOne(Long id);

    /**
     * Delete the "id" reportBlocksContentData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
