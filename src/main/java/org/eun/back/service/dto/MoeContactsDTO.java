package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.MoeContacts} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MoeContactsDTO implements Serializable {

    private Long id;

    private String countryCode;

    private String countryName;

    private Boolean isActive;

    private String ministryName;

    private String ministryEnglishName;

    private String postalAddress;

    private String invoicingAddress;

    private String shippingAddress;

    private String contactEunFirstName;

    private String contactEunLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getMinistryName() {
        return ministryName;
    }

    public void setMinistryName(String ministryName) {
        this.ministryName = ministryName;
    }

    public String getMinistryEnglishName() {
        return ministryEnglishName;
    }

    public void setMinistryEnglishName(String ministryEnglishName) {
        this.ministryEnglishName = ministryEnglishName;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getInvoicingAddress() {
        return invoicingAddress;
    }

    public void setInvoicingAddress(String invoicingAddress) {
        this.invoicingAddress = invoicingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getContactEunFirstName() {
        return contactEunFirstName;
    }

    public void setContactEunFirstName(String contactEunFirstName) {
        this.contactEunFirstName = contactEunFirstName;
    }

    public String getContactEunLastName() {
        return contactEunLastName;
    }

    public void setContactEunLastName(String contactEunLastName) {
        this.contactEunLastName = contactEunLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoeContactsDTO)) {
            return false;
        }

        MoeContactsDTO moeContactsDTO = (MoeContactsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moeContactsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoeContactsDTO{" +
            "id=" + getId() +
            ", countryCode='" + getCountryCode() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", ministryName='" + getMinistryName() + "'" +
            ", ministryEnglishName='" + getMinistryEnglishName() + "'" +
            ", postalAddress='" + getPostalAddress() + "'" +
            ", invoicingAddress='" + getInvoicingAddress() + "'" +
            ", shippingAddress='" + getShippingAddress() + "'" +
            ", contactEunFirstName='" + getContactEunFirstName() + "'" +
            ", contactEunLastName='" + getContactEunLastName() + "'" +
            "}";
    }
}
