package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ReportBlocksContent;
import org.eun.back.repository.ReportBlocksContentRepository;
import org.eun.back.service.ReportBlocksContentService;
import org.eun.back.service.dto.ReportBlocksContentDTO;
import org.eun.back.service.mapper.ReportBlocksContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReportBlocksContent}.
 */
@Service
@Transactional
public class ReportBlocksContentServiceImpl implements ReportBlocksContentService {

    private final Logger log = LoggerFactory.getLogger(ReportBlocksContentServiceImpl.class);

    private final ReportBlocksContentRepository reportBlocksContentRepository;

    private final ReportBlocksContentMapper reportBlocksContentMapper;

    public ReportBlocksContentServiceImpl(
        ReportBlocksContentRepository reportBlocksContentRepository,
        ReportBlocksContentMapper reportBlocksContentMapper
    ) {
        this.reportBlocksContentRepository = reportBlocksContentRepository;
        this.reportBlocksContentMapper = reportBlocksContentMapper;
    }

    @Override
    public ReportBlocksContentDTO save(ReportBlocksContentDTO reportBlocksContentDTO) {
        log.debug("Request to save ReportBlocksContent : {}", reportBlocksContentDTO);
        ReportBlocksContent reportBlocksContent = reportBlocksContentMapper.toEntity(reportBlocksContentDTO);
        reportBlocksContent = reportBlocksContentRepository.save(reportBlocksContent);
        return reportBlocksContentMapper.toDto(reportBlocksContent);
    }

    @Override
    public ReportBlocksContentDTO update(ReportBlocksContentDTO reportBlocksContentDTO) {
        log.debug("Request to update ReportBlocksContent : {}", reportBlocksContentDTO);
        ReportBlocksContent reportBlocksContent = reportBlocksContentMapper.toEntity(reportBlocksContentDTO);
        reportBlocksContent = reportBlocksContentRepository.save(reportBlocksContent);
        return reportBlocksContentMapper.toDto(reportBlocksContent);
    }

    @Override
    public Optional<ReportBlocksContentDTO> partialUpdate(ReportBlocksContentDTO reportBlocksContentDTO) {
        log.debug("Request to partially update ReportBlocksContent : {}", reportBlocksContentDTO);

        return reportBlocksContentRepository
            .findById(reportBlocksContentDTO.getId())
            .map(existingReportBlocksContent -> {
                reportBlocksContentMapper.partialUpdate(existingReportBlocksContent, reportBlocksContentDTO);

                return existingReportBlocksContent;
            })
            .map(reportBlocksContentRepository::save)
            .map(reportBlocksContentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportBlocksContentDTO> findAll() {
        log.debug("Request to get all ReportBlocksContents");
        return reportBlocksContentRepository
            .findAll()
            .stream()
            .map(reportBlocksContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportBlocksContentDTO> findOne(Long id) {
        log.debug("Request to get ReportBlocksContent : {}", id);
        return reportBlocksContentRepository.findById(id).map(reportBlocksContentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportBlocksContent : {}", id);
        reportBlocksContentRepository.deleteById(id);
    }
}
