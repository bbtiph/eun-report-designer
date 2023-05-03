package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.eun.back.domain.enumeration.GdprStatus;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eun_db_id")
    private Long eunDbId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "salutation")
    private Integer salutation;

    @Column(name = "main_contract_email")
    private String mainContractEmail;

    @Column(name = "extra_contract_email")
    private String extraContractEmail;

    @Column(name = "language_mother_tongue")
    private String languageMotherTongue;

    @Column(name = "language_other")
    private String languageOther;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "phone")
    private String phone;

    @Column(name = "social_network_contacts")
    private String socialNetworkContacts;

    @Column(name = "status")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "gdpr_status")
    private GdprStatus gdprStatus;

    @Column(name = "last_login_date")
    private LocalDate lastLoginDate;

    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties(value = { "ministry", "operationalBodyMember", "organization", "person" }, allowSetters = true)
    private Set<Country> countries = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "teams", "people" }, allowSetters = true)
    private EunTeamMember eunTeamMember;

    @ManyToOne
    @JsonIgnoreProperties(value = { "events", "people" }, allowSetters = true)
    private EventParticipant eventParticipant;

    @ManyToOne
    @JsonIgnoreProperties(value = { "people", "organizations" }, allowSetters = true)
    private PersonInOrganization personInOrganization;

    @ManyToOne
    @JsonIgnoreProperties(value = { "people", "projects" }, allowSetters = true)
    private PersonInProject personInProject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEunDbId() {
        return this.eunDbId;
    }

    public Person eunDbId(Long eunDbId) {
        this.setEunDbId(eunDbId);
        return this;
    }

    public void setEunDbId(Long eunDbId) {
        this.eunDbId = eunDbId;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Person firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Person lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getSalutation() {
        return this.salutation;
    }

    public Person salutation(Integer salutation) {
        this.setSalutation(salutation);
        return this;
    }

    public void setSalutation(Integer salutation) {
        this.salutation = salutation;
    }

    public String getMainContractEmail() {
        return this.mainContractEmail;
    }

    public Person mainContractEmail(String mainContractEmail) {
        this.setMainContractEmail(mainContractEmail);
        return this;
    }

    public void setMainContractEmail(String mainContractEmail) {
        this.mainContractEmail = mainContractEmail;
    }

    public String getExtraContractEmail() {
        return this.extraContractEmail;
    }

    public Person extraContractEmail(String extraContractEmail) {
        this.setExtraContractEmail(extraContractEmail);
        return this;
    }

    public void setExtraContractEmail(String extraContractEmail) {
        this.extraContractEmail = extraContractEmail;
    }

    public String getLanguageMotherTongue() {
        return this.languageMotherTongue;
    }

    public Person languageMotherTongue(String languageMotherTongue) {
        this.setLanguageMotherTongue(languageMotherTongue);
        return this;
    }

    public void setLanguageMotherTongue(String languageMotherTongue) {
        this.languageMotherTongue = languageMotherTongue;
    }

    public String getLanguageOther() {
        return this.languageOther;
    }

    public Person languageOther(String languageOther) {
        this.setLanguageOther(languageOther);
        return this;
    }

    public void setLanguageOther(String languageOther) {
        this.languageOther = languageOther;
    }

    public String getDescription() {
        return this.description;
    }

    public Person description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return this.website;
    }

    public Person website(String website) {
        this.setWebsite(website);
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMobile() {
        return this.mobile;
    }

    public Person mobile(String mobile) {
        this.setMobile(mobile);
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return this.phone;
    }

    public Person phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocialNetworkContacts() {
        return this.socialNetworkContacts;
    }

    public Person socialNetworkContacts(String socialNetworkContacts) {
        this.setSocialNetworkContacts(socialNetworkContacts);
        return this;
    }

    public void setSocialNetworkContacts(String socialNetworkContacts) {
        this.socialNetworkContacts = socialNetworkContacts;
    }

    public String getStatus() {
        return this.status;
    }

    public Person status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GdprStatus getGdprStatus() {
        return this.gdprStatus;
    }

    public Person gdprStatus(GdprStatus gdprStatus) {
        this.setGdprStatus(gdprStatus);
        return this;
    }

    public void setGdprStatus(GdprStatus gdprStatus) {
        this.gdprStatus = gdprStatus;
    }

    public LocalDate getLastLoginDate() {
        return this.lastLoginDate;
    }

    public Person lastLoginDate(LocalDate lastLoginDate) {
        this.setLastLoginDate(lastLoginDate);
        return this;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Set<Country> getCountries() {
        return this.countries;
    }

    public void setCountries(Set<Country> countries) {
        if (this.countries != null) {
            this.countries.forEach(i -> i.setPerson(null));
        }
        if (countries != null) {
            countries.forEach(i -> i.setPerson(this));
        }
        this.countries = countries;
    }

    public Person countries(Set<Country> countries) {
        this.setCountries(countries);
        return this;
    }

    public Person addCountry(Country country) {
        this.countries.add(country);
        country.setPerson(this);
        return this;
    }

    public Person removeCountry(Country country) {
        this.countries.remove(country);
        country.setPerson(null);
        return this;
    }

    public EunTeamMember getEunTeamMember() {
        return this.eunTeamMember;
    }

    public void setEunTeamMember(EunTeamMember eunTeamMember) {
        this.eunTeamMember = eunTeamMember;
    }

    public Person eunTeamMember(EunTeamMember eunTeamMember) {
        this.setEunTeamMember(eunTeamMember);
        return this;
    }

    public EventParticipant getEventParticipant() {
        return this.eventParticipant;
    }

    public void setEventParticipant(EventParticipant eventParticipant) {
        this.eventParticipant = eventParticipant;
    }

    public Person eventParticipant(EventParticipant eventParticipant) {
        this.setEventParticipant(eventParticipant);
        return this;
    }

    public PersonInOrganization getPersonInOrganization() {
        return this.personInOrganization;
    }

    public void setPersonInOrganization(PersonInOrganization personInOrganization) {
        this.personInOrganization = personInOrganization;
    }

    public Person personInOrganization(PersonInOrganization personInOrganization) {
        this.setPersonInOrganization(personInOrganization);
        return this;
    }

    public PersonInProject getPersonInProject() {
        return this.personInProject;
    }

    public void setPersonInProject(PersonInProject personInProject) {
        this.personInProject = personInProject;
    }

    public Person personInProject(PersonInProject personInProject) {
        this.setPersonInProject(personInProject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
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
            "}";
    }
}
