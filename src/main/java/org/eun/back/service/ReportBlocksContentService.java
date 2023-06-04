package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.ReportBlocksContentDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.ReportBlocksContent}.
 */
public interface ReportBlocksContentService {
    /**
     * Save a reportBlocksContent.
     *
     * @param reportBlocksContentDTO the entity to save.
     * @return the persisted entity.
     */
    ReportBlocksContentDTO save(ReportBlocksContentDTO reportBlocksContentDTO);

    /**
     * Updates a reportBlocksContent.
     *
     * @param reportBlocksContentDTO the entity to update.
     * @return the persisted entity.
     */
    ReportBlocksContentDTO update(ReportBlocksContentDTO reportBlocksContentDTO);

    /**
     * Partially updates a reportBlocksContent.
     *
     * @param reportBlocksContentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReportBlocksContentDTO> partialUpdate(ReportBlocksContentDTO reportBlocksContentDTO);

    /**
     * Get all the reportBlocksContents.
     *
     * @return the list of entities.
     */
    List<ReportBlocksContentDTO> findAll();

    /**
     * Get the "id" reportBlocksContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReportBlocksContentDTO> findOne(Long id);

    /**
     * Delete the "id" reportBlocksContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
