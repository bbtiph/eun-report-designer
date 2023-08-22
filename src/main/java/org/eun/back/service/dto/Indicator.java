package org.eun.back.service.dto;

import java.util.List;
import lombok.Data;

public class Indicator<T> {

    private String code;
    private String label;
    private String value;
    private List<T> data;

    public Indicator() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
    // Constructors, getters, setters
}
