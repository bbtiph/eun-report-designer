package org.eun.back.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.eun.back.security.SecurityUtils;
import org.eun.back.service.dto.*;
import org.eun.back.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class BirtReportService implements ApplicationContextAware, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(BirtReportService.class);

    @Value("${eun.report-designer.url}")
    private String currentDomain;

    @Value("${reports.relative.path}")
    private String reportsPath;

    @Value("${images.relative.path}")
    private String imagesPath;

    @Value("${os.linux.docker}")
    private Boolean isDocker;

    private HTMLServerImageHandler htmlImageHandler = new HTMLServerImageHandler();

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ReportBlocksService reportBlocksService;

    private IReportEngine birtEngine;
    private ApplicationContext context;
    private String imageFolder;

    private Map<String, IReportRunnable> reports = new HashMap<>();

    private final ReportService reportService;

    private final GeneratedReportService generatedReportService;

    public BirtReportService(ReportService reportService, GeneratedReportService generatedReportService) {
        this.reportService = reportService;
        this.generatedReportService = generatedReportService;
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    protected void initialize() throws BirtException {
        EngineConfig config = new EngineConfig();
        config.getAppContext().put("spring", this.context);
        Platform.startup(config);
        IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(
            IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY
        );
        birtEngine = factory.createReportEngine(config);
        imageFolder = Utils.getOsPath(isDocker) + reportsPath + imagesPath;
        loadReports();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Load report files to memory
     */
    public void loadReports() throws EngineException {
        reportsPath = Utils.getOsPath(isDocker) + reportsPath;
        log.info(reportsPath);
        File folder = new File(reportsPath);
        for (String file : Objects.requireNonNull(folder.list())) {
            if (!file.endsWith(".rptdesign")) {
                continue;
            }
            reports.put(file.replace(".rptdesign", ""), birtEngine.openReportDesign(folder.getAbsolutePath() + File.separator + file));
        }
    }

    public List<Report> getReports() {
        List<Report> response = new ArrayList<>();
        for (Map.Entry<String, IReportRunnable> entry : reports.entrySet()) {
            IReportRunnable report = reports.get(entry.getKey());
            IGetParameterDefinitionTask task = birtEngine.createGetParameterDefinitionTask(report);
            Report reportItem = new Report(report.getDesignHandle().getProperty("title").toString(), entry.getKey());
            //            for (Object h : task.getParameterDefns(false)) {
            //                IParameterDefn def = (IParameterDefn) h;
            //                reportItem.getParameters()
            //                  .add(new Report.Parameter(def.getPromptText(), def.getName(), getParameterType(def)));
            //            }
            response.add(reportItem);
        }
        return response;
    }

    private Report.ParameterType getParameterType(IParameterDefn param) {
        if (IParameterDefn.TYPE_INTEGER == param.getDataType()) {
            return Report.ParameterType.INT;
        }
        return Report.ParameterType.STRING;
    }

    private Pair<ReportDTO, IReportRunnable> getReport(Long reportId) {
        ReportDTO report = this.reportService.findOne(reportId).get();
        try {
            Pair<ReportDTO, IReportRunnable> response = ImmutablePair.of(
                report,
                birtEngine.openReportDesign(convertByteArrayToInputStream(report.getReportTemplate().getFile()))
            );

            return response;
        } catch (EngineException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream convertByteArrayToInputStream(byte[] byteArray) throws IOException {
        return new InputStream() {
            private int index = 0;

            @Override
            public int read() {
                if (index >= byteArray.length) {
                    return -1;
                }
                return byteArray[index++];
            }
        };
    }

    public void generateMainReport(
        String reportName,
        ReportRequest reportRequest,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        String format = reportRequest.getOutput();
        String lang = reportRequest.getLang();
        Long countryId = reportRequest.getCountryId();
        Long reportId = reportRequest.getReportId();
        OutputType output = OutputType.from(format);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            reportRequest.setData(objectMapper.writeValueAsString(reportBlocksService.findAllByReportAndCountry(reportId, countryId)));
        } catch (JsonProcessingException e) {
            reportRequest.setData("");
        }
        String data = reportRequest.getData();

        switch (output) {
            case HTML:
                generateHTMLReport(getReport(reportId).getRight(), data, lang, countryId, response, request);
                break;
            case PDF:
                generatePDFReport(getReport(reportId).getRight(), data, lang, countryId, response, request);
                break;
            case DOCX:
                generateDOCXReport(getReport(reportId).getRight(), data, lang, countryId, response, request);
                break;
            case DOC:
                generateReport(getReport(reportId).getRight(), output, data, lang, countryId, response, request);
                break;
            case XLSX:
                generateXLSXReport(reports.get(reportName.toLowerCase()), data, lang, countryId, response, request);
                break;
            case ODT:
                generateReport(reports.get(reportName.toLowerCase()), output, data, lang, countryId, response, request);
                break;
            default:
                throw new IllegalArgumentException("Output type not recognized:" + output);
        }
    }

    public GeneratedReportDTO generateExternalReport(
        ReportExternalRequest reportExternalRequest,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        //        URL url = null;
        //        try {
        //            url = new URL(request.getRequestURL().toString());
        //        } catch (MalformedURLException e) {
        //            throw new RuntimeException(e);
        //        }
        //        String currentDomain = url.getProtocol() + "://" + url.getHost();

        String format = reportExternalRequest.getFormat();
        String lang = reportExternalRequest.getLang();
        Long countryId = reportExternalRequest.getCountryId();
        Long reportId = reportExternalRequest.getReportId();
        OutputType output = OutputType.from(format);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            reportExternalRequest.setData(
                objectMapper.writeValueAsString(
                    reportBlocksService.findAllByReportBlockIdsAndReportAndCountry(
                        reportId,
                        countryId,
                        reportExternalRequest.getReportBlockIds()
                    )
                )
            );
        } catch (JsonProcessingException e) {
            reportExternalRequest.setData("");
        }
        String data = reportExternalRequest.getData();

        switch (output) {
            case HTML:
                return generateHTMLReportForExternalServices(getReport(reportId), data, lang, countryId, response, request);
            case PDF:
                return generatePDFReportForExternalServices(getReport(reportId), data, lang, countryId, response, request);
            case DOC:
                return generateReportForExternalServices(getReport(reportId), output, data, lang, countryId, response, request);
            default:
                throw new IllegalArgumentException("Output type not recognized:" + output);
        }
    }

    public Optional<GeneratedReportDTO> getGeneratedReportDTO(Long fileId) {
        return generatedReportService.findOne(fileId);
    }

    /**
     * Generate a report as HTML
     */
    @SuppressWarnings("unchecked")
    private void generateHTMLReport(
        IReportRunnable report,
        Object id,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType("html"));
        IRenderOption options = new RenderOption();
        HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
        htmlOptions.setOutputFormat("html");
        htmlOptions.setBaseImageURL("/" + reportsPath + imagesPath);
        htmlOptions.setImageDirectory(imageFolder);
        htmlOptions.setImageHandler(htmlImageHandler);

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(htmlOptions);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, request);

        try {
            htmlOptions.setOutputStream(response.getOutputStream());
            runAndRenderTask.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    /**
     * Generate a report as PDF
     */
    @SuppressWarnings("unchecked")
    private void generatePDFReport(
        IReportRunnable report,
        Object data,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType("pdf"));
        IRenderOption options = new RenderOption();
        PDFRenderOption pdfRenderOption = new PDFRenderOption(options);
        pdfRenderOption.setOutputFormat("pdf");

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", data);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(pdfRenderOption);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, request);

        try {
            pdfRenderOption.setOutputStream(response.getOutputStream());
            runAndRenderTask.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    /**
     * Generate a report as DOCX
     */
    @SuppressWarnings("unchecked")
    private void generateDOCXReport(
        IReportRunnable report,
        Object id,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType("docx"));
        DocxRenderOption docxRenderOption = new DocxRenderOption();
        docxRenderOption.setOutputFormat("docx");

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(docxRenderOption);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_URL_PARAM_FORMAT_KEY, request);

        try {
            docxRenderOption.setOutputStream(response.getOutputStream());
            runAndRenderTask.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    /**
     * Generate a report as XLSX
     */
    @SuppressWarnings("unchecked")
    private void generateXLSXReport(
        IReportRunnable report,
        Object id,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType("xlsx"));
        IExcelRenderOption options = new EXCELRenderOption();
        options.setOutputFormat("xlsx");

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(options);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_URL_PARAM_FORMAT_KEY, request);

        try {
            options.setOutputStream(response.getOutputStream());
            runAndRenderTask.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    /**
     * Generate a report
     */
    @SuppressWarnings("unchecked")
    private void generateReport(
        IReportRunnable report,
        OutputType output,
        Object id,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType(output.name()));
        IRenderOption options = new RenderOption();
        options.setOutputFormat(output.name());

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(options);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_URL_PARAM_FORMAT_KEY, request);

        try {
            options.setOutputStream(response.getOutputStream());
            runAndRenderTask.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    @SuppressWarnings("unchecked")
    private GeneratedReportDTO generateHTMLReportForExternalServices(
        Pair<ReportDTO, IReportRunnable> report,
        Object id,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report.getRight());
        IRenderOption options = new RenderOption();
        HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
        htmlOptions.setOutputFormat("html");
        htmlOptions.setBaseImageURL("/" + reportsPath + imagesPath);
        htmlOptions.setImageDirectory(imageFolder);
        htmlOptions.setImageHandler(htmlImageHandler);

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(htmlOptions);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            htmlOptions.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();

            String generatedHTML = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
            generatedHTML =
                generatedHTML
                    .replaceAll("(?i)<(/?)html( [^>]*)?>", "")
                    .replaceAll("(?i)<(/?)body( [^>]*)?>", "")
                    .replaceAll("\n", "")
                    .replaceAll("\t", "");

            GeneratedReportDTO generatedReportDTO = new GeneratedReportDTO();
            generatedReportDTO.setName(report.getLeft().getReportName());
            generatedReportDTO.setDescription(report.getLeft().getDescription());
            generatedReportDTO.setFile(generatedHTML.getBytes(StandardCharsets.UTF_8));
            generatedReportDTO.setFileContentType(birtEngine.getMIMEType("html"));
            generatedReportDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            generatedReportDTO.setCreatedDate(LocalDate.now());

            generatedReportDTO = generatedReportService.save(generatedReportDTO);
            generatedReportDTO.setUrl(currentDomain + "api/reports/download/" + generatedReportDTO.getId());
            generatedReportDTO.setContent(generatedHTML);
            generatedReportDTO.setFile(null);
            return generatedReportDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    /**
     * Generate a report as PDF
     */
    @SuppressWarnings("unchecked")
    private GeneratedReportDTO generatePDFReportForExternalServices(
        Pair<ReportDTO, IReportRunnable> report,
        Object data,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report.getRight());
        IRenderOption options = new RenderOption();
        PDFRenderOption pdfRenderOption = new PDFRenderOption(options);
        pdfRenderOption.setOutputFormat("pdf");

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", data);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(pdfRenderOption);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfRenderOption.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();

            GeneratedReportDTO generatedReportDTO = new GeneratedReportDTO();
            generatedReportDTO.setName(report.getLeft().getReportName());
            generatedReportDTO.setDescription(report.getLeft().getDescription());
            generatedReportDTO.setFile(byteArrayOutputStream.toByteArray());
            generatedReportDTO.setFileContentType(birtEngine.getMIMEType("pdf"));
            generatedReportDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            generatedReportDTO.setCreatedDate(LocalDate.now());

            generatedReportDTO = generatedReportService.save(generatedReportDTO);
            generatedReportDTO.setUrl(currentDomain + "api/reports/download/" + generatedReportDTO.getId());
            generatedReportDTO.setFile(null);
            return generatedReportDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    private GeneratedReportDTO generateReportForExternalServices(
        Pair<ReportDTO, IReportRunnable> report,
        OutputType output,
        Object id,
        String lang,
        Long country,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report.getRight());
        IRenderOption options = new RenderOption();
        options.setOutputFormat(output.name());

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(options);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            options.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();

            GeneratedReportDTO generatedReportDTO = new GeneratedReportDTO();
            generatedReportDTO.setName(report.getLeft().getReportName());
            generatedReportDTO.setDescription(report.getLeft().getDescription());
            generatedReportDTO.setFile(byteArrayOutputStream.toByteArray());
            generatedReportDTO.setFileContentType(birtEngine.getMIMEType("doc"));
            generatedReportDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
            generatedReportDTO.setCreatedDate(LocalDate.now());

            generatedReportDTO = generatedReportService.save(generatedReportDTO);
            generatedReportDTO.setUrl(currentDomain + "api/reports/download/" + generatedReportDTO.getId());
            generatedReportDTO.setFile(null);
            return generatedReportDTO;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
        }
    }

    @Override
    public void destroy() {
        birtEngine.destroy();
        Platform.shutdown();
    }
}
