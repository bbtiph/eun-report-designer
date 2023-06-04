package org.eun.back.repository;

import org.eun.back.domain.ReportBlocksContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportBlocksContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportBlocksContentRepository extends JpaRepository<ReportBlocksContent, Long> {}
