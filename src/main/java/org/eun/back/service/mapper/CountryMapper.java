package org.eun.back.service.mapper;

import org.eun.back.domain.Country;
import org.eun.back.domain.Ministry;
import org.eun.back.domain.OperationalBodyMember;
import org.eun.back.domain.Organization;
import org.eun.back.domain.Person;
import org.eun.back.service.dto.CountryDTO;
import org.eun.back.service.dto.MinistryDTO;
import org.eun.back.service.dto.OperationalBodyMemberDTO;
import org.eun.back.service.dto.OrganizationDTO;
import org.eun.back.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Country} and its DTO {@link CountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDTO, Country> {
    @Mapping(target = "ministry", source = "ministry", qualifiedByName = "ministryId")
    @Mapping(target = "operationalBodyMember", source = "operationalBodyMember", qualifiedByName = "operationalBodyMemberId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    @Mapping(target = "person", source = "person", qualifiedByName = "personId")
    CountryDTO toDto(Country s);

    @Named("ministryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MinistryDTO toDtoMinistryId(Ministry ministry);

    @Named("operationalBodyMemberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OperationalBodyMemberDTO toDtoOperationalBodyMemberId(OperationalBodyMember operationalBodyMember);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonDTO toDtoPersonId(Person person);
}
