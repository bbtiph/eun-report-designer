package org.eun.back.service.mapper;

import org.eun.back.domain.Ministry;
import org.eun.back.domain.Organization;
import org.eun.back.domain.OrganizationInMinistry;
import org.eun.back.service.dto.MinistryDTO;
import org.eun.back.service.dto.OrganizationDTO;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationInMinistry} and its DTO {@link OrganizationInMinistryDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationInMinistryMapper extends EntityMapper<OrganizationInMinistryDTO, OrganizationInMinistry> {
    @Mapping(target = "ministry", source = "ministry", qualifiedByName = "ministryId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    OrganizationInMinistryDTO toDto(OrganizationInMinistry s);

    @Named("ministryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MinistryDTO toDtoMinistryId(Ministry ministry);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
