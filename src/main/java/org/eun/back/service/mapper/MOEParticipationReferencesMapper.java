package org.eun.back.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.eun.back.domain.Countries;
import org.eun.back.domain.MOEParticipationReferences;
import org.eun.back.service.dto.CountriesWithMoeRepresentativesDTO;
import org.eun.back.service.dto.MOEParticipationReferencesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MOEParticipationReferences} and its DTO {@link MOEParticipationReferencesDTO}.
 */
@Mapper(componentModel = "spring")
public interface MOEParticipationReferencesMapper extends EntityMapper<MOEParticipationReferencesDTO, MOEParticipationReferences> {
    @Mapping(target = "countries", source = "countries", qualifiedByName = "countriesIdSet")
    MOEParticipationReferencesDTO toDto(MOEParticipationReferences s);

    //    @Mapping(target = "removeCountries", ignore = true)
    MOEParticipationReferences toEntity(MOEParticipationReferencesDTO mOEParticipationReferencesDTO);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "countryName", source = "countryName")
    CountriesWithMoeRepresentativesDTO toDtoCountriesId(Countries countries);

    @Named("countriesIdSet")
    default Set<CountriesWithMoeRepresentativesDTO> toDtoCountriesIdSet(Set<Countries> countries) {
        return countries.stream().map(this::toDtoCountriesId).collect(Collectors.toSet());
    }
}
