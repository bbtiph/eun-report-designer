package org.eun.back.service.mapper;

import org.eun.back.domain.OrganizationEunIndicator;
import org.eun.back.service.dto.OrganizationEunIndicatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationEunIndicator} and its DTO {@link OrganizationEunIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationEunIndicatorMapper extends EntityMapper<OrganizationEunIndicatorDTO, OrganizationEunIndicator> {}
