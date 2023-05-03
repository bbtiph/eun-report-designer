package org.eun.back.service.mapper;

import org.eun.back.domain.Countries;
import org.eun.back.service.dto.CountriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Countries} and its DTO {@link CountriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface CountriesMapper extends EntityMapper<CountriesDTO, Countries> {}
