package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.PersonEunIndicator;
import org.eun.back.repository.PersonEunIndicatorRepository;
import org.eun.back.service.PersonEunIndicatorService;
import org.eun.back.service.dto.PersonEunIndicatorDTO;
import org.eun.back.service.mapper.PersonEunIndicatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonEunIndicator}.
 */
@Service
@Transactional
public class PersonEunIndicatorServiceImpl implements PersonEunIndicatorService {

    private final Logger log = LoggerFactory.getLogger(PersonEunIndicatorServiceImpl.class);

    private final PersonEunIndicatorRepository personEunIndicatorRepository;

    private final PersonEunIndicatorMapper personEunIndicatorMapper;

    public PersonEunIndicatorServiceImpl(
        PersonEunIndicatorRepository personEunIndicatorRepository,
        PersonEunIndicatorMapper personEunIndicatorMapper
    ) {
        this.personEunIndicatorRepository = personEunIndicatorRepository;
        this.personEunIndicatorMapper = personEunIndicatorMapper;
    }

    @Override
    public PersonEunIndicatorDTO save(PersonEunIndicatorDTO personEunIndicatorDTO) {
        log.debug("Request to save PersonEunIndicator : {}", personEunIndicatorDTO);
        PersonEunIndicator personEunIndicator = personEunIndicatorMapper.toEntity(personEunIndicatorDTO);
        personEunIndicator = personEunIndicatorRepository.save(personEunIndicator);
        return personEunIndicatorMapper.toDto(personEunIndicator);
    }

    @Override
    public PersonEunIndicatorDTO update(PersonEunIndicatorDTO personEunIndicatorDTO) {
        log.debug("Request to update PersonEunIndicator : {}", personEunIndicatorDTO);
        PersonEunIndicator personEunIndicator = personEunIndicatorMapper.toEntity(personEunIndicatorDTO);
        personEunIndicator = personEunIndicatorRepository.save(personEunIndicator);
        return personEunIndicatorMapper.toDto(personEunIndicator);
    }

    @Override
    public Optional<PersonEunIndicatorDTO> partialUpdate(PersonEunIndicatorDTO personEunIndicatorDTO) {
        log.debug("Request to partially update PersonEunIndicator : {}", personEunIndicatorDTO);

        return personEunIndicatorRepository
            .findById(personEunIndicatorDTO.getId())
            .map(existingPersonEunIndicator -> {
                personEunIndicatorMapper.partialUpdate(existingPersonEunIndicator, personEunIndicatorDTO);

                return existingPersonEunIndicator;
            })
            .map(personEunIndicatorRepository::save)
            .map(personEunIndicatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonEunIndicatorDTO> findAll() {
        log.debug("Request to get all PersonEunIndicators");
        return personEunIndicatorRepository
            .findAll()
            .stream()
            .map(personEunIndicatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonEunIndicatorDTO> findOne(Long id) {
        log.debug("Request to get PersonEunIndicator : {}", id);
        return personEunIndicatorRepository.findById(id).map(personEunIndicatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonEunIndicator : {}", id);
        personEunIndicatorRepository.deleteById(id);
    }
}
