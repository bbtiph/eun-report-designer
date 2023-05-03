package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @ManyToOne
    @JsonIgnoreProperties(value = { "organizationInMinistries", "country" }, allowSetters = true)
    private Ministry ministry;

    @ManyToOne
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private OperationalBodyMember operationalBodyMember;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "eventInOrganizations", "organizationInMinistries", "organizationInProjects", "personInOrganizations", "country" },
        allowSetters = true
    )
    private Organization organization;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "eunTeamMembers", "eventParticipants", "personInOrganizations", "personInProjects", "country" },
        allowSetters = true
    )
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Country id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public Country countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Ministry getMinistry() {
        return this.ministry;
    }

    public void setMinistry(Ministry ministry) {
        this.ministry = ministry;
    }

    public Country ministry(Ministry ministry) {
        this.setMinistry(ministry);
        return this;
    }

    public OperationalBodyMember getOperationalBodyMember() {
        return this.operationalBodyMember;
    }

    public void setOperationalBodyMember(OperationalBodyMember operationalBodyMember) {
        this.operationalBodyMember = operationalBodyMember;
    }

    public Country operationalBodyMember(OperationalBodyMember operationalBodyMember) {
        this.setOperationalBodyMember(operationalBodyMember);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Country organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Country person(Person person) {
        this.setPerson(person);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}
