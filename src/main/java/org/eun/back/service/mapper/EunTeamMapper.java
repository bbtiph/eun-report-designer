package org.eun.back.service.mapper;

import org.eun.back.domain.EunTeam;
import org.eun.back.service.dto.EunTeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EunTeam} and its DTO {@link EunTeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface EunTeamMapper extends EntityMapper<EunTeamDTO, EunTeam> {}
