package org.eun.back.service.mapper;

import org.eun.back.domain.Event;
import org.eun.back.service.dto.EventIndicatorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventIndicatorMapper extends EntityMapper<EventIndicatorDTO, Event> {}
