package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ReportBlocks.
 */
@Entity
@Table(name = "report_blocks")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "priority_number")
    private Long priorityNumber;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "config")
    private String config;

    @ManyToMany
    @JoinTable(
        name = "rel_report_blocks_country",
        joinColumns = @JoinColumn(name = "report_blocks_id"),
        inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    @JsonIgnoreProperties(value = { "reportBlockIds" }, allowSetters = true)
    private Set<Countries> countryIds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "reportBlocks" }, allowSetters = true)
    private Report report;

    @OneToMany(mappedBy = "reportBlocks", cascade = CascadeType.ALL)
    //    @JsonBackReference
    @JsonIgnoreProperties(value = { "reportBlocks", "reportBlocksContentData" }, allowSetters = true)
    private Set<ReportBlocksContent> reportBlocksContents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReportBlocks id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ReportBlocks name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriorityNumber() {
        return this.priorityNumber;
    }

    public ReportBlocks priorityNumber(Long priorityNumber) {
        this.setPriorityNumber(priorityNumber);
        return this;
    }

    public void setPriorityNumber(Long priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ReportBlocks isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getConfig() {
        return this.config;
    }

    public ReportBlocks config(String config) {
        this.setConfig(config);
        return this;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Set<Countries> getCountryIds() {
        return this.countryIds;
    }

    public void setCountryIds(Set<Countries> countries) {
        this.countryIds = countries;
    }

    public ReportBlocks countryIds(Set<Countries> countries) {
        this.setCountryIds(countries);
        return this;
    }

    public ReportBlocks addCountryId(Countries countries) {
        this.countryIds.add(countries);
        countries.getReportBlockIds().add(this);
        return this;
    }

    public ReportBlocks removeCountryId(Countries countries) {
        this.countryIds.remove(countries);
        countries.getReportBlockIds().remove(this);
        return this;
    }

    public Report getReport() {
        return this.report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public ReportBlocks report(Report report) {
        this.setReport(report);
        return this;
    }

    public Set<ReportBlocksContent> getReportBlocksContents() {
        return this.reportBlocksContents;
    }

    public void setReportBlocksContents(Set<ReportBlocksContent> reportBlocksContents) {
        if (this.reportBlocksContents != null) {
            this.reportBlocksContents.forEach(i -> i.setReportBlocks(null));
        }
        if (reportBlocksContents != null) {
            reportBlocksContents.forEach(i -> i.setReportBlocks(this));
        }
        this.reportBlocksContents = reportBlocksContents;
    }

    public ReportBlocks reportBlocksContents(Set<ReportBlocksContent> reportBlocksContents) {
        this.setReportBlocksContents(reportBlocksContents);
        return this;
    }

    public ReportBlocks addReportBlocksContent(ReportBlocksContent reportBlocksContent) {
        this.reportBlocksContents.add(reportBlocksContent);
        reportBlocksContent.setReportBlocks(this);
        return this;
    }

    public ReportBlocks removeReportBlocksContent(ReportBlocksContent reportBlocksContent) {
        this.reportBlocksContents.remove(reportBlocksContent);
        reportBlocksContent.setReportBlocks(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportBlocks)) {
            return false;
        }
        return id != null && id.equals(((ReportBlocks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReportBlocks{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priorityNumber=" + getPriorityNumber() +
            ", isActive='" + getIsActive() + "'" +
            ", config='" + getConfig() + "'" +
            "}";
    }
}
