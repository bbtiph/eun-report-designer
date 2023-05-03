package org.eun.back.repository;

import org.eun.back.domain.PersonInOrganization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PersonInOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonInOrganizationRepository extends JpaRepository<PersonInOrganization, Long> {}
