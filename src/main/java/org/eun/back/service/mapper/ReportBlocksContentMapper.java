package org.eun.back.service.mapper;

import org.eun.back.domain.ReportBlocks;
import org.eun.back.domain.ReportBlocksContent;
import org.eun.back.service.dto.ReportBlocksContentDTO;
import org.eun.back.service.dto.ReportBlocksDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportBlocksContent} and its DTO {@link ReportBlocksContentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportBlocksContentMapper extends EntityMapper<ReportBlocksContentDTO, ReportBlocksContent> {
    //    @Mapping(target = "reportBlocks", source = "reportBlocks", qualifiedByName = "reportBlocksId")
    ReportBlocksContentDTO toDto(ReportBlocksContent s);

    @Named("toDto")
    @Mapping(target = "reportBlocksContentData", ignore = true)
    @Mapping(target = "template", source = "template", qualifiedByName = "truncateTemplate")
    ReportBlocksContentDTO toDtoToShowInHomePage(ReportBlocksContent s);

    @Named("reportBlocksId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReportBlocksDTO toDtoReportBlocksId(ReportBlocks reportBlocks);

    @Named("truncateTemplate")
    default String truncateTemplate(String template) {
        if (template != null && template.length() > 200) {
            return template.substring(0, 200) + "...";
        }
        return template;
    }
}
