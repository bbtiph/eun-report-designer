package org.eun.back.service.mapper;

import org.eun.back.domain.Countries;
import org.eun.back.domain.OperationalBodyMember;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.OperationalBodyMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OperationalBodyMember} and its DTO {@link OperationalBodyMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperationalBodyMemberMapper extends EntityMapper<OperationalBodyMemberDTO, OperationalBodyMember> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countriesId")
    OperationalBodyMemberDTO toDto(OperationalBodyMember s);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountriesDTO toDtoCountriesId(Countries countries);
}
