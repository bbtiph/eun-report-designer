package org.eun.back.repository;

import org.eun.back.domain.OperationalBodyMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OperationalBodyMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationalBodyMemberRepository extends JpaRepository<OperationalBodyMember, Long> {}
