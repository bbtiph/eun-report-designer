package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link org.eun.back.domain.ReportBlocks} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocksDTO implements Serializable {

    private Long id;

    private String name;

    private Long priorityNumber;

    private Boolean isActive;

    private String config;

    private Set<CountriesDTO> countryIds = new HashSet<>();

    private ReportDTO report;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Long priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Set<CountriesDTO> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(Set<CountriesDTO> countryIds) {
        this.countryIds = countryIds;
    }

    public ReportDTO getReport() {
        return report;
    }

    public void setReport(ReportDTO report) {
        this.report = report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportBlocksDTO)) {
            return false;
        }

        ReportBlocksDTO reportBlocksDTO = (ReportBlocksDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportBlocksDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportBlocksDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priorityNumber=" + getPriorityNumber() +
            ", isActive='" + getIsActive() + "'" +
            ", config='" + getConfig() + "'" +
            ", countryIds=" + getCountryIds() +
            ", report=" + getReport() +
            "}";
    }
}
