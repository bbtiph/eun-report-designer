package org.eun.back.repository;

import org.eun.back.domain.ReportBlocks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportBlocks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportBlocksRepository extends JpaRepository<ReportBlocks, Long> {}
