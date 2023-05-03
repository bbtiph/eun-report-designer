package org.eun.back.service.mapper;

import org.eun.back.domain.EventParticipant;
import org.eun.back.service.dto.EventParticipantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventParticipant} and its DTO {@link EventParticipantDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventParticipantMapper extends EntityMapper<EventParticipantDTO, EventParticipant> {}
