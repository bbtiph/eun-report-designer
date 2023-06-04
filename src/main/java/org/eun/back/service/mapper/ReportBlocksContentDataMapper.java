package org.eun.back.service.mapper;

import org.eun.back.domain.Countries;
import org.eun.back.domain.ReportBlocksContent;
import org.eun.back.domain.ReportBlocksContentData;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.ReportBlocksContentDTO;
import org.eun.back.service.dto.ReportBlocksContentDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportBlocksContentData} and its DTO {@link ReportBlocksContentDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportBlocksContentDataMapper extends EntityMapper<ReportBlocksContentDataDTO, ReportBlocksContentData> {
    @Mapping(target = "reportBlocksContent", source = "reportBlocksContent", qualifiedByName = "reportBlocksContentId")
    @Mapping(target = "country", source = "country", qualifiedByName = "countriesId")
    ReportBlocksContentDataDTO toDto(ReportBlocksContentData s);

    @Named("reportBlocksContentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReportBlocksContentDTO toDtoReportBlocksContentId(ReportBlocksContent reportBlocksContent);

    @Named("countriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CountriesDTO toDtoCountriesId(Countries countries);
}
