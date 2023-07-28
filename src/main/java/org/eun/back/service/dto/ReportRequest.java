package org.eun.back.service.dto;

public class ReportRequest {

    private String data;

    private String output;

    private String lang;

    private String country;

    private Long reportId;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return (
            "ReportRequest{" +
            "data='" +
            data +
            '\'' +
            ", output='" +
            output +
            '\'' +
            ", lang='" +
            lang +
            '\'' +
            ", country='" +
            country +
            '\'' +
            ", reportId=" +
            reportId +
            '}'
        );
    }
}
