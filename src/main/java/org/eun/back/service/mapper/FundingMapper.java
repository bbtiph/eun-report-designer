package org.eun.back.service.mapper;

import org.eun.back.domain.Funding;
import org.eun.back.service.dto.FundingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funding} and its DTO {@link FundingDTO}.
 */
@Mapper(componentModel = "spring")
public interface FundingMapper extends EntityMapper<FundingDTO, Funding> {}
