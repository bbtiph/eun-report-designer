package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.JobInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobInfoContentFromERPDTO implements Serializable {

    private String id;

    private String odataEtag;

    private String externalId;

    private String jobNumber;

    private String description;

    private String description2;

    private String billToCustomerNo;

    private String billToName;

    private String billToCountryRegionCode;

    private String sellToContact;

    private String yourReference;

    private String contractNo;

    private String statusProposal;

    private String startingDate;

    private String endingDate;

    private Integer durationInMonths;

    private String projectManager;

    private String projectManagerMail;

    private String eunRole;

    private String visaNumber;

    private String jobType;

    private String jobTypeText;

    private String jobProgram;

    private String programManager;

    private Double budgetEUN;

    private Double fundingEUN;

    private Double fundingRate;

    private Double budgetConsortium;

    private Double fundingConsortium;

    private Double overheadPerc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOdataEtag() {
        return odataEtag;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getBillToCustomerNo() {
        return billToCustomerNo;
    }

    public void setBillToCustomerNo(String billToCustomerNo) {
        this.billToCustomerNo = billToCustomerNo;
    }

    public String getBillToName() {
        return billToName;
    }

    public void setBillToName(String billToName) {
        this.billToName = billToName;
    }

    public String getBillToCountryRegionCode() {
        return billToCountryRegionCode;
    }

    public void setBillToCountryRegionCode(String billToCountryRegionCode) {
        this.billToCountryRegionCode = billToCountryRegionCode;
    }

    public String getSellToContact() {
        return sellToContact;
    }

    public void setSellToContact(String sellToContact) {
        this.sellToContact = sellToContact;
    }

    public String getYourReference() {
        return yourReference;
    }

    public void setYourReference(String yourReference) {
        this.yourReference = yourReference;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getStatusProposal() {
        return statusProposal;
    }

    public void setStatusProposal(String statusProposal) {
        this.statusProposal = statusProposal;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProjectManagerMail() {
        return projectManagerMail;
    }

    public void setProjectManagerMail(String projectManagerMail) {
        this.projectManagerMail = projectManagerMail;
    }

    public String getEunRole() {
        return eunRole;
    }

    public void setEunRole(String eunRole) {
        this.eunRole = eunRole;
    }

    public String getVisaNumber() {
        return visaNumber;
    }

    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobTypeText() {
        return jobTypeText;
    }

    public void setJobTypeText(String jobTypeText) {
        this.jobTypeText = jobTypeText;
    }

    public String getJobProgram() {
        return jobProgram;
    }

    public void setJobProgram(String jobProgram) {
        this.jobProgram = jobProgram;
    }

    public String getProgramManager() {
        return programManager;
    }

    public void setProgramManager(String programManager) {
        this.programManager = programManager;
    }

    public Double getBudgetEUN() {
        return budgetEUN;
    }

    public void setBudgetEUN(Double budgetEUN) {
        this.budgetEUN = budgetEUN;
    }

    public Double getFundingEUN() {
        return fundingEUN;
    }

    public void setFundingEUN(Double fundingEUN) {
        this.fundingEUN = fundingEUN;
    }

    public Double getFundingRate() {
        return fundingRate;
    }

    public void setFundingRate(Double fundingRate) {
        this.fundingRate = fundingRate;
    }

    public Double getBudgetConsortium() {
        return budgetConsortium;
    }

    public void setBudgetConsortium(Double budgetConsortium) {
        this.budgetConsortium = budgetConsortium;
    }

    public Double getFundingConsortium() {
        return fundingConsortium;
    }

    public void setFundingConsortium(Double fundingConsortium) {
        this.fundingConsortium = fundingConsortium;
    }

    public Double getOverheadPerc() {
        return overheadPerc;
    }

    public void setOverheadPerc(Double overheadPerc) {
        this.overheadPerc = overheadPerc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobInfoContentFromERPDTO)) {
            return false;
        }

        JobInfoContentFromERPDTO jobInfoDTO = (JobInfoContentFromERPDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jobInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobInfoDTO{" +
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
