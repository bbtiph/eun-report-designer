package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ReportTemplate;
import org.eun.back.repository.ReportTemplateRepository;
import org.eun.back.service.ReportTemplateService;
import org.eun.back.service.dto.ReportTemplateDTO;
import org.eun.back.service.mapper.ReportTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReportTemplate}.
 */
@Service
@Transactional
public class ReportTemplateServiceImpl implements ReportTemplateService {

    private final Logger log = LoggerFactory.getLogger(ReportTemplateServiceImpl.class);

    private final ReportTemplateRepository reportTemplateRepository;

    private final ReportTemplateMapper reportTemplateMapper;

    public ReportTemplateServiceImpl(ReportTemplateRepository reportTemplateRepository, ReportTemplateMapper reportTemplateMapper) {
        this.reportTemplateRepository = reportTemplateRepository;
        this.reportTemplateMapper = reportTemplateMapper;
    }

    @Override
    public ReportTemplateDTO save(ReportTemplateDTO reportTemplateDTO) {
        log.debug("Request to save ReportTemplate : {}", reportTemplateDTO);
        ReportTemplate reportTemplate = reportTemplateMapper.toEntity(reportTemplateDTO);
        reportTemplate = reportTemplateRepository.save(reportTemplate);
        return reportTemplateMapper.toDto(reportTemplate);
    }

    @Override
    public ReportTemplateDTO update(ReportTemplateDTO reportTemplateDTO) {
        log.debug("Request to update ReportTemplate : {}", reportTemplateDTO);
        ReportTemplate reportTemplate = reportTemplateMapper.toEntity(reportTemplateDTO);
        reportTemplate = reportTemplateRepository.save(reportTemplate);
        return reportTemplateMapper.toDto(reportTemplate);
    }

    @Override
    public Optional<ReportTemplateDTO> partialUpdate(ReportTemplateDTO reportTemplateDTO) {
        log.debug("Request to partially update ReportTemplate : {}", reportTemplateDTO);

        return reportTemplateRepository
            .findById(reportTemplateDTO.getId())
            .map(existingReportTemplate -> {
                reportTemplateMapper.partialUpdate(existingReportTemplate, reportTemplateDTO);

                return existingReportTemplate;
            })
            .map(reportTemplateRepository::save)
            .map(reportTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportTemplateDTO> findAll() {
        log.debug("Request to get all ReportTemplates");
        return reportTemplateRepository
            .findAll()
            .stream()
            .map(reportTemplateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportTemplateDTO> findOne(Long id) {
        log.debug("Request to get ReportTemplate : {}", id);
        return reportTemplateRepository.findById(id).map(reportTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportTemplateDTO> findOneByName(String name) {
        log.debug("Request to get ReportTemplate : {}", name);
        return reportTemplateRepository.findByName(name).map(reportTemplateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReportTemplate : {}", id);
        reportTemplateRepository.deleteById(id);
    }
}
