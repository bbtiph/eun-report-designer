package org.eun.back.service.mapper;

import org.eun.back.domain.Event;
import org.eun.back.service.dto.EventDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMapper extends EntityMapper<EventDTO, Event> {}
