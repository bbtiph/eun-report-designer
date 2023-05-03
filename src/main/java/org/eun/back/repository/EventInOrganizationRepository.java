package org.eun.back.repository;

import org.eun.back.domain.EventInOrganization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventInOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventInOrganizationRepository extends JpaRepository<EventInOrganization, Long> {}
