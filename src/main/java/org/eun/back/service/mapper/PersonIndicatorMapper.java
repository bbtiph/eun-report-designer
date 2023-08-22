package org.eun.back.service.mapper;

import org.eun.back.domain.Person;
import org.eun.back.service.dto.PersonIndicatorDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonIndicatorMapper extends EntityMapper<PersonIndicatorDTO, Person> {}
