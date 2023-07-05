package org.eun.back.service.mapper;

import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.service.dto.WorkingGroupReferencesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkingGroupReferences} and its DTO {@link WorkingGroupReferencesDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkingGroupReferencesMapper extends EntityMapper<WorkingGroupReferencesDTO, WorkingGroupReferences> {}
