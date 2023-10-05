package org.eun.back.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public class ReportExternalRequest {

    private Long reportId;
    private Long countryId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String format;
    private String lang;
    private String data;
    private List<ReportBlockId> reportBlockIds;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<ReportBlockId> getReportBlockIds() {
        return reportBlockIds;
    }

    public void setReportBlockIds(List<ReportBlockId> reportBlockIds) {
        this.reportBlockIds = reportBlockIds;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return (
            "ReportExternalRequest{" +
            "reportId=" +
            reportId +
            ", countryId=" +
            countryId +
            ", startDate=" +
            startDate +
            ", endDate=" +
            endDate +
            ", format='" +
            format +
            '\'' +
            ", reportBlockIds=" +
            reportBlockIds +
            '}'
        );
    }
}
