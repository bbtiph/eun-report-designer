package org.eun.back.repository;

import java.util.List;
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
    Page<Report> findAllByIsMinistryAndIsActive(boolean forMinistries, boolean isActive, Pageable pageable);

    @Query(
        "SELECT DISTINCT r FROM Report r " +
        "INNER JOIN r.reportBlockIds rb " +
        "INNER JOIN rb.countryIds c " +
        "WHERE c.id = :countryId AND " +
        "r.isMinistry = :forMinistries AND " +
        "r.isActive = :isActive"
    )
    List<Report> findReportsByCountry(
        @Param("forMinistries") boolean forMinistries,
        @Param("isActive") boolean isActive,
        @Param("countryId") Long countryId
    );

    @Modifying
    @Query("UPDATE Report r SET r.isActive = false WHERE r.id = :id")
    void deleteByChangeIsActive(@Param("id") Long id);

    Page<Report> findAllByIsActive(boolean isActive, Pageable pageable);
}
