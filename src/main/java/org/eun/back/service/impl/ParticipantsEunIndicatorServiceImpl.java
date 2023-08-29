package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ParticipantsEunIndicator;
import org.eun.back.repository.ParticipantsEunIndicatorRepository;
import org.eun.back.service.ParticipantsEunIndicatorService;
import org.eun.back.service.dto.ParticipantsEunIndicatorDTO;
import org.eun.back.service.mapper.ParticipantsEunIndicatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParticipantsEunIndicator}.
 */
@Service
@Transactional
public class ParticipantsEunIndicatorServiceImpl implements ParticipantsEunIndicatorService {

    private final Logger log = LoggerFactory.getLogger(ParticipantsEunIndicatorServiceImpl.class);

    private final ParticipantsEunIndicatorRepository participantsEunIndicatorRepository;

    private final ParticipantsEunIndicatorMapper participantsEunIndicatorMapper;

    public ParticipantsEunIndicatorServiceImpl(
        ParticipantsEunIndicatorRepository participantsEunIndicatorRepository,
        ParticipantsEunIndicatorMapper participantsEunIndicatorMapper
    ) {
        this.participantsEunIndicatorRepository = participantsEunIndicatorRepository;
        this.participantsEunIndicatorMapper = participantsEunIndicatorMapper;
    }

    @Override
    public ParticipantsEunIndicatorDTO save(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO) {
        log.debug("Request to save ParticipantsEunIndicator : {}", participantsEunIndicatorDTO);
        ParticipantsEunIndicator participantsEunIndicator = participantsEunIndicatorMapper.toEntity(participantsEunIndicatorDTO);
        participantsEunIndicator = participantsEunIndicatorRepository.save(participantsEunIndicator);
        return participantsEunIndicatorMapper.toDto(participantsEunIndicator);
    }

    @Override
    public ParticipantsEunIndicatorDTO update(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO) {
        log.debug("Request to update ParticipantsEunIndicator : {}", participantsEunIndicatorDTO);
        ParticipantsEunIndicator participantsEunIndicator = participantsEunIndicatorMapper.toEntity(participantsEunIndicatorDTO);
        participantsEunIndicator = participantsEunIndicatorRepository.save(participantsEunIndicator);
        return participantsEunIndicatorMapper.toDto(participantsEunIndicator);
    }

    @Override
    public Optional<ParticipantsEunIndicatorDTO> partialUpdate(ParticipantsEunIndicatorDTO participantsEunIndicatorDTO) {
        log.debug("Request to partially update ParticipantsEunIndicator : {}", participantsEunIndicatorDTO);

        return participantsEunIndicatorRepository
            .findById(participantsEunIndicatorDTO.getId())
            .map(existingParticipantsEunIndicator -> {
                participantsEunIndicatorMapper.partialUpdate(existingParticipantsEunIndicator, participantsEunIndicatorDTO);

                return existingParticipantsEunIndicator;
            })
            .map(participantsEunIndicatorRepository::save)
            .map(participantsEunIndicatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantsEunIndicatorDTO> findAll() {
        log.debug("Request to get all ParticipantsEunIndicators");
        return participantsEunIndicatorRepository
            .findAll()
            .stream()
            .map(participantsEunIndicatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParticipantsEunIndicatorDTO> findOne(Long id) {
        log.debug("Request to get ParticipantsEunIndicator : {}", id);
        return participantsEunIndicatorRepository.findById(id).map(participantsEunIndicatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParticipantsEunIndicator : {}", id);
        participantsEunIndicatorRepository.deleteById(id);
    }
}
