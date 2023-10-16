package org.eun.back.repository;

import java.util.List;
import org.eun.back.domain.RelReportBlocksReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RelReportBlocksReportRepository extends JpaRepository<RelReportBlocksReport, Long> {
    List<RelReportBlocksReport> findAllByReportId(Long reportId);

    @Modifying
    @Query(
        "UPDATE RelReportBlocksReport r SET r.priorityNumber = :priorityNumber WHERE r.reportId = :reportId AND r.reportBlocksId = :reportBlocksId"
    )
    void updatePriorityNumberByReportIdAndReportBlockId(
        @Param("priorityNumber") Long priorityNumber,
        @Param("reportId") Long reportId,
        @Param("reportBlocksId") Long reportBlocksId
    );

    @Query("SELECT MAX(r.priorityNumber) FROM RelReportBlocksReport r WHERE r.reportId = :reportId")
    Long findMaxPriorityNumberByReportId(@Param("reportId") Long reportId);
}
