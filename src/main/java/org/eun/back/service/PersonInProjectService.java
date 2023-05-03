package org.eun.back.service;

import java.util.Optional;
import org.eun.back.service.dto.PersonInProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.eun.back.domain.PersonInProject}.
 */
public interface PersonInProjectService {
    /**
     * Save a personInProject.
     *
     * @param personInProjectDTO the entity to save.
     * @return the persisted entity.
     */
    PersonInProjectDTO save(PersonInProjectDTO personInProjectDTO);

    /**
     * Updates a personInProject.
     *
     * @param personInProjectDTO the entity to update.
     * @return the persisted entity.
     */
    PersonInProjectDTO update(PersonInProjectDTO personInProjectDTO);

    /**
     * Partially updates a personInProject.
     *
     * @param personInProjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonInProjectDTO> partialUpdate(PersonInProjectDTO personInProjectDTO);

    /**
     * Get all the personInProjects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonInProjectDTO> findAll(Pageable pageable);

    /**
     * Get the "id" personInProject.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonInProjectDTO> findOne(Long id);

    /**
     * Delete the "id" personInProject.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
