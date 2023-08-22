package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import org.eun.back.domain.enumeration.OrgStatus;

/**
 * A DTO for the {@link org.eun.back.domain.Organization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationIndicatorDTO implements Serializable {

    private Long id;

    @NotNull
    private String officialName;

    private String description;

    private String type;

    private String address;

    private String website;

    private String organisationNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationIndicatorDTO)) {
            return false;
        }

        OrganizationIndicatorDTO organizationDTO = (OrganizationIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "OrganizationIndicatorDTO{" +
            "id=" +
            id +
            ", officialName='" +
            officialName +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", address='" +
            address +
            '\'' +
            ", website='" +
            website +
            '\'' +
            ", organisationNumber='" +
            organisationNumber +
            '\'' +
            '}'
        );
    }
}
