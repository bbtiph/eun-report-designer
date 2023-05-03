package org.eun.back.service.mapper;

import org.eun.back.domain.EunTeamMember;
import org.eun.back.service.dto.EunTeamMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EunTeamMember} and its DTO {@link EunTeamMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface EunTeamMemberMapper extends EntityMapper<EunTeamMemberDTO, EunTeamMember> {}
