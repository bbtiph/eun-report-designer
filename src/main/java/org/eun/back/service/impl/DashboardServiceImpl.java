package org.eun.back.service.impl;

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

    private final OrganizationService organizationService;

    private final PersonService personService;

    private final ReportBlocksService reportBlocksService;

    private final OrganizationEunIndicatorService organizationEunIndicatorService;

    private final PersonEunIndicatorService personEunIndicatorService;

    public DashboardServiceImpl(
        WorkingGroupReferencesService workingGroupReferencesService,
        ProjectService projectService,
        EventService eventService,
        OrganizationService organizationService,
        PersonService personService,
        ReportBlocksService reportBlocksService,
        OrganizationEunIndicatorService organizationEunIndicatorService,
        PersonEunIndicatorService personEunIndicatorService
    ) {
        this.workingGroupReferencesService = workingGroupReferencesService;
        this.projectService = projectService;
        this.eventService = eventService;
        this.organizationService = organizationService;
        this.personService = personService;
        this.reportBlocksService = reportBlocksService;
        this.organizationEunIndicatorService = organizationEunIndicatorService;
        this.personEunIndicatorService = personEunIndicatorService;
    }

    @Override
    @Transactional(readOnly = true)
    public IndicatorsDto getIndicators(Long countryId, Long reportId) {
        IndicatorsDto indicators = new IndicatorsDto();
        indicators.getIndicators().add(workingGroupReferencesService.getIndicator(countryId, reportId));
        indicators.getIndicators().add(projectService.getIndicator(countryId, reportId));
        indicators.getIndicators().add(eventService.getIndicator(countryId, reportId));
        indicators.getIndicators().add(organizationService.getIndicator(countryId, reportId));
        indicators.getIndicators().add(personService.getIndicator(countryId, reportId));
        indicators.getIndicators().add(reportBlocksService.getIndicator(countryId, reportId));
        indicators.getIndicators().add(organizationEunIndicatorService.getIndicator(countryId, reportId));
        indicators.getIndicators().add(personEunIndicatorService.getIndicator(countryId, reportId));
        return indicators;
    }
}
