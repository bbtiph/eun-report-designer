package org.eun.back.service.mapper;

import org.eun.back.domain.Funding;
import org.eun.back.domain.Project;
import org.eun.back.service.dto.FundingDTO;
import org.eun.back.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funding} and its DTO {@link FundingDTO}.
 */
@Mapper(componentModel = "spring")
public interface FundingMapper extends EntityMapper<FundingDTO, Funding> {
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    FundingDTO toDto(Funding s);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);
}
