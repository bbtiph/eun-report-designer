package org.eun.back.repository;

import org.eun.back.domain.Privilege;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Privilege entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {}
