package org.eun.back.service.impl;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.Event;
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.repository.EventRepository;
import org.eun.back.service.EventService;
import org.eun.back.service.dto.EventDTO;
import org.eun.back.service.dto.EventIndicatorDTO;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.WorkingGroupReferencesIndicatorDTO;
import org.eun.back.service.mapper.EventIndicatorMapper;
import org.eun.back.service.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Event}.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final EventIndicatorMapper eventIndicatorMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, EventIndicatorMapper eventIndicatorMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.eventIndicatorMapper = eventIndicatorMapper;
    }

    @Override
    public Indicator<?> getIndicator(Long countryId, Long reportId) {
        List<Event> data = this.eventRepository.findAll();
        Indicator<EventIndicatorDTO> indicator = new Indicator<>();
        indicator.setData(eventIndicatorMapper.toDto(data));
        indicator.setCode("events");
        indicator.setLabel("Events");
        indicator.setValue(String.valueOf(data.size()));
        return indicator;
    }

    @Override
    public EventDTO save(EventDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    public EventDTO update(EventDTO eventDTO) {
        log.debug("Request to update Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    public Optional<EventDTO> partialUpdate(EventDTO eventDTO) {
        log.debug("Request to partially update Event : {}", eventDTO);

        return eventRepository
            .findById(eventDTO.getId())
            .map(existingEvent -> {
                eventMapper.partialUpdate(existingEvent, eventDTO);

                return existingEvent;
            })
            .map(eventRepository::save)
            .map(eventMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable).map(eventMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventDTO> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id).map(eventMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }
}
