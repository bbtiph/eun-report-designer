package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.ReportBlocks} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocksDTO implements Serializable {

    private Long id;

    private String countryName;

    private Long priorityNumber;

    private String content;

    private Boolean isActive;

    private String type;

    private String sqlScript;

    private CountriesDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Long priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSqlScript() {
        return sqlScript;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
    }

    public CountriesDTO getCountry() {
        return country;
    }

    public void setCountry(CountriesDTO country) {
        this.country = country;
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
            ", countryName='" + getCountryName() + "'" +
            ", priorityNumber=" + getPriorityNumber() +
            ", content='" + getContent() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", type='" + getType() + "'" +
            ", sqlScript='" + getSqlScript() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}
