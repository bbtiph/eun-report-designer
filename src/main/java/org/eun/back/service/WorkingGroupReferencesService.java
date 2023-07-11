package org.eun.back.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.WorkingGroupReferencesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link org.eun.back.domain.WorkingGroupReferences}.
 */
public interface WorkingGroupReferencesService {
    /**
     * Save a workingGroupReferences.
     *
     * @param workingGroupReferencesDTO the entity to save.
     * @return the persisted entity.
     */
    WorkingGroupReferencesDTO save(WorkingGroupReferencesDTO workingGroupReferencesDTO);

    /**
     * Updates a workingGroupReferences.
     *
     * @param workingGroupReferencesDTO the entity to update.
     * @return the persisted entity.
     */
    WorkingGroupReferencesDTO update(WorkingGroupReferencesDTO workingGroupReferencesDTO);

    /**
     * Partially updates a workingGroupReferences.
     *
     * @param workingGroupReferencesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkingGroupReferencesDTO> partialUpdate(WorkingGroupReferencesDTO workingGroupReferencesDTO);

    /**
     * Get all the workingGroupReferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingGroupReferencesDTO> findAll(Pageable pageable);

    List<WorkingGroupReferencesDTO> findAllByCountry(String countryCode);

    /**
     * Get the "id" workingGroupReferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkingGroupReferencesDTO> findOne(Long id);

    /**
     * Delete the "id" workingGroupReferences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void upload(MultipartFile file) throws IOException;
}
