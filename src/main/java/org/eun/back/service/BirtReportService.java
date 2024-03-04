package org.eun.back.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.eun.back.security.SecurityUtils;
import org.eun.back.service.dto.*;
import org.eun.back.util.Utils;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
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
    private static final int TOP_MARGIN = 750;
    private static final int LINE_START_X = 50;
    private static final int LINE_END_X_OFFSET = -10;

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

    private final ReportTemplateService reportTemplateService;

    public BirtReportService(
        ReportService reportService,
        GeneratedReportService generatedReportService,
        ReportTemplateService reportTemplateService
    ) {
        this.reportService = reportService;
        this.generatedReportService = generatedReportService;
        this.reportTemplateService = reportTemplateService;
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

    private Pair<ReportDTO, IReportRunnable> getReport(String reportName) {
        ReportDTO report = this.reportService.findOne(reportName).get();
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
        HttpServletRequest request,
        Map<String, Object> params
    ) throws IOException {
        String format = reportRequest.getOutput();
        String lang = reportRequest.getLang();
        Long countryId = reportRequest.getCountryId();
        Long reportId = reportRequest.getReportId();
        OutputType output = OutputType.from(format);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ReportBlocksDTO> content = reportBlocksService.findAllByReportAndCountry(reportId, countryId);
        List<String> listOfHeaders = content.stream().map(ReportBlocksDTO::getName).collect(Collectors.toList());
        try {
            reportRequest.setData(objectMapper.writeValueAsString(content));
        } catch (JsonProcessingException e) {
            reportRequest.setData("");
        }
        String data = reportRequest.getData();

        switch (output) {
            case HTML:
                generateHTMLReport(getReport(reportId).getRight(), data, lang, countryId, response, request);
                break;
            case PDF:
                generatePDFReport(getReport(reportId).getRight(), data, lang, countryId, listOfHeaders, response, request, params);
                break;
            case DOCX:
                generateDOCXReport(getReport(reportId).getRight(), data, lang, countryId, listOfHeaders, response, request, params);
                break;
            case DOC:
                generateReport(getReport(reportId).getRight(), output, data, lang, countryId, listOfHeaders, response, request, params);
                break;
            case XLSX:
                generateXLSXReport(reports.get(reportName.toLowerCase()), data, lang, countryId, response, request);
                break;
            //            case ODT:
            //                generateReport(reports.get(reportName.toLowerCase()), output, data, lang, countryId, response, request);
            //                break;
            default:
                throw new IllegalArgumentException("Output type not recognized:" + output);
        }
    }

    public File generateMainReportFile(String outputType, Map<String, Object> params) {
        OutputType output = OutputType.from(outputType);
        ObjectMapper objectMapper = new ObjectMapper();
        String paramsJson;
        try {
            paramsJson = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        switch (output) {
            case PDF:
                return generatePDFReportFile(reports.get("main_page"), paramsJson);
            case DOC:
                return generateReportFile(reports.get("main_page"), output, paramsJson);
            case DOCX:
                return generateDOCXReportFile(reports.get("main_page"), paramsJson);
            default:
                throw new IllegalArgumentException("Output type not recognized:" + output);
        }
    }

    public void generateMainReportWithTOC(
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
        List<ReportBlocksDTO> reportBlocksDTOList = reportBlocksService.findAllByReportAndCountry(reportId, countryId);
        List<ReportFile> generatedReports = new ArrayList<>();

        for (ReportBlocksDTO reportBlock : reportBlocksDTOList) {
            try {
                reportRequest.setData(objectMapper.writeValueAsString(new ArrayList<>(Collections.singleton(reportBlock))));
            } catch (JsonProcessingException e) {
                reportRequest.setData("");
            }
            String data = reportRequest.getData();

            int pageCount = 0;
            switch (output) {
                case PDF:
                    File pdfReport = generatePDFReportRes(getReport(reportId).getRight(), data, lang, countryId);
                    try {
                        pageCount = getPageCount(pdfReport, output);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    generatedReports.add(new ReportFile(pdfReport, pageCount, reportBlock.getName()));
                    break;
                case DOCX:
                    File docxReport = generateDOCXReportRes(getReport(reportId).getRight(), data, lang, countryId);
                    try {
                        pageCount = getPageCount(docxReport, output);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    generatedReports.add(new ReportFile(docxReport, pageCount, reportBlock.getName()));
                    break;
                default:
                    throw new IllegalArgumentException("Output type not recognized:" + output);
            }
        }

        File res;
        switch (output) {
            case PDF:
                try {
                    ReportFile newContents = new ReportFile(generatePDFTOC(generatedReports, new File("contents.pdf")), 1, "Contents:");
                    generatedReports.add(0, newContents);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                res = mergePDFs(generatedReports);
                break;
            case DOCX:
                res = mergeDOCXs(null);
                break;
            default:
                throw new IllegalArgumentException("Output type not recognized:" + output);
        }

        try (FileInputStream fis = new FileInputStream(res)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" + res.getName());

            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File generatePDFTOC(List<ReportFile> generatedReports, File outputFile) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPageTree pages = document.getPages();
            PDPage tocPage = new PDPage();
            pages.add(tocPage);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, tocPage)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(LINE_START_X, TOP_MARGIN);
                contentStream.showText("Contents:");
                contentStream.endText();

                PDDocumentOutline outline = new PDDocumentOutline();
                document.getDocumentCatalog().setDocumentOutline(outline);

                PDOutlineItem tocItem = new PDOutlineItem();
                tocItem.setTitle("Contents:");
                outline.addLast(tocItem);

                int pageNumber = 1;
                int page = 1;
                for (ReportFile reportFile : generatedReports) {
                    PDPageXYZDestination dest = new PDPageXYZDestination();
                    dest.setPage(tocPage);
                    dest.setTop(TOP_MARGIN - (20 * pageNumber));
                    PDOutlineItem item = new PDOutlineItem();
                    item.setDestination(dest);
                    item.setTitle(reportFile.getReportBlockName());
                    tocItem.addLast(item);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(LINE_START_X, TOP_MARGIN - (20 * pageNumber));
                    contentStream.showText(reportFile.getReportBlockName());
                    contentStream.endText();

                    float pageWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(Integer.toString(pageNumber)) / 1000f * 12;
                    float xPos = tocPage.getMediaBox().getWidth() - LINE_START_X - pageWidth;

                    contentStream.beginText();
                    contentStream.newLineAtOffset(xPos, TOP_MARGIN - (20 * pageNumber));
                    contentStream.showText(Integer.toString(page));
                    contentStream.endText();

                    contentStream.drawLine(
                        LINE_START_X,
                        TOP_MARGIN - 2.5f - (20 * pageNumber),
                        xPos + LINE_END_X_OFFSET,
                        TOP_MARGIN - 2.5f - (20 * pageNumber)
                    ); // Draw dot leader
                    page += reportFile.getPageCount();
                    pageNumber++;
                }
            }

            document.save(outputFile);
            return outputFile;
        }
    }

    public static Pair<List<String>, File> generatePDFTOC(Map<String, Integer> tocContent, File outputFile) throws IOException {
        List<String> contents = new ArrayList<>();
        contents.add("Contents:");
        try (PDDocument document = new PDDocument()) {
            PDPageTree pages = document.getPages();
            PDPage tocPage = new PDPage();
            pages.add(tocPage);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, tocPage)) {
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(LINE_START_X, TOP_MARGIN);
                //                contentStream.setNonStrokingColor(68, 114, 196); // #4472C4 in RGB
                contentStream.showText("Contents:");
                contentStream.endText();

                PDDocumentOutline outline = new PDDocumentOutline();
                document.getDocumentCatalog().setDocumentOutline(outline);

                PDOutlineItem tocItem = new PDOutlineItem();
                tocItem.setTitle("Contents:");
                outline.addLast(tocItem);

                int pageNumber = 1;
                for (Map.Entry<String, Integer> entry : tocContent.entrySet()) {
                    PDPageXYZDestination dest = new PDPageXYZDestination();
                    dest.setPage(tocPage);
                    dest.setTop(TOP_MARGIN - (20 * pageNumber));
                    PDOutlineItem item = new PDOutlineItem();
                    item.setDestination(dest);
                    item.setTitle(entry.getKey());
                    tocItem.addLast(item);

                    // Draw dots
                    float keyWidth = PDType1Font.TIMES_ROMAN.getStringWidth(entry.getKey()) / 1000f * 12;
                    float dotsStartX = LINE_START_X + keyWidth;
                    float dotsEndX = tocPage.getMediaBox().getWidth() - LINE_START_X - 12;
                    float dotX = dotsStartX;
                    String dotsLine = "";
                    while (dotX < dotsEndX) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(dotX, TOP_MARGIN - 2.5f - (20 * pageNumber));
                        contentStream.showText(".");
                        contentStream.endText();
                        dotX += 3;
                        dotsLine += '.';
                    }

                    contentStream.beginText();
                    contentStream.newLineAtOffset(LINE_START_X, TOP_MARGIN - (20 * pageNumber));
                    //                    contentStream.setNonStrokingColor(68, 114, 196); // #4472C4 in RGB
                    contentStream.showText(entry.getKey());
                    contentStream.endText();

                    // Draw page number
                    float pageNumberWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(Integer.toString(entry.getValue())) / 1000f * 12;
                    float pageNumberXPos = tocPage.getMediaBox().getWidth() - LINE_START_X - pageNumberWidth;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(pageNumberXPos, TOP_MARGIN - (20 * pageNumber));
                    contentStream.setNonStrokingColor(0, 0, 0);
                    contentStream.showText(Integer.toString(entry.getValue() + 1));
                    contentStream.endText();

                    contents.add(entry.getKey() + dotsLine + (entry.getValue() + 1));
                    pageNumber++;
                }
            }

            document.save(outputFile);
            return ImmutablePair.of(contents, outputFile);
        }
    }

    public static int getPageCount(File file, OutputType output) throws IOException {
        switch (output) {
            case PDF:
                try (PDDocument document = PDDocument.load(file)) {
                    return document.getNumberOfPages();
                }
            case DOCX:
                try (XWPFDocument document = new XWPFDocument(new FileInputStream(file))) {
                    return document.getProperties().getExtendedProperties().getPages();
                }
            default:
                return 0;
        }
    }

    public File generatePDFReportRes(IReportRunnable report, Object data, String lang, Long country) {
        try {
            IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
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
                File pdfFile = File.createTempFile("report", ".pdf");
                FileUtils.writeByteArrayToFile(pdfFile, byteArrayOutputStream.toByteArray());
                return pdfFile;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                runAndRenderTask.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF report", e);
        }
    }

    public File generatePDFReportFile(IReportRunnable report, String params) {
        try {
            IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
            IRenderOption options = new RenderOption();
            PDFRenderOption pdfRenderOption = new PDFRenderOption(options);
            pdfRenderOption.setOutputFormat("pdf");

            Map<String, Object> birtParams = new HashMap<>();
            birtParams.put("params", params);

            runAndRenderTask.setParameterValues(birtParams);
            runAndRenderTask.setRenderOption(pdfRenderOption);

            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                pdfRenderOption.setOutputStream(byteArrayOutputStream);
                runAndRenderTask.run();
                File pdfFile = File.createTempFile("report", ".pdf");
                FileUtils.writeByteArrayToFile(pdfFile, byteArrayOutputStream.toByteArray());
                return pdfFile;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                runAndRenderTask.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF report", e);
        }
    }

    public File generateDOCXReportRes(IReportRunnable report, Object id, String lang, Long country) {
        try {
            IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
            DocxRenderOption docxRenderOption = new DocxRenderOption();
            docxRenderOption.setOutputFormat("docx");

            Map<String, Object> birtParams = new HashMap<>();
            birtParams.put("docId", id);
            birtParams.put("country", country);
            birtParams.put("lang", lang);

            runAndRenderTask.setParameterValues(birtParams);
            runAndRenderTask.setRenderOption(docxRenderOption);

            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                docxRenderOption.setOutputStream(byteArrayOutputStream);
                runAndRenderTask.run();

                File docxFile = File.createTempFile("report", ".docx");
                FileUtils.writeByteArrayToFile(docxFile, byteArrayOutputStream.toByteArray());
                return docxFile;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                runAndRenderTask.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate DOCX report", e);
        }
    }

    public File generateDOCXReportFile(IReportRunnable report, String params) {
        try {
            IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
            DocxRenderOption docxRenderOption = new DocxRenderOption();
            docxRenderOption.setOutputFormat("docx");
            docxRenderOption.setOption(DocxRenderOption.OPTION_EMBED_HTML, Boolean.FALSE);

            Map<String, Object> birtParams = new HashMap<>();
            birtParams.put("params", params);

            runAndRenderTask.setParameterValues(birtParams);
            runAndRenderTask.setRenderOption(docxRenderOption);

            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                docxRenderOption.setOutputStream(byteArrayOutputStream);
                runAndRenderTask.run();

                File docxFile = File.createTempFile("report", ".docx");
                FileUtils.writeByteArrayToFile(docxFile, byteArrayOutputStream.toByteArray());
                return docxFile;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally {
                runAndRenderTask.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate DOCX report", e);
        }
    }

    public File mergeDOCXs(final List<File> files) {
        File outFile = new File("src/main/resources/out.docx");
        try (
            OutputStream os = new FileOutputStream(outFile);
            InputStream is = new FileInputStream(files.get(0));
            XWPFDocument doc = new XWPFDocument(is);
        ) {
            CTBody ctBody = doc.getDocument().getBody();
            for (int i = 1; i < files.size(); i++) {
                try (InputStream isI = new FileInputStream(files.get(i)); XWPFDocument docI = new XWPFDocument(isI);) {
                    CTBody ctBodyI = docI.getDocument().getBody();
                    appendBody(ctBody, ctBodyI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            doc.write(os);
            return outFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File("empty.docx");
    }

    private static void appendBody(CTBody src, CTBody append) throws XmlException {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String mainPart = srcString.substring(srcString.indexOf(">") + 1, srcString.lastIndexOf("<"));
        String suffix = srcString.substring(srcString.lastIndexOf("<"));
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        CTBody makeBody;
        try {
            makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + suffix);
        } catch (XmlException e) {
            e.printStackTrace();
            throw new XmlException("", e);
        }
        src.set(makeBody);
    }

    public File mergePDFs(List<ReportFile> pdfFiles) {
        try {
            File mergedPdfFile = File.createTempFile("merged_report", ".pdf");
            PDFMergerUtility merger = new PDFMergerUtility();
            merger.setDestinationFileName(mergedPdfFile.getAbsolutePath());

            for (ReportFile pdfFile : pdfFiles) {
                merger.addSource(pdfFile.getFile());
            }

            merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            return mergedPdfFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to merge PDF files", e);
        }
    }

    public File mergePDFsWithTypeFile(List<File> pdfFiles) {
        try {
            File mergedPdfFile = File.createTempFile("merged_report", ".pdf");
            PDFMergerUtility merger = new PDFMergerUtility();
            merger.setDestinationFileName(mergedPdfFile.getAbsolutePath());

            for (File pdfFile : pdfFiles) {
                merger.addSource(pdfFile);
            }

            merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            return mergedPdfFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to merge PDF files", e);
        }
    }

    public GeneratedReportDTO generateExternalReport(
        ReportExternalRequest reportExternalRequest,
        HttpServletResponse response,
        HttpServletRequest request
    ) {
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

    public byte[] generateReferenceReport(ReferenceTableSettingsDTO referenceTableSettingsDTO) throws IOException, EngineException {
        ReportTemplateDTO reportTemplate = reportTemplateService.findOneByName("Reference").get();
        return generateXLSXReportForReferences(
            birtEngine.openReportDesign(convertByteArrayToInputStream(reportTemplate.getFile())),
            referenceTableSettingsDTO.getId(),
            "en"
        );
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
        List<String> listOfHeaders,
        HttpServletResponse response,
        HttpServletRequest request,
        Map<String, Object> params
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

        File pdfFile = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfRenderOption.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();
            List<File> generatedReports = new ArrayList<>();
            generatedReports.add(generateMainReportFile("pdf", params));
            pdfFile = File.createTempFile("report", ".pdf");
            FileUtils.writeByteArrayToFile(pdfFile, byteArrayOutputStream.toByteArray());
            Map<String, Integer> tocContent = analyzePDFReport(pdfFile, listOfHeaders);
            Map<String, Integer> sortedTocContent = tocContent
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);
            generatedReports.add(generatePDFTOC(sortedTocContent, new File("contents.pdf")).getRight());
            generatedReports.add(pdfFile);
            pdfFile = mergePDFsWithTypeFile(generatedReports);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
            if (pdfFile != null) {
                try (FileInputStream fis = new FileInputStream(pdfFile)) {
                    response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
                    response.setContentLength((int) pdfFile.length());
                    ServletOutputStream os = response.getOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } finally {
                    pdfFile.delete();
                }
            }
        }
    }

    private Map<String, Integer> getTocFromFDF(IReportRunnable report, Object data, String lang, Long country, List<String> listOfHeaders) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        IRenderOption options = new RenderOption();
        PDFRenderOption pdfRenderOption = new PDFRenderOption(options);
        pdfRenderOption.setOutputFormat("pdf");

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", data);
        birtParams.put("country", country);
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(pdfRenderOption);

        File pdfFile;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfRenderOption.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();
            pdfFile = File.createTempFile("report", ".pdf");
            FileUtils.writeByteArrayToFile(pdfFile, byteArrayOutputStream.toByteArray());
            Map<String, Integer> tocContent = analyzePDFReport(pdfFile, listOfHeaders);
            Map<String, Integer> sortedTocContent = tocContent
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(LinkedHashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), LinkedHashMap::putAll);
            return sortedTocContent;
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
        List<String> listOfHeaders,
        HttpServletResponse response,
        HttpServletRequest request,
        Map<String, Object> params
    ) throws IOException {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType("docx"));
        DocxRenderOption docxRenderOption = new DocxRenderOption();
        docxRenderOption.setOutputFormat("docx");
        docxRenderOption.setOption(DocxRenderOption.OPTION_EMBED_HTML, Boolean.FALSE);

        ObjectMapper objectMapper = new ObjectMapper();
        String paramsJson;
        String tocJson;
        Pair<List<String>, File> res = generatePDFTOC(getTocFromFDF(report, id, lang, country, listOfHeaders), new File("contents.pdf"));
        try {
            tocJson = objectMapper.writeValueAsString(res.getLeft());
            params.put("toc", tocJson);
            paramsJson = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);
        birtParams.put("params", paramsJson);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(docxRenderOption);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_URL_PARAM_FORMAT_KEY, request);

        File docxFile = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            docxRenderOption.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();
            List<File> generatedReports = new ArrayList<>();
            generatedReports.add(generateMainReportFile("docx", params));
            docxFile = File.createTempFile("reportContent", ".docx");
            FileUtils.writeByteArrayToFile(docxFile, byteArrayOutputStream.toByteArray());
            generatedReports.add(docxFile);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
            if (docxFile != null) {
                try (FileInputStream fis = new FileInputStream(docxFile)) {
                    response.setHeader("Content-Disposition", "attachment; filename=report.docx");
                    response.setContentLength((int) docxFile.length());
                    ServletOutputStream os = response.getOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } finally {
                    docxFile.delete();
                }
            }
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
        List<String> listOfHeaders,
        HttpServletResponse response,
        HttpServletRequest request,
        Map<String, Object> params
    ) throws IOException {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        response.setContentType(birtEngine.getMIMEType(output.name()));
        IRenderOption options = new RenderOption();
        options.setOutputFormat(output.name());

        ObjectMapper objectMapper = new ObjectMapper();
        String paramsJson;
        String tocJson;
        Pair<List<String>, File> res = generatePDFTOC(getTocFromFDF(report, id, lang, country, listOfHeaders), new File("contents.pdf"));
        try {
            tocJson = objectMapper.writeValueAsString(res.getLeft());
            params.put("toc", res.getLeft());
            paramsJson = objectMapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("docId", id);
        birtParams.put("country", country);
        birtParams.put("lang", lang);
        birtParams.put("params", paramsJson);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(options);
        runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_URL_PARAM_FORMAT_KEY, request);

        File docFile = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            options.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();
            List<File> generatedReports = new ArrayList<>();
            generatedReports.add(generateMainReportFile("doc", params));
            docFile = File.createTempFile("reportContent", ".doc");
            FileUtils.writeByteArrayToFile(docFile, byteArrayOutputStream.toByteArray());
            generatedReports.add(docFile);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            runAndRenderTask.close();
            if (docFile != null) {
                try (FileInputStream fis = new FileInputStream(docFile)) {
                    response.setHeader("Content-Disposition", "attachment; filename=report.doc");
                    response.setContentLength((int) docFile.length());
                    ServletOutputStream os = response.getOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                    os.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                } finally {
                    docFile.delete();
                }
            }
        }
    }

    private File generateReportFile(IReportRunnable report, OutputType output, String params) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        IRenderOption options = new RenderOption();
        options.setOutputFormat(output.name());

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("params", params);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(options);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            options.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();
            File file = File.createTempFile("report", "." + output.name());
            FileUtils.writeByteArrayToFile(file, byteArrayOutputStream.toByteArray());
            return file;
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
            Pattern headPattern = Pattern.compile("(?i)<head>(.*?)</head>", Pattern.DOTALL);
            Matcher headMatcher = headPattern.matcher(generatedHTML);

            String extractedHead = "";
            if (headMatcher.find()) {
                extractedHead = headMatcher.group(1);
                generatedHTML = generatedHTML.replace(headMatcher.group(), ""); // Remove the <head> section from generatedHTML
            }

            generatedHTML =
                generatedHTML
                    .replaceAll("(?i)<!DOCTYPE\\s*HTML[^>]*>", "") // Remove DOCTYPE declaration
                    .replaceAll("(?i)<(/?)html( [^>]*)?>", "") // Remove html tags
                    .replaceAll("(?i)<(/?)body( [^>]*)?>", "") // Remove body tags
                    .replaceAll("\n", "")
                    .replaceAll("\t", "");

            extractedHead = extractedHead.replaceAll("\n", "").replaceAll("\t", "");

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
            generatedReportDTO.setHead(extractedHead);
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

    private byte[] generateXLSXReportForReferences(IReportRunnable report, Object id, String lang) {
        IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
        IExcelRenderOption options = new EXCELRenderOption();
        options.setOutputFormat("xlsx");

        Map<String, Object> birtParams = new HashMap<>();
        birtParams.put("id", id.toString());
        birtParams.put("lang", lang);

        runAndRenderTask.setParameterValues(birtParams);
        runAndRenderTask.setRenderOption(options);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            options.setOutputStream(byteArrayOutputStream);
            runAndRenderTask.run();

            return byteArrayOutputStream.toByteArray();
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

    public static Map<String, Integer> analyzePDFReport(File pdfFile, List<String> blockNames) {
        Map<String, Integer> blockPageMapping = new HashMap<>();
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            for (int pageNum = 0; pageNum < document.getNumberOfPages(); pageNum++) {
                stripper.setStartPage(pageNum + 1);
                stripper.setEndPage(pageNum + 1);
                String text = stripper.getText(document);
                for (String blockName : blockNames) {
                    if (text.contains(blockName)) {
                        blockPageMapping.put(blockName, pageNum + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blockPageMapping;
    }

    @Override
    public void destroy() {
        birtEngine.destroy();
        Platform.shutdown();
    }
}
