package org.eun.back.service.mapper;

import org.eun.back.domain.Organization;
import org.eun.back.service.dto.OrganizationIndicatorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationIndicatorMapper extends EntityMapper<OrganizationIndicatorDTO, Organization> {}
