package org.eun.back.repository;

import org.eun.back.domain.OperationalBody;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OperationalBody entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperationalBodyRepository extends JpaRepository<OperationalBody, Long> {}
