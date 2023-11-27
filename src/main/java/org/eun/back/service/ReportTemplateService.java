package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.ReportTemplateDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.ReportTemplate}.
 */
public interface ReportTemplateService {
    /**
     * Save a reportTemplate.
     *
     * @param reportTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    ReportTemplateDTO save(ReportTemplateDTO reportTemplateDTO);

    /**
     * Updates a reportTemplate.
     *
     * @param reportTemplateDTO the entity to update.
     * @return the persisted entity.
     */
    ReportTemplateDTO update(ReportTemplateDTO reportTemplateDTO);

    /**
     * Partially updates a reportTemplate.
     *
     * @param reportTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReportTemplateDTO> partialUpdate(ReportTemplateDTO reportTemplateDTO);

    /**
     * Get all the reportTemplates.
     *
     * @return the list of entities.
     */
    List<ReportTemplateDTO> findAll();

    /**
     * Get the "id" reportTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReportTemplateDTO> findOne(Long id);

    Optional<ReportTemplateDTO> findOneByName(String name);

    /**
     * Delete the "id" reportTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
