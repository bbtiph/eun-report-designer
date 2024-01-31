package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.ProjectPartner} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectPartnerDTO implements Serializable {

    private Long id;

    private String odataEtag;

    private Long no;

    private String jobNo;

    private String vendorCode;

    private String vendorName;

    private String countryCode;

    private String countryName;

    private Double partnerAmount;

    private Boolean isActive;

    private String createdBy;

    private String lastModifiedBy;

    private LocalDate createdDate;

    private LocalDate lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOdataEtag() {
        return odataEtag;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getPartnerAmount() {
        return partnerAmount;
    }

    public void setPartnerAmount(Double partnerAmount) {
        this.partnerAmount = partnerAmount;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectPartnerDTO)) {
            return false;
        }

        ProjectPartnerDTO projectPartnerDTO = (ProjectPartnerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectPartnerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectPartnerDTO{" +
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
