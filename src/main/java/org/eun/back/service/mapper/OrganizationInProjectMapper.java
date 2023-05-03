package org.eun.back.service.mapper;

import org.eun.back.domain.Organization;
import org.eun.back.domain.OrganizationInProject;
import org.eun.back.domain.Project;
import org.eun.back.service.dto.OrganizationDTO;
import org.eun.back.service.dto.OrganizationInProjectDTO;
import org.eun.back.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationInProject} and its DTO {@link OrganizationInProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationInProjectMapper extends EntityMapper<OrganizationInProjectDTO, OrganizationInProject> {
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    OrganizationInProjectDTO toDto(OrganizationInProject s);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
