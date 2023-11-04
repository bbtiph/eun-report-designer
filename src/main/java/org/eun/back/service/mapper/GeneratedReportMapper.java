package org.eun.back.service.mapper;

import org.eun.back.domain.GeneratedReport;
import org.eun.back.service.dto.GeneratedReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GeneratedReport} and its DTO {@link GeneratedReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface GeneratedReportMapper extends EntityMapper<GeneratedReportDTO, GeneratedReport> {}
