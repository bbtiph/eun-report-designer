package org.eun.back.service.mapper;

import org.eun.back.domain.EunTeam;
import org.eun.back.domain.EunTeamMember;
import org.eun.back.domain.Person;
import org.eun.back.service.dto.EunTeamDTO;
import org.eun.back.service.dto.EunTeamMemberDTO;
import org.eun.back.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EunTeamMember} and its DTO {@link EunTeamMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface EunTeamMemberMapper extends EntityMapper<EunTeamMemberDTO, EunTeamMember> {
    @Mapping(target = "team", source = "team", qualifiedByName = "eunTeamId")
    @Mapping(target = "person", source = "person", qualifiedByName = "personId")
    EunTeamMemberDTO toDto(EunTeamMember s);

    @Named("eunTeamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EunTeamDTO toDtoEunTeamId(EunTeam eunTeam);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonDTO toDtoPersonId(Person person);
}
