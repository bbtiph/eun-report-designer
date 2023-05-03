package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.Ministry} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MinistryDTO implements Serializable {

    private Long id;

    private String languages;

    private String formalName;

    private String englishName;

    private String acronym;

    private String description;

    private String website;

    private String steercomMemberName;

    private String steercomMemberEmail;

    private String postalAddressRegion;

    private String postalAddressPostalCode;

    private String postalAddressCity;

    private String postalAddressStreet;

    private String status;

    private String eunContactFirstname;

    private String eunContactLastname;

    private String eunContactEmail;

    private String invoicingAddress;

    private String financialCorrespondingEmail;

    private CountriesDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSteercomMemberName() {
        return steercomMemberName;
    }

    public void setSteercomMemberName(String steercomMemberName) {
        this.steercomMemberName = steercomMemberName;
    }

    public String getSteercomMemberEmail() {
        return steercomMemberEmail;
    }

    public void setSteercomMemberEmail(String steercomMemberEmail) {
        this.steercomMemberEmail = steercomMemberEmail;
    }

    public String getPostalAddressRegion() {
        return postalAddressRegion;
    }

    public void setPostalAddressRegion(String postalAddressRegion) {
        this.postalAddressRegion = postalAddressRegion;
    }

    public String getPostalAddressPostalCode() {
        return postalAddressPostalCode;
    }

    public void setPostalAddressPostalCode(String postalAddressPostalCode) {
        this.postalAddressPostalCode = postalAddressPostalCode;
    }

    public String getPostalAddressCity() {
        return postalAddressCity;
    }

    public void setPostalAddressCity(String postalAddressCity) {
        this.postalAddressCity = postalAddressCity;
    }

    public String getPostalAddressStreet() {
        return postalAddressStreet;
    }

    public void setPostalAddressStreet(String postalAddressStreet) {
        this.postalAddressStreet = postalAddressStreet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEunContactFirstname() {
        return eunContactFirstname;
    }

    public void setEunContactFirstname(String eunContactFirstname) {
        this.eunContactFirstname = eunContactFirstname;
    }

    public String getEunContactLastname() {
        return eunContactLastname;
    }

    public void setEunContactLastname(String eunContactLastname) {
        this.eunContactLastname = eunContactLastname;
    }

    public String getEunContactEmail() {
        return eunContactEmail;
    }

    public void setEunContactEmail(String eunContactEmail) {
        this.eunContactEmail = eunContactEmail;
    }

    public String getInvoicingAddress() {
        return invoicingAddress;
    }

    public void setInvoicingAddress(String invoicingAddress) {
        this.invoicingAddress = invoicingAddress;
    }

    public String getFinancialCorrespondingEmail() {
        return financialCorrespondingEmail;
    }

    public void setFinancialCorrespondingEmail(String financialCorrespondingEmail) {
        this.financialCorrespondingEmail = financialCorrespondingEmail;
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
        if (!(o instanceof MinistryDTO)) {
            return false;
        }

        MinistryDTO ministryDTO = (MinistryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ministryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MinistryDTO{" +
            "id=" + getId() +
            ", languages='" + getLanguages() + "'" +
            ", formalName='" + getFormalName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", description='" + getDescription() + "'" +
            ", website='" + getWebsite() + "'" +
            ", steercomMemberName='" + getSteercomMemberName() + "'" +
            ", steercomMemberEmail='" + getSteercomMemberEmail() + "'" +
            ", postalAddressRegion='" + getPostalAddressRegion() + "'" +
            ", postalAddressPostalCode='" + getPostalAddressPostalCode() + "'" +
            ", postalAddressCity='" + getPostalAddressCity() + "'" +
            ", postalAddressStreet='" + getPostalAddressStreet() + "'" +
            ", status='" + getStatus() + "'" +
            ", eunContactFirstname='" + getEunContactFirstname() + "'" +
            ", eunContactLastname='" + getEunContactLastname() + "'" +
            ", eunContactEmail='" + getEunContactEmail() + "'" +
            ", invoicingAddress='" + getInvoicingAddress() + "'" +
            ", financialCorrespondingEmail='" + getFinancialCorrespondingEmail() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}
