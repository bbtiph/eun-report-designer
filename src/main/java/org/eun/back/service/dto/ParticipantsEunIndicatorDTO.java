package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.ParticipantsEunIndicator} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParticipantsEunIndicatorDTO implements Serializable {

    private Long id;

    private Long period;

    private Long nCount;

    private String courseId;

    private String courseName;

    private String countryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Long getnCount() {
        return nCount;
    }

    public void setnCount(Long nCount) {
        this.nCount = nCount;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParticipantsEunIndicatorDTO)) {
            return false;
        }

        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO = (ParticipantsEunIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, participantsEunIndicatorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "ParticipantsEunIndicatorDTO{" +
            "id=" + id +
            ", period=" + period +
            ", nCount=" + nCount +
            ", courseId='" + courseId + '\'' +
            ", courseName='" + courseName + '\'' +
            ", countryCode='" + countryCode + '\'' +
            '}';
    }
}
