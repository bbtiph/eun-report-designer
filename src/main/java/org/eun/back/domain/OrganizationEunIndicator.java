package org.eun.back.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A OrganizationEunIndicator.
 */
@Entity
@Table(name = "organization_eun_indicator")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationEunIndicator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "period")
    private Long period;

    @Column(name = "n_count")
    private Long nCount;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_url")
    private String projectUrl;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "reports_project_id")
    private Long reportsProjectId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationEunIndicator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPeriod() {
        return this.period;
    }

    public OrganizationEunIndicator period(Long period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Long getnCount() {
        return this.nCount;
    }

    public OrganizationEunIndicator nCount(Long nCount) {
        this.setnCount(nCount);
        return this;
    }

    public void setnCount(Long nCount) {
        this.nCount = nCount;
    }

    public Long getCountryId() {
        return this.countryId;
    }

    public OrganizationEunIndicator countryId(Long countryId) {
        this.setCountryId(countryId);
        return this;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public OrganizationEunIndicator projectId(Long projectId) {
        this.setProjectId(projectId);
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectUrl() {
        return this.projectUrl;
    }

    public OrganizationEunIndicator projectUrl(String projectUrl) {
        this.setProjectUrl(projectUrl);
        return this;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public OrganizationEunIndicator countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public OrganizationEunIndicator projectName(String projectName) {
        this.setProjectName(projectName);
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getReportsProjectId() {
        return this.reportsProjectId;
    }

    public OrganizationEunIndicator reportsProjectId(Long reportsProjectId) {
        this.setReportsProjectId(reportsProjectId);
        return this;
    }

    public void setReportsProjectId(Long reportsProjectId) {
        this.reportsProjectId = reportsProjectId;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OrganizationEunIndicator createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public OrganizationEunIndicator lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public OrganizationEunIndicator createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public OrganizationEunIndicator lastModifiedDate(LocalDate lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationEunIndicator)) {
            return false;
        }
        return id != null && id.equals(((OrganizationEunIndicator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationEunIndicator{" +
            "id=" + getId() +
            ", period=" + getPeriod() +
            ", nCount=" + getnCount() +
            ", countryId=" + getCountryId() +
            ", projectId=" + getProjectId() +
            ", projectUrl='" + getProjectUrl() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", reportsProjectId=" + getReportsProjectId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
