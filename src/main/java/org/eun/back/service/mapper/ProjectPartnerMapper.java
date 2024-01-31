package org.eun.back.service.mapper;

import org.eun.back.domain.JobInfo;
import org.eun.back.domain.ProjectPartner;
import org.eun.back.service.dto.JobInfoDTO;
import org.eun.back.service.dto.ProjectPartnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectPartner} and its DTO {@link ProjectPartnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectPartnerMapper extends EntityMapper<ProjectPartnerDTO, ProjectPartner> {
    @Mapping(target = "jobInfo", source = "jobInfo", qualifiedByName = "jobInfoId")
    ProjectPartnerDTO toDto(ProjectPartner s);

    @Named("jobInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JobInfoDTO toDtoJobInfoId(JobInfo jobInfo);
}
