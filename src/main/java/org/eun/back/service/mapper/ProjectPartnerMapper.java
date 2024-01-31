package org.eun.back.service.mapper;

import org.eun.back.domain.ProjectPartner;
import org.eun.back.service.dto.ProjectPartnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectPartner} and its DTO {@link ProjectPartnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectPartnerMapper extends EntityMapper<ProjectPartnerDTO, ProjectPartner> {}
