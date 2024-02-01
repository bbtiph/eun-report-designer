package org.eun.back.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect(
    "SELECT " +
    "j.id AS id, " +
    "j.job_number AS job_number, " +
    "j.description AS description, " +
    "j.budget_eun AS budget_eun, " +
    "j.funding_eun AS funding_eun, " +
    "j.funding_rate AS funding_rate, " +
    "(j.budget_eun + j.budget_consortium) AS total_budget, " +
    "(j.funding_eun + j.funding_consortium) AS total_funding, " +
    "j.overhead_perc AS overhead_perc " +
    "FROM public.job_info j where j.status_proposal = 'Approved'"
)
public class FundingAndBudget implements Serializable {

    @Id
    private Long id;

    @Column(name = "budget_eun")
    private Double budgetEUN;

    @Column(name = "funding_eun")
    private Double fundingEUN;

    @Column(name = "job_number")
    private String jobNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "funding_rate")
    private Double fundingRate;

    @Column(name = "total_budget")
    private Double totalBudget;

    @Column(name = "total_funding")
    private Double totalFunding;

    @Column(name = "overhead_perc")
    private Integer overheadPerc;

    public FundingAndBudget(
        Long id,
        Double budgetEUN,
        Double fundingEUN,
        String jobNumber,
        String description,
        Double fundingRate,
        Double totalBudget,
        Double totalFunding,
        Integer overheadPerc
    ) {
        this.id = id;
        this.budgetEUN = budgetEUN;
        this.fundingEUN = fundingEUN;
        this.jobNumber = jobNumber;
        this.description = description;
        this.fundingRate = fundingRate;
        this.totalBudget = totalBudget;
        this.totalFunding = totalFunding;
        this.overheadPerc = overheadPerc;
    }

    public FundingAndBudget() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Double getTotalFunding() {
        return totalFunding;
    }

    public void setTotalFunding(Double totalFunding) {
        this.totalFunding = totalFunding;
    }

    public Integer getOverheadPerc() {
        return overheadPerc;
    }

    public void setOverheadPerc(Integer overheadPerc) {
        this.overheadPerc = overheadPerc;
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

    @Override
    public String toString() {
        return (
            "FundingAndBudget{" +
            "id=" +
            id +
            ", budgetEUN=" +
            budgetEUN +
            ", fundingEUN=" +
            fundingEUN +
            ", fundingRate=" +
            fundingRate +
            ", totalBudget=" +
            totalBudget +
            ", totalFunding=" +
            totalFunding +
            ", overheadPerc=" +
            overheadPerc +
            '}'
        );
    }
}
