package org.eun.back.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link org.eun.back.domain.ReferenceTableSettings}.
 */
public interface ReferenceTableSettingsService {
    /**
     * Save a referenceTableSettings.
     *
     * @param referenceTableSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    ReferenceTableSettingsDTO save(ReferenceTableSettingsDTO referenceTableSettingsDTO);

    /**
     * Updates a referenceTableSettings.
     *
     * @param referenceTableSettingsDTO the entity to update.
     * @return the persisted entity.
     */
    ReferenceTableSettingsDTO update(ReferenceTableSettingsDTO referenceTableSettingsDTO);

    /**
     * Partially updates a referenceTableSettings.
     *
     * @param referenceTableSettingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReferenceTableSettingsDTO> partialUpdate(ReferenceTableSettingsDTO referenceTableSettingsDTO);

    /**
     * Get all the referenceTableSettings.
     *
     * @return the list of entities.
     */
    List<ReferenceTableSettingsDTO> findAll();

    List<?> findAllDataByRefTable(String refTable);

    byte[] download(String refTable) throws IOException;

    void upload(MultipartFile file, String refTable) throws IOException;

    /**
     * Get the "id" referenceTableSettings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReferenceTableSettingsDTO> findOne(Long id);

    Optional<ReferenceTableSettingsDTO> findOneByRefTable(String name);

    /**
     * Delete the "id" referenceTableSettings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void deleteReferenceRowByRefTableAndId(String refTable, Long id);
}
