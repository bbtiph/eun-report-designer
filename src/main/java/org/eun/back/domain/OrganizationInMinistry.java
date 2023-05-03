package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrganizationInMinistry.
 */
@Entity
@Table(name = "organization_in_ministry")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationInMinistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "organizationInMinistry")
    @JsonIgnoreProperties(value = { "countries", "organizationInMinistry" }, allowSetters = true)
    private Set<Ministry> ministries = new HashSet<>();

    @OneToMany(mappedBy = "organizationInMinistry")
    @JsonIgnoreProperties(
        value = { "countries", "eventInOrganization", "organizationInMinistry", "organizationInProject", "personInOrganization" },
        allowSetters = true
    )
    private Set<Organization> organizations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationInMinistry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public OrganizationInMinistry status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Ministry> getMinistries() {
        return this.ministries;
    }

    public void setMinistries(Set<Ministry> ministries) {
        if (this.ministries != null) {
            this.ministries.forEach(i -> i.setOrganizationInMinistry(null));
        }
        if (ministries != null) {
            ministries.forEach(i -> i.setOrganizationInMinistry(this));
        }
        this.ministries = ministries;
    }

    public OrganizationInMinistry ministries(Set<Ministry> ministries) {
        this.setMinistries(ministries);
        return this;
    }

    public OrganizationInMinistry addMinistry(Ministry ministry) {
        this.ministries.add(ministry);
        ministry.setOrganizationInMinistry(this);
        return this;
    }

    public OrganizationInMinistry removeMinistry(Ministry ministry) {
        this.ministries.remove(ministry);
        ministry.setOrganizationInMinistry(null);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        if (this.organizations != null) {
            this.organizations.forEach(i -> i.setOrganizationInMinistry(null));
        }
        if (organizations != null) {
            organizations.forEach(i -> i.setOrganizationInMinistry(this));
        }
        this.organizations = organizations;
    }

    public OrganizationInMinistry organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public OrganizationInMinistry addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.setOrganizationInMinistry(this);
        return this;
    }

    public OrganizationInMinistry removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.setOrganizationInMinistry(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationInMinistry)) {
            return false;
        }
        return id != null && id.equals(((OrganizationInMinistry) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationInMinistry{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
