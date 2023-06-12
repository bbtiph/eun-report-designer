package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.eun.back.domain.ReportBlocksContentData;

/**
 * A DTO for the {@link org.eun.back.domain.ReportBlocksContent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocksContentDTO implements Serializable {

    private Long id;

    private String type;

    private Long priorityNumber;

    private String template;

    private Boolean isActive;

    //    private ReportBlocksDTO reportBlocks;

    private Set<ReportBlocksContentDataDTO> reportBlocksContentData = new HashSet<>();

    private String newContentData;

    public String getNewContentData() {
        return newContentData;
    }

    public void setNewContentData(String newContentData) {
        this.newContentData = newContentData;
    }

    public Set<ReportBlocksContentDataDTO> getReportBlocksContentData() {
        return reportBlocksContentData;
    }

    public void setReportBlocksContentData(Set<ReportBlocksContentDataDTO> reportBlocksContentData) {
        this.reportBlocksContentData = reportBlocksContentData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Long priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    //    public ReportBlocksDTO getReportBlocks() {
    //        return reportBlocks;
    //    }
    //
    //    public void setReportBlocks(ReportBlocksDTO reportBlocks) {
    //        this.reportBlocks = reportBlocks;
    //    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportBlocksContentDTO)) {
            return false;
        }

        ReportBlocksContentDTO reportBlocksContentDTO = (ReportBlocksContentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportBlocksContentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportBlocksContentDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", priorityNumber=" + getPriorityNumber() +
            ", template='" + getTemplate() + "'" +
            ", isActive='" + getIsActive() + "'" +
//            ", reportBlocks=" + getReportBlocks() +
            "}";
    }
}
