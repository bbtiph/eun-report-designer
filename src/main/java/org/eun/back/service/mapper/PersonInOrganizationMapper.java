package org.eun.back.service.mapper;

import org.eun.back.domain.PersonInOrganization;
import org.eun.back.service.dto.PersonInOrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonInOrganization} and its DTO {@link PersonInOrganizationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonInOrganizationMapper extends EntityMapper<PersonInOrganizationDTO, PersonInOrganization> {}
