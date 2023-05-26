package org.eun.back.service.mapper;

import org.eun.back.domain.MoeContacts;
import org.eun.back.service.dto.MoeContactsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MoeContacts} and its DTO {@link MoeContactsDTO}.
 */
@Mapper(componentModel = "spring")
public interface MoeContactsMapper extends EntityMapper<MoeContactsDTO, MoeContacts> {}
