package org.eun.back.repository;

import org.eun.back.domain.ParticipantsEunIndicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ParticipantsEunIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantsEunIndicatorRepository extends JpaRepository<ParticipantsEunIndicator, Long> {}
