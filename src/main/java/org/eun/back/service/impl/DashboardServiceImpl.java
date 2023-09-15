package org.eun.back.service.impl;

import java.util.Map;
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.service.*;
import org.eun.back.service.dto.IndicatorsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkingGroupReferences}.
 */
@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    private final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private final WorkingGroupReferencesService workingGroupReferencesService;

    private final ProjectService projectService;

    private final EventService eventService;

    private final ReportBlocksService reportBlocksService;

    private final OrganizationEunIndicatorService organizationEunIndicatorService;

    private final PersonEunIndicatorService personEunIndicatorService;

    private final ParticipantsEunIndicatorService participantsEunIndicatorService;

    public DashboardServiceImpl(
        WorkingGroupReferencesService workingGroupReferencesService,
        ProjectService projectService,
        EventService eventService,
        ReportBlocksService reportBlocksService,
        OrganizationEunIndicatorService organizationEunIndicatorService,
        PersonEunIndicatorService personEunIndicatorService,
        ParticipantsEunIndicatorService participantsEunIndicatorService
    ) {
        this.workingGroupReferencesService = workingGroupReferencesService;
        this.projectService = projectService;
        this.eventService = eventService;
        this.reportBlocksService = reportBlocksService;
        this.organizationEunIndicatorService = organizationEunIndicatorService;
        this.personEunIndicatorService = personEunIndicatorService;
        this.participantsEunIndicatorService = participantsEunIndicatorService;
    }

    @Override
    @Transactional(readOnly = true)
    public IndicatorsDto getIndicatorsByReport(Map<String, String> params, Long reportId) {
        String countryId = params.get("country_id");
        IndicatorsDto indicators = new IndicatorsDto();
        indicators.getIndicators().add(workingGroupReferencesService.getIndicator(Long.parseLong(countryId), reportId));
        indicators.getIndicators().add(projectService.getIndicator(Long.parseLong(countryId), reportId));
        indicators.getIndicators().add(eventService.getIndicator(Long.parseLong(countryId), reportId));
        indicators.getIndicators().add(reportBlocksService.getIndicator(Long.parseLong(countryId), reportId));
        indicators.getIndicators().add(organizationEunIndicatorService.getIndicator(params, reportId));
        indicators.getIndicators().add(personEunIndicatorService.getIndicator(params, reportId));
        indicators.getIndicators().add(participantsEunIndicatorService.getIndicator(params, reportId));
        return indicators;
    }

    @Override
    public IndicatorsDto getIndicators(Map<String, String> params) {
        String countryId = params.get("country_id");
        IndicatorsDto indicators = new IndicatorsDto();
        indicators.getIndicators().add(workingGroupReferencesService.getIndicator(Long.parseLong(countryId), null));
        indicators.getIndicators().add(projectService.getIndicator(Long.parseLong(countryId), null));
        indicators.getIndicators().add(eventService.getIndicator(Long.parseLong(countryId), null));
        indicators.getIndicators().add(organizationEunIndicatorService.getIndicator(params, null));
        indicators.getIndicators().add(personEunIndicatorService.getIndicator(params, null));
        indicators.getIndicators().add(participantsEunIndicatorService.getIndicator(params, null));
        return indicators;
    }
}
