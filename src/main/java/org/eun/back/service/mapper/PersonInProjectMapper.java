package org.eun.back.service.mapper;

import org.eun.back.domain.PersonInProject;
import org.eun.back.service.dto.PersonInProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonInProject} and its DTO {@link PersonInProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonInProjectMapper extends EntityMapper<PersonInProjectDTO, PersonInProject> {}
