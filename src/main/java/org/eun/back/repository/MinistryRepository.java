package org.eun.back.repository;

import org.eun.back.domain.Ministry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ministry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MinistryRepository extends JpaRepository<Ministry, Long> {}
