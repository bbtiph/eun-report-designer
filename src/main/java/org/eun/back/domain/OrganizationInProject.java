package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A OrganizationInProject.
 */
@Entity
@Table(name = "organization_in_project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationInProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "funding_for_organization")
    private Integer fundingForOrganization;

    @Column(name = "participation_to_matching_funding")
    private Integer participationToMatchingFunding;

    @Column(name = "school_registration_possible")
    private Boolean schoolRegistrationPossible;

    @Column(name = "teacher_participation_possible")
    private Boolean teacherParticipationPossible;

    @Column(name = "ambassadors_pilot_teachers_leading_teachers_identified")
    private Boolean ambassadorsPilotTeachersLeadingTeachersIdentified;

    @Column(name = "users_can_register_to_portal")
    private Boolean usersCanRegisterToPortal;

    @OneToMany(mappedBy = "organizationInProject")
    @JsonIgnoreProperties(value = { "fundings", "organizationInProject", "personInProject" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "organizationInProject")
    @JsonIgnoreProperties(
        value = { "countries", "eventInOrganization", "organizationInMinistry", "organizationInProject", "personInOrganization" },
        allowSetters = true
    )
    private Set<Organization> organizations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrganizationInProject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public OrganizationInProject status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getJoinDate() {
        return this.joinDate;
    }

    public OrganizationInProject joinDate(LocalDate joinDate) {
        this.setJoinDate(joinDate);
        return this;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getFundingForOrganization() {
        return this.fundingForOrganization;
    }

    public OrganizationInProject fundingForOrganization(Integer fundingForOrganization) {
        this.setFundingForOrganization(fundingForOrganization);
        return this;
    }

    public void setFundingForOrganization(Integer fundingForOrganization) {
        this.fundingForOrganization = fundingForOrganization;
    }

    public Integer getParticipationToMatchingFunding() {
        return this.participationToMatchingFunding;
    }

    public OrganizationInProject participationToMatchingFunding(Integer participationToMatchingFunding) {
        this.setParticipationToMatchingFunding(participationToMatchingFunding);
        return this;
    }

    public void setParticipationToMatchingFunding(Integer participationToMatchingFunding) {
        this.participationToMatchingFunding = participationToMatchingFunding;
    }

    public Boolean getSchoolRegistrationPossible() {
        return this.schoolRegistrationPossible;
    }

    public OrganizationInProject schoolRegistrationPossible(Boolean schoolRegistrationPossible) {
        this.setSchoolRegistrationPossible(schoolRegistrationPossible);
        return this;
    }

    public void setSchoolRegistrationPossible(Boolean schoolRegistrationPossible) {
        this.schoolRegistrationPossible = schoolRegistrationPossible;
    }

    public Boolean getTeacherParticipationPossible() {
        return this.teacherParticipationPossible;
    }

    public OrganizationInProject teacherParticipationPossible(Boolean teacherParticipationPossible) {
        this.setTeacherParticipationPossible(teacherParticipationPossible);
        return this;
    }

    public void setTeacherParticipationPossible(Boolean teacherParticipationPossible) {
        this.teacherParticipationPossible = teacherParticipationPossible;
    }

    public Boolean getAmbassadorsPilotTeachersLeadingTeachersIdentified() {
        return this.ambassadorsPilotTeachersLeadingTeachersIdentified;
    }

    public OrganizationInProject ambassadorsPilotTeachersLeadingTeachersIdentified(
        Boolean ambassadorsPilotTeachersLeadingTeachersIdentified
    ) {
        this.setAmbassadorsPilotTeachersLeadingTeachersIdentified(ambassadorsPilotTeachersLeadingTeachersIdentified);
        return this;
    }

    public void setAmbassadorsPilotTeachersLeadingTeachersIdentified(Boolean ambassadorsPilotTeachersLeadingTeachersIdentified) {
        this.ambassadorsPilotTeachersLeadingTeachersIdentified = ambassadorsPilotTeachersLeadingTeachersIdentified;
    }

    public Boolean getUsersCanRegisterToPortal() {
        return this.usersCanRegisterToPortal;
    }

    public OrganizationInProject usersCanRegisterToPortal(Boolean usersCanRegisterToPortal) {
        this.setUsersCanRegisterToPortal(usersCanRegisterToPortal);
        return this;
    }

    public void setUsersCanRegisterToPortal(Boolean usersCanRegisterToPortal) {
        this.usersCanRegisterToPortal = usersCanRegisterToPortal;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setOrganizationInProject(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setOrganizationInProject(this));
        }
        this.projects = projects;
    }

    public OrganizationInProject projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public OrganizationInProject addProject(Project project) {
        this.projects.add(project);
        project.setOrganizationInProject(this);
        return this;
    }

    public OrganizationInProject removeProject(Project project) {
        this.projects.remove(project);
        project.setOrganizationInProject(null);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        if (this.organizations != null) {
            this.organizations.forEach(i -> i.setOrganizationInProject(null));
        }
        if (organizations != null) {
            organizations.forEach(i -> i.setOrganizationInProject(this));
        }
        this.organizations = organizations;
    }

    public OrganizationInProject organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public OrganizationInProject addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.setOrganizationInProject(this);
        return this;
    }

    public OrganizationInProject removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.setOrganizationInProject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationInProject)) {
            return false;
        }
        return id != null && id.equals(((OrganizationInProject) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganizationInProject{" +
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
