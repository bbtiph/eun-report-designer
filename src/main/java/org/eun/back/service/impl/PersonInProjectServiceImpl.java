package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.PersonInProject;
import org.eun.back.repository.PersonInProjectRepository;
import org.eun.back.service.PersonInProjectService;
import org.eun.back.service.dto.PersonInProjectDTO;
import org.eun.back.service.mapper.PersonInProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonInProject}.
 */
@Service
@Transactional
public class PersonInProjectServiceImpl implements PersonInProjectService {

    private final Logger log = LoggerFactory.getLogger(PersonInProjectServiceImpl.class);

    private final PersonInProjectRepository personInProjectRepository;

    private final PersonInProjectMapper personInProjectMapper;

    public PersonInProjectServiceImpl(PersonInProjectRepository personInProjectRepository, PersonInProjectMapper personInProjectMapper) {
        this.personInProjectRepository = personInProjectRepository;
        this.personInProjectMapper = personInProjectMapper;
    }

    @Override
    public PersonInProjectDTO save(PersonInProjectDTO personInProjectDTO) {
        log.debug("Request to save PersonInProject : {}", personInProjectDTO);
        PersonInProject personInProject = personInProjectMapper.toEntity(personInProjectDTO);
        personInProject = personInProjectRepository.save(personInProject);
        return personInProjectMapper.toDto(personInProject);
    }

    @Override
    public PersonInProjectDTO update(PersonInProjectDTO personInProjectDTO) {
        log.debug("Request to update PersonInProject : {}", personInProjectDTO);
        PersonInProject personInProject = personInProjectMapper.toEntity(personInProjectDTO);
        personInProject = personInProjectRepository.save(personInProject);
        return personInProjectMapper.toDto(personInProject);
    }

    @Override
    public Optional<PersonInProjectDTO> partialUpdate(PersonInProjectDTO personInProjectDTO) {
        log.debug("Request to partially update PersonInProject : {}", personInProjectDTO);

        return personInProjectRepository
            .findById(personInProjectDTO.getId())
            .map(existingPersonInProject -> {
                personInProjectMapper.partialUpdate(existingPersonInProject, personInProjectDTO);

                return existingPersonInProject;
            })
            .map(personInProjectRepository::save)
            .map(personInProjectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonInProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonInProjects");
        return personInProjectRepository.findAll(pageable).map(personInProjectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonInProjectDTO> findOne(Long id) {
        log.debug("Request to get PersonInProject : {}", id);
        return personInProjectRepository.findById(id).map(personInProjectMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonInProject : {}", id);
        personInProjectRepository.deleteById(id);
    }
}
