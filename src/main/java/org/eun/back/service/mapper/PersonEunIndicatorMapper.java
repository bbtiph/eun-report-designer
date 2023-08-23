package org.eun.back.service.mapper;

import org.eun.back.domain.PersonEunIndicator;
import org.eun.back.service.dto.PersonEunIndicatorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonEunIndicator} and its DTO {@link PersonEunIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonEunIndicatorMapper extends EntityMapper<PersonEunIndicatorDTO, PersonEunIndicator> {}
