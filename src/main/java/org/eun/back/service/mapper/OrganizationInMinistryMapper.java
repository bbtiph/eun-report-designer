package org.eun.back.service.mapper;

import org.eun.back.domain.OrganizationInMinistry;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationInMinistry} and its DTO {@link OrganizationInMinistryDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationInMinistryMapper extends EntityMapper<OrganizationInMinistryDTO, OrganizationInMinistry> {}
