package org.eun.back.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eun.back.service.DashboardService;
import org.eun.back.service.dto.Indicator;
import org.eun.back.service.dto.IndicatorsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link org.eun.back.domain.WorkingGroupReferences}.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    private static final String ENTITY_NAME = "workingGroupReferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/report/{reportId}/indicators")
    public ResponseEntity<IndicatorsDto> getAllWorkingGroupReferences(
        @PathVariable Long reportId,
        @RequestParam Map<String, String> params
    ) {
        IndicatorsDto result = dashboardService.getIndicators(params, reportId);
        return ResponseEntity.ok().body(result);
    }
}
