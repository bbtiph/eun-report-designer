package org.eun.back.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.eun.back.domain.Countries;
import org.eun.back.domain.EventReferences;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.CountriesWithParticipantsDTO;
import org.eun.back.service.dto.EventReferencesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventReferences} and its DTO {@link EventReferencesDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventReferencesMapper extends EntityMapper<EventReferencesDTO, EventReferences> {
    @Mapping(target = "countries", source = "countries", qualifiedByName = "countriesIdSet")
    EventReferencesDTO toDto(EventReferences s);

    @Mapping(target = "removeCountries", ignore = true)
    EventReferences toEntity(EventReferencesDTO eventReferencesDTO);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "countryName", source = "countryName")
    CountriesWithParticipantsDTO toDtoCountriesId(Countries countries);

    @Named("countriesIdSet")
    default Set<CountriesWithParticipantsDTO> toDtoCountriesIdSet(Set<Countries> countries) {
        return countries.stream().map(this::toDtoCountriesId).collect(Collectors.toSet());
    }
}
