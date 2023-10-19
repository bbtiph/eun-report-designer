package org.eun.back.service.impl;

import java.util.List;
import org.eun.back.domain.RelReportBlocksReport;
import org.eun.back.repository.RelReportBlocksReportRepository;
import org.eun.back.service.RelReportBlocksReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RelReportBlocksReportServiceImpl implements RelReportBlocksReportService {

    private final Logger log = LoggerFactory.getLogger(RelReportBlocksReportServiceImpl.class);

    private final RelReportBlocksReportRepository relReportBlocksReportRepository;

    public RelReportBlocksReportServiceImpl(RelReportBlocksReportRepository relReportBlocksReportRepository) {
        this.relReportBlocksReportRepository = relReportBlocksReportRepository;
    }

    @Override
    public List<RelReportBlocksReport> findAll() {
        return relReportBlocksReportRepository.findAll();
    }

    @Override
    public List<RelReportBlocksReport> findAllByReport(Long reportId) {
        return relReportBlocksReportRepository.findAllByReportId(reportId);
    }

    @Override
    public void updatePriorityNumber(Long priorityNumber, Long reportId, Long reportBlocksId) {
        relReportBlocksReportRepository.updatePriorityNumberByReportIdAndReportBlockId(priorityNumber, reportId, reportBlocksId);
    }

    @Override
    public Long findMaxPriorityNumberByReportId(Long reportId) {
        return relReportBlocksReportRepository.findMaxPriorityNumberByReportId(reportId);
    }

    @Override
    public RelReportBlocksReport save(RelReportBlocksReport relReportBlocksReport) {
        return relReportBlocksReportRepository.save(relReportBlocksReport);
    }
}
