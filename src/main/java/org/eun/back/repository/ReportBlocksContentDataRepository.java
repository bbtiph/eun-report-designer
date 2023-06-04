package org.eun.back.repository;

import org.eun.back.domain.ReportBlocksContentData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportBlocksContentData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportBlocksContentDataRepository extends JpaRepository<ReportBlocksContentData, Long> {}
