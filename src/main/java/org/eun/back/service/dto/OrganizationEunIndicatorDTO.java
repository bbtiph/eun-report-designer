package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.OrganizationEunIndicator} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationEunIndicatorDTO implements Serializable {

    private Long id;

    private Long period;

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
        if (!(o instanceof OrganizationEunIndicatorDTO)) {
            return false;
        }

        OrganizationEunIndicatorDTO organizationEunIndicatorDTO = (OrganizationEunIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationEunIndicatorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationEunIndicatorDTO{" +
            "id=" + id +
            ", period=" + period +
            ", nCount=" + nCount +
            ", countryId=" + countryId +
            ", projectId=" + projectId +
            ", projectUrl='" + projectUrl + '\'' +
            ", countryName='" + countryName + '\'' +
            ", projectName='" + projectName + '\'' +
            ", reportsProjectId=" + reportsProjectId +
            '}';
    }
}
