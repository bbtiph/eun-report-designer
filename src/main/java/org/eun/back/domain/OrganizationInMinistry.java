package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "organizationInMinistries", "country" }, allowSetters = true)
    private Ministry ministry;

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

    public Ministry getMinistry() {
        return this.ministry;
    }

    public void setMinistry(Ministry ministry) {
        this.ministry = ministry;
    }

    public OrganizationInMinistry ministry(Ministry ministry) {
        this.setMinistry(ministry);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public OrganizationInMinistry organization(Organization organization) {
        this.setOrganization(organization);
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
