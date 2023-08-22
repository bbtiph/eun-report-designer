package org.eun.back.service.impl;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.Project;
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.repository.ProjectRepository;
import org.eun.back.service.ProjectService;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.ProjectDTO;
import org.eun.back.service.dto.ProjectIndicatorDTO;
import org.eun.back.service.dto.WorkingGroupReferencesIndicatorDTO;
import org.eun.back.service.mapper.ProjectIndicatorMapper;
import org.eun.back.service.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Project}.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private final ProjectIndicatorMapper projectIndicatorMapper;

    public ProjectServiceImpl(
        ProjectRepository projectRepository,
        ProjectMapper projectMapper,
        ProjectIndicatorMapper projectIndicatorMapper
    ) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectIndicatorMapper = projectIndicatorMapper;
    }

    @Override
    public Indicator<?> getIndicator(Long countryId, Long reportId) {
        List<Project> data = this.projectRepository.findAll();
        Indicator<ProjectIndicatorDTO> indicator = new Indicator<>();
        Long value = 0L;
        for (Project project : data) {
            value += project.getTotalBudget() != null ? project.getTotalBudget() : 0L;
        }
        indicator.setData(projectIndicatorMapper.toDto(data));
        indicator.setCode("EC_funded_budget");
        indicator.setLabel("EC Funded projects budget");
        indicator.setValue(value.toString());
        return indicator;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO) {
        log.debug("Request to update Project : {}", projectDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public Optional<ProjectDTO> partialUpdate(ProjectDTO projectDTO) {
        log.debug("Request to partially update Project : {}", projectDTO);

        return projectRepository
            .findById(projectDTO.getId())
            .map(existingProject -> {
                projectMapper.partialUpdate(existingProject, projectDTO);

                return existingProject;
            })
            .map(projectRepository::save)
            .map(projectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id).map(projectMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }
}
