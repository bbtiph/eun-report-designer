package org.eun.back.repository;

import org.eun.back.domain.Funding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Funding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FundingRepository extends JpaRepository<Funding, Long> {}
