package org.eun.back.service.mapper;

import org.eun.back.domain.Funding;
import org.eun.back.domain.Project;
import org.eun.back.service.dto.FundingDTO;
import org.eun.back.service.dto.ProjectDTO;
import org.eun.back.service.dto.ProjectIndicatorDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectIndicatorMapper extends EntityMapper<ProjectIndicatorDTO, Project> {}
