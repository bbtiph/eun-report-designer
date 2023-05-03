package org.eun.back.service.dto;

import org.eclipse.birt.report.engine.api.IRenderOption;

public enum OutputType {
    HTML(IRenderOption.OUTPUT_FORMAT_HTML),
    PDF(IRenderOption.OUTPUT_FORMAT_PDF),
    DOCX("docx"),
    DOC("doc"),
    XLSX("xlsx"),
    ODT("odt"),
    INVALID("invalid");

    String val;

    OutputType(String val) {
        this.val = val;
    }

    public static OutputType from(String text) {
        for (OutputType output : values()) {
            if (output.val.equalsIgnoreCase(text)) return output;
        }
        return INVALID;
    }
}
