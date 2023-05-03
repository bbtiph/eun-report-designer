package org.eun.back.service.mapper;

import org.eun.back.domain.EventInOrganization;
import org.eun.back.service.dto.EventInOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventInOrganization} and its DTO {@link EventInOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventInOrganizationMapper extends EntityMapper<EventInOrganizationDTO, EventInOrganization> {}
