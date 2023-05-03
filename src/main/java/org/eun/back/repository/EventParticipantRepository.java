package org.eun.back.repository;

import org.eun.back.domain.EventParticipant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventParticipant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {}
