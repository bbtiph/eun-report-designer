package org.eun.back.service.mapper;

import org.eun.back.domain.EunTeam;
import org.eun.back.domain.EunTeamMember;
import org.eun.back.service.dto.EunTeamDTO;
import org.eun.back.service.dto.EunTeamMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EunTeam} and its DTO {@link EunTeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface EunTeamMapper extends EntityMapper<EunTeamDTO, EunTeam> {
    @Mapping(target = "eunTeamMember", source = "eunTeamMember", qualifiedByName = "eunTeamMemberId")
    EunTeamDTO toDto(EunTeam s);

    @Named("eunTeamMemberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EunTeamMemberDTO toDtoEunTeamMemberId(EunTeamMember eunTeamMember);
}
