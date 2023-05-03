package org.eun.back.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eun.back.service.BirtReportService;
import org.eun.back.service.dto.OutputType;
import org.eun.back.service.dto.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class BirtReportController {

    private static final Logger log = LoggerFactory.getLogger(BirtReportController.class);

    @Autowired
    private BirtReportService reportService;

    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "")
    @ResponseBody
    public List<Report> listReports() {
        return reportService.getReports();
    }

    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "reload")
    @ResponseBody
    public ResponseEntity reloadReports(HttpServletResponse response) {
        try {
            log.info("Reloading reports");
            reportService.loadReports();
        } catch (EngineException e) {
            log.error("There was an error reloading the reports in memory: ", e);
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{name}")
    @ResponseBody
    public void generateFullReport(
        HttpServletResponse response,
        HttpServletRequest request,
        @PathVariable("name") String name,
        @RequestParam("id") Object id,
        @RequestParam("output") String output,
        @RequestParam("lang") String lang
    ) {
        //        try {
        //            id = URLDecoder.decode((String) id, StandardCharsets.UTF_8.toString());
        //        } catch (UnsupportedEncodingException e) {
        //            throw new RuntimeException(e);
        //        }
        log.info("Generating full report: " + name + "; id: " + id + "; format: " + output + "; lang: " + lang);
        OutputType format = OutputType.from(output);
        reportService.generateMainReport(name, format, id, lang, response, request);
    }
}
