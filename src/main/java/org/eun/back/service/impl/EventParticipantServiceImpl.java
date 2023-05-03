package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.EventParticipant;
import org.eun.back.repository.EventParticipantRepository;
import org.eun.back.service.EventParticipantService;
import org.eun.back.service.dto.EventParticipantDTO;
import org.eun.back.service.mapper.EventParticipantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventParticipant}.
 */
@Service
@Transactional
public class EventParticipantServiceImpl implements EventParticipantService {

    private final Logger log = LoggerFactory.getLogger(EventParticipantServiceImpl.class);

    private final EventParticipantRepository eventParticipantRepository;

    private final EventParticipantMapper eventParticipantMapper;

    public EventParticipantServiceImpl(
        EventParticipantRepository eventParticipantRepository,
        EventParticipantMapper eventParticipantMapper
    ) {
        this.eventParticipantRepository = eventParticipantRepository;
        this.eventParticipantMapper = eventParticipantMapper;
    }

    @Override
    public EventParticipantDTO save(EventParticipantDTO eventParticipantDTO) {
        log.debug("Request to save EventParticipant : {}", eventParticipantDTO);
        EventParticipant eventParticipant = eventParticipantMapper.toEntity(eventParticipantDTO);
        eventParticipant = eventParticipantRepository.save(eventParticipant);
        return eventParticipantMapper.toDto(eventParticipant);
    }

    @Override
    public EventParticipantDTO update(EventParticipantDTO eventParticipantDTO) {
        log.debug("Request to update EventParticipant : {}", eventParticipantDTO);
        EventParticipant eventParticipant = eventParticipantMapper.toEntity(eventParticipantDTO);
        eventParticipant = eventParticipantRepository.save(eventParticipant);
        return eventParticipantMapper.toDto(eventParticipant);
    }

    @Override
    public Optional<EventParticipantDTO> partialUpdate(EventParticipantDTO eventParticipantDTO) {
        log.debug("Request to partially update EventParticipant : {}", eventParticipantDTO);

        return eventParticipantRepository
            .findById(eventParticipantDTO.getId())
            .map(existingEventParticipant -> {
                eventParticipantMapper.partialUpdate(existingEventParticipant, eventParticipantDTO);

                return existingEventParticipant;
            })
            .map(eventParticipantRepository::save)
            .map(eventParticipantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventParticipants");
        return eventParticipantRepository.findAll(pageable).map(eventParticipantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventParticipantDTO> findOne(Long id) {
        log.debug("Request to get EventParticipant : {}", id);
        return eventParticipantRepository.findById(id).map(eventParticipantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventParticipant : {}", id);
        eventParticipantRepository.deleteById(id);
    }
}
