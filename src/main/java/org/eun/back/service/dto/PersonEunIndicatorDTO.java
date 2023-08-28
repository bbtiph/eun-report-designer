package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.PersonEunIndicator} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonEunIndicatorDTO implements Serializable {

    private Long id;

    private Long nCount;

    private Long countryId;

    private Long projectId;

    private String projectUrl;

    private String countryName;

    private String projectName;

    private Long reportsProjectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getnCount() {
        return nCount;
    }

    public void setnCount(Long nCount) {
        this.nCount = nCount;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getReportsProjectId() {
        return reportsProjectId;
    }

    public void setReportsProjectId(Long reportsProjectId) {
        this.reportsProjectId = reportsProjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonEunIndicatorDTO)) {
            return false;
        }

        PersonEunIndicatorDTO personEunIndicatorDTO = (PersonEunIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personEunIndicatorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "PersonEunIndicatorDTO{" +
            "id=" +
            id +
            ", nCount=" +
            nCount +
            ", countryId=" +
            countryId +
            ", projectId=" +
            projectId +
            ", projectUrl='" +
            projectUrl +
            '\'' +
            ", countryName='" +
            countryName +
            '\'' +
            ", projectName='" +
            projectName +
            '\'' +
            ", reportsProjectId=" +
            reportsProjectId +
            '}'
        );
    }
}
