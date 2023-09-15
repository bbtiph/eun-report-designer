package org.eun.back.web.rest;

import java.util.Map;
import org.eun.back.service.DashboardService;
import org.eun.back.service.dto.IndicatorsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link org.eun.back.domain.WorkingGroupReferences}.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private final Logger log = LoggerFactory.getLogger(DashboardResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/report/{reportId}/indicators")
    public ResponseEntity<IndicatorsDto> getIndicatorsByReport(@PathVariable Long reportId, @RequestParam Map<String, String> params) {
        IndicatorsDto result = dashboardService.getIndicatorsByReport(params, reportId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/indicators")
    public ResponseEntity<IndicatorsDto> getIndicators(@RequestParam Map<String, String> params) {
        IndicatorsDto result = dashboardService.getIndicators(params);
        return ResponseEntity.ok().body(result);
    }
}
