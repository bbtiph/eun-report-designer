package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.Country} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountryDTO implements Serializable {

    private Long id;

    private String countryName;

    private MinistryDTO ministry;

    private OperationalBodyMemberDTO operationalBodyMember;

    private OrganizationDTO organization;

    private PersonDTO person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public MinistryDTO getMinistry() {
        return ministry;
    }

    public void setMinistry(MinistryDTO ministry) {
        this.ministry = ministry;
    }

    public OperationalBodyMemberDTO getOperationalBodyMember() {
        return operationalBodyMember;
    }

    public void setOperationalBodyMember(OperationalBodyMemberDTO operationalBodyMember) {
        this.operationalBodyMember = operationalBodyMember;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountryDTO)) {
            return false;
        }

        CountryDTO countryDTO = (CountryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountryDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", ministry=" + getMinistry() +
            ", operationalBodyMember=" + getOperationalBodyMember() +
            ", organization=" + getOrganization() +
            ", person=" + getPerson() +
            "}";
    }
}
