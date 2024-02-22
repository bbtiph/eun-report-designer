package org.eun.back.service.dto;

import java.io.File;

public class ReportFile {

    private File file;
    private int pageCount;

    private String reportBlockName;

    public ReportFile(File file, int pageCount, String reportBlockName) {
        this.file = file;
        this.pageCount = pageCount;
        this.reportBlockName = reportBlockName;
    }

    public File getFile() {
        return file;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getReportBlockName() {
        return reportBlockName;
    }

    public void setReportBlockName(String reportBlockName) {
        this.reportBlockName = reportBlockName;
    }
}
