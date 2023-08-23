package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.repository.OrganizationEunIndicatorRepository;
import org.eun.back.service.OrganizationEunIndicatorService;
import org.eun.back.service.dto.OrganizationEunIndicatorDTO;
import org.eun.back.service.mapper.OrganizationEunIndicatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrganizationEunIndicator}.
 */
@Service
@Transactional
public class OrganizationEunIndicatorServiceImpl implements OrganizationEunIndicatorService {

    private final Logger log = LoggerFactory.getLogger(OrganizationEunIndicatorServiceImpl.class);

    private final OrganizationEunIndicatorRepository organizationEunIndicatorRepository;

    private final OrganizationEunIndicatorMapper organizationEunIndicatorMapper;

    public OrganizationEunIndicatorServiceImpl(
        OrganizationEunIndicatorRepository organizationEunIndicatorRepository,
        OrganizationEunIndicatorMapper organizationEunIndicatorMapper
    ) {
        this.organizationEunIndicatorRepository = organizationEunIndicatorRepository;
        this.organizationEunIndicatorMapper = organizationEunIndicatorMapper;
    }

    @Override
    public OrganizationEunIndicatorDTO save(OrganizationEunIndicatorDTO organizationEunIndicatorDTO) {
        log.debug("Request to save OrganizationEunIndicator : {}", organizationEunIndicatorDTO);
        OrganizationEunIndicator organizationEunIndicator = organizationEunIndicatorMapper.toEntity(organizationEunIndicatorDTO);
        organizationEunIndicator = organizationEunIndicatorRepository.save(organizationEunIndicator);
        return organizationEunIndicatorMapper.toDto(organizationEunIndicator);
    }

    @Override
    public OrganizationEunIndicatorDTO update(OrganizationEunIndicatorDTO organizationEunIndicatorDTO) {
        log.debug("Request to update OrganizationEunIndicator : {}", organizationEunIndicatorDTO);
        OrganizationEunIndicator organizationEunIndicator = organizationEunIndicatorMapper.toEntity(organizationEunIndicatorDTO);
        organizationEunIndicator = organizationEunIndicatorRepository.save(organizationEunIndicator);
        return organizationEunIndicatorMapper.toDto(organizationEunIndicator);
    }

    @Override
    public Optional<OrganizationEunIndicatorDTO> partialUpdate(OrganizationEunIndicatorDTO organizationEunIndicatorDTO) {
        log.debug("Request to partially update OrganizationEunIndicator : {}", organizationEunIndicatorDTO);

        return organizationEunIndicatorRepository
            .findById(organizationEunIndicatorDTO.getId())
            .map(existingOrganizationEunIndicator -> {
                organizationEunIndicatorMapper.partialUpdate(existingOrganizationEunIndicator, organizationEunIndicatorDTO);

                return existingOrganizationEunIndicator;
            })
            .map(organizationEunIndicatorRepository::save)
            .map(organizationEunIndicatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationEunIndicatorDTO> findAll() {
        log.debug("Request to get all OrganizationEunIndicators");
        return organizationEunIndicatorRepository
            .findAll()
            .stream()
            .map(organizationEunIndicatorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationEunIndicatorDTO> findOne(Long id) {
        log.debug("Request to get OrganizationEunIndicator : {}", id);
        return organizationEunIndicatorRepository.findById(id).map(organizationEunIndicatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationEunIndicator : {}", id);
        organizationEunIndicatorRepository.deleteById(id);
    }
}
