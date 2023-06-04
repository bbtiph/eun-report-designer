package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ReportBlocksContentData;
import org.eun.back.repository.ReportBlocksContentDataRepository;
import org.eun.back.service.ReportBlocksContentDataService;
import org.eun.back.service.dto.ReportBlocksContentDataDTO;
import org.eun.back.service.mapper.ReportBlocksContentDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReportBlocksContentData}.
 */
@Service
@Transactional
public class ReportBlocksContentDataServiceImpl implements ReportBlocksContentDataService {

    private final Logger log = LoggerFactory.getLogger(ReportBlocksContentDataServiceImpl.class);

    private final ReportBlocksContentDataRepository reportBlocksContentDataRepository;

    private final ReportBlocksContentDataMapper reportBlocksContentDataMapper;

    public ReportBlocksContentDataServiceImpl(
        ReportBlocksContentDataRepository reportBlocksContentDataRepository,
        ReportBlocksContentDataMapper reportBlocksContentDataMapper
    ) {
        this.reportBlocksContentDataRepository = reportBlocksContentDataRepository;
        this.reportBlocksContentDataMapper = reportBlocksContentDataMapper;
    }

    @Override
    public ReportBlocksContentDataDTO save(ReportBlocksContentDataDTO reportBlocksContentDataDTO) {
        log.debug("Request to save ReportBlocksContentData : {}", reportBlocksContentDataDTO);
        ReportBlocksContentData reportBlocksContentData = reportBlocksContentDataMapper.toEntity(reportBlocksContentDataDTO);
        reportBlocksContentData = reportBlocksContentDataRepository.save(reportBlocksContentData);
        return reportBlocksContentDataMapper.toDto(reportBlocksContentData);
    }

    @Override
    public ReportBlocksContentDataDTO update(ReportBlocksContentDataDTO reportBlocksContentDataDTO) {
        log.debug("Request to update ReportBlocksContentData : {}", reportBlocksContentDataDTO);
        ReportBlocksContentData reportBlocksContentData = reportBlocksContentDataMapper.toEntity(reportBlocksContentDataDTO);
        reportBlocksContentData = reportBlocksContentDataRepository.save(reportBlocksContentData);
        return reportBlocksContentDataMapper.toDto(reportBlocksContentData);
    }

    @Override
    public Optional<ReportBlocksContentDataDTO> partialUpdate(ReportBlocksContentDataDTO reportBlocksContentDataDTO) {
        log.debug("Request to partially update ReportBlocksContentData : {}", reportBlocksContentDataDTO);

        return reportBlocksContentDataRepository
            .findById(reportBlocksContentDataDTO.getId())
            .map(existingReportBlocksContentData -> {
                reportBlocksContentDataMapper.partialUpdate(existingReportBlocksContentData, reportBlocksContentDataDTO);

                return existingReportBlocksContentData;
            })
            .map(reportBlocksContentDataRepository::save)
            .map(reportBlocksContentDataMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportBlocksContentDataDTO> findAll() {
        log.debug("Request to get all ReportBlocksContentData");
        return reportBlocksContentDataRepository
            .findAll()
            .stream()
            .map(reportBlocksContentDataMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportBlocksContentDataDTO> findOne(Long id) {
        log.debug("Request to get ReportBlocksContentData : {}", id);
        return reportBlocksContentDataRepository.findById(id).map(reportBlocksContentDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportBlocksContentData : {}", id);
        reportBlocksContentDataRepository.deleteById(id);
    }
}
