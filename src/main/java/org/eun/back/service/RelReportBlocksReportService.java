package org.eun.back.service;

import java.util.List;
import org.eun.back.domain.RelReportBlocksReport;
import org.springframework.data.repository.query.Param;

public interface RelReportBlocksReportService {
    List<RelReportBlocksReport> findAll();

    List<RelReportBlocksReport> findAllByReport(Long reportId);

    void updatePriorityNumber(Long priorityNumber, Long reportId, Long reportBlocksId);

    Long findMaxPriorityNumberByReportId(Long reportId);
}
