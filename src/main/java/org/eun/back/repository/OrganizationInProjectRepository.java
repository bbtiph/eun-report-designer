package org.eun.back.repository;

import org.eun.back.domain.OrganizationInProject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationInProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationInProjectRepository extends JpaRepository<OrganizationInProject, Long> {}
