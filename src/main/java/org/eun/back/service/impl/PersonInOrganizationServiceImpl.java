package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.PersonInOrganization;
import org.eun.back.repository.PersonInOrganizationRepository;
import org.eun.back.service.PersonInOrganizationService;
import org.eun.back.service.dto.PersonInOrganizationDTO;
import org.eun.back.service.mapper.PersonInOrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonInOrganization}.
 */
@Service
@Transactional
public class PersonInOrganizationServiceImpl implements PersonInOrganizationService {

    private final Logger log = LoggerFactory.getLogger(PersonInOrganizationServiceImpl.class);

    private final PersonInOrganizationRepository personInOrganizationRepository;

    private final PersonInOrganizationMapper personInOrganizationMapper;

    public PersonInOrganizationServiceImpl(
        PersonInOrganizationRepository personInOrganizationRepository,
        PersonInOrganizationMapper personInOrganizationMapper
    ) {
        this.personInOrganizationRepository = personInOrganizationRepository;
        this.personInOrganizationMapper = personInOrganizationMapper;
    }

    @Override
    public PersonInOrganizationDTO save(PersonInOrganizationDTO personInOrganizationDTO) {
        log.debug("Request to save PersonInOrganization : {}", personInOrganizationDTO);
        PersonInOrganization personInOrganization = personInOrganizationMapper.toEntity(personInOrganizationDTO);
        personInOrganization = personInOrganizationRepository.save(personInOrganization);
        return personInOrganizationMapper.toDto(personInOrganization);
    }

    @Override
    public PersonInOrganizationDTO update(PersonInOrganizationDTO personInOrganizationDTO) {
        log.debug("Request to update PersonInOrganization : {}", personInOrganizationDTO);
        PersonInOrganization personInOrganization = personInOrganizationMapper.toEntity(personInOrganizationDTO);
        personInOrganization = personInOrganizationRepository.save(personInOrganization);
        return personInOrganizationMapper.toDto(personInOrganization);
    }

    @Override
    public Optional<PersonInOrganizationDTO> partialUpdate(PersonInOrganizationDTO personInOrganizationDTO) {
        log.debug("Request to partially update PersonInOrganization : {}", personInOrganizationDTO);

        return personInOrganizationRepository
            .findById(personInOrganizationDTO.getId())
            .map(existingPersonInOrganization -> {
                personInOrganizationMapper.partialUpdate(existingPersonInOrganization, personInOrganizationDTO);

                return existingPersonInOrganization;
            })
            .map(personInOrganizationRepository::save)
            .map(personInOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonInOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonInOrganizations");
        return personInOrganizationRepository.findAll(pageable).map(personInOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonInOrganizationDTO> findOne(Long id) {
        log.debug("Request to get PersonInOrganization : {}", id);
        return personInOrganizationRepository.findById(id).map(personInOrganizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonInOrganization : {}", id);
        personInOrganizationRepository.deleteById(id);
    }
}
