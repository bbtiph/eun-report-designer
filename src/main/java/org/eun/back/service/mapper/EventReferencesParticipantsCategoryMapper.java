package org.eun.back.service.mapper;

import org.eun.back.domain.EventReferences;
import org.eun.back.domain.EventReferencesParticipantsCategory;
import org.eun.back.service.dto.EventReferencesDTO;
import org.eun.back.service.dto.EventReferencesParticipantsCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventReferencesParticipantsCategory} and its DTO {@link EventReferencesParticipantsCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventReferencesParticipantsCategoryMapper
    extends EntityMapper<EventReferencesParticipantsCategoryDTO, EventReferencesParticipantsCategory> {
    @Mapping(target = "eventReference", source = "eventReference", qualifiedByName = "eventReferencesId")
    EventReferencesParticipantsCategoryDTO toDto(EventReferencesParticipantsCategory s);

    @Named("eventReferencesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventReferencesDTO toDtoEventReferencesId(EventReferences eventReferences);
}
