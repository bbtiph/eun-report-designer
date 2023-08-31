package org.eun.back.service.mapper;

import org.eun.back.domain.ReportTemplate;
import org.eun.back.service.dto.ReportTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportTemplate} and its DTO {@link ReportTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportTemplateMapper extends EntityMapper<ReportTemplateDTO, ReportTemplate> {}
