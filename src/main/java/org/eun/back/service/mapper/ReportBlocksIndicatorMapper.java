package org.eun.back.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.eun.back.domain.Countries;
import org.eun.back.domain.Report;
import org.eun.back.domain.ReportBlocks;
import org.eun.back.service.dto.CountriesDTO;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.eun.back.service.dto.ReportBlocksIndicatorDTO;
import org.eun.back.service.dto.ReportDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link ReportBlocks} and its DTO {@link ReportBlocksIndicatorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportBlocksIndicatorMapper extends EntityMapper<ReportBlocksIndicatorDTO, ReportBlocks> {
    @Named("reportId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReportDTO toDtoReportId(Report report);
}
