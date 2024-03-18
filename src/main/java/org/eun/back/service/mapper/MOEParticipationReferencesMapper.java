package org.eun.back.service.mapper;

import org.eun.back.domain.MOEParticipationReferences;
import org.eun.back.service.dto.MOEParticipationReferencesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MOEParticipationReferences} and its DTO {@link MOEParticipationReferencesDTO}.
 */
@Mapper(componentModel = "spring")
public interface MOEParticipationReferencesMapper extends EntityMapper<MOEParticipationReferencesDTO, MOEParticipationReferences> {}
