package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Countries.
 */
@Entity
@Table(name = "countries")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(value = { "organizationInMinistries", "country" }, allowSetters = true)
    private Set<Ministry> ministries = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private Set<OperationalBodyMember> operationalBodyMembers = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(
        value = { "eventInOrganizations", "organizationInMinistries", "organizationInProjects", "personInOrganizations", "country" },
        allowSetters = true
    )
    private Set<Organization> organizations = new HashSet<>();

    @OneToMany(mappedBy = "country")
    @JsonIgnoreProperties(
        value = { "eunTeamMembers", "eventParticipants", "personInOrganizations", "personInProjects", "country" },
        allowSetters = true
    )
    private Set<Person> people = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Countries id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public Countries countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Set<Ministry> getMinistries() {
        return this.ministries;
    }

    public void setMinistries(Set<Ministry> ministries) {
        if (this.ministries != null) {
            this.ministries.forEach(i -> i.setCountry(null));
        }
        if (ministries != null) {
            ministries.forEach(i -> i.setCountry(this));
        }
        this.ministries = ministries;
    }

    public Countries ministries(Set<Ministry> ministries) {
        this.setMinistries(ministries);
        return this;
    }

    public Countries addMinistry(Ministry ministry) {
        this.ministries.add(ministry);
        ministry.setCountry(this);
        return this;
    }

    public Countries removeMinistry(Ministry ministry) {
        this.ministries.remove(ministry);
        ministry.setCountry(null);
        return this;
    }

    public Set<OperationalBodyMember> getOperationalBodyMembers() {
        return this.operationalBodyMembers;
    }

    public void setOperationalBodyMembers(Set<OperationalBodyMember> operationalBodyMembers) {
        if (this.operationalBodyMembers != null) {
            this.operationalBodyMembers.forEach(i -> i.setCountry(null));
        }
        if (operationalBodyMembers != null) {
            operationalBodyMembers.forEach(i -> i.setCountry(this));
        }
        this.operationalBodyMembers = operationalBodyMembers;
    }

    public Countries operationalBodyMembers(Set<OperationalBodyMember> operationalBodyMembers) {
        this.setOperationalBodyMembers(operationalBodyMembers);
        return this;
    }

    public Countries addOperationalBodyMember(OperationalBodyMember operationalBodyMember) {
        this.operationalBodyMembers.add(operationalBodyMember);
        operationalBodyMember.setCountry(this);
        return this;
    }

    public Countries removeOperationalBodyMember(OperationalBodyMember operationalBodyMember) {
        this.operationalBodyMembers.remove(operationalBodyMember);
        operationalBodyMember.setCountry(null);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        if (this.organizations != null) {
            this.organizations.forEach(i -> i.setCountry(null));
        }
        if (organizations != null) {
            organizations.forEach(i -> i.setCountry(this));
        }
        this.organizations = organizations;
    }

    public Countries organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public Countries addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.setCountry(this);
        return this;
    }

    public Countries removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.setCountry(null);
        return this;
    }

    public Set<Person> getPeople() {
        return this.people;
    }

    public void setPeople(Set<Person> people) {
        if (this.people != null) {
            this.people.forEach(i -> i.setCountry(null));
        }
        if (people != null) {
            people.forEach(i -> i.setCountry(this));
        }
        this.people = people;
    }

    public Countries people(Set<Person> people) {
        this.setPeople(people);
        return this;
    }

    public Countries addPerson(Person person) {
        this.people.add(person);
        person.setCountry(this);
        return this;
    }

    public Countries removePerson(Person person) {
        this.people.remove(person);
        person.setCountry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Countries)) {
            return false;
        }
        return id != null && id.equals(((Countries) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Countries{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}
