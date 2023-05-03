package org.eun.back.service.mapper;

import org.eun.back.domain.Person;
import org.eun.back.domain.PersonInProject;
import org.eun.back.domain.Project;
import org.eun.back.service.dto.PersonDTO;
import org.eun.back.service.dto.PersonInProjectDTO;
import org.eun.back.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonInProject} and its DTO {@link PersonInProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonInProjectMapper extends EntityMapper<PersonInProjectDTO, PersonInProject> {
    @Mapping(target = "person", source = "person", qualifiedByName = "personId")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    PersonInProjectDTO toDto(PersonInProject s);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonDTO toDtoPersonId(Person person);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);
}
