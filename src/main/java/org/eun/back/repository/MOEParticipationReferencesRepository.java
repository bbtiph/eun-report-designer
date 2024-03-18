package org.eun.back.repository;

import org.eun.back.domain.MOEParticipationReferences;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MOEParticipationReferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MOEParticipationReferencesRepository extends JpaRepository<MOEParticipationReferences, Long> {}
