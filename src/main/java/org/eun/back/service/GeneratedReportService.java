package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.GeneratedReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.GeneratedReport}.
 */
public interface GeneratedReportService {
    /**
     * Save a generatedReport.
     *
     * @param generatedReportDTO the entity to save.
     * @return the persisted entity.
     */
    GeneratedReportDTO save(GeneratedReportDTO generatedReportDTO);

    /**
     * Updates a generatedReport.
     *
     * @param generatedReportDTO the entity to update.
     * @return the persisted entity.
     */
    GeneratedReportDTO update(GeneratedReportDTO generatedReportDTO);

    /**
     * Partially updates a generatedReport.
     *
     * @param generatedReportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GeneratedReportDTO> partialUpdate(GeneratedReportDTO generatedReportDTO);

    /**
     * Get all the generatedReports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GeneratedReportDTO> findAll(Pageable pageable);

    /**
     * Get the "id" generatedReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GeneratedReportDTO> findOne(Long id);

    /**
     * Delete the "id" generatedReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
