package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.repository.ReportBlocksRepository;
import org.eun.back.service.ReportBlocksService;
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

    private final ReportBlocksMapper reportBlocksMapper;

    public ReportBlocksServiceImpl(ReportBlocksRepository reportBlocksRepository, ReportBlocksMapper reportBlocksMapper) {
        this.reportBlocksRepository = reportBlocksRepository;
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
