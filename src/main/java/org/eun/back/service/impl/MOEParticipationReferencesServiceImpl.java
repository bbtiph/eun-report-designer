package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.MOEParticipationReferences;
import org.eun.back.domain.RelEventReferencesCountries;
import org.eun.back.domain.RelMOEParticipationReferencesCountries;
import org.eun.back.repository.MOEParticipationReferencesRepository;
import org.eun.back.repository.RelMOEParticipantionReferencesCountriesRepository;
import org.eun.back.service.MOEParticipationReferencesService;
import org.eun.back.service.dto.CountriesWithMoeRepresentativesDTO;
import org.eun.back.service.dto.CountriesWithParticipantsDTO;
import org.eun.back.service.dto.EventReferencesDTO;
import org.eun.back.service.dto.MOEParticipationReferencesDTO;
import org.eun.back.service.mapper.MOEParticipationReferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MOEParticipationReferences}.
 */
@Service
@Transactional
public class MOEParticipationReferencesServiceImpl implements MOEParticipationReferencesService {

    private final Logger log = LoggerFactory.getLogger(MOEParticipationReferencesServiceImpl.class);

    private final MOEParticipationReferencesRepository mOEParticipationReferencesRepository;

    private final MOEParticipationReferencesMapper mOEParticipationReferencesMapper;
    private final RelMOEParticipantionReferencesCountriesRepository relMOEParticipantionReferencesCountriesRepository;

    public MOEParticipationReferencesServiceImpl(
        MOEParticipationReferencesRepository mOEParticipationReferencesRepository,
        MOEParticipationReferencesMapper mOEParticipationReferencesMapper,
        RelMOEParticipantionReferencesCountriesRepository relMOEParticipantionReferencesCountriesRepository
    ) {
        this.mOEParticipationReferencesRepository = mOEParticipationReferencesRepository;
        this.mOEParticipationReferencesMapper = mOEParticipationReferencesMapper;
        this.relMOEParticipantionReferencesCountriesRepository = relMOEParticipantionReferencesCountriesRepository;
    }

    @Override
    public MOEParticipationReferencesDTO save(MOEParticipationReferencesDTO mOEParticipationReferencesDTO) {
        log.debug("Request to save MOEParticipationReferences : {}", mOEParticipationReferencesDTO);
        MOEParticipationReferences mOEParticipationReferences = mOEParticipationReferencesMapper.toEntity(mOEParticipationReferencesDTO);
        mOEParticipationReferences = mOEParticipationReferencesRepository.save(mOEParticipationReferences);
        return mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);
    }

    @Override
    public MOEParticipationReferencesDTO update(MOEParticipationReferencesDTO mOEParticipationReferencesDTO) {
        log.debug("Request to update MOEParticipationReferences : {}", mOEParticipationReferencesDTO);
        MOEParticipationReferences mOEParticipationReferences = mOEParticipationReferencesMapper.toEntity(mOEParticipationReferencesDTO);
        mOEParticipationReferences = mOEParticipationReferencesRepository.save(mOEParticipationReferences);
        return mOEParticipationReferencesMapper.toDto(mOEParticipationReferences);
    }

    @Override
    public Optional<MOEParticipationReferencesDTO> partialUpdate(MOEParticipationReferencesDTO mOEParticipationReferencesDTO) {
        log.debug("Request to partially update MOEParticipationReferences : {}", mOEParticipationReferencesDTO);

        return mOEParticipationReferencesRepository
            .findById(mOEParticipationReferencesDTO.getId())
            .map(existingMOEParticipationReferences -> {
                mOEParticipationReferencesMapper.partialUpdate(existingMOEParticipationReferences, mOEParticipationReferencesDTO);

                return existingMOEParticipationReferences;
            })
            .map(mOEParticipationReferencesRepository::save)
            .map(mOEParticipationReferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MOEParticipationReferencesDTO> findAll() {
        log.debug("Request to get all MOEParticipationReferences");
        return mOEParticipationReferencesRepository
            .findAll()
            .stream()
            .map(mOEParticipationReferencesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<MOEParticipationReferencesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mOEParticipationReferencesRepository.findAllWithEagerRelationships(pageable).map(mOEParticipationReferencesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MOEParticipationReferencesDTO> findOne(Long id) {
        log.debug("Request to get MOEParticipationReferences : {}", id);
        return mOEParticipationReferencesRepository.findOneWithEagerRelationships(id).map(mOEParticipationReferencesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MOEParticipationReferences : {}", id);
        mOEParticipationReferencesRepository.deleteById(id);
    }

    @Override
    public List<MOEParticipationReferencesDTO> findAllByIsActive(Boolean isActive) {
        List<MOEParticipationReferencesDTO> res = mOEParticipationReferencesRepository
            .findAllByIsActive(isActive)
            .stream()
            .map(mOEParticipationReferencesMapper::toDto)
            .collect(Collectors.toList());

        for (MOEParticipationReferencesDTO moeParticipationReferencesDTO : res) {
            Long totalMoeRepresentativesCount = 0L;
            for (CountriesWithMoeRepresentativesDTO countries : moeParticipationReferencesDTO.getCountries()) {
                RelMOEParticipationReferencesCountries relMOEParticipationReferencesCountries = relMOEParticipantionReferencesCountriesRepository
                    .findFirstByCountriesIdAndMoeParticipationReferencesId(countries.getId(), moeParticipationReferencesDTO.getId())
                    .get();
                countries.setMoeRepresentatives(relMOEParticipationReferencesCountries.getMoeRepresentatives());
                countries.setType(relMOEParticipationReferencesCountries.getType());
                totalMoeRepresentativesCount += countries.getMoeRepresentatives() != null ? countries.getMoeRepresentatives() : 0L;
            }
            moeParticipationReferencesDTO.setTotalMoeRepresentativesCount(totalMoeRepresentativesCount);
        }

        return res;
    }

    @Override
    public List<MOEParticipationReferencesDTO> findAllByCountryId(Long countryId) {
        List<RelMOEParticipationReferencesCountries> relEventReferencesCountries = relMOEParticipantionReferencesCountriesRepository.findFirstByCountriesId(
            countryId
        );

        List<Long> eventReferencesIds = relEventReferencesCountries
            .stream()
            .map(RelMOEParticipationReferencesCountries::getMoeParticipationReferencesId)
            .collect(Collectors.toList());

        List<MOEParticipationReferencesDTO> eventReferencesDTOS = mOEParticipationReferencesRepository
            .findAllByIdIn(eventReferencesIds)
            .stream()
            .map(mOEParticipationReferencesMapper::toDto)
            .collect(Collectors.toList());

        for (RelMOEParticipationReferencesCountries relMOEParticipationReferencesCountries : relEventReferencesCountries) {
            Long moeParticipationReferencesId = relMOEParticipationReferencesCountries.getMoeParticipationReferencesId();
            Long participantsCount = relMOEParticipationReferencesCountries.getMoeRepresentatives() != null
                ? relMOEParticipationReferencesCountries.getMoeRepresentatives()
                : 0;

            eventReferencesDTOS
                .stream()
                .filter(dto -> dto.getId().equals(moeParticipationReferencesId))
                .findFirst()
                .ifPresent(dto -> dto.setTotalMoeRepresentativesCount(participantsCount));
        }

        return eventReferencesDTOS;
    }
}
