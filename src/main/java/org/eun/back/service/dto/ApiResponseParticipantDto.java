package org.eun.back.service.dto;

import lombok.Data;

@Data
public class ApiResponseParticipantDto {

    private Long period;
    private Long n_count;
    private String course_id;
    private String course_name;
    private String country_code;

    public Long getPeriod() {
        return period;
    }

    public Long getN_count() {
        return n_count;
    }

    public String getCourse_id() {
        return course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCountry_code() {
        return country_code;
    }
}
