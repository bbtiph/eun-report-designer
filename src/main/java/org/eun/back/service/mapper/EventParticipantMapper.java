package org.eun.back.service.mapper;

import org.eun.back.domain.Event;
import org.eun.back.domain.EventParticipant;
import org.eun.back.domain.Person;
import org.eun.back.service.dto.EventDTO;
import org.eun.back.service.dto.EventParticipantDTO;
import org.eun.back.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventParticipant} and its DTO {@link EventParticipantDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventParticipantMapper extends EntityMapper<EventParticipantDTO, EventParticipant> {
    @Mapping(target = "event", source = "event", qualifiedByName = "eventId")
    @Mapping(target = "person", source = "person", qualifiedByName = "personId")
    EventParticipantDTO toDto(EventParticipant s);

    @Named("eventId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDTO toDtoEventId(Event event);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonDTO toDtoPersonId(Person person);
}
