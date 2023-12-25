package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.EventReferencesParticipantsCategory;
import org.eun.back.repository.EventReferencesParticipantsCategoryRepository;
import org.eun.back.service.EventReferencesParticipantsCategoryService;
import org.eun.back.service.dto.EventReferencesParticipantsCategoryDTO;
import org.eun.back.service.mapper.EventReferencesParticipantsCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventReferencesParticipantsCategory}.
 */
@Service
@Transactional
public class EventReferencesParticipantsCategoryServiceImpl implements EventReferencesParticipantsCategoryService {

    private final Logger log = LoggerFactory.getLogger(EventReferencesParticipantsCategoryServiceImpl.class);

    private final EventReferencesParticipantsCategoryRepository eventReferencesParticipantsCategoryRepository;

    private final EventReferencesParticipantsCategoryMapper eventReferencesParticipantsCategoryMapper;

    public EventReferencesParticipantsCategoryServiceImpl(
        EventReferencesParticipantsCategoryRepository eventReferencesParticipantsCategoryRepository,
        EventReferencesParticipantsCategoryMapper eventReferencesParticipantsCategoryMapper
    ) {
        this.eventReferencesParticipantsCategoryRepository = eventReferencesParticipantsCategoryRepository;
        this.eventReferencesParticipantsCategoryMapper = eventReferencesParticipantsCategoryMapper;
    }

    @Override
    public EventReferencesParticipantsCategoryDTO save(EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO) {
        log.debug("Request to save EventReferencesParticipantsCategory : {}", eventReferencesParticipantsCategoryDTO);
        EventReferencesParticipantsCategory eventReferencesParticipantsCategory = eventReferencesParticipantsCategoryMapper.toEntity(
            eventReferencesParticipantsCategoryDTO
        );
        eventReferencesParticipantsCategory = eventReferencesParticipantsCategoryRepository.save(eventReferencesParticipantsCategory);
        return eventReferencesParticipantsCategoryMapper.toDto(eventReferencesParticipantsCategory);
    }

    @Override
    public EventReferencesParticipantsCategoryDTO update(EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO) {
        log.debug("Request to update EventReferencesParticipantsCategory : {}", eventReferencesParticipantsCategoryDTO);
        EventReferencesParticipantsCategory eventReferencesParticipantsCategory = eventReferencesParticipantsCategoryMapper.toEntity(
            eventReferencesParticipantsCategoryDTO
        );
        eventReferencesParticipantsCategory = eventReferencesParticipantsCategoryRepository.save(eventReferencesParticipantsCategory);
        return eventReferencesParticipantsCategoryMapper.toDto(eventReferencesParticipantsCategory);
    }

    @Override
    public Optional<EventReferencesParticipantsCategoryDTO> partialUpdate(
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO
    ) {
        log.debug("Request to partially update EventReferencesParticipantsCategory : {}", eventReferencesParticipantsCategoryDTO);

        return eventReferencesParticipantsCategoryRepository
            .findById(eventReferencesParticipantsCategoryDTO.getId())
            .map(existingEventReferencesParticipantsCategory -> {
                eventReferencesParticipantsCategoryMapper.partialUpdate(
                    existingEventReferencesParticipantsCategory,
                    eventReferencesParticipantsCategoryDTO
                );

                return existingEventReferencesParticipantsCategory;
            })
            .map(eventReferencesParticipantsCategoryRepository::save)
            .map(eventReferencesParticipantsCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventReferencesParticipantsCategoryDTO> findAll() {
        log.debug("Request to get all EventReferencesParticipantsCategories");
        return eventReferencesParticipantsCategoryRepository
            .findAll()
            .stream()
            .map(eventReferencesParticipantsCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventReferencesParticipantsCategoryDTO> findOne(Long id) {
        log.debug("Request to get EventReferencesParticipantsCategory : {}", id);
        return eventReferencesParticipantsCategoryRepository.findById(id).map(eventReferencesParticipantsCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventReferencesParticipantsCategory : {}", id);
        eventReferencesParticipantsCategoryRepository.deleteById(id);
    }
}
