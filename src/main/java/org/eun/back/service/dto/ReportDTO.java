package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.eun.back.domain.Report} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportDTO implements Serializable {

    private Long id;

    @NotNull
    private String reportName;

    private String acronym;

    private String description;

    private String type;

    private Boolean isActive;

    private Boolean isMinistry;

    private Long parentId;

    @Lob
    private byte[] file;

    private String fileContentType;
    private ReportTemplateDTO reportTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsMinistry() {
        return isMinistry;
    }

    public void setIsMinistry(Boolean isMinistry) {
        this.isMinistry = isMinistry;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public ReportTemplateDTO getReportTemplate() {
        return reportTemplate;
    }

    public void setReportTemplate(ReportTemplateDTO reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportDTO)) {
            return false;
        }

        ReportDTO reportDTO = (ReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportDTO{" +
            "id=" + getId() +
            ", reportName='" + getReportName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isMinistry='" + getIsMinistry() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", file='" + getFile() + "'" +
            ", reportTemplate=" + getReportTemplate() +
            "}";
    }
}
