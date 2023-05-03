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
    @JsonIgnoreProperties(value = { "team", "person" }, allowSetters = true)
    private Set<EunTeamMember> eunTeamMembers = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties(value = { "event", "person" }, allowSetters = true)
    private Set<EventParticipant> eventParticipants = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties(value = { "person", "organization" }, allowSetters = true)
    private Set<PersonInOrganization> personInOrganizations = new HashSet<>();

    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties(value = { "person", "project" }, allowSetters = true)
    private Set<PersonInProject> personInProjects = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ministries", "operationalBodyMembers", "organizations", "people" }, allowSetters = true)
    private Countries country;

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

    public Set<EunTeamMember> getEunTeamMembers() {
        return this.eunTeamMembers;
    }

    public void setEunTeamMembers(Set<EunTeamMember> eunTeamMembers) {
        if (this.eunTeamMembers != null) {
            this.eunTeamMembers.forEach(i -> i.setPerson(null));
        }
        if (eunTeamMembers != null) {
            eunTeamMembers.forEach(i -> i.setPerson(this));
        }
        this.eunTeamMembers = eunTeamMembers;
    }

    public Person eunTeamMembers(Set<EunTeamMember> eunTeamMembers) {
        this.setEunTeamMembers(eunTeamMembers);
        return this;
    }

    public Person addEunTeamMember(EunTeamMember eunTeamMember) {
        this.eunTeamMembers.add(eunTeamMember);
        eunTeamMember.setPerson(this);
        return this;
    }

    public Person removeEunTeamMember(EunTeamMember eunTeamMember) {
        this.eunTeamMembers.remove(eunTeamMember);
        eunTeamMember.setPerson(null);
        return this;
    }

    public Set<EventParticipant> getEventParticipants() {
        return this.eventParticipants;
    }

    public void setEventParticipants(Set<EventParticipant> eventParticipants) {
        if (this.eventParticipants != null) {
            this.eventParticipants.forEach(i -> i.setPerson(null));
        }
        if (eventParticipants != null) {
            eventParticipants.forEach(i -> i.setPerson(this));
        }
        this.eventParticipants = eventParticipants;
    }

    public Person eventParticipants(Set<EventParticipant> eventParticipants) {
        this.setEventParticipants(eventParticipants);
        return this;
    }

    public Person addEventParticipant(EventParticipant eventParticipant) {
        this.eventParticipants.add(eventParticipant);
        eventParticipant.setPerson(this);
        return this;
    }

    public Person removeEventParticipant(EventParticipant eventParticipant) {
        this.eventParticipants.remove(eventParticipant);
        eventParticipant.setPerson(null);
        return this;
    }

    public Set<PersonInOrganization> getPersonInOrganizations() {
        return this.personInOrganizations;
    }

    public void setPersonInOrganizations(Set<PersonInOrganization> personInOrganizations) {
        if (this.personInOrganizations != null) {
            this.personInOrganizations.forEach(i -> i.setPerson(null));
        }
        if (personInOrganizations != null) {
            personInOrganizations.forEach(i -> i.setPerson(this));
        }
        this.personInOrganizations = personInOrganizations;
    }

    public Person personInOrganizations(Set<PersonInOrganization> personInOrganizations) {
        this.setPersonInOrganizations(personInOrganizations);
        return this;
    }

    public Person addPersonInOrganization(PersonInOrganization personInOrganization) {
        this.personInOrganizations.add(personInOrganization);
        personInOrganization.setPerson(this);
        return this;
    }

    public Person removePersonInOrganization(PersonInOrganization personInOrganization) {
        this.personInOrganizations.remove(personInOrganization);
        personInOrganization.setPerson(null);
        return this;
    }

    public Set<PersonInProject> getPersonInProjects() {
        return this.personInProjects;
    }

    public void setPersonInProjects(Set<PersonInProject> personInProjects) {
        if (this.personInProjects != null) {
            this.personInProjects.forEach(i -> i.setPerson(null));
        }
        if (personInProjects != null) {
            personInProjects.forEach(i -> i.setPerson(this));
        }
        this.personInProjects = personInProjects;
    }

    public Person personInProjects(Set<PersonInProject> personInProjects) {
        this.setPersonInProjects(personInProjects);
        return this;
    }

    public Person addPersonInProject(PersonInProject personInProject) {
        this.personInProjects.add(personInProject);
        personInProject.setPerson(this);
        return this;
    }

    public Person removePersonInProject(PersonInProject personInProject) {
        this.personInProjects.remove(personInProject);
        personInProject.setPerson(null);
        return this;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public Person country(Countries countries) {
        this.setCountry(countries);
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
