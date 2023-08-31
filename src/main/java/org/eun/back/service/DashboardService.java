package org.eun.back.service;

import java.util.Map;
import org.eun.back.service.dto.IndicatorsDto;

/**
 * Service Interface for managing {@link org.eun.back.domain.WorkingGroupReferences}.
 */
public interface DashboardService {
    IndicatorsDto getIndicators(Map<String, String> params, Long reportId);
}
