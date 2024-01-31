package org.eun.back.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProjectPartner.
 */
@Entity
@Table(name = "project_partner")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "odata_etag")
    private String odataEtag;

    @Column(name = "no")
    private Long no;

    @Column(name = "job_no")
    private String jobNo;

    @Column(name = "vendor_code")
    private String vendorCode;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "partner_amount")
    private Double partnerAmount;

    @Column(name = "is_active")
    private Boolean isActive;

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

    public ProjectPartner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOdataEtag() {
        return this.odataEtag;
    }

    public ProjectPartner odataEtag(String odataEtag) {
        this.setOdataEtag(odataEtag);
        return this;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public Long getNo() {
        return this.no;
    }

    public ProjectPartner no(Long no) {
        this.setNo(no);
        return this;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getJobNo() {
        return this.jobNo;
    }

    public ProjectPartner jobNo(String jobNo) {
        this.setJobNo(jobNo);
        return this;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getVendorCode() {
        return this.vendorCode;
    }

    public ProjectPartner vendorCode(String vendorCode) {
        this.setVendorCode(vendorCode);
        return this;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public ProjectPartner vendorName(String vendorName) {
        this.setVendorName(vendorName);
        return this;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public ProjectPartner countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public ProjectPartner countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getPartnerAmount() {
        return this.partnerAmount;
    }

    public ProjectPartner partnerAmount(Double partnerAmount) {
        this.setPartnerAmount(partnerAmount);
        return this;
    }

    public void setPartnerAmount(Double partnerAmount) {
        this.partnerAmount = partnerAmount;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ProjectPartner isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ProjectPartner createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProjectPartner lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public ProjectPartner createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public ProjectPartner lastModifiedDate(LocalDate lastModifiedDate) {
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
        if (!(o instanceof ProjectPartner)) {
            return false;
        }
        return id != null && id.equals(((ProjectPartner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectPartner{" +
            "id=" + getId() +
            ", odataEtag='" + getOdataEtag() + "'" +
            ", no=" + getNo() +
            ", jobNo='" + getJobNo() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorName='" + getVendorName() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", partnerAmount=" + getPartnerAmount() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
