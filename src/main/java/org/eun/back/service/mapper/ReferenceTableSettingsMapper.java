package org.eun.back.service.mapper;

import org.eun.back.domain.ReferenceTableSettings;
import org.eun.back.service.dto.ReferenceTableSettingsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferenceTableSettings} and its DTO {@link ReferenceTableSettingsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReferenceTableSettingsMapper extends EntityMapper<ReferenceTableSettingsDTO, ReferenceTableSettings> {
    @Named("toDto")
    @Mapping(target = "columns", source = "columns", qualifiedByName = "truncateTemplate")
    @Mapping(target = "columnsOfTemplate", source = "columnsOfTemplate", qualifiedByName = "truncateTemplate")
    ReferenceTableSettingsDTO toDtoToShowInHomePage(ReferenceTableSettings s);

    @Named("truncateTemplate")
    default String truncateTemplate(String template) {
        if (template != null && template.length() > 200) {
            return template.substring(0, 200) + "...";
        }
        return template;
    }
}
