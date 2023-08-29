package org.eun.back.service.dto;

import lombok.Data;

@Data
public class ApiResponseOrganizationAndPersonDto {

    private Long period;
    private Long n_count;
    private Long country_id;
    private Long project_id;
    private String project_url;
    private String country_name;
    private String project_name;
    private Long reports_project_id;

    public Long getN_count() {
        return n_count;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public String getProject_url() {
        return project_url;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getProject_name() {
        return project_name;
    }

    public Long getReports_project_id() {
        return reports_project_id;
    }

    public Long getPeriod() {
        return period;
    }
}
