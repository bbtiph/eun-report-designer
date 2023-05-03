package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.eun.back.domain.enumeration.ProjectStatus;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @Column(name = "supported_country_ids")
    private String supportedCountryIds;

    @Column(name = "supported_language_ids")
    private String supportedLanguageIds;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "acronym")
    private String acronym;

    @Column(name = "period")
    private String period;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "total_budget")
    private Long totalBudget;

    @Column(name = "total_funding")
    private Long totalFunding;

    @Column(name = "total_budget_for_eun")
    private Long totalBudgetForEun;

    @Column(name = "total_funding_for_eun")
    private Long totalFundingForEun;

    @Column(name = "funding_value")
    private Long fundingValue;

    @Column(name = "percentage_of_funding")
    private Long percentageOfFunding;

    @Column(name = "percentage_of_indirect_costs")
    private Long percentageOfIndirectCosts;

    @Column(name = "percentage_of_funding_for_eun")
    private Long percentageOfFundingForEun;

    @Column(name = "percentage_of_indirect_costs_for_eun")
    private Long percentageOfIndirectCostsForEun;

    @Column(name = "funding_extra_comment")
    private String fundingExtraComment;

    @Column(name = "programme")
    private String programme;

    @Column(name = "eu_dg")
    private String euDg;

    @Column(name = "role_of_eun")
    private String roleOfEun;

    @Column(name = "summary")
    private String summary;

    @Column(name = "main_tasks")
    private String mainTasks;

    @Column(name = "expected_outcomes")
    private String expectedOutcomes;

    @Column(name = "eu_call_reference")
    private String euCallReference;

    @Column(name = "eu_project_reference")
    private String euProjectReference;

    @Column(name = "eu_call_deadline")
    private String euCallDeadline;

    @Column(name = "project_manager")
    private String projectManager;

    @Column(name = "reference_number_of_project")
    private Integer referenceNumberOfProject;

    @Column(name = "eun_team")
    private String eunTeam;

    @Column(name = "sys_creat_timestamp")
    private Instant sysCreatTimestamp;

    @Column(name = "sys_creat_ip_address")
    private String sysCreatIpAddress;

    @Column(name = "sys_modif_timestamp")
    private Instant sysModifTimestamp;

    @Column(name = "sys_modif_ip_address")
    private String sysModifIpAddress;

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties(value = { "project" }, allowSetters = true)
    private Set<Funding> fundings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "projects", "organizations" }, allowSetters = true)
    private OrganizationInProject organizationInProject;

    @ManyToOne
    @JsonIgnoreProperties(value = { "people", "projects" }, allowSetters = true)
    private PersonInProject personInProject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectStatus getStatus() {
        return this.status;
    }

    public Project status(ProjectStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getSupportedCountryIds() {
        return this.supportedCountryIds;
    }

    public Project supportedCountryIds(String supportedCountryIds) {
        this.setSupportedCountryIds(supportedCountryIds);
        return this;
    }

    public void setSupportedCountryIds(String supportedCountryIds) {
        this.supportedCountryIds = supportedCountryIds;
    }

    public String getSupportedLanguageIds() {
        return this.supportedLanguageIds;
    }

    public Project supportedLanguageIds(String supportedLanguageIds) {
        this.setSupportedLanguageIds(supportedLanguageIds);
        return this;
    }

    public void setSupportedLanguageIds(String supportedLanguageIds) {
        this.supportedLanguageIds = supportedLanguageIds;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public Project acronym(String acronym) {
        this.setAcronym(acronym);
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPeriod() {
        return this.period;
    }

    public Project period(String period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return this.description;
    }

    public Project description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public Project contactEmail(String contactEmail) {
        this.setContactEmail(contactEmail);
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return this.contactName;
    }

    public Project contactName(String contactName) {
        this.setContactName(contactName);
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getTotalBudget() {
        return this.totalBudget;
    }

    public Project totalBudget(Long totalBudget) {
        this.setTotalBudget(totalBudget);
        return this;
    }

    public void setTotalBudget(Long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Long getTotalFunding() {
        return this.totalFunding;
    }

    public Project totalFunding(Long totalFunding) {
        this.setTotalFunding(totalFunding);
        return this;
    }

    public void setTotalFunding(Long totalFunding) {
        this.totalFunding = totalFunding;
    }

    public Long getTotalBudgetForEun() {
        return this.totalBudgetForEun;
    }

    public Project totalBudgetForEun(Long totalBudgetForEun) {
        this.setTotalBudgetForEun(totalBudgetForEun);
        return this;
    }

    public void setTotalBudgetForEun(Long totalBudgetForEun) {
        this.totalBudgetForEun = totalBudgetForEun;
    }

    public Long getTotalFundingForEun() {
        return this.totalFundingForEun;
    }

    public Project totalFundingForEun(Long totalFundingForEun) {
        this.setTotalFundingForEun(totalFundingForEun);
        return this;
    }

    public void setTotalFundingForEun(Long totalFundingForEun) {
        this.totalFundingForEun = totalFundingForEun;
    }

    public Long getFundingValue() {
        return this.fundingValue;
    }

    public Project fundingValue(Long fundingValue) {
        this.setFundingValue(fundingValue);
        return this;
    }

    public void setFundingValue(Long fundingValue) {
        this.fundingValue = fundingValue;
    }

    public Long getPercentageOfFunding() {
        return this.percentageOfFunding;
    }

    public Project percentageOfFunding(Long percentageOfFunding) {
        this.setPercentageOfFunding(percentageOfFunding);
        return this;
    }

    public void setPercentageOfFunding(Long percentageOfFunding) {
        this.percentageOfFunding = percentageOfFunding;
    }

    public Long getPercentageOfIndirectCosts() {
        return this.percentageOfIndirectCosts;
    }

    public Project percentageOfIndirectCosts(Long percentageOfIndirectCosts) {
        this.setPercentageOfIndirectCosts(percentageOfIndirectCosts);
        return this;
    }

    public void setPercentageOfIndirectCosts(Long percentageOfIndirectCosts) {
        this.percentageOfIndirectCosts = percentageOfIndirectCosts;
    }

    public Long getPercentageOfFundingForEun() {
        return this.percentageOfFundingForEun;
    }

    public Project percentageOfFundingForEun(Long percentageOfFundingForEun) {
        this.setPercentageOfFundingForEun(percentageOfFundingForEun);
        return this;
    }

    public void setPercentageOfFundingForEun(Long percentageOfFundingForEun) {
        this.percentageOfFundingForEun = percentageOfFundingForEun;
    }

    public Long getPercentageOfIndirectCostsForEun() {
        return this.percentageOfIndirectCostsForEun;
    }

    public Project percentageOfIndirectCostsForEun(Long percentageOfIndirectCostsForEun) {
        this.setPercentageOfIndirectCostsForEun(percentageOfIndirectCostsForEun);
        return this;
    }

    public void setPercentageOfIndirectCostsForEun(Long percentageOfIndirectCostsForEun) {
        this.percentageOfIndirectCostsForEun = percentageOfIndirectCostsForEun;
    }

    public String getFundingExtraComment() {
        return this.fundingExtraComment;
    }

    public Project fundingExtraComment(String fundingExtraComment) {
        this.setFundingExtraComment(fundingExtraComment);
        return this;
    }

    public void setFundingExtraComment(String fundingExtraComment) {
        this.fundingExtraComment = fundingExtraComment;
    }

    public String getProgramme() {
        return this.programme;
    }

    public Project programme(String programme) {
        this.setProgramme(programme);
        return this;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getEuDg() {
        return this.euDg;
    }

    public Project euDg(String euDg) {
        this.setEuDg(euDg);
        return this;
    }

    public void setEuDg(String euDg) {
        this.euDg = euDg;
    }

    public String getRoleOfEun() {
        return this.roleOfEun;
    }

    public Project roleOfEun(String roleOfEun) {
        this.setRoleOfEun(roleOfEun);
        return this;
    }

    public void setRoleOfEun(String roleOfEun) {
        this.roleOfEun = roleOfEun;
    }

    public String getSummary() {
        return this.summary;
    }

    public Project summary(String summary) {
        this.setSummary(summary);
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMainTasks() {
        return this.mainTasks;
    }

    public Project mainTasks(String mainTasks) {
        this.setMainTasks(mainTasks);
        return this;
    }

    public void setMainTasks(String mainTasks) {
        this.mainTasks = mainTasks;
    }

    public String getExpectedOutcomes() {
        return this.expectedOutcomes;
    }

    public Project expectedOutcomes(String expectedOutcomes) {
        this.setExpectedOutcomes(expectedOutcomes);
        return this;
    }

    public void setExpectedOutcomes(String expectedOutcomes) {
        this.expectedOutcomes = expectedOutcomes;
    }

    public String getEuCallReference() {
        return this.euCallReference;
    }

    public Project euCallReference(String euCallReference) {
        this.setEuCallReference(euCallReference);
        return this;
    }

    public void setEuCallReference(String euCallReference) {
        this.euCallReference = euCallReference;
    }

    public String getEuProjectReference() {
        return this.euProjectReference;
    }

    public Project euProjectReference(String euProjectReference) {
        this.setEuProjectReference(euProjectReference);
        return this;
    }

    public void setEuProjectReference(String euProjectReference) {
        this.euProjectReference = euProjectReference;
    }

    public String getEuCallDeadline() {
        return this.euCallDeadline;
    }

    public Project euCallDeadline(String euCallDeadline) {
        this.setEuCallDeadline(euCallDeadline);
        return this;
    }

    public void setEuCallDeadline(String euCallDeadline) {
        this.euCallDeadline = euCallDeadline;
    }

    public String getProjectManager() {
        return this.projectManager;
    }

    public Project projectManager(String projectManager) {
        this.setProjectManager(projectManager);
        return this;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getReferenceNumberOfProject() {
        return this.referenceNumberOfProject;
    }

    public Project referenceNumberOfProject(Integer referenceNumberOfProject) {
        this.setReferenceNumberOfProject(referenceNumberOfProject);
        return this;
    }

    public void setReferenceNumberOfProject(Integer referenceNumberOfProject) {
        this.referenceNumberOfProject = referenceNumberOfProject;
    }

    public String getEunTeam() {
        return this.eunTeam;
    }

    public Project eunTeam(String eunTeam) {
        this.setEunTeam(eunTeam);
        return this;
    }

    public void setEunTeam(String eunTeam) {
        this.eunTeam = eunTeam;
    }

    public Instant getSysCreatTimestamp() {
        return this.sysCreatTimestamp;
    }

    public Project sysCreatTimestamp(Instant sysCreatTimestamp) {
        this.setSysCreatTimestamp(sysCreatTimestamp);
        return this;
    }

    public void setSysCreatTimestamp(Instant sysCreatTimestamp) {
        this.sysCreatTimestamp = sysCreatTimestamp;
    }

    public String getSysCreatIpAddress() {
        return this.sysCreatIpAddress;
    }

    public Project sysCreatIpAddress(String sysCreatIpAddress) {
        this.setSysCreatIpAddress(sysCreatIpAddress);
        return this;
    }

    public void setSysCreatIpAddress(String sysCreatIpAddress) {
        this.sysCreatIpAddress = sysCreatIpAddress;
    }

    public Instant getSysModifTimestamp() {
        return this.sysModifTimestamp;
    }

    public Project sysModifTimestamp(Instant sysModifTimestamp) {
        this.setSysModifTimestamp(sysModifTimestamp);
        return this;
    }

    public void setSysModifTimestamp(Instant sysModifTimestamp) {
        this.sysModifTimestamp = sysModifTimestamp;
    }

    public String getSysModifIpAddress() {
        return this.sysModifIpAddress;
    }

    public Project sysModifIpAddress(String sysModifIpAddress) {
        this.setSysModifIpAddress(sysModifIpAddress);
        return this;
    }

    public void setSysModifIpAddress(String sysModifIpAddress) {
        this.sysModifIpAddress = sysModifIpAddress;
    }

    public Set<Funding> getFundings() {
        return this.fundings;
    }

    public void setFundings(Set<Funding> fundings) {
        if (this.fundings != null) {
            this.fundings.forEach(i -> i.setProject(null));
        }
        if (fundings != null) {
            fundings.forEach(i -> i.setProject(this));
        }
        this.fundings = fundings;
    }

    public Project fundings(Set<Funding> fundings) {
        this.setFundings(fundings);
        return this;
    }

    public Project addFunding(Funding funding) {
        this.fundings.add(funding);
        funding.setProject(this);
        return this;
    }

    public Project removeFunding(Funding funding) {
        this.fundings.remove(funding);
        funding.setProject(null);
        return this;
    }

    public OrganizationInProject getOrganizationInProject() {
        return this.organizationInProject;
    }

    public void setOrganizationInProject(OrganizationInProject organizationInProject) {
        this.organizationInProject = organizationInProject;
    }

    public Project organizationInProject(OrganizationInProject organizationInProject) {
        this.setOrganizationInProject(organizationInProject);
        return this;
    }

    public PersonInProject getPersonInProject() {
        return this.personInProject;
    }

    public void setPersonInProject(PersonInProject personInProject) {
        this.personInProject = personInProject;
    }

    public Project personInProject(PersonInProject personInProject) {
        this.setPersonInProject(personInProject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
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
            "}";
    }
}
