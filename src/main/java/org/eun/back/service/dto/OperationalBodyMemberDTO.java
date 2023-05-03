package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.OperationalBodyMember} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationalBodyMemberDTO implements Serializable {

    private Long id;

    private Long personId;

    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    private String department;

    private String eunContactFirstname;

    private String eunContactLastname;

    private String cooperationField;

    private String status;

    private CountriesDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEunContactFirstname() {
        return eunContactFirstname;
    }

    public void setEunContactFirstname(String eunContactFirstname) {
        this.eunContactFirstname = eunContactFirstname;
    }

    public String getEunContactLastname() {
        return eunContactLastname;
    }

    public void setEunContactLastname(String eunContactLastname) {
        this.eunContactLastname = eunContactLastname;
    }

    public String getCooperationField() {
        return cooperationField;
    }

    public void setCooperationField(String cooperationField) {
        this.cooperationField = cooperationField;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CountriesDTO getCountry() {
        return country;
    }

    public void setCountry(CountriesDTO country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationalBodyMemberDTO)) {
            return false;
        }

        OperationalBodyMemberDTO operationalBodyMemberDTO = (OperationalBodyMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, operationalBodyMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationalBodyMemberDTO{" +
            "id=" + getId() +
            ", personId=" + getPersonId() +
            ", position='" + getPosition() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", department='" + getDepartment() + "'" +
            ", eunContactFirstname='" + getEunContactFirstname() + "'" +
            ", eunContactLastname='" + getEunContactLastname() + "'" +
            ", cooperationField='" + getCooperationField() + "'" +
            ", status='" + getStatus() + "'" +
            ", country=" + getCountry() +
            "}";
    }
}
