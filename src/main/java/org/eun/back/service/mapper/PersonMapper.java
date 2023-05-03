package org.eun.back.service.mapper;

import org.eun.back.domain.Countries;
import org.eun.back.domain.Person;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countriesId")
    PersonDTO toDto(Person s);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountriesDTO toDtoCountriesId(Countries countries);
}
