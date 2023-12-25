package org.eun.back.service.mapper;

import org.eun.back.domain.EventReferences;
import org.eun.back.service.dto.EventReferencesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventReferences} and its DTO {@link EventReferencesDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventReferencesMapper extends EntityMapper<EventReferencesDTO, EventReferences> {}
