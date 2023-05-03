package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.OrganizationInMinistry} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationInMinistryDTO implements Serializable {

    private Long id;

    private String status;

    private MinistryDTO ministry;

    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MinistryDTO getMinistry() {
        return ministry;
    }

    public void setMinistry(MinistryDTO ministry) {
        this.ministry = ministry;
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
        if (!(o instanceof OrganizationInMinistryDTO)) {
            return false;
        }

        OrganizationInMinistryDTO organizationInMinistryDTO = (OrganizationInMinistryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationInMinistryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationInMinistryDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", ministry=" + getMinistry() +
            ", organization=" + getOrganization() +
            "}";
    }
}
