package org.eun.back.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.eun.back.domain.Countries;
import org.eun.back.domain.Report;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.eun.back.service.dto.ReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportBlocks} and its DTO {@link ReportBlocksDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportBlocksMapper extends EntityMapper<ReportBlocksDTO, ReportBlocks> {
    @Mapping(target = "countryIds", source = "countryIds", qualifiedByName = "countriesIdSet")
    @Mapping(target = "report", source = "report", qualifiedByName = "reportId")
    ReportBlocksDTO toDto(ReportBlocks s);

    @Mapping(target = "removeCountryId", ignore = true)
    ReportBlocks toEntity(ReportBlocksDTO reportBlocksDTO);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountriesDTO toDtoCountriesId(Countries countries);

    @Named("countriesIdSet")
    default Set<CountriesDTO> toDtoCountriesIdSet(Set<Countries> countries) {
        return countries.stream().map(this::toDtoCountriesId).collect(Collectors.toSet());
    }

    @Named("reportId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReportDTO toDtoReportId(Report report);
}
