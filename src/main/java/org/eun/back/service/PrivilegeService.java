package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.Privilege;

/**
 * Service Interface for managing {@link Privilege}.
 */
public interface PrivilegeService {
    /**
     * Save a privilege.
     *
     * @param privilege the entity to save.
     * @return the persisted entity.
     */
    Privilege save(Privilege privilege);

    /**
     * Updates a privilege.
     *
     * @param privilege the entity to update.
     * @return the persisted entity.
     */
    Privilege update(Privilege privilege);

    /**
     * Partially updates a privilege.
     *
     * @param privilege the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Privilege> partialUpdate(Privilege privilege);

    /**
     * Get all the privileges.
     *
     * @return the list of entities.
     */
    List<Privilege> findAll();

    /**
     * Get the "id" privilege.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Privilege> findOne(Long id);

    /**
     * Delete the "id" privilege.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
