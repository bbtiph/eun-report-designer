package org.eun.back.service.mapper;

import org.eun.back.domain.ParticipantsEunIndicator;
import org.eun.back.service.dto.ParticipantsEunIndicatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParticipantsEunIndicator} and its DTO {@link ParticipantsEunIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParticipantsEunIndicatorMapper extends EntityMapper<ParticipantsEunIndicatorDTO, ParticipantsEunIndicator> {}
