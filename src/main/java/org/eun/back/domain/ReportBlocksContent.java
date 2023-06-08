package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ReportBlocksContent.
 */
@Entity
@Table(name = "report_blocks_content")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocksContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "priority_number")
    private Long priorityNumber;

    @Column(name = "template")
    private String template;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    //    @JsonBackReference
    @JsonIgnoreProperties(value = { "countryIds", "report", "reportBlocksContents", "reportBlocks" }, allowSetters = true)
    private ReportBlocks reportBlocks;

    @OneToMany(mappedBy = "reportBlocksContent", cascade = CascadeType.ALL)
    //    @JsonManagedReference
    @JsonIgnoreProperties(value = { "reportBlocksContent", "country" }, allowSetters = true)
    private Set<ReportBlocksContentData> reportBlocksContentData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReportBlocksContent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public ReportBlocksContent type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPriorityNumber() {
        return this.priorityNumber;
    }

    public ReportBlocksContent priorityNumber(Long priorityNumber) {
        this.setPriorityNumber(priorityNumber);
        return this;
    }

    public void setPriorityNumber(Long priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getTemplate() {
        return this.template;
    }

    public ReportBlocksContent template(String template) {
        this.setTemplate(template);
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ReportBlocksContent isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ReportBlocks getReportBlocks() {
        return this.reportBlocks;
    }

    public void setReportBlocks(ReportBlocks reportBlocks) {
        this.reportBlocks = reportBlocks;
    }

    public ReportBlocksContent reportBlocks(ReportBlocks reportBlocks) {
        this.setReportBlocks(reportBlocks);
        return this;
    }

    public Set<ReportBlocksContentData> getReportBlocksContentData() {
        return this.reportBlocksContentData;
    }

    public void setReportBlocksContentData(Set<ReportBlocksContentData> reportBlocksContentData) {
        if (this.reportBlocksContentData != null) {
            this.reportBlocksContentData.forEach(i -> i.setReportBlocksContent(null));
        }
        if (reportBlocksContentData != null) {
            reportBlocksContentData.forEach(i -> i.setReportBlocksContent(this));
        }
        this.reportBlocksContentData = reportBlocksContentData;
    }

    public ReportBlocksContent reportBlocksContentData(Set<ReportBlocksContentData> reportBlocksContentData) {
        this.setReportBlocksContentData(reportBlocksContentData);
        return this;
    }

    public ReportBlocksContent addReportBlocksContentData(ReportBlocksContentData reportBlocksContentData) {
        this.reportBlocksContentData.add(reportBlocksContentData);
        reportBlocksContentData.setReportBlocksContent(this);
        return this;
    }

    public ReportBlocksContent removeReportBlocksContentData(ReportBlocksContentData reportBlocksContentData) {
        this.reportBlocksContentData.remove(reportBlocksContentData);
        reportBlocksContentData.setReportBlocksContent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportBlocksContent)) {
            return false;
        }
        return id != null && id.equals(((ReportBlocksContent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportBlocksContent{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", priorityNumber=" + getPriorityNumber() +
            ", template='" + getTemplate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
