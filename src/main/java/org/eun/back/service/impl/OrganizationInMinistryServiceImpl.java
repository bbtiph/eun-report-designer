package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.OrganizationInMinistry;
import org.eun.back.repository.OrganizationInMinistryRepository;
import org.eun.back.service.OrganizationInMinistryService;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
import org.eun.back.service.mapper.OrganizationInMinistryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrganizationInMinistry}.
 */
@Service
@Transactional
public class OrganizationInMinistryServiceImpl implements OrganizationInMinistryService {

    private final Logger log = LoggerFactory.getLogger(OrganizationInMinistryServiceImpl.class);

    private final OrganizationInMinistryRepository organizationInMinistryRepository;

    private final OrganizationInMinistryMapper organizationInMinistryMapper;

    public OrganizationInMinistryServiceImpl(
        OrganizationInMinistryRepository organizationInMinistryRepository,
        OrganizationInMinistryMapper organizationInMinistryMapper
    ) {
        this.organizationInMinistryRepository = organizationInMinistryRepository;
        this.organizationInMinistryMapper = organizationInMinistryMapper;
    }

    @Override
    public OrganizationInMinistryDTO save(OrganizationInMinistryDTO organizationInMinistryDTO) {
        log.debug("Request to save OrganizationInMinistry : {}", organizationInMinistryDTO);
        OrganizationInMinistry organizationInMinistry = organizationInMinistryMapper.toEntity(organizationInMinistryDTO);
        organizationInMinistry = organizationInMinistryRepository.save(organizationInMinistry);
        return organizationInMinistryMapper.toDto(organizationInMinistry);
    }

    @Override
    public OrganizationInMinistryDTO update(OrganizationInMinistryDTO organizationInMinistryDTO) {
        log.debug("Request to update OrganizationInMinistry : {}", organizationInMinistryDTO);
        OrganizationInMinistry organizationInMinistry = organizationInMinistryMapper.toEntity(organizationInMinistryDTO);
        organizationInMinistry = organizationInMinistryRepository.save(organizationInMinistry);
        return organizationInMinistryMapper.toDto(organizationInMinistry);
    }

    @Override
    public Optional<OrganizationInMinistryDTO> partialUpdate(OrganizationInMinistryDTO organizationInMinistryDTO) {
        log.debug("Request to partially update OrganizationInMinistry : {}", organizationInMinistryDTO);

        return organizationInMinistryRepository
            .findById(organizationInMinistryDTO.getId())
            .map(existingOrganizationInMinistry -> {
                organizationInMinistryMapper.partialUpdate(existingOrganizationInMinistry, organizationInMinistryDTO);

                return existingOrganizationInMinistry;
            })
            .map(organizationInMinistryRepository::save)
            .map(organizationInMinistryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationInMinistryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationInMinistries");
        return organizationInMinistryRepository.findAll(pageable).map(organizationInMinistryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationInMinistryDTO> findOne(Long id) {
        log.debug("Request to get OrganizationInMinistry : {}", id);
        return organizationInMinistryRepository.findById(id).map(organizationInMinistryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationInMinistry : {}", id);
        organizationInMinistryRepository.deleteById(id);
    }
}
