package org.eun.back.service.dto;

public enum ReportFormat {
    DOC("Word Document"),
    PDF("Portable Document Format"),
    HTML("HyperText Markup Language");

    private final String description;

    ReportFormat(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
