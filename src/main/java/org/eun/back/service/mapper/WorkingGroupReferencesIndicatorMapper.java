package org.eun.back.service.mapper;

import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.service.dto.WorkingGroupReferencesIndicatorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link WorkingGroupReferences} and its DTO {@link WorkingGroupReferencesIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkingGroupReferencesIndicatorMapper extends EntityMapper<WorkingGroupReferencesIndicatorDTO, WorkingGroupReferences> {}
