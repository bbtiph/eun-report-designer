package org.eun.back.service.mapper;

import org.eun.back.domain.Event;
import org.eun.back.domain.EventInOrganization;
import org.eun.back.domain.EventParticipant;
import org.eun.back.service.dto.EventDTO;
import org.eun.back.service.dto.EventInOrganizationDTO;
import org.eun.back.service.dto.EventParticipantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {
    @Mapping(target = "eventInOrganization", source = "eventInOrganization", qualifiedByName = "eventInOrganizationId")
    @Mapping(target = "eventParticipant", source = "eventParticipant", qualifiedByName = "eventParticipantId")
    EventDTO toDto(Event s);

    @Named("eventInOrganizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventInOrganizationDTO toDtoEventInOrganizationId(EventInOrganization eventInOrganization);

    @Named("eventParticipantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventParticipantDTO toDtoEventParticipantId(EventParticipant eventParticipant);
}
