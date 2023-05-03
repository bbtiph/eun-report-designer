package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.EventInOrganization;
import org.eun.back.repository.EventInOrganizationRepository;
import org.eun.back.service.EventInOrganizationService;
import org.eun.back.service.dto.EventInOrganizationDTO;
import org.eun.back.service.mapper.EventInOrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventInOrganization}.
 */
@Service
@Transactional
public class EventInOrganizationServiceImpl implements EventInOrganizationService {

    private final Logger log = LoggerFactory.getLogger(EventInOrganizationServiceImpl.class);

    private final EventInOrganizationRepository eventInOrganizationRepository;

    private final EventInOrganizationMapper eventInOrganizationMapper;

    public EventInOrganizationServiceImpl(
        EventInOrganizationRepository eventInOrganizationRepository,
        EventInOrganizationMapper eventInOrganizationMapper
    ) {
        this.eventInOrganizationRepository = eventInOrganizationRepository;
        this.eventInOrganizationMapper = eventInOrganizationMapper;
    }

    @Override
    public EventInOrganizationDTO save(EventInOrganizationDTO eventInOrganizationDTO) {
        log.debug("Request to save EventInOrganization : {}", eventInOrganizationDTO);
        EventInOrganization eventInOrganization = eventInOrganizationMapper.toEntity(eventInOrganizationDTO);
        eventInOrganization = eventInOrganizationRepository.save(eventInOrganization);
        return eventInOrganizationMapper.toDto(eventInOrganization);
    }

    @Override
    public EventInOrganizationDTO update(EventInOrganizationDTO eventInOrganizationDTO) {
        log.debug("Request to update EventInOrganization : {}", eventInOrganizationDTO);
        EventInOrganization eventInOrganization = eventInOrganizationMapper.toEntity(eventInOrganizationDTO);
        eventInOrganization = eventInOrganizationRepository.save(eventInOrganization);
        return eventInOrganizationMapper.toDto(eventInOrganization);
    }

    @Override
    public Optional<EventInOrganizationDTO> partialUpdate(EventInOrganizationDTO eventInOrganizationDTO) {
        log.debug("Request to partially update EventInOrganization : {}", eventInOrganizationDTO);

        return eventInOrganizationRepository
            .findById(eventInOrganizationDTO.getId())
            .map(existingEventInOrganization -> {
                eventInOrganizationMapper.partialUpdate(existingEventInOrganization, eventInOrganizationDTO);

                return existingEventInOrganization;
            })
            .map(eventInOrganizationRepository::save)
            .map(eventInOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventInOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventInOrganizations");
        return eventInOrganizationRepository.findAll(pageable).map(eventInOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventInOrganizationDTO> findOne(Long id) {
        log.debug("Request to get EventInOrganization : {}", id);
        return eventInOrganizationRepository.findById(id).map(eventInOrganizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventInOrganization : {}", id);
        eventInOrganizationRepository.deleteById(id);
    }
}
