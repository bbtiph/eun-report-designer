package org.eun.back.service.mapper;

import org.eun.back.domain.Report;
import org.eun.back.domain.ReportTemplate;
import org.eun.back.service.dto.ReportDTO;
import org.eun.back.service.dto.ReportTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {
    //    @Mapping(target = "reportTemplate", source = "reportTemplate", qualifiedByName = "reportTemplateId")
    //    ReportDTO toDto(Report s);
    //
    //    @Named("reportTemplateId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    ReportTemplateDTO toDtoReportTemplateId(ReportTemplate reportTemplate);
}
