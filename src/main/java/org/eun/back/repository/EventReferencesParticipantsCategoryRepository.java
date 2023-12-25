package org.eun.back.repository;

import org.eun.back.domain.EventReferencesParticipantsCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventReferencesParticipantsCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventReferencesParticipantsCategoryRepository extends JpaRepository<EventReferencesParticipantsCategory, Long> {}
