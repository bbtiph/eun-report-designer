package org.eun.back.repository;

import org.eun.back.domain.OrganizationInMinistry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrganizationInMinistry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationInMinistryRepository extends JpaRepository<OrganizationInMinistry, Long> {}
