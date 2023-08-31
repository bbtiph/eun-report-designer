package org.eun.back.repository;

import org.eun.back.domain.ReportTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long> {}
