package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.EventReferences;
import org.eun.back.domain.RelEventReferencesCountries;
import org.eun.back.repository.EventReferencesRepository;
import org.eun.back.service.EventReferencesService;
import org.eun.back.service.RelEventReferencesCountriesService;
import org.eun.back.service.dto.CountriesWithParticipantsDTO;
import org.eun.back.service.dto.EventReferencesDTO;
import org.eun.back.service.mapper.EventReferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final RelEventReferencesCountriesService relEventReferencesCountriesService;

    public EventReferencesServiceImpl(
        EventReferencesRepository eventReferencesRepository,
        EventReferencesMapper eventReferencesMapper,
        RelEventReferencesCountriesService relEventReferencesCountriesService
    ) {
        this.eventReferencesRepository = eventReferencesRepository;
        this.eventReferencesMapper = eventReferencesMapper;
        this.relEventReferencesCountriesService = relEventReferencesCountriesService;
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

    public Page<EventReferencesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eventReferencesRepository.findAllWithEagerRelationships(pageable).map(eventReferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventReferencesDTO> findOne(Long id) {
        log.debug("Request to get EventReferences : {}", id);
        return eventReferencesRepository.findOneWithEagerRelationships(id).map(eventReferencesMapper::toDto);
    }

    @Override
    public Optional<EventReferencesDTO> findOneByCountryId(Long id, Long countryId) {
        Optional<EventReferencesDTO> response = eventReferencesRepository
            .findOneWithEagerRelationships(id)
            .map(eventReferencesMapper::toDto);
        Optional<RelEventReferencesCountries> relEventReferencesCountries = relEventReferencesCountriesService.findFirstByCountriesIdAndEventReferencesId(
            countryId,
            id
        );
        if (relEventReferencesCountries.isPresent()) response
            .get()
            .setParticipantsCount(
                relEventReferencesCountries.get().getParticipantsCount() != null
                    ? relEventReferencesCountries.get().getParticipantsCount()
                    : 0
            );
        return response;
    }

    @Override
    public List<EventReferencesDTO> findAllByCountryId(Long countryId) {
        List<RelEventReferencesCountries> relEventReferencesCountries = relEventReferencesCountriesService.findFirstByCountriesId(
            countryId
        );

        List<Long> eventReferencesIds = relEventReferencesCountries
            .stream()
            .map(RelEventReferencesCountries::getEventReferencesId)
            .collect(Collectors.toList());

        List<EventReferencesDTO> eventReferencesDTOS = eventReferencesRepository
            .findAllByIdIn(eventReferencesIds)
            .stream()
            .map(eventReferencesMapper::toDto)
            .collect(Collectors.toList());

        for (RelEventReferencesCountries relEventReferencesCountry : relEventReferencesCountries) {
            Long eventId = relEventReferencesCountry.getEventReferencesId();
            Long participantsCount = relEventReferencesCountry.getParticipantsCount() != null
                ? relEventReferencesCountry.getParticipantsCount()
                : 0;

            eventReferencesDTOS
                .stream()
                .filter(dto -> dto.getId().equals(eventId))
                .findFirst()
                .ifPresent(dto -> dto.setParticipantsCount(participantsCount));
        }

        return eventReferencesDTOS;
    }

    @Override
    public List<EventReferencesDTO> findAllByIsActive(Boolean isActive) {
        List<EventReferencesDTO> res = eventReferencesRepository
            .findAllByIsActive(isActive)
            .stream()
            .map(eventReferencesMapper::toDto)
            .collect(Collectors.toList());

        for (EventReferencesDTO eventReferencesDTO : res) {
            Long totalParticipantsCount = 0L;
            for (CountriesWithParticipantsDTO countries : eventReferencesDTO.getCountries()) {
                countries.setParticipantsCount(
                    relEventReferencesCountriesService
                        .findFirstByCountriesIdAndEventReferencesId(countries.getId(), eventReferencesDTO.getId())
                        .orElseThrow()
                        .getParticipantsCount()
                );
                totalParticipantsCount += countries.getParticipantsCount();
            }
            eventReferencesDTO.setParticipantsCount(totalParticipantsCount);
        }

        return res;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventReferences : {}", id);
        EventReferences eventReferences = eventReferencesRepository.findFirstById(id);
        eventReferences.setIsActive(false);
        eventReferencesRepository.save(eventReferences);
    }
}
