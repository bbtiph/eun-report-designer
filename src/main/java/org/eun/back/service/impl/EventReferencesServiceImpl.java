package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.EventReferences;
import org.eun.back.repository.EventReferencesRepository;
import org.eun.back.service.EventReferencesService;
import org.eun.back.service.dto.EventReferencesDTO;
import org.eun.back.service.mapper.EventReferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventReferences}.
 */
@Service
@Transactional
public class EventReferencesServiceImpl implements EventReferencesService {

    private final Logger log = LoggerFactory.getLogger(EventReferencesServiceImpl.class);

    private final EventReferencesRepository eventReferencesRepository;

    private final EventReferencesMapper eventReferencesMapper;

    public EventReferencesServiceImpl(EventReferencesRepository eventReferencesRepository, EventReferencesMapper eventReferencesMapper) {
        this.eventReferencesRepository = eventReferencesRepository;
        this.eventReferencesMapper = eventReferencesMapper;
    }

    @Override
    public EventReferencesDTO save(EventReferencesDTO eventReferencesDTO) {
        log.debug("Request to save EventReferences : {}", eventReferencesDTO);
        EventReferences eventReferences = eventReferencesMapper.toEntity(eventReferencesDTO);
        eventReferences = eventReferencesRepository.save(eventReferences);
        return eventReferencesMapper.toDto(eventReferences);
    }

    @Override
    public EventReferencesDTO update(EventReferencesDTO eventReferencesDTO) {
        log.debug("Request to update EventReferences : {}", eventReferencesDTO);
        EventReferences eventReferences = eventReferencesMapper.toEntity(eventReferencesDTO);
        eventReferences = eventReferencesRepository.save(eventReferences);
        return eventReferencesMapper.toDto(eventReferences);
    }

    @Override
    public Optional<EventReferencesDTO> partialUpdate(EventReferencesDTO eventReferencesDTO) {
        log.debug("Request to partially update EventReferences : {}", eventReferencesDTO);

        return eventReferencesRepository
            .findById(eventReferencesDTO.getId())
            .map(existingEventReferences -> {
                eventReferencesMapper.partialUpdate(existingEventReferences, eventReferencesDTO);

                return existingEventReferences;
            })
            .map(eventReferencesRepository::save)
            .map(eventReferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventReferencesDTO> findAll() {
        log.debug("Request to get all EventReferences");
        return eventReferencesRepository
            .findAll()
            .stream()
            .map(eventReferencesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventReferencesDTO> findOne(Long id) {
        log.debug("Request to get EventReferences : {}", id);
        return eventReferencesRepository.findById(id).map(eventReferencesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventReferences : {}", id);
        eventReferencesRepository.deleteById(id);
    }
}
