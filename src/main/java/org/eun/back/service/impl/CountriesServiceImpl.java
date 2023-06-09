package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.Countries;
import org.eun.back.repository.CountriesRepository;
import org.eun.back.service.CountriesService;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.mapper.CountriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Countries}.
 */
@Service
@Transactional
public class CountriesServiceImpl implements CountriesService {

    private final Logger log = LoggerFactory.getLogger(CountriesServiceImpl.class);

    private final CountriesRepository countriesRepository;

    private final CountriesMapper countriesMapper;

    public CountriesServiceImpl(CountriesRepository countriesRepository, CountriesMapper countriesMapper) {
        this.countriesRepository = countriesRepository;
        this.countriesMapper = countriesMapper;
    }

    @Override
    public CountriesDTO save(CountriesDTO countriesDTO) {
        log.debug("Request to save Countries : {}", countriesDTO);
        Countries countries = countriesMapper.toEntity(countriesDTO);
        countries = countriesRepository.save(countries);
        return countriesMapper.toDto(countries);
    }

    @Override
    public CountriesDTO update(CountriesDTO countriesDTO) {
        log.debug("Request to update Countries : {}", countriesDTO);
        Countries countries = countriesMapper.toEntity(countriesDTO);
        countries = countriesRepository.save(countries);
        return countriesMapper.toDto(countries);
    }

    @Override
    public Optional<CountriesDTO> partialUpdate(CountriesDTO countriesDTO) {
        log.debug("Request to partially update Countries : {}", countriesDTO);

        return countriesRepository
            .findById(countriesDTO.getId())
            .map(existingCountries -> {
                countriesMapper.partialUpdate(existingCountries, countriesDTO);

                return existingCountries;
            })
            .map(countriesRepository::save)
            .map(countriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countriesRepository.findAll(pageable).map(countriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CountriesDTO> findOne(Long id) {
        log.debug("Request to get Countries : {}", id);
        return countriesRepository.findById(id).map(countriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Countries : {}", id);
        countriesRepository.deleteById(id);
    }
}
