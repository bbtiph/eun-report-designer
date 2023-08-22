package org.eun.back.service.impl;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.Person;
import org.eun.back.domain.Project;
import org.eun.back.repository.PersonRepository;
import org.eun.back.service.PersonService;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.PersonDTO;
import org.eun.back.service.dto.PersonIndicatorDTO;
import org.eun.back.service.dto.ProjectIndicatorDTO;
import org.eun.back.service.mapper.PersonIndicatorMapper;
import org.eun.back.service.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Person}.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    private final PersonIndicatorMapper personIndicatorMapper;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper, PersonIndicatorMapper personIndicatorMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.personIndicatorMapper = personIndicatorMapper;
    }

    @Override
    public Indicator<?> getIndicator(Long countryId, Long reportId) {
        List<Person> data = this.personRepository.findAll();
        Indicator<PersonIndicatorDTO> indicator = new Indicator<>();
        indicator.setData(personIndicatorMapper.toDto(data));
        indicator.setCode("Teachers");
        indicator.setLabel("teachers");
        indicator.setValue(String.valueOf(data.size()));
        return indicator;
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        log.debug("Request to save Person : {}", personDTO);
        Person person = personMapper.toEntity(personDTO);
        person = personRepository.save(person);
        return personMapper.toDto(person);
    }

    @Override
    public PersonDTO update(PersonDTO personDTO) {
        log.debug("Request to update Person : {}", personDTO);
        Person person = personMapper.toEntity(personDTO);
        person = personRepository.save(person);
        return personMapper.toDto(person);
    }

    @Override
    public Optional<PersonDTO> partialUpdate(PersonDTO personDTO) {
        log.debug("Request to partially update Person : {}", personDTO);

        return personRepository
            .findById(personDTO.getId())
            .map(existingPerson -> {
                personMapper.partialUpdate(existingPerson, personDTO);

                return existingPerson;
            })
            .map(personRepository::save)
            .map(personMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all People");
        return personRepository.findAll(pageable).map(personMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonDTO> findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        return personRepository.findById(id).map(personMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.deleteById(id);
    }
}
