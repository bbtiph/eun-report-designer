package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.OperationalBody;
import org.eun.back.repository.OperationalBodyRepository;
import org.eun.back.service.OperationalBodyService;
import org.eun.back.service.dto.OperationalBodyDTO;
import org.eun.back.service.mapper.OperationalBodyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OperationalBody}.
 */
@Service
@Transactional
public class OperationalBodyServiceImpl implements OperationalBodyService {

    private final Logger log = LoggerFactory.getLogger(OperationalBodyServiceImpl.class);

    private final OperationalBodyRepository operationalBodyRepository;

    private final OperationalBodyMapper operationalBodyMapper;

    public OperationalBodyServiceImpl(OperationalBodyRepository operationalBodyRepository, OperationalBodyMapper operationalBodyMapper) {
        this.operationalBodyRepository = operationalBodyRepository;
        this.operationalBodyMapper = operationalBodyMapper;
    }

    @Override
    public OperationalBodyDTO save(OperationalBodyDTO operationalBodyDTO) {
        log.debug("Request to save OperationalBody : {}", operationalBodyDTO);
        OperationalBody operationalBody = operationalBodyMapper.toEntity(operationalBodyDTO);
        operationalBody = operationalBodyRepository.save(operationalBody);
        return operationalBodyMapper.toDto(operationalBody);
    }

    @Override
    public OperationalBodyDTO update(OperationalBodyDTO operationalBodyDTO) {
        log.debug("Request to update OperationalBody : {}", operationalBodyDTO);
        OperationalBody operationalBody = operationalBodyMapper.toEntity(operationalBodyDTO);
        operationalBody = operationalBodyRepository.save(operationalBody);
        return operationalBodyMapper.toDto(operationalBody);
    }

    @Override
    public Optional<OperationalBodyDTO> partialUpdate(OperationalBodyDTO operationalBodyDTO) {
        log.debug("Request to partially update OperationalBody : {}", operationalBodyDTO);

        return operationalBodyRepository
            .findById(operationalBodyDTO.getId())
            .map(existingOperationalBody -> {
                operationalBodyMapper.partialUpdate(existingOperationalBody, operationalBodyDTO);

                return existingOperationalBody;
            })
            .map(operationalBodyRepository::save)
            .map(operationalBodyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OperationalBodyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OperationalBodies");
        return operationalBodyRepository.findAll(pageable).map(operationalBodyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OperationalBodyDTO> findOne(Long id) {
        log.debug("Request to get OperationalBody : {}", id);
        return operationalBodyRepository.findById(id).map(operationalBodyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OperationalBody : {}", id);
        operationalBodyRepository.deleteById(id);
    }
}
