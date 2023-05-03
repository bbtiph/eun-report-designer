package org.eun.back.service.mapper;

import org.eun.back.domain.EventInOrganization;
import org.eun.back.domain.Organization;
import org.eun.back.domain.OrganizationInMinistry;
import org.eun.back.domain.OrganizationInProject;
import org.eun.back.domain.PersonInOrganization;
import org.eun.back.service.dto.EventInOrganizationDTO;
import org.eun.back.service.dto.OrganizationDTO;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
import org.eun.back.service.dto.OrganizationInProjectDTO;
import org.eun.back.service.dto.PersonInOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {
    @Mapping(target = "eventInOrganization", source = "eventInOrganization", qualifiedByName = "eventInOrganizationId")
    @Mapping(target = "organizationInMinistry", source = "organizationInMinistry", qualifiedByName = "organizationInMinistryId")
    @Mapping(target = "organizationInProject", source = "organizationInProject", qualifiedByName = "organizationInProjectId")
    @Mapping(target = "personInOrganization", source = "personInOrganization", qualifiedByName = "personInOrganizationId")
    OrganizationDTO toDto(Organization s);

    @Named("eventInOrganizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventInOrganizationDTO toDtoEventInOrganizationId(EventInOrganization eventInOrganization);

    @Named("organizationInMinistryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationInMinistryDTO toDtoOrganizationInMinistryId(OrganizationInMinistry organizationInMinistry);

    @Named("organizationInProjectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationInProjectDTO toDtoOrganizationInProjectId(OrganizationInProject organizationInProject);

    @Named("personInOrganizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonInOrganizationDTO toDtoPersonInOrganizationId(PersonInOrganization personInOrganization);
}
