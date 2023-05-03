package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.OrganizationInProject} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationInProjectDTO implements Serializable {

    private Long id;

    private String status;

    private LocalDate joinDate;

    private Integer fundingForOrganization;

    private Integer participationToMatchingFunding;

    private Boolean schoolRegistrationPossible;

    private Boolean teacherParticipationPossible;

    private Boolean ambassadorsPilotTeachersLeadingTeachersIdentified;

    private Boolean usersCanRegisterToPortal;

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

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getFundingForOrganization() {
        return fundingForOrganization;
    }

    public void setFundingForOrganization(Integer fundingForOrganization) {
        this.fundingForOrganization = fundingForOrganization;
    }

    public Integer getParticipationToMatchingFunding() {
        return participationToMatchingFunding;
    }

    public void setParticipationToMatchingFunding(Integer participationToMatchingFunding) {
        this.participationToMatchingFunding = participationToMatchingFunding;
    }

    public Boolean getSchoolRegistrationPossible() {
        return schoolRegistrationPossible;
    }

    public void setSchoolRegistrationPossible(Boolean schoolRegistrationPossible) {
        this.schoolRegistrationPossible = schoolRegistrationPossible;
    }

    public Boolean getTeacherParticipationPossible() {
        return teacherParticipationPossible;
    }

    public void setTeacherParticipationPossible(Boolean teacherParticipationPossible) {
        this.teacherParticipationPossible = teacherParticipationPossible;
    }

    public Boolean getAmbassadorsPilotTeachersLeadingTeachersIdentified() {
        return ambassadorsPilotTeachersLeadingTeachersIdentified;
    }

    public void setAmbassadorsPilotTeachersLeadingTeachersIdentified(Boolean ambassadorsPilotTeachersLeadingTeachersIdentified) {
        this.ambassadorsPilotTeachersLeadingTeachersIdentified = ambassadorsPilotTeachersLeadingTeachersIdentified;
    }

    public Boolean getUsersCanRegisterToPortal() {
        return usersCanRegisterToPortal;
    }

    public void setUsersCanRegisterToPortal(Boolean usersCanRegisterToPortal) {
        this.usersCanRegisterToPortal = usersCanRegisterToPortal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationInProjectDTO)) {
            return false;
        }

        OrganizationInProjectDTO organizationInProjectDTO = (OrganizationInProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organizationInProjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationInProjectDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", joinDate='" + getJoinDate() + "'" +
            ", fundingForOrganization=" + getFundingForOrganization() +
            ", participationToMatchingFunding=" + getParticipationToMatchingFunding() +
            ", schoolRegistrationPossible='" + getSchoolRegistrationPossible() + "'" +
            ", teacherParticipationPossible='" + getTeacherParticipationPossible() + "'" +
            ", ambassadorsPilotTeachersLeadingTeachersIdentified='" + getAmbassadorsPilotTeachersLeadingTeachersIdentified() + "'" +
            ", usersCanRegisterToPortal='" + getUsersCanRegisterToPortal() + "'" +
            "}";
    }
}
