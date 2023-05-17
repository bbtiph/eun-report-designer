package org.eun.back.service.mapper;

import org.eun.back.domain.Countries;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportBlocks} and its DTO {@link ReportBlocksDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportBlocksMapper extends EntityMapper<ReportBlocksDTO, ReportBlocks> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countriesId")
    ReportBlocksDTO toDto(ReportBlocks s);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountriesDTO toDtoCountriesId(Countries countries);
}
