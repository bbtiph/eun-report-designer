package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.GeneratedReport;
import org.eun.back.repository.GeneratedReportRepository;
import org.eun.back.service.GeneratedReportService;
import org.eun.back.service.dto.GeneratedReportDTO;
import org.eun.back.service.mapper.GeneratedReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GeneratedReport}.
 */
@Service
@Transactional
public class GeneratedReportServiceImpl implements GeneratedReportService {

    private final Logger log = LoggerFactory.getLogger(GeneratedReportServiceImpl.class);

    private final GeneratedReportRepository generatedReportRepository;

    private final GeneratedReportMapper generatedReportMapper;

    public GeneratedReportServiceImpl(GeneratedReportRepository generatedReportRepository, GeneratedReportMapper generatedReportMapper) {
        this.generatedReportRepository = generatedReportRepository;
        this.generatedReportMapper = generatedReportMapper;
    }

    @Override
    public GeneratedReportDTO save(GeneratedReportDTO generatedReportDTO) {
        log.debug("Request to save GeneratedReport : {}", generatedReportDTO);
        GeneratedReport generatedReport = generatedReportMapper.toEntity(generatedReportDTO);
        generatedReport = generatedReportRepository.save(generatedReport);
        return generatedReportMapper.toDto(generatedReport);
    }

    @Override
    public GeneratedReportDTO update(GeneratedReportDTO generatedReportDTO) {
        log.debug("Request to update GeneratedReport : {}", generatedReportDTO);
        GeneratedReport generatedReport = generatedReportMapper.toEntity(generatedReportDTO);
        generatedReport = generatedReportRepository.save(generatedReport);
        return generatedReportMapper.toDto(generatedReport);
    }

    @Override
    public Optional<GeneratedReportDTO> partialUpdate(GeneratedReportDTO generatedReportDTO) {
        log.debug("Request to partially update GeneratedReport : {}", generatedReportDTO);

        return generatedReportRepository
            .findById(generatedReportDTO.getId())
            .map(existingGeneratedReport -> {
                generatedReportMapper.partialUpdate(existingGeneratedReport, generatedReportDTO);

                return existingGeneratedReport;
            })
            .map(generatedReportRepository::save)
            .map(generatedReportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GeneratedReportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GeneratedReports");
        return generatedReportRepository.findAll(pageable).map(generatedReportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GeneratedReportDTO> findOne(Long id) {
        log.debug("Request to get GeneratedReport : {}", id);
        return generatedReportRepository.findById(id).map(generatedReportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GeneratedReport : {}", id);
        generatedReportRepository.deleteById(id);
    }
}
