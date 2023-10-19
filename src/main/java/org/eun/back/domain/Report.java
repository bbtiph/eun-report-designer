package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "report_name", nullable = false)
    private String reportName;

    @Column(name = "acronym")
    private String acronym;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_ministry")
    private Boolean isMinistry;

    @Column(name = "parent_id")
    private Long parentId;

    @Lob
    @Column(name = "file")
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

    @ManyToMany(mappedBy = "reportIds")
    @JsonIgnoreProperties(value = { "countryIds", "reportIds", "reportBlocksContents" }, allowSetters = true)
    private Set<ReportBlocks> reportBlockIds = new HashSet<>();

    @ManyToOne
    private ReportTemplate reportTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Report id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportName() {
        return this.reportName;
    }

    public Report reportName(String reportName) {
        this.setReportName(reportName);
        return this;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Report acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return this.description;
    }

    public Report description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public Report type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Report isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsMinistry() {
        return this.isMinistry;
    }

    public Report isMinistry(Boolean isMinistry) {
        this.setIsMinistry(isMinistry);
        return this;
    }

    public void setIsMinistry(Boolean isMinistry) {
        this.isMinistry = isMinistry;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Report parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public byte[] getFile() {
        return this.file;
    }

    public Report file(byte[] file) {
        this.setFile(file);
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public Report fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Set<ReportBlocks> getReportBlockIds() {
        return this.reportBlockIds;
    }

    public void setReportBlockIds(Set<ReportBlocks> reportBlocks) {
        if (this.reportBlockIds != null) {
            this.reportBlockIds.forEach(i -> i.removeReportId(this));
        }
        if (reportBlocks != null) {
            reportBlocks.forEach(i -> i.addReportId(this));
        }
        this.reportBlockIds = reportBlocks;
    }

    public Report reportBlockIds(Set<ReportBlocks> reportBlocks) {
        this.setReportBlockIds(reportBlocks);
        return this;
    }

    public Report addReportBlockId(ReportBlocks reportBlocks) {
        this.reportBlockIds.add(reportBlocks);
        reportBlocks.getReportIds().add(this);
        return this;
    }

    public Report removeReportBlockId(ReportBlocks reportBlocks) {
        this.reportBlockIds.remove(reportBlocks);
        reportBlocks.getReportIds().remove(this);
        return this;
    }

    public ReportTemplate getReportTemplate() {
        return this.reportTemplate;
    }

    public void setReportTemplate(ReportTemplate reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    public Report reportTemplate(ReportTemplate reportTemplate) {
        this.setReportTemplate(reportTemplate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        return id != null && id.equals(((Report) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", reportName='" + getReportName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isMinistry='" + getIsMinistry() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
