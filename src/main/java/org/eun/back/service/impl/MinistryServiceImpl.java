package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.Ministry;
import org.eun.back.repository.MinistryRepository;
import org.eun.back.service.MinistryService;
import org.eun.back.service.dto.MinistryDTO;
import org.eun.back.service.mapper.MinistryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ministry}.
 */
@Service
@Transactional
public class MinistryServiceImpl implements MinistryService {

    private final Logger log = LoggerFactory.getLogger(MinistryServiceImpl.class);

    private final MinistryRepository ministryRepository;

    private final MinistryMapper ministryMapper;

    public MinistryServiceImpl(MinistryRepository ministryRepository, MinistryMapper ministryMapper) {
        this.ministryRepository = ministryRepository;
        this.ministryMapper = ministryMapper;
    }

    @Override
    public MinistryDTO save(MinistryDTO ministryDTO) {
        log.debug("Request to save Ministry : {}", ministryDTO);
        Ministry ministry = ministryMapper.toEntity(ministryDTO);
        ministry = ministryRepository.save(ministry);
        return ministryMapper.toDto(ministry);
    }

    @Override
    public MinistryDTO update(MinistryDTO ministryDTO) {
        log.debug("Request to update Ministry : {}", ministryDTO);
        Ministry ministry = ministryMapper.toEntity(ministryDTO);
        ministry = ministryRepository.save(ministry);
        return ministryMapper.toDto(ministry);
    }

    @Override
    public Optional<MinistryDTO> partialUpdate(MinistryDTO ministryDTO) {
        log.debug("Request to partially update Ministry : {}", ministryDTO);

        return ministryRepository
            .findById(ministryDTO.getId())
            .map(existingMinistry -> {
                ministryMapper.partialUpdate(existingMinistry, ministryDTO);

                return existingMinistry;
            })
            .map(ministryRepository::save)
            .map(ministryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MinistryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ministries");
        return ministryRepository.findAll(pageable).map(ministryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MinistryDTO> findOne(Long id) {
        log.debug("Request to get Ministry : {}", id);
        return ministryRepository.findById(id).map(ministryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ministry : {}", id);
        ministryRepository.deleteById(id);
    }
}
