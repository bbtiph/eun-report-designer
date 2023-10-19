package org.eun.back.repository;

import org.eun.back.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Report entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findAllByIsMinistry(boolean forMinistries, Pageable pageable);

    @Modifying
    @Query("UPDATE Report r SET r.isActive = false WHERE r.id = :id")
    void deleteByChangeIsActive(@Param("id") Long id);

    Page<Report> findAllByIsActive(boolean isActive, Pageable pageable);
}
