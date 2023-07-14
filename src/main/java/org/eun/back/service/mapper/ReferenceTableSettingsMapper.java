package org.eun.back.service.mapper;

import org.eun.back.domain.ReferenceTableSettings;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferenceTableSettings} and its DTO {@link ReferenceTableSettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReferenceTableSettingsMapper extends EntityMapper<ReferenceTableSettingsDTO, ReferenceTableSettings> {}
