package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.eun.back.domain.enumeration.GdprStatus;

/**
 * A DTO for the {@link org.eun.back.domain.Person} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonDTO implements Serializable {

    private Long id;

    private Long eunDbId;

    private String firstname;

    private String lastname;

    private Integer salutation;

    private String mainContractEmail;

    private String extraContractEmail;

    private String languageMotherTongue;

    private String languageOther;

    private String description;

    private String website;

    private String mobile;

    private String phone;

    private String socialNetworkContacts;

    private String status;

    private GdprStatus gdprStatus;

    private LocalDate lastLoginDate;

    private CountriesDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEunDbId() {
        return eunDbId;
    }

    public void setEunDbId(Long eunDbId) {
        this.eunDbId = eunDbId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getSalutation() {
        return salutation;
    }

    public void setSalutation(Integer salutation) {
        this.salutation = salutation;
    }

    public String getMainContractEmail() {
        return mainContractEmail;
    }

    public void setMainContractEmail(String mainContractEmail) {
        this.mainContractEmail = mainContractEmail;
    }

    public String getExtraContractEmail() {
        return extraContractEmail;
    }

    public void setExtraContractEmail(String extraContractEmail) {
        this.extraContractEmail = extraContractEmail;
    }

    public String getLanguageMotherTongue() {
        return languageMotherTongue;
    }

    public void setLanguageMotherTongue(String languageMotherTongue) {
        this.languageMotherTongue = languageMotherTongue;
    }

    public String getLanguageOther() {
        return languageOther;
    }

    public void setLanguageOther(String languageOther) {
        this.languageOther = languageOther;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocialNetworkContacts() {
        return socialNetworkContacts;
    }

    public void setSocialNetworkContacts(String socialNetworkContacts) {
        this.socialNetworkContacts = socialNetworkContacts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GdprStatus getGdprStatus() {
        return gdprStatus;
    }

    public void setGdprStatus(GdprStatus gdprStatus) {
        this.gdprStatus = gdprStatus;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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
        if (!(o instanceof PersonDTO)) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + getId() +
            ", eunDbId=" + getEunDbId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", salutation=" + getSalutation() +
            ", mainContractEmail='" + getMainContractEmail() + "'" +
            ", extraContractEmail='" + getExtraContractEmail() + "'" +
            ", languageMotherTongue='" + getLanguageMotherTongue() + "'" +
            ", languageOther='" + getLanguageOther() + "'" +
            ", description='" + getDescription() + "'" +
            ", website='" + getWebsite() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", phone='" + getPhone() + "'" +
            ", socialNetworkContacts='" + getSocialNetworkContacts() + "'" +
            ", status='" + getStatus() + "'" +
            ", gdprStatus='" + getGdprStatus() + "'" +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}
