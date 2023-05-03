package org.eun.back.service.mapper;

import org.eun.back.domain.OrganizationInProject;
import org.eun.back.domain.PersonInProject;
import org.eun.back.domain.Project;
import org.eun.back.service.dto.OrganizationInProjectDTO;
import org.eun.back.service.dto.PersonInProjectDTO;
import org.eun.back.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "organizationInProject", source = "organizationInProject", qualifiedByName = "organizationInProjectId")
    @Mapping(target = "personInProject", source = "personInProject", qualifiedByName = "personInProjectId")
    ProjectDTO toDto(Project s);

    @Named("organizationInProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationInProjectDTO toDtoOrganizationInProjectId(OrganizationInProject organizationInProject);

    @Named("personInProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonInProjectDTO toDtoPersonInProjectId(PersonInProject personInProject);
}
