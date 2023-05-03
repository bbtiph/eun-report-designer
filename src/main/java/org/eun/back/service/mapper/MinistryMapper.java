package org.eun.back.service.mapper;

import org.eun.back.domain.Ministry;
import org.eun.back.domain.OrganizationInMinistry;
import org.eun.back.service.dto.MinistryDTO;
import org.eun.back.service.dto.OrganizationInMinistryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ministry} and its DTO {@link MinistryDTO}.
 */
@Mapper(componentModel = "spring")
public interface MinistryMapper extends EntityMapper<MinistryDTO, Ministry> {
    @Mapping(target = "organizationInMinistry", source = "organizationInMinistry", qualifiedByName = "organizationInMinistryId")
    MinistryDTO toDto(Ministry s);

    @Named("organizationInMinistryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationInMinistryDTO toDtoOrganizationInMinistryId(OrganizationInMinistry organizationInMinistry);
}
