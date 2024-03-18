package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.MOEParticipationReferences;
import org.eun.back.repository.MOEParticipationReferencesRepository;
import org.eun.back.service.MOEParticipationReferencesService;
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

    public MOEParticipationReferencesServiceImpl(
        MOEParticipationReferencesRepository mOEParticipationReferencesRepository,
        MOEParticipationReferencesMapper mOEParticipationReferencesMapper
    ) {
        this.mOEParticipationReferencesRepository = mOEParticipationReferencesRepository;
        this.mOEParticipationReferencesMapper = mOEParticipationReferencesMapper;
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
}
