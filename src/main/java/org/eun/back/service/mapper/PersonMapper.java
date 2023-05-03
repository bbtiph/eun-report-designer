package org.eun.back.service.mapper;

import org.eun.back.domain.EunTeamMember;
import org.eun.back.domain.EventParticipant;
import org.eun.back.domain.Person;
import org.eun.back.domain.PersonInOrganization;
import org.eun.back.domain.PersonInProject;
import org.eun.back.service.dto.EunTeamMemberDTO;
import org.eun.back.service.dto.EventParticipantDTO;
import org.eun.back.service.dto.PersonDTO;
import org.eun.back.service.dto.PersonInOrganizationDTO;
import org.eun.back.service.dto.PersonInProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "eunTeamMember", source = "eunTeamMember", qualifiedByName = "eunTeamMemberId")
    @Mapping(target = "eventParticipant", source = "eventParticipant", qualifiedByName = "eventParticipantId")
    @Mapping(target = "personInOrganization", source = "personInOrganization", qualifiedByName = "personInOrganizationId")
    @Mapping(target = "personInProject", source = "personInProject", qualifiedByName = "personInProjectId")
    PersonDTO toDto(Person s);

    @Named("eunTeamMemberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EunTeamMemberDTO toDtoEunTeamMemberId(EunTeamMember eunTeamMember);

    @Named("eventParticipantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventParticipantDTO toDtoEventParticipantId(EventParticipant eventParticipant);

    @Named("personInOrganizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonInOrganizationDTO toDtoPersonInOrganizationId(PersonInOrganization personInOrganization);

    @Named("personInProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonInProjectDTO toDtoPersonInProjectId(PersonInProject personInProject);
}
