package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PersonInOrganization.
 */
@Entity
@Table(name = "person_in_organization")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonInOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "role_in_organization")
    private String roleInOrganization;

    @OneToMany(mappedBy = "personInOrganization")
    @JsonIgnoreProperties(
        value = { "countries", "eunTeamMember", "eventParticipant", "personInOrganization", "personInProject" },
        allowSetters = true
    )
    private Set<Person> people = new HashSet<>();

    @OneToMany(mappedBy = "personInOrganization")
    @JsonIgnoreProperties(
        value = { "countries", "eventInOrganization", "organizationInMinistry", "organizationInProject", "personInOrganization" },
        allowSetters = true
    )
    private Set<Organization> organizations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonInOrganization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleInOrganization() {
        return this.roleInOrganization;
    }

    public PersonInOrganization roleInOrganization(String roleInOrganization) {
        this.setRoleInOrganization(roleInOrganization);
        return this;
    }

    public void setRoleInOrganization(String roleInOrganization) {
        this.roleInOrganization = roleInOrganization;
    }

    public Set<Person> getPeople() {
        return this.people;
    }

    public void setPeople(Set<Person> people) {
        if (this.people != null) {
            this.people.forEach(i -> i.setPersonInOrganization(null));
        }
        if (people != null) {
            people.forEach(i -> i.setPersonInOrganization(this));
        }
        this.people = people;
    }

    public PersonInOrganization people(Set<Person> people) {
        this.setPeople(people);
        return this;
    }

    public PersonInOrganization addPerson(Person person) {
        this.people.add(person);
        person.setPersonInOrganization(this);
        return this;
    }

    public PersonInOrganization removePerson(Person person) {
        this.people.remove(person);
        person.setPersonInOrganization(null);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        if (this.organizations != null) {
            this.organizations.forEach(i -> i.setPersonInOrganization(null));
        }
        if (organizations != null) {
            organizations.forEach(i -> i.setPersonInOrganization(this));
        }
        this.organizations = organizations;
    }

    public PersonInOrganization organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public PersonInOrganization addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.setPersonInOrganization(this);
        return this;
    }

    public PersonInOrganization removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.setPersonInOrganization(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonInOrganization)) {
            return false;
        }
        return id != null && id.equals(((PersonInOrganization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonInOrganization{" +
            "id=" + getId() +
            ", roleInOrganization='" + getRoleInOrganization() + "'" +
            "}";
    }
}
