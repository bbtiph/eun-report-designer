package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ReferenceTableSettings;
import org.eun.back.repository.MoeContactsRepository;
import org.eun.back.repository.ReferenceTableSettingsRepository;
import org.eun.back.repository.WorkingGroupReferencesRepository;
import org.eun.back.service.ReferenceTableSettingsService;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.eun.back.service.mapper.ReferenceTableSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReferenceTableSettings}.
 */
@Service
@Transactional
public class ReferenceTableSettingsServiceImpl implements ReferenceTableSettingsService {

    private final Logger log = LoggerFactory.getLogger(ReferenceTableSettingsServiceImpl.class);

    private final ReferenceTableSettingsRepository referenceTableSettingsRepository;

    private final ReferenceTableSettingsMapper referenceTableSettingsMapper;

    private final WorkingGroupReferencesRepository workingGroupReferencesRepository;

    private final MoeContactsRepository moeContactsRepository;

    public ReferenceTableSettingsServiceImpl(
        ReferenceTableSettingsRepository referenceTableSettingsRepository,
        ReferenceTableSettingsMapper referenceTableSettingsMapper,
        WorkingGroupReferencesRepository workingGroupReferencesRepository,
        MoeContactsRepository moeContactsRepository
    ) {
        this.referenceTableSettingsRepository = referenceTableSettingsRepository;
        this.referenceTableSettingsMapper = referenceTableSettingsMapper;
        this.workingGroupReferencesRepository = workingGroupReferencesRepository;
        this.moeContactsRepository = moeContactsRepository;
    }

    @Override
    public ReferenceTableSettingsDTO save(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
        log.debug("Request to save ReferenceTableSettings : {}", referenceTableSettingsDTO);
        ReferenceTableSettings referenceTableSettings = referenceTableSettingsMapper.toEntity(referenceTableSettingsDTO);
        referenceTableSettings = referenceTableSettingsRepository.save(referenceTableSettings);
        return referenceTableSettingsMapper.toDto(referenceTableSettings);
    }

    @Override
    public ReferenceTableSettingsDTO update(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
        log.debug("Request to update ReferenceTableSettings : {}", referenceTableSettingsDTO);
        ReferenceTableSettings referenceTableSettings = referenceTableSettingsMapper.toEntity(referenceTableSettingsDTO);
        referenceTableSettings = referenceTableSettingsRepository.save(referenceTableSettings);
        return referenceTableSettingsMapper.toDto(referenceTableSettings);
    }

    @Override
    public Optional<ReferenceTableSettingsDTO> partialUpdate(ReferenceTableSettingsDTO referenceTableSettingsDTO) {
        log.debug("Request to partially update ReferenceTableSettings : {}", referenceTableSettingsDTO);

        return referenceTableSettingsRepository
            .findById(referenceTableSettingsDTO.getId())
            .map(existingReferenceTableSettings -> {
                referenceTableSettingsMapper.partialUpdate(existingReferenceTableSettings, referenceTableSettingsDTO);

                return existingReferenceTableSettings;
            })
            .map(referenceTableSettingsRepository::save)
            .map(referenceTableSettingsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferenceTableSettingsDTO> findAll() {
        log.debug("Request to get all ReferenceTableSettings");
        return referenceTableSettingsRepository
            .findAll()
            .stream()
            .map(referenceTableSettingsMapper::toDtoToShowInHomePage)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<?> findAllDataByRefTable(String refTable) {
        log.debug("Request to get all ReferenceTableSettings Data");
        switch (refTable) {
            case "moe_contacts_reference":
            case "moe_contacts":
                return moeContactsRepository.findAll();
            case "working_group_reference":
            case "working_group":
                return workingGroupReferencesRepository.findAllByIsActive(true);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReferenceTableSettingsDTO> findOne(Long id) {
        log.debug("Request to get ReferenceTableSettings : {}", id);
        return referenceTableSettingsRepository.findById(id).map(referenceTableSettingsMapper::toDto);
    }

    @Override
    public Optional<ReferenceTableSettingsDTO> findOneByRefTable(String refTable) {
        return referenceTableSettingsRepository.findByRefTable(refTable).map(referenceTableSettingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReferenceTableSettings : {}", id);
        referenceTableSettingsRepository.deleteById(id);
    }
}
