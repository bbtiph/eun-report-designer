package org.eun.back.service.mapper;

import org.eun.back.domain.Funding;
import org.eun.back.domain.Project;
import org.eun.back.service.dto.FundingDTO;
import org.eun.back.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "funding", source = "funding", qualifiedByName = "fundingId")
    ProjectDTO toDto(Project s);

    @Named("fundingId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FundingDTO toDtoFundingId(Funding funding);
}
