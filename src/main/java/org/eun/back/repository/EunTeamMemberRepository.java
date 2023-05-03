package org.eun.back.repository;

import org.eun.back.domain.EunTeamMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EunTeamMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EunTeamMemberRepository extends JpaRepository<EunTeamMember, Long> {}
