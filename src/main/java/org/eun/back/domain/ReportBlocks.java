package org.eun.back.domain;

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

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "priority_number")
    private Long priorityNumber;

    @Column(name = "content")
    private String content;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "type")
    private String type;

    @Column(name = "sql_script")
    private String sqlScript;

    @ManyToMany
    @JoinTable(
        name = "rel_report_blocks_country_id",
        joinColumns = @JoinColumn(name = "report_blocks_id"),
        inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    @JsonIgnoreProperties(value = { "reportBlockIds" }, allowSetters = true)
    private Set<Countries> countryIds = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_report_blocks_report_id",
        joinColumns = @JoinColumn(name = "report_blocks_id"),
        inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    @JsonIgnoreProperties(value = { "reportBlockIds" }, allowSetters = true)
    private Set<Report> reportIds = new HashSet<>();

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

    public String getCountryName() {
        return this.countryName;
    }

    public ReportBlocks countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getContent() {
        return this.content;
    }

    public ReportBlocks content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getType() {
        return this.type;
    }

    public ReportBlocks type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSqlScript() {
        return this.sqlScript;
    }

    public ReportBlocks sqlScript(String sqlScript) {
        this.setSqlScript(sqlScript);
        return this;
    }

    public void setSqlScript(String sqlScript) {
        this.sqlScript = sqlScript;
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

    public Set<Report> getReportIds() {
        return this.reportIds;
    }

    public void setReportIds(Set<Report> reports) {
        this.reportIds = reports;
    }

    public ReportBlocks reportIds(Set<Report> reports) {
        this.setReportIds(reports);
        return this;
    }

    public ReportBlocks addReportId(Report report) {
        this.reportIds.add(report);
        report.getReportBlockIds().add(this);
        return this;
    }

    public ReportBlocks removeReportId(Report report) {
        this.reportIds.remove(report);
        report.getReportBlockIds().remove(this);
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
            ", countryName='" + getCountryName() + "'" +
            ", priorityNumber=" + getPriorityNumber() +
            ", content='" + getContent() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", type='" + getType() + "'" +
            ", sqlScript='" + getSqlScript() + "'" +
            "}";
    }
}
