package org.eun.back.service.impl;

import java.util.Optional;
import org.eun.back.domain.OrganizationInProject;
import org.eun.back.repository.OrganizationInProjectRepository;
import org.eun.back.service.OrganizationInProjectService;
import org.eun.back.service.dto.OrganizationInProjectDTO;
import org.eun.back.service.mapper.OrganizationInProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrganizationInProject}.
 */
@Service
@Transactional
public class OrganizationInProjectServiceImpl implements OrganizationInProjectService {

    private final Logger log = LoggerFactory.getLogger(OrganizationInProjectServiceImpl.class);

    private final OrganizationInProjectRepository organizationInProjectRepository;

    private final OrganizationInProjectMapper organizationInProjectMapper;

    public OrganizationInProjectServiceImpl(
        OrganizationInProjectRepository organizationInProjectRepository,
        OrganizationInProjectMapper organizationInProjectMapper
    ) {
        this.organizationInProjectRepository = organizationInProjectRepository;
        this.organizationInProjectMapper = organizationInProjectMapper;
    }

    @Override
    public OrganizationInProjectDTO save(OrganizationInProjectDTO organizationInProjectDTO) {
        log.debug("Request to save OrganizationInProject : {}", organizationInProjectDTO);
        OrganizationInProject organizationInProject = organizationInProjectMapper.toEntity(organizationInProjectDTO);
        organizationInProject = organizationInProjectRepository.save(organizationInProject);
        return organizationInProjectMapper.toDto(organizationInProject);
    }

    @Override
    public OrganizationInProjectDTO update(OrganizationInProjectDTO organizationInProjectDTO) {
        log.debug("Request to update OrganizationInProject : {}", organizationInProjectDTO);
        OrganizationInProject organizationInProject = organizationInProjectMapper.toEntity(organizationInProjectDTO);
        organizationInProject = organizationInProjectRepository.save(organizationInProject);
        return organizationInProjectMapper.toDto(organizationInProject);
    }

    @Override
    public Optional<OrganizationInProjectDTO> partialUpdate(OrganizationInProjectDTO organizationInProjectDTO) {
        log.debug("Request to partially update OrganizationInProject : {}", organizationInProjectDTO);

        return organizationInProjectRepository
            .findById(organizationInProjectDTO.getId())
            .map(existingOrganizationInProject -> {
                organizationInProjectMapper.partialUpdate(existingOrganizationInProject, organizationInProjectDTO);

                return existingOrganizationInProject;
            })
            .map(organizationInProjectRepository::save)
            .map(organizationInProjectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationInProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationInProjects");
        return organizationInProjectRepository.findAll(pageable).map(organizationInProjectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationInProjectDTO> findOne(Long id) {
        log.debug("Request to get OrganizationInProject : {}", id);
        return organizationInProjectRepository.findById(id).map(organizationInProjectMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationInProject : {}", id);
        organizationInProjectRepository.deleteById(id);
    }
}
