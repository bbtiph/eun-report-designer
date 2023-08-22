package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.WorkingGroupReferences} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkingGroupReferencesIndicatorDTO implements Serializable {

    private Long id;

    private String countryRepresentativeFirstName;

    private String countryRepresentativeLastName;

    private String countryRepresentativeMail;

    private String countryRepresentativePosition;

    private String countryRepresentativeMinistry;

    private String countryRepresentativeDepartment;

    private String contactEunFirstName;

    private String contactEunLastName;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingGroupReferencesIndicatorDTO)) {
            return false;
        }

        WorkingGroupReferencesIndicatorDTO workingGroupReferencesDTO = (WorkingGroupReferencesIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workingGroupReferencesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "WorkingGroupReferencesIndicatorDTO{" +
            "id=" +
            id +
            ", countryRepresentativeFirstName='" +
            countryRepresentativeFirstName +
            '\'' +
            ", countryRepresentativeLastName='" +
            countryRepresentativeLastName +
            '\'' +
            ", countryRepresentativeMail='" +
            countryRepresentativeMail +
            '\'' +
            ", countryRepresentativePosition='" +
            countryRepresentativePosition +
            '\'' +
            ", countryRepresentativeMinistry='" +
            countryRepresentativeMinistry +
            '\'' +
            ", countryRepresentativeDepartment='" +
            countryRepresentativeDepartment +
            '\'' +
            ", contactEunFirstName='" +
            contactEunFirstName +
            '\'' +
            ", contactEunLastName='" +
            contactEunLastName +
            '\'' +
            ", type='" +
            type +
            '\'' +
            '}'
        );
    }
}
