package org.eun.back.service.mapper;

import org.eun.back.domain.Organization;
import org.eun.back.domain.Person;
import org.eun.back.domain.PersonInOrganization;
import org.eun.back.service.dto.OrganizationDTO;
import org.eun.back.service.dto.PersonDTO;
import org.eun.back.service.dto.PersonInOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonInOrganization} and its DTO {@link PersonInOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonInOrganizationMapper extends EntityMapper<PersonInOrganizationDTO, PersonInOrganization> {
    @Mapping(target = "person", source = "person", qualifiedByName = "personId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    PersonInOrganizationDTO toDto(PersonInOrganization s);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonDTO toDtoPersonId(Person person);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
