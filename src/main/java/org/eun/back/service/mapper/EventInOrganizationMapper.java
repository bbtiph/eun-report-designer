package org.eun.back.service.mapper;

import org.eun.back.domain.Event;
import org.eun.back.domain.EventInOrganization;
import org.eun.back.domain.Organization;
import org.eun.back.service.dto.EventDTO;
import org.eun.back.service.dto.EventInOrganizationDTO;
import org.eun.back.service.dto.OrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventInOrganization} and its DTO {@link EventInOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventInOrganizationMapper extends EntityMapper<EventInOrganizationDTO, EventInOrganization> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    EventInOrganizationDTO toDto(EventInOrganization s);

    @Named("eventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDTO toDtoEventId(Event event);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
