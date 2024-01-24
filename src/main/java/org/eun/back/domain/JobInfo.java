package org.eun.back.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JobInfo.
 */
@Entity
@Table(name = "job_info")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Builder
public class JobInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "odata_etag")
    private String odataEtag;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "job_number")
    private String jobNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "description_2")
    private String description2;

    @Column(name = "bill_to_customer_no")
    private String billToCustomerNo;

    @Column(name = "bill_to_name")
    private String billToName;

    @Column(name = "bill_to_country_region_code")
    private String billToCountryRegionCode;

    @Column(name = "sell_to_contact")
    private String sellToContact;

    @Column(name = "your_reference")
    private String yourReference;

    @Column(name = "contract_no")
    private String contractNo;

    @Column(name = "status_proposal")
    private String statusProposal;

    @Column(name = "starting_date")
    private LocalDate startingDate;

    @Column(name = "ending_date")
    private LocalDate endingDate;

    @Column(name = "duration_in_months")
    private Integer durationInMonths;

    @Column(name = "project_manager")
    private String projectManager;

    @Column(name = "project_manager_mail")
    private String projectManagerMail;

    @Column(name = "eun_role")
    private String eunRole;

    @Column(name = "visa_number")
    private String visaNumber;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "job_type_text")
    private String jobTypeText;

    @Column(name = "job_program")
    private String jobProgram;

    @Column(name = "program_manager")
    private String programManager;

    @Column(name = "budget_eun")
    private Double budgetEUN;

    @Column(name = "funding_eun")
    private Double fundingEUN;

    @Column(name = "funding_rate")
    private Double fundingRate;

    @Column(name = "budget_consortium")
    private Double budgetConsortium;

    @Column(name = "funding_consortium")
    private Double fundingConsortium;

    @Column(name = "overhead_perc")
    private Integer overheadPerc;

    public JobInfo() {}

    public JobInfo(
        Long id,
        String odataEtag,
        String externalId,
        String jobNumber,
        String description,
        String description2,
        String billToCustomerNo,
        String billToName,
        String billToCountryRegionCode,
        String sellToContact,
        String yourReference,
        String contractNo,
        String statusProposal,
        LocalDate startingDate,
        LocalDate endingDate,
        Integer durationInMonths,
        String projectManager,
        String projectManagerMail,
        String eunRole,
        String visaNumber,
        String jobType,
        String jobTypeText,
        String jobProgram,
        String programManager,
        Double budgetEUN,
        Double fundingEUN,
        Double fundingRate,
        Double budgetConsortium,
        Double fundingConsortium,
        Integer overheadPerc
    ) {
        this.id = id;
        this.odataEtag = odataEtag;
        this.externalId = externalId;
        this.jobNumber = jobNumber;
        this.description = description;
        this.description2 = description2;
        this.billToCustomerNo = billToCustomerNo;
        this.billToName = billToName;
        this.billToCountryRegionCode = billToCountryRegionCode;
        this.sellToContact = sellToContact;
        this.yourReference = yourReference;
        this.contractNo = contractNo;
        this.statusProposal = statusProposal;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.durationInMonths = durationInMonths;
        this.projectManager = projectManager;
        this.projectManagerMail = projectManagerMail;
        this.eunRole = eunRole;
        this.visaNumber = visaNumber;
        this.jobType = jobType;
        this.jobTypeText = jobTypeText;
        this.jobProgram = jobProgram;
        this.programManager = programManager;
        this.budgetEUN = budgetEUN;
        this.fundingEUN = fundingEUN;
        this.fundingRate = fundingRate;
        this.budgetConsortium = budgetConsortium;
        this.fundingConsortium = fundingConsortium;
        this.overheadPerc = overheadPerc;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOdataEtag() {
        return this.odataEtag;
    }

    public JobInfo odataEtag(String odataEtag) {
        this.setOdataEtag(odataEtag);
        return this;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public JobInfo externalId(String externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getJobNumber() {
        return this.jobNumber;
    }

    public JobInfo jobNumber(String jobNumber) {
        this.setJobNumber(jobNumber);
        return this;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public JobInfo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return this.description2;
    }

    public JobInfo description2(String description2) {
        this.setDescription2(description2);
        return this;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getBillToCustomerNo() {
        return this.billToCustomerNo;
    }

    public JobInfo billToCustomerNo(String billToCustomerNo) {
        this.setBillToCustomerNo(billToCustomerNo);
        return this;
    }

    public void setBillToCustomerNo(String billToCustomerNo) {
        this.billToCustomerNo = billToCustomerNo;
    }

    public String getBillToName() {
        return this.billToName;
    }

    public JobInfo billToName(String billToName) {
        this.setBillToName(billToName);
        return this;
    }

    public void setBillToName(String billToName) {
        this.billToName = billToName;
    }

    public String getBillToCountryRegionCode() {
        return this.billToCountryRegionCode;
    }

    public JobInfo billToCountryRegionCode(String billToCountryRegionCode) {
        this.setBillToCountryRegionCode(billToCountryRegionCode);
        return this;
    }

    public void setBillToCountryRegionCode(String billToCountryRegionCode) {
        this.billToCountryRegionCode = billToCountryRegionCode;
    }

    public String getSellToContact() {
        return this.sellToContact;
    }

    public JobInfo sellToContact(String sellToContact) {
        this.setSellToContact(sellToContact);
        return this;
    }

    public void setSellToContact(String sellToContact) {
        this.sellToContact = sellToContact;
    }

    public String getYourReference() {
        return this.yourReference;
    }

    public JobInfo yourReference(String yourReference) {
        this.setYourReference(yourReference);
        return this;
    }

    public void setYourReference(String yourReference) {
        this.yourReference = yourReference;
    }

    public String getContractNo() {
        return this.contractNo;
    }

    public JobInfo contractNo(String contractNo) {
        this.setContractNo(contractNo);
        return this;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getStatusProposal() {
        return this.statusProposal;
    }

    public JobInfo statusProposal(String statusProposal) {
        this.setStatusProposal(statusProposal);
        return this;
    }

    public void setStatusProposal(String statusProposal) {
        this.statusProposal = statusProposal;
    }

    public LocalDate getStartingDate() {
        return this.startingDate;
    }

    public JobInfo startingDate(LocalDate startingDate) {
        this.setStartingDate(startingDate);
        return this;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return this.endingDate;
    }

    public JobInfo endingDate(LocalDate endingDate) {
        this.setEndingDate(endingDate);
        return this;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public Integer getDurationInMonths() {
        return this.durationInMonths;
    }

    public JobInfo durationInMonths(Integer durationInMonths) {
        this.setDurationInMonths(durationInMonths);
        return this;
    }

    public void setDurationInMonths(Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public String getProjectManager() {
        return this.projectManager;
    }

    public JobInfo projectManager(String projectManager) {
        this.setProjectManager(projectManager);
        return this;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectManagerMail() {
        return this.projectManagerMail;
    }

    public JobInfo projectManagerMail(String projectManagerMail) {
        this.setProjectManagerMail(projectManagerMail);
        return this;
    }

    public void setProjectManagerMail(String projectManagerMail) {
        this.projectManagerMail = projectManagerMail;
    }

    public String getEunRole() {
        return this.eunRole;
    }

    public JobInfo eunRole(String eunRole) {
        this.setEunRole(eunRole);
        return this;
    }

    public void setEunRole(String eunRole) {
        this.eunRole = eunRole;
    }

    public String getVisaNumber() {
        return this.visaNumber;
    }

    public JobInfo visaNumber(String visaNumber) {
        this.setVisaNumber(visaNumber);
        return this;
    }

    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public String getJobType() {
        return this.jobType;
    }

    public JobInfo jobType(String jobType) {
        this.setJobType(jobType);
        return this;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobTypeText() {
        return this.jobTypeText;
    }

    public JobInfo jobTypeText(String jobTypeText) {
        this.setJobTypeText(jobTypeText);
        return this;
    }

    public void setJobTypeText(String jobTypeText) {
        this.jobTypeText = jobTypeText;
    }

    public String getJobProgram() {
        return this.jobProgram;
    }

    public JobInfo jobProgram(String jobProgram) {
        this.setJobProgram(jobProgram);
        return this;
    }

    public void setJobProgram(String jobProgram) {
        this.jobProgram = jobProgram;
    }

    public String getProgramManager() {
        return this.programManager;
    }

    public JobInfo programManager(String programManager) {
        this.setProgramManager(programManager);
        return this;
    }

    public void setProgramManager(String programManager) {
        this.programManager = programManager;
    }

    public Double getBudgetEUN() {
        return this.budgetEUN;
    }

    public JobInfo budgetEUN(Double budgetEUN) {
        this.setBudgetEUN(budgetEUN);
        return this;
    }

    public void setBudgetEUN(Double budgetEUN) {
        this.budgetEUN = budgetEUN;
    }

    public Double getFundingEUN() {
        return this.fundingEUN;
    }

    public JobInfo fundingEUN(Double fundingEUN) {
        this.setFundingEUN(fundingEUN);
        return this;
    }

    public void setFundingEUN(Double fundingEUN) {
        this.fundingEUN = fundingEUN;
    }

    public Double getFundingRate() {
        return this.fundingRate;
    }

    public JobInfo fundingRate(Double fundingRate) {
        this.setFundingRate(fundingRate);
        return this;
    }

    public void setFundingRate(Double fundingRate) {
        this.fundingRate = fundingRate;
    }

    public Double getBudgetConsortium() {
        return this.budgetConsortium;
    }

    public JobInfo budgetConsortium(Double budgetConsortium) {
        this.setBudgetConsortium(budgetConsortium);
        return this;
    }

    public void setBudgetConsortium(Double budgetConsortium) {
        this.budgetConsortium = budgetConsortium;
    }

    public Double getFundingConsortium() {
        return this.fundingConsortium;
    }

    public JobInfo fundingConsortium(Double fundingConsortium) {
        this.setFundingConsortium(fundingConsortium);
        return this;
    }

    public void setFundingConsortium(Double fundingConsortium) {
        this.fundingConsortium = fundingConsortium;
    }

    public Integer getOverheadPerc() {
        return this.overheadPerc;
    }

    public JobInfo overheadPerc(Integer overheadPerc) {
        this.setOverheadPerc(overheadPerc);
        return this;
    }

    public void setOverheadPerc(Integer overheadPerc) {
        this.overheadPerc = overheadPerc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobInfo)) {
            return false;
        }
        return id != null && id.equals(((JobInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobInfo{" +
            "id=" + getId() +
            ", odataEtag='" + getOdataEtag() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", jobNumber='" + getJobNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", description2='" + getDescription2() + "'" +
            ", billToCustomerNo='" + getBillToCustomerNo() + "'" +
            ", billToName='" + getBillToName() + "'" +
            ", billToCountryRegionCode='" + getBillToCountryRegionCode() + "'" +
            ", sellToContact='" + getSellToContact() + "'" +
            ", yourReference='" + getYourReference() + "'" +
            ", contractNo='" + getContractNo() + "'" +
            ", statusProposal='" + getStatusProposal() + "'" +
            ", startingDate='" + getStartingDate() + "'" +
            ", endingDate='" + getEndingDate() + "'" +
            ", durationInMonths=" + getDurationInMonths() +
            ", projectManager='" + getProjectManager() + "'" +
            ", projectManagerMail='" + getProjectManagerMail() + "'" +
            ", eunRole='" + getEunRole() + "'" +
            ", visaNumber='" + getVisaNumber() + "'" +
            ", jobType='" + getJobType() + "'" +
            ", jobTypeText='" + getJobTypeText() + "'" +
            ", jobProgram='" + getJobProgram() + "'" +
            ", programManager='" + getProgramManager() + "'" +
            ", budgetEUN=" + getBudgetEUN() +
            ", fundingEUN=" + getFundingEUN() +
            ", fundingRate=" + getFundingRate() +
            ", budgetConsortium=" + getBudgetConsortium() +
            ", fundingConsortium=" + getFundingConsortium() +
            ", overheadPerc=" + getOverheadPerc() +
            "}";
    }
}
