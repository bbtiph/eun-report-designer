package org.eun.back.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eun.back.domain.ProjectPartner;
import org.eun.back.repository.ProjectPartnerRepository;
import org.eun.back.service.ProjectPartnerService;
import org.eun.back.service.dto.ProjectPartnerDTO;
import org.eun.back.service.mapper.ProjectPartnerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectPartner}.
 */
@Service
@Transactional
public class ProjectPartnerServiceImpl implements ProjectPartnerService {

    private final Logger log = LoggerFactory.getLogger(ProjectPartnerServiceImpl.class);

    private final ProjectPartnerRepository projectPartnerRepository;

    private final ProjectPartnerMapper projectPartnerMapper;

    public ProjectPartnerServiceImpl(ProjectPartnerRepository projectPartnerRepository, ProjectPartnerMapper projectPartnerMapper) {
        this.projectPartnerRepository = projectPartnerRepository;
        this.projectPartnerMapper = projectPartnerMapper;
    }

    @Override
    public ProjectPartnerDTO save(ProjectPartnerDTO projectPartnerDTO) {
        log.debug("Request to save ProjectPartner : {}", projectPartnerDTO);
        ProjectPartner projectPartner = projectPartnerMapper.toEntity(projectPartnerDTO);
        projectPartner = projectPartnerRepository.save(projectPartner);
        return projectPartnerMapper.toDto(projectPartner);
    }

    @Override
    public ProjectPartnerDTO update(ProjectPartnerDTO projectPartnerDTO) {
        log.debug("Request to update ProjectPartner : {}", projectPartnerDTO);
        ProjectPartner projectPartner = projectPartnerMapper.toEntity(projectPartnerDTO);
        projectPartner = projectPartnerRepository.save(projectPartner);
        return projectPartnerMapper.toDto(projectPartner);
    }

    @Override
    public Optional<ProjectPartnerDTO> partialUpdate(ProjectPartnerDTO projectPartnerDTO) {
        log.debug("Request to partially update ProjectPartner : {}", projectPartnerDTO);

        return projectPartnerRepository
            .findById(projectPartnerDTO.getId())
            .map(existingProjectPartner -> {
                projectPartnerMapper.partialUpdate(existingProjectPartner, projectPartnerDTO);

                return existingProjectPartner;
            })
            .map(projectPartnerRepository::save)
            .map(projectPartnerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectPartnerDTO> findAll() {
        log.debug("Request to get all ProjectPartners");
        return projectPartnerRepository
            .findAll()
            .stream()
            .map(projectPartnerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectPartnerDTO> findOne(Long id) {
        log.debug("Request to get ProjectPartner : {}", id);
        return projectPartnerRepository.findById(id).map(projectPartnerMapper::toDto);
    }

    @Override
    public ProjectPartnerDTO findFirstByCountryCodeAndVendorCode(Long id, String countryCode, String vendorCode) {
        return projectPartnerMapper.toDto(
            projectPartnerRepository.findFirstByJobInfoIdAndCountryCodeAndVendorCode(id, countryCode, vendorCode)
        );
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectPartner : {}", id);
        projectPartnerRepository.deleteById(id);
    }
}
