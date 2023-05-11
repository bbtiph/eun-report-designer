package org.eun.back.service;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Role}.
 */
public interface RoleService {
    /**
     * Save a role.
     *
     * @param role the entity to save.
     * @return the persisted entity.
     */
    Role save(Role role);

    /**
     * Updates a role.
     *
     * @param role the entity to update.
     * @return the persisted entity.
     */
    Role update(Role role);

    /**
     * Partially updates a role.
     *
     * @param role the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Role> partialUpdate(Role role);

    /**
     * Get all the roles.
     *
     * @return the list of entities.
     */
    List<Role> findAll();

    /**
     * Get all the roles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Role> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" role.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Role> findOne(Long id);

    /**
     * Delete the "id" role.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
