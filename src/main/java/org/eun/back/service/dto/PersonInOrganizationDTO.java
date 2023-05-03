package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.PersonInOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonInOrganizationDTO implements Serializable {

    private Long id;

    private String roleInOrganization;

    private PersonDTO person;

    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleInOrganization() {
        return roleInOrganization;
    }

    public void setRoleInOrganization(String roleInOrganization) {
        this.roleInOrganization = roleInOrganization;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonInOrganizationDTO)) {
            return false;
        }

        PersonInOrganizationDTO personInOrganizationDTO = (PersonInOrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personInOrganizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonInOrganizationDTO{" +
            "id=" + getId() +
            ", roleInOrganization='" + getRoleInOrganization() + "'" +
            ", person=" + getPerson() +
            ", organization=" + getOrganization() +
            "}";
    }
}
