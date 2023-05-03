package org.eun.back.service.mapper;

import org.eun.back.domain.OrganizationInProject;
import org.eun.back.service.dto.OrganizationInProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationInProject} and its DTO {@link OrganizationInProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationInProjectMapper extends EntityMapper<OrganizationInProjectDTO, OrganizationInProject> {}
