package org.eun.back.service;

import java.util.List;
import org.eun.back.service.dto.IndicatorsDto;

/**
 * Service Interface for managing {@link org.eun.back.domain.WorkingGroupReferences}.
 */
public interface DashboardService {
    IndicatorsDto getIndicators(Long countryId, Long reportId);
}
