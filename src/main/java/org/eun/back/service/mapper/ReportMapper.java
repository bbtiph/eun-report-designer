package org.eun.back.service.mapper;

import org.eun.back.domain.Report;
import org.eun.back.service.dto.ReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {
    ReportDTO toDto(Report s);
}
