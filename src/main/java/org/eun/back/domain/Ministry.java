package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Ministry.
 */
@Entity
@Table(name = "ministry")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ministry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "languages")
    private String languages;

    @Column(name = "formal_name")
    private String formalName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "acronym")
    private String acronym;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @Column(name = "steercom_member_name")
    private String steercomMemberName;

    @Column(name = "steercom_member_email")
    private String steercomMemberEmail;

    @Column(name = "postal_address_region")
    private String postalAddressRegion;

    @Column(name = "postal_address_postal_code")
    private String postalAddressPostalCode;

    @Column(name = "postal_address_city")
    private String postalAddressCity;

    @Column(name = "postal_address_street")
    private String postalAddressStreet;

    @Column(name = "status")
    private String status;

    @Column(name = "eun_contact_firstname")
    private String eunContactFirstname;

    @Column(name = "eun_contact_lastname")
    private String eunContactLastname;

    @Column(name = "eun_contact_email")
    private String eunContactEmail;

    @Column(name = "invoicing_address")
    private String invoicingAddress;

    @Column(name = "financial_corresponding_email")
    private String financialCorrespondingEmail;

    @OneToMany(mappedBy = "ministry")
    @JsonIgnoreProperties(value = { "ministry", "operationalBodyMember", "organization", "person" }, allowSetters = true)
    private Set<Country> countries = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ministries", "organizations" }, allowSetters = true)
    private OrganizationInMinistry organizationInMinistry;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ministry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguages() {
        return this.languages;
    }

    public Ministry languages(String languages) {
        this.setLanguages(languages);
        return this;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getFormalName() {
        return this.formalName;
    }

    public Ministry formalName(String formalName) {
        this.setFormalName(formalName);
        return this;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public Ministry englishName(String englishName) {
        this.setEnglishName(englishName);
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Ministry acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return this.description;
    }

    public Ministry description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return this.website;
    }

    public Ministry website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSteercomMemberName() {
        return this.steercomMemberName;
    }

    public Ministry steercomMemberName(String steercomMemberName) {
        this.setSteercomMemberName(steercomMemberName);
        return this;
    }

    public void setSteercomMemberName(String steercomMemberName) {
        this.steercomMemberName = steercomMemberName;
    }

    public String getSteercomMemberEmail() {
        return this.steercomMemberEmail;
    }

    public Ministry steercomMemberEmail(String steercomMemberEmail) {
        this.setSteercomMemberEmail(steercomMemberEmail);
        return this;
    }

    public void setSteercomMemberEmail(String steercomMemberEmail) {
        this.steercomMemberEmail = steercomMemberEmail;
    }

    public String getPostalAddressRegion() {
        return this.postalAddressRegion;
    }

    public Ministry postalAddressRegion(String postalAddressRegion) {
        this.setPostalAddressRegion(postalAddressRegion);
        return this;
    }

    public void setPostalAddressRegion(String postalAddressRegion) {
        this.postalAddressRegion = postalAddressRegion;
    }

    public String getPostalAddressPostalCode() {
        return this.postalAddressPostalCode;
    }

    public Ministry postalAddressPostalCode(String postalAddressPostalCode) {
        this.setPostalAddressPostalCode(postalAddressPostalCode);
        return this;
    }

    public void setPostalAddressPostalCode(String postalAddressPostalCode) {
        this.postalAddressPostalCode = postalAddressPostalCode;
    }

    public String getPostalAddressCity() {
        return this.postalAddressCity;
    }

    public Ministry postalAddressCity(String postalAddressCity) {
        this.setPostalAddressCity(postalAddressCity);
        return this;
    }

    public void setPostalAddressCity(String postalAddressCity) {
        this.postalAddressCity = postalAddressCity;
    }

    public String getPostalAddressStreet() {
        return this.postalAddressStreet;
    }

    public Ministry postalAddressStreet(String postalAddressStreet) {
        this.setPostalAddressStreet(postalAddressStreet);
        return this;
    }

    public void setPostalAddressStreet(String postalAddressStreet) {
        this.postalAddressStreet = postalAddressStreet;
    }

    public String getStatus() {
        return this.status;
    }

    public Ministry status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEunContactFirstname() {
        return this.eunContactFirstname;
    }

    public Ministry eunContactFirstname(String eunContactFirstname) {
        this.setEunContactFirstname(eunContactFirstname);
        return this;
    }

    public void setEunContactFirstname(String eunContactFirstname) {
        this.eunContactFirstname = eunContactFirstname;
    }

    public String getEunContactLastname() {
        return this.eunContactLastname;
    }

    public Ministry eunContactLastname(String eunContactLastname) {
        this.setEunContactLastname(eunContactLastname);
        return this;
    }

    public void setEunContactLastname(String eunContactLastname) {
        this.eunContactLastname = eunContactLastname;
    }

    public String getEunContactEmail() {
        return this.eunContactEmail;
    }

    public Ministry eunContactEmail(String eunContactEmail) {
        this.setEunContactEmail(eunContactEmail);
        return this;
    }

    public void setEunContactEmail(String eunContactEmail) {
        this.eunContactEmail = eunContactEmail;
    }

    public String getInvoicingAddress() {
        return this.invoicingAddress;
    }

    public Ministry invoicingAddress(String invoicingAddress) {
        this.setInvoicingAddress(invoicingAddress);
        return this;
    }

    public void setInvoicingAddress(String invoicingAddress) {
        this.invoicingAddress = invoicingAddress;
    }

    public String getFinancialCorrespondingEmail() {
        return this.financialCorrespondingEmail;
    }

    public Ministry financialCorrespondingEmail(String financialCorrespondingEmail) {
        this.setFinancialCorrespondingEmail(financialCorrespondingEmail);
        return this;
    }

    public void setFinancialCorrespondingEmail(String financialCorrespondingEmail) {
        this.financialCorrespondingEmail = financialCorrespondingEmail;
    }

    public Set<Country> getCountries() {
        return this.countries;
    }

    public void setCountries(Set<Country> countries) {
        if (this.countries != null) {
            this.countries.forEach(i -> i.setMinistry(null));
        }
        if (countries != null) {
            countries.forEach(i -> i.setMinistry(this));
        }
        this.countries = countries;
    }

    public Ministry countries(Set<Country> countries) {
        this.setCountries(countries);
        return this;
    }

    public Ministry addCountry(Country country) {
        this.countries.add(country);
        country.setMinistry(this);
        return this;
    }

    public Ministry removeCountry(Country country) {
        this.countries.remove(country);
        country.setMinistry(null);
        return this;
    }

    public OrganizationInMinistry getOrganizationInMinistry() {
        return this.organizationInMinistry;
    }

    public void setOrganizationInMinistry(OrganizationInMinistry organizationInMinistry) {
        this.organizationInMinistry = organizationInMinistry;
    }

    public Ministry organizationInMinistry(OrganizationInMinistry organizationInMinistry) {
        this.setOrganizationInMinistry(organizationInMinistry);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ministry)) {
            return false;
        }
        return id != null && id.equals(((Ministry) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ministry{" +
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
            "}";
    }
}
