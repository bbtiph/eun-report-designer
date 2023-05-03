package org.eun.back.service.mapper;

import org.eun.back.domain.Countries;
import org.eun.back.domain.Organization;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.OrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countriesId")
    OrganizationDTO toDto(Organization s);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountriesDTO toDtoCountriesId(Countries countries);
}
