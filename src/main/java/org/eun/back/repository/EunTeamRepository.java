package org.eun.back.repository;

import org.eun.back.domain.EunTeam;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EunTeam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EunTeamRepository extends JpaRepository<EunTeam, Long> {}
