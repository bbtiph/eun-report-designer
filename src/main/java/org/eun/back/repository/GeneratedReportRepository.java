package org.eun.back.repository;

import org.eun.back.domain.GeneratedReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GeneratedReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneratedReportRepository extends JpaRepository<GeneratedReport, Long> {}
