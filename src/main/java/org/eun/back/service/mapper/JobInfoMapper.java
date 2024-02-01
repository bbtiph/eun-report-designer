package org.eun.back.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.eun.back.domain.JobInfo;
import org.eun.back.domain.ProjectPartner;
import org.eun.back.service.dto.JobInfoDTO;
import org.eun.back.service.dto.ProjectPartnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobInfo} and its DTO {@link JobInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface JobInfoMapper extends EntityMapper<JobInfoDTO, JobInfo> {
    @Mapping(target = "projectPartners", source = "projectPartners", qualifiedByName = "projectPartnersIdSet")
    JobInfoDTO toDto(JobInfo s);

    @Named("projectPartnersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "odataEtag", source = "odataEtag")
    @Mapping(target = "no", source = "no")
    @Mapping(target = "jobNo", source = "jobNo")
    @Mapping(target = "vendorCode", source = "vendorCode")
    @Mapping(target = "vendorName", source = "vendorName")
    @Mapping(target = "partnerAmount", source = "partnerAmount")
    @Mapping(target = "countryCode", source = "countryCode")
    @Mapping(target = "isActive", source = "isActive")
    ProjectPartnerDTO toDtoProjectPartnersId(ProjectPartner partner);

    @Named("projectPartnersIdSet")
    default Set<ProjectPartnerDTO> toDtoCountriesIdSet(Set<ProjectPartner> projectPartners) {
        return projectPartners.stream().map(this::toDtoProjectPartnersId).collect(Collectors.toSet());
    }
}
