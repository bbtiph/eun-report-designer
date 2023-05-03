package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "eunTeamMembers", "eventParticipants", "personInOrganizations", "personInProjects", "country" },
        allowSetters = true
    )
    private Person person;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "eventInOrganizations", "organizationInMinistries", "organizationInProjects", "personInOrganizations", "country" },
        allowSetters = true
    )
    private Organization organization;

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

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PersonInOrganization person(Person person) {
        this.setPerson(person);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public PersonInOrganization organization(Organization organization) {
        this.setOrganization(organization);
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
