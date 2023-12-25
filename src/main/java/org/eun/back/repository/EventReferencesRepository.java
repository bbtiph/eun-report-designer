package org.eun.back.repository;

import org.eun.back.domain.EventReferences;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventReferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventReferencesRepository extends JpaRepository<EventReferences, Long> {}
