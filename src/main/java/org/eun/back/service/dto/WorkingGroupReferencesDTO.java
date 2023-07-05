package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.WorkingGroupReferences} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingGroupReferencesDTO implements Serializable {

    private Long id;

    private String countryCode;

    private String countryName;

    private String countryRepresentativeFirstName;

    private String countryRepresentativeLastName;

    private String countryRepresentativeMail;

    private String countryRepresentativePosition;

    private LocalDate countryRepresentativeStartDate;

    private LocalDate countryRepresentativeEndDate;

    private String countryRepresentativeMinistry;

    private String countryRepresentativeDepartment;

    private String contactEunFirstName;

    private String contactEunLastName;

    private String type;

    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryRepresentativeFirstName() {
        return countryRepresentativeFirstName;
    }

    public void setCountryRepresentativeFirstName(String countryRepresentativeFirstName) {
        this.countryRepresentativeFirstName = countryRepresentativeFirstName;
    }

    public String getCountryRepresentativeLastName() {
        return countryRepresentativeLastName;
    }

    public void setCountryRepresentativeLastName(String countryRepresentativeLastName) {
        this.countryRepresentativeLastName = countryRepresentativeLastName;
    }

    public String getCountryRepresentativeMail() {
        return countryRepresentativeMail;
    }

    public void setCountryRepresentativeMail(String countryRepresentativeMail) {
        this.countryRepresentativeMail = countryRepresentativeMail;
    }

    public String getCountryRepresentativePosition() {
        return countryRepresentativePosition;
    }

    public void setCountryRepresentativePosition(String countryRepresentativePosition) {
        this.countryRepresentativePosition = countryRepresentativePosition;
    }

    public LocalDate getCountryRepresentativeStartDate() {
        return countryRepresentativeStartDate;
    }

    public void setCountryRepresentativeStartDate(LocalDate countryRepresentativeStartDate) {
        this.countryRepresentativeStartDate = countryRepresentativeStartDate;
    }

    public LocalDate getCountryRepresentativeEndDate() {
        return countryRepresentativeEndDate;
    }

    public void setCountryRepresentativeEndDate(LocalDate countryRepresentativeEndDate) {
        this.countryRepresentativeEndDate = countryRepresentativeEndDate;
    }

    public String getCountryRepresentativeMinistry() {
        return countryRepresentativeMinistry;
    }

    public void setCountryRepresentativeMinistry(String countryRepresentativeMinistry) {
        this.countryRepresentativeMinistry = countryRepresentativeMinistry;
    }

    public String getCountryRepresentativeDepartment() {
        return countryRepresentativeDepartment;
    }

    public void setCountryRepresentativeDepartment(String countryRepresentativeDepartment) {
        this.countryRepresentativeDepartment = countryRepresentativeDepartment;
    }

    public String getContactEunFirstName() {
        return contactEunFirstName;
    }

    public void setContactEunFirstName(String contactEunFirstName) {
        this.contactEunFirstName = contactEunFirstName;
    }

    public String getContactEunLastName() {
        return contactEunLastName;
    }

    public void setContactEunLastName(String contactEunLastName) {
        this.contactEunLastName = contactEunLastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingGroupReferencesDTO)) {
            return false;
        }

        WorkingGroupReferencesDTO workingGroupReferencesDTO = (WorkingGroupReferencesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workingGroupReferencesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingGroupReferencesDTO{" +
            "id=" + getId() +
            ", countryCode='" + getCountryCode() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", countryRepresentativeFirstName='" + getCountryRepresentativeFirstName() + "'" +
            ", countryRepresentativeLastName='" + getCountryRepresentativeLastName() + "'" +
            ", countryRepresentativeMail='" + getCountryRepresentativeMail() + "'" +
            ", countryRepresentativePosition='" + getCountryRepresentativePosition() + "'" +
            ", countryRepresentativeStartDate='" + getCountryRepresentativeStartDate() + "'" +
            ", countryRepresentativeEndDate='" + getCountryRepresentativeEndDate() + "'" +
            ", countryRepresentativeMinistry='" + getCountryRepresentativeMinistry() + "'" +
            ", countryRepresentativeDepartment='" + getCountryRepresentativeDepartment() + "'" +
            ", contactEunFirstName='" + getContactEunFirstName() + "'" +
            ", contactEunLastName='" + getContactEunLastName() + "'" +
            ", type='" + getType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
