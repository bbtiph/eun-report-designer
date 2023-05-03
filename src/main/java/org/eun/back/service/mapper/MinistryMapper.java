package org.eun.back.service.mapper;

import org.eun.back.domain.Countries;
import org.eun.back.domain.Ministry;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.MinistryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ministry} and its DTO {@link MinistryDTO}.
 */
@Mapper(componentModel = "spring")
public interface MinistryMapper extends EntityMapper<MinistryDTO, Ministry> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countriesId")
    MinistryDTO toDto(Ministry s);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountriesDTO toDtoCountriesId(Countries countries);
}
