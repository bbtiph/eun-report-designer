package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.service.dto.MOEParticipationReferencesDTO;

/**
 * Service Interface for managing {@link org.eun.back.domain.MOEParticipationReferences}.
 */
public interface MOEParticipationReferencesService {
    /**
     * Save a mOEParticipationReferences.
     *
     * @param mOEParticipationReferencesDTO the entity to save.
     * @return the persisted entity.
     */
    MOEParticipationReferencesDTO save(MOEParticipationReferencesDTO mOEParticipationReferencesDTO);

    /**
     * Updates a mOEParticipationReferences.
     *
     * @param mOEParticipationReferencesDTO the entity to update.
     * @return the persisted entity.
     */
    MOEParticipationReferencesDTO update(MOEParticipationReferencesDTO mOEParticipationReferencesDTO);

    /**
     * Partially updates a mOEParticipationReferences.
     *
     * @param mOEParticipationReferencesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MOEParticipationReferencesDTO> partialUpdate(MOEParticipationReferencesDTO mOEParticipationReferencesDTO);

    /**
     * Get all the mOEParticipationReferences.
     *
     * @return the list of entities.
     */
    List<MOEParticipationReferencesDTO> findAll();

    /**
     * Get the "id" mOEParticipationReferences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MOEParticipationReferencesDTO> findOne(Long id);

    /**
     * Delete the "id" mOEParticipationReferences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
