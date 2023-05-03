package org.eun.back.service.mapper;

import org.eun.back.domain.OperationalBody;
import org.eun.back.service.dto.OperationalBodyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OperationalBody} and its DTO {@link OperationalBodyDTO}.
 */
@Mapper(componentModel = "spring")
public interface OperationalBodyMapper extends EntityMapper<OperationalBodyDTO, OperationalBody> {}
