package org.eun.back.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A MoeContacts.
 */
@Entity
@Table(name = "moe_contacts")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MoeContacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "ministry_name")
    private String ministryName;

    @Column(name = "ministry_english_name")
    private String ministryEnglishName;

    @Column(name = "postal_address")
    private String postalAddress;

    @Column(name = "invoicing_address")
    private String invoicingAddress;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "contact_eun_first_name")
    private String contactEunFirstName;

    @Column(name = "contact_eun_last_name")
    private String contactEunLastName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MoeContacts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public MoeContacts countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public MoeContacts countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getMinistryName() {
        return this.ministryName;
    }

    public MoeContacts ministryName(String ministryName) {
        this.setMinistryName(ministryName);
        return this;
    }

    public void setMinistryName(String ministryName) {
        this.ministryName = ministryName;
    }

    public String getMinistryEnglishName() {
        return this.ministryEnglishName;
    }

    public MoeContacts ministryEnglishName(String ministryEnglishName) {
        this.setMinistryEnglishName(ministryEnglishName);
        return this;
    }

    public void setMinistryEnglishName(String ministryEnglishName) {
        this.ministryEnglishName = ministryEnglishName;
    }

    public String getPostalAddress() {
        return this.postalAddress;
    }

    public MoeContacts postalAddress(String postalAddress) {
        this.setPostalAddress(postalAddress);
        return this;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getInvoicingAddress() {
        return this.invoicingAddress;
    }

    public MoeContacts invoicingAddress(String invoicingAddress) {
        this.setInvoicingAddress(invoicingAddress);
        return this;
    }

    public void setInvoicingAddress(String invoicingAddress) {
        this.invoicingAddress = invoicingAddress;
    }

    public String getShippingAddress() {
        return this.shippingAddress;
    }

    public MoeContacts shippingAddress(String shippingAddress) {
        this.setShippingAddress(shippingAddress);
        return this;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getContactEunFirstName() {
        return this.contactEunFirstName;
    }

    public MoeContacts contactEunFirstName(String contactEunFirstName) {
        this.setContactEunFirstName(contactEunFirstName);
        return this;
    }

    public void setContactEunFirstName(String contactEunFirstName) {
        this.contactEunFirstName = contactEunFirstName;
    }

    public String getContactEunLastName() {
        return this.contactEunLastName;
    }

    public MoeContacts contactEunLastName(String contactEunLastName) {
        this.setContactEunLastName(contactEunLastName);
        return this;
    }

    public void setContactEunLastName(String contactEunLastName) {
        this.contactEunLastName = contactEunLastName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public MoeContacts isActive(Boolean isActive) {
        this.setActive(isActive);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MoeContacts)) {
            return false;
        }
        return id != null && id.equals(((MoeContacts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoeContacts{" +
            "id=" + getId() +
            ", countryCode='" + getCountryCode() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", isActive=" + getActive() +
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
