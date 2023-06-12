package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.repository.ReportBlocksRepository;
import org.eun.back.service.ReportBlocksContentDataService;
import org.eun.back.service.ReportBlocksContentService;
import org.eun.back.service.ReportBlocksService;
import org.eun.back.service.dto.ReportBlocksContentDTO;
import org.eun.back.service.dto.ReportBlocksContentDataDTO;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.eun.back.service.mapper.ReportBlocksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReportBlocks}.
 */
@Service
@Transactional
public class ReportBlocksServiceImpl implements ReportBlocksService {

    private final Logger log = LoggerFactory.getLogger(ReportBlocksServiceImpl.class);

    private final ReportBlocksRepository reportBlocksRepository;

    private final ReportBlocksContentDataService reportBlocksContentDataService;

    private final ReportBlocksContentService reportBlocksContentService;

    private final ReportBlocksMapper reportBlocksMapper;

    public ReportBlocksServiceImpl(
        ReportBlocksRepository reportBlocksRepository,
        ReportBlocksContentDataService reportBlocksContentDataService,
        ReportBlocksContentService reportBlocksContentService,
        ReportBlocksMapper reportBlocksMapper
    ) {
        this.reportBlocksRepository = reportBlocksRepository;
        this.reportBlocksContentDataService = reportBlocksContentDataService;
        this.reportBlocksContentService = reportBlocksContentService;
        this.reportBlocksMapper = reportBlocksMapper;
    }

    @Override
    public ReportBlocksDTO save(ReportBlocksDTO reportBlocksDTO) {
        log.debug("Request to save ReportBlocks : {}", reportBlocksDTO);
        ReportBlocks reportBlocks = reportBlocksMapper.toEntity(reportBlocksDTO);
        reportBlocks = reportBlocksRepository.save(reportBlocks);
        return reportBlocksMapper.toDto(reportBlocks);
    }

    @Override
    public ReportBlocksDTO update(ReportBlocksDTO reportBlocksDTO) {
        log.debug("Request to update ReportBlocks : {}", reportBlocksDTO);
        for (ReportBlocksContentDTO reportBlocksContentDTO : reportBlocksDTO.getReportBlocksContents()) {
            if (reportBlocksContentDTO.getNewContentData() != null && reportBlocksContentDTO.getNewContentData().equals("true")) {
                for (ReportBlocksContentDataDTO reportBlocksContentDataDTO : reportBlocksContentDTO.getReportBlocksContentData()) {
                    if (
                        reportBlocksContentDTO.getNewContentData() != null && reportBlocksContentDataDTO.getNewContentData().equals("true")
                    ) {
                        reportBlocksContentDataDTO.setId(null);
                        //reportBlocksContentDataDTO = reportBlocksContentDataService.save(reportBlocksContentDataDTO);
                    }
                }
                reportBlocksContentDTO.setId(null);
                //reportBlocksContentDTO = reportBlocksContentService.save(reportBlocksContentDTO);
            }
        }
        ReportBlocks reportBlocks = reportBlocksMapper.toEntity(reportBlocksDTO);
        reportBlocks = reportBlocksRepository.save(reportBlocks);
        return reportBlocksMapper.toDto(reportBlocks);
    }

    @Override
    public Optional<ReportBlocksDTO> partialUpdate(ReportBlocksDTO reportBlocksDTO) {
        log.debug("Request to partially update ReportBlocks : {}", reportBlocksDTO);

        return reportBlocksRepository
            .findById(reportBlocksDTO.getId())
            .map(existingReportBlocks -> {
                reportBlocksMapper.partialUpdate(existingReportBlocks, reportBlocksDTO);

                return existingReportBlocks;
            })
            .map(reportBlocksRepository::save)
            .map(reportBlocksMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportBlocksDTO> findAll() {
        log.debug("Request to get all ReportBlocks");
        return reportBlocksRepository.findAll().stream().map(reportBlocksMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ReportBlocksDTO> findAllWithEagerRelationships(Pageable pageable) {
        return reportBlocksRepository.findAllWithEagerRelationships(pageable).map(reportBlocksMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportBlocksDTO> findOne(Long id) {
        log.debug("Request to get ReportBlocks : {}", id);
        return reportBlocksRepository.findOneWithEagerRelationships(id).map(reportBlocksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportBlocks : {}", id);
        reportBlocksRepository.deleteById(id);
    }
}
