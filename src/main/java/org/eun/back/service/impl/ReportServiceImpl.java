package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.RelReportBlocksReport;
import org.eun.back.domain.Report;
import org.eun.back.repository.ReportRepository;
import org.eun.back.service.RelReportBlocksReportService;
import org.eun.back.service.ReportService;
import org.eun.back.service.dto.ReportDTO;
import org.eun.back.service.mapper.ReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Report}.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    private final RelReportBlocksReportService relReportBlocksReportService;

    public ReportServiceImpl(
        ReportRepository reportRepository,
        ReportMapper reportMapper,
        RelReportBlocksReportService relReportBlocksReportService
    ) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.relReportBlocksReportService = relReportBlocksReportService;
    }

    @Override
    public ReportDTO save(ReportDTO reportDTO) {
        log.debug("Request to save Report : {}", reportDTO);
        Report report = reportMapper.toEntity(reportDTO);
        report = reportRepository.save(report);
        return reportMapper.toDto(report);
    }

    @Override
    public ReportDTO clone(ReportDTO reportDTO) {
        log.debug("Request to clone Report : {}", reportDTO);
        Report reportToClone = reportRepository.findById(reportDTO.getId()).get();
        reportDTO.setId(null);

        Report newReport = reportMapper.toEntity(reportDTO);
        newReport.setReportTemplate(reportToClone.getReportTemplate());
        newReport.setParentId(reportToClone.getId());
        newReport = reportRepository.save(newReport);
        return reportMapper.toDto(newReport);
    }

    @Override
    public ReportDTO cloneReportBlocks(ReportDTO reportDTO) {
        List<RelReportBlocksReport> relReportBlocksReports = relReportBlocksReportService.findAllByReport(reportDTO.getParentId());
        for (RelReportBlocksReport relReportBlocksReport : relReportBlocksReports) {
            RelReportBlocksReport newRelReportBlocksReport = new RelReportBlocksReport();
            newRelReportBlocksReport.setReportId(reportDTO.getId());
            newRelReportBlocksReport.setReportBlocksId(relReportBlocksReport.getReportBlocksId());
            newRelReportBlocksReport.setPriorityNumber(relReportBlocksReport.getPriorityNumber());

            relReportBlocksReportService.save(newRelReportBlocksReport);
        }
        return reportDTO;
    }

    @Override
    public ReportDTO update(ReportDTO reportDTO) {
        log.debug("Request to update Report : {}", reportDTO);
        Report report = reportMapper.toEntity(reportDTO);
        report = reportRepository.save(report);
        return reportMapper.toDto(report);
    }

    @Override
    public Optional<ReportDTO> partialUpdate(ReportDTO reportDTO) {
        log.debug("Request to partially update Report : {}", reportDTO);

        return reportRepository
            .findById(reportDTO.getId())
            .map(existingReport -> {
                reportMapper.partialUpdate(existingReport, reportDTO);

                return existingReport;
            })
            .map(reportRepository::save)
            .map(reportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReportDTO> findAll(Pageable pageable, boolean forMinistries) {
        log.debug("Request to get all Reports");
        if (forMinistries) {
            return reportRepository.findAllByIsMinistryAndIsActive(true, true, pageable).map(reportMapper::toDtoToShowInHomePage);
        }
        return reportRepository.findAllByIsActive(true, pageable).map(reportMapper::toDtoToShowInHomePage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDTO> findAllByCountry(boolean forMinistries, Long countryId) {
        log.debug("Request to get all Reports By Country");
        return reportRepository
            .findReportsByCountry(forMinistries, true, countryId)
            .stream()
            .map(reportMapper::toDtoToShowInHomePage)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportDTO> findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        return reportRepository.findById(id).map(reportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        reportRepository.deleteByChangeIsActive(id);
    }
}
