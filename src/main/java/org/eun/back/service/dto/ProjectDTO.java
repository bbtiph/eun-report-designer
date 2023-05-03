package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;
import org.eun.back.domain.enumeration.ProjectStatus;

/**
 * A DTO for the {@link org.eun.back.domain.Project} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectDTO implements Serializable {

    private Long id;

    @NotNull
    private ProjectStatus status;

    private String supportedCountryIds;

    private String supportedLanguageIds;

    @NotNull
    private String name;

    private String acronym;

    private String period;

    @NotNull
    private String description;

    private String contactEmail;

    private String contactName;

    private Long totalBudget;

    private Long totalFunding;

    private Long totalBudgetForEun;

    private Long totalFundingForEun;

    private Long fundingValue;

    private Long percentageOfFunding;

    private Long percentageOfIndirectCosts;

    private Long percentageOfFundingForEun;

    private Long percentageOfIndirectCostsForEun;

    private String fundingExtraComment;

    private String programme;

    private String euDg;

    private String roleOfEun;

    private String summary;

    private String mainTasks;

    private String expectedOutcomes;

    private String euCallReference;

    private String euProjectReference;

    private String euCallDeadline;

    private String projectManager;

    private Integer referenceNumberOfProject;

    private String eunTeam;

    private Instant sysCreatTimestamp;

    private String sysCreatIpAddress;

    private Instant sysModifTimestamp;

    private String sysModifIpAddress;

    private OrganizationInProjectDTO organizationInProject;

    private PersonInProjectDTO personInProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getSupportedCountryIds() {
        return supportedCountryIds;
    }

    public void setSupportedCountryIds(String supportedCountryIds) {
        this.supportedCountryIds = supportedCountryIds;
    }

    public String getSupportedLanguageIds() {
        return supportedLanguageIds;
    }

    public void setSupportedLanguageIds(String supportedLanguageIds) {
        this.supportedLanguageIds = supportedLanguageIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Long getTotalFunding() {
        return totalFunding;
    }

    public void setTotalFunding(Long totalFunding) {
        this.totalFunding = totalFunding;
    }

    public Long getTotalBudgetForEun() {
        return totalBudgetForEun;
    }

    public void setTotalBudgetForEun(Long totalBudgetForEun) {
        this.totalBudgetForEun = totalBudgetForEun;
    }

    public Long getTotalFundingForEun() {
        return totalFundingForEun;
    }

    public void setTotalFundingForEun(Long totalFundingForEun) {
        this.totalFundingForEun = totalFundingForEun;
    }

    public Long getFundingValue() {
        return fundingValue;
    }

    public void setFundingValue(Long fundingValue) {
        this.fundingValue = fundingValue;
    }

    public Long getPercentageOfFunding() {
        return percentageOfFunding;
    }

    public void setPercentageOfFunding(Long percentageOfFunding) {
        this.percentageOfFunding = percentageOfFunding;
    }

    public Long getPercentageOfIndirectCosts() {
        return percentageOfIndirectCosts;
    }

    public void setPercentageOfIndirectCosts(Long percentageOfIndirectCosts) {
        this.percentageOfIndirectCosts = percentageOfIndirectCosts;
    }

    public Long getPercentageOfFundingForEun() {
        return percentageOfFundingForEun;
    }

    public void setPercentageOfFundingForEun(Long percentageOfFundingForEun) {
        this.percentageOfFundingForEun = percentageOfFundingForEun;
    }

    public Long getPercentageOfIndirectCostsForEun() {
        return percentageOfIndirectCostsForEun;
    }

    public void setPercentageOfIndirectCostsForEun(Long percentageOfIndirectCostsForEun) {
        this.percentageOfIndirectCostsForEun = percentageOfIndirectCostsForEun;
    }

    public String getFundingExtraComment() {
        return fundingExtraComment;
    }

    public void setFundingExtraComment(String fundingExtraComment) {
        this.fundingExtraComment = fundingExtraComment;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getEuDg() {
        return euDg;
    }

    public void setEuDg(String euDg) {
        this.euDg = euDg;
    }

    public String getRoleOfEun() {
        return roleOfEun;
    }

    public void setRoleOfEun(String roleOfEun) {
        this.roleOfEun = roleOfEun;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMainTasks() {
        return mainTasks;
    }

    public void setMainTasks(String mainTasks) {
        this.mainTasks = mainTasks;
    }

    public String getExpectedOutcomes() {
        return expectedOutcomes;
    }

    public void setExpectedOutcomes(String expectedOutcomes) {
        this.expectedOutcomes = expectedOutcomes;
    }

    public String getEuCallReference() {
        return euCallReference;
    }

    public void setEuCallReference(String euCallReference) {
        this.euCallReference = euCallReference;
    }

    public String getEuProjectReference() {
        return euProjectReference;
    }

    public void setEuProjectReference(String euProjectReference) {
        this.euProjectReference = euProjectReference;
    }

    public String getEuCallDeadline() {
        return euCallDeadline;
    }

    public void setEuCallDeadline(String euCallDeadline) {
        this.euCallDeadline = euCallDeadline;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getReferenceNumberOfProject() {
        return referenceNumberOfProject;
    }

    public void setReferenceNumberOfProject(Integer referenceNumberOfProject) {
        this.referenceNumberOfProject = referenceNumberOfProject;
    }

    public String getEunTeam() {
        return eunTeam;
    }

    public void setEunTeam(String eunTeam) {
        this.eunTeam = eunTeam;
    }

    public Instant getSysCreatTimestamp() {
        return sysCreatTimestamp;
    }

    public void setSysCreatTimestamp(Instant sysCreatTimestamp) {
        this.sysCreatTimestamp = sysCreatTimestamp;
    }

    public String getSysCreatIpAddress() {
        return sysCreatIpAddress;
    }

    public void setSysCreatIpAddress(String sysCreatIpAddress) {
        this.sysCreatIpAddress = sysCreatIpAddress;
    }

    public Instant getSysModifTimestamp() {
        return sysModifTimestamp;
    }

    public void setSysModifTimestamp(Instant sysModifTimestamp) {
        this.sysModifTimestamp = sysModifTimestamp;
    }

    public String getSysModifIpAddress() {
        return sysModifIpAddress;
    }

    public void setSysModifIpAddress(String sysModifIpAddress) {
        this.sysModifIpAddress = sysModifIpAddress;
    }

    public OrganizationInProjectDTO getOrganizationInProject() {
        return organizationInProject;
    }

    public void setOrganizationInProject(OrganizationInProjectDTO organizationInProject) {
        this.organizationInProject = organizationInProject;
    }

    public PersonInProjectDTO getPersonInProject() {
        return personInProject;
    }

    public void setPersonInProject(PersonInProjectDTO personInProject) {
        this.personInProject = personInProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", supportedCountryIds='" + getSupportedCountryIds() + "'" +
            ", supportedLanguageIds='" + getSupportedLanguageIds() + "'" +
            ", name='" + getName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", period='" + getPeriod() + "'" +
            ", description='" + getDescription() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", totalBudget=" + getTotalBudget() +
            ", totalFunding=" + getTotalFunding() +
            ", totalBudgetForEun=" + getTotalBudgetForEun() +
            ", totalFundingForEun=" + getTotalFundingForEun() +
            ", fundingValue=" + getFundingValue() +
            ", percentageOfFunding=" + getPercentageOfFunding() +
            ", percentageOfIndirectCosts=" + getPercentageOfIndirectCosts() +
            ", percentageOfFundingForEun=" + getPercentageOfFundingForEun() +
            ", percentageOfIndirectCostsForEun=" + getPercentageOfIndirectCostsForEun() +
            ", fundingExtraComment='" + getFundingExtraComment() + "'" +
            ", programme='" + getProgramme() + "'" +
            ", euDg='" + getEuDg() + "'" +
            ", roleOfEun='" + getRoleOfEun() + "'" +
            ", summary='" + getSummary() + "'" +
            ", mainTasks='" + getMainTasks() + "'" +
            ", expectedOutcomes='" + getExpectedOutcomes() + "'" +
            ", euCallReference='" + getEuCallReference() + "'" +
            ", euProjectReference='" + getEuProjectReference() + "'" +
            ", euCallDeadline='" + getEuCallDeadline() + "'" +
            ", projectManager='" + getProjectManager() + "'" +
            ", referenceNumberOfProject=" + getReferenceNumberOfProject() +
            ", eunTeam='" + getEunTeam() + "'" +
            ", sysCreatTimestamp='" + getSysCreatTimestamp() + "'" +
            ", sysCreatIpAddress='" + getSysCreatIpAddress() + "'" +
            ", sysModifTimestamp='" + getSysModifTimestamp() + "'" +
            ", sysModifIpAddress='" + getSysModifIpAddress() + "'" +
            ", organizationInProject=" + getOrganizationInProject() +
            ", personInProject=" + getPersonInProject() +
            "}";
    }
}
