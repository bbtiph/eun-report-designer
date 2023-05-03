package org.eun.back.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link org.eun.back.domain.Project} entity. This class is used
 * in {@link org.eun.back.web.rest.ProjectResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter status;

    private StringFilter name;

    private StringFilter acronym;

    private StringFilter description;

    private StringFilter contactEmail;

    private StringFilter contactName;

    private LongFilter total_budget;

    private LongFilter total_funding;

    private LongFilter total_budget_for_eun;

    private LongFilter total_funding_for_eun;

    private LongFilter funding_value;

    private LongFilter percentage_of_funding;

    private LongFilter percentage_of_indirect_costs;

    private LongFilter percentage_of_funding_for_eun;

    private LongFilter percentage_of_indirect_costs_for_eun;

    private StringFilter funding_extra_comment;

    private StringFilter programme;

    private StringFilter eu_dg;

    private StringFilter role_of_eun;

    private StringFilter summary;

    private StringFilter main_tasks;

    private StringFilter expected_outcomes;

    private StringFilter eu_call_reference;

    private StringFilter eu_project_reference;

    private StringFilter eu_call_deadline;

    private StringFilter project_manager;

    private IntegerFilter reference_number_of_project;

    private StringFilter eun_team;

    private LongFilter funding_id;

    private Boolean distinct;

    public ProjectCriteria() {}

    public ProjectCriteria(ProjectCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.acronym = other.acronym == null ? null : other.acronym.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.contactEmail = other.contactEmail == null ? null : other.contactEmail.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.total_budget = other.total_budget == null ? null : other.total_budget.copy();
        this.total_funding = other.total_funding == null ? null : other.total_funding.copy();
        this.total_budget_for_eun = other.total_budget_for_eun == null ? null : other.total_budget_for_eun.copy();
        this.total_funding_for_eun = other.total_funding_for_eun == null ? null : other.total_funding_for_eun.copy();
        this.funding_value = other.funding_value == null ? null : other.funding_value.copy();
        this.percentage_of_funding = other.percentage_of_funding == null ? null : other.percentage_of_funding.copy();
        this.percentage_of_indirect_costs = other.percentage_of_indirect_costs == null ? null : other.percentage_of_indirect_costs.copy();
        this.percentage_of_funding_for_eun =
            other.percentage_of_funding_for_eun == null ? null : other.percentage_of_funding_for_eun.copy();
        this.percentage_of_indirect_costs_for_eun =
            other.percentage_of_indirect_costs_for_eun == null ? null : other.percentage_of_indirect_costs_for_eun.copy();
        this.funding_extra_comment = other.funding_extra_comment == null ? null : other.funding_extra_comment.copy();
        this.programme = other.programme == null ? null : other.programme.copy();
        this.eu_dg = other.eu_dg == null ? null : other.eu_dg.copy();
        this.role_of_eun = other.role_of_eun == null ? null : other.role_of_eun.copy();
        this.summary = other.summary == null ? null : other.summary.copy();
        this.main_tasks = other.main_tasks == null ? null : other.main_tasks.copy();
        this.expected_outcomes = other.expected_outcomes == null ? null : other.expected_outcomes.copy();
        this.eu_call_reference = other.eu_call_reference == null ? null : other.eu_call_reference.copy();
        this.eu_project_reference = other.eu_project_reference == null ? null : other.eu_project_reference.copy();
        this.eu_call_deadline = other.eu_call_deadline == null ? null : other.eu_call_deadline.copy();
        this.project_manager = other.project_manager == null ? null : other.project_manager.copy();
        this.reference_number_of_project = other.reference_number_of_project == null ? null : other.reference_number_of_project.copy();
        this.eun_team = other.eun_team == null ? null : other.eun_team.copy();
        this.funding_id = other.funding_id == null ? null : other.funding_id.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectCriteria copy() {
        return new ProjectCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAcronym() {
        return acronym;
    }

    public StringFilter acronym() {
        if (acronym == null) {
            acronym = new StringFilter();
        }
        return acronym;
    }

    public void setAcronym(StringFilter acronym) {
        this.acronym = acronym;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getContactEmail() {
        return contactEmail;
    }

    public StringFilter contactEmail() {
        if (contactEmail == null) {
            contactEmail = new StringFilter();
        }
        return contactEmail;
    }

    public void setContactEmail(StringFilter contactEmail) {
        this.contactEmail = contactEmail;
    }

    public StringFilter getContactName() {
        return contactName;
    }

    public StringFilter contactName() {
        if (contactName == null) {
            contactName = new StringFilter();
        }
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public LongFilter getTotal_budget() {
        return total_budget;
    }

    public LongFilter total_budget() {
        if (total_budget == null) {
            total_budget = new LongFilter();
        }
        return total_budget;
    }

    public void setTotal_budget(LongFilter total_budget) {
        this.total_budget = total_budget;
    }

    public LongFilter getTotal_funding() {
        return total_funding;
    }

    public LongFilter total_funding() {
        if (total_funding == null) {
            total_funding = new LongFilter();
        }
        return total_funding;
    }

    public void setTotal_funding(LongFilter total_funding) {
        this.total_funding = total_funding;
    }

    public LongFilter getTotal_budget_for_eun() {
        return total_budget_for_eun;
    }

    public LongFilter total_budget_for_eun() {
        if (total_budget_for_eun == null) {
            total_budget_for_eun = new LongFilter();
        }
        return total_budget_for_eun;
    }

    public void setTotal_budget_for_eun(LongFilter total_budget_for_eun) {
        this.total_budget_for_eun = total_budget_for_eun;
    }

    public LongFilter getTotal_funding_for_eun() {
        return total_funding_for_eun;
    }

    public LongFilter total_funding_for_eun() {
        if (total_funding_for_eun == null) {
            total_funding_for_eun = new LongFilter();
        }
        return total_funding_for_eun;
    }

    public void setTotal_funding_for_eun(LongFilter total_funding_for_eun) {
        this.total_funding_for_eun = total_funding_for_eun;
    }

    public LongFilter getFunding_value() {
        return funding_value;
    }

    public LongFilter funding_value() {
        if (funding_value == null) {
            funding_value = new LongFilter();
        }
        return funding_value;
    }

    public void setFunding_value(LongFilter funding_value) {
        this.funding_value = funding_value;
    }

    public LongFilter getPercentage_of_funding() {
        return percentage_of_funding;
    }

    public LongFilter percentage_of_funding() {
        if (percentage_of_funding == null) {
            percentage_of_funding = new LongFilter();
        }
        return percentage_of_funding;
    }

    public void setPercentage_of_funding(LongFilter percentage_of_funding) {
        this.percentage_of_funding = percentage_of_funding;
    }

    public LongFilter getPercentage_of_indirect_costs() {
        return percentage_of_indirect_costs;
    }

    public LongFilter percentage_of_indirect_costs() {
        if (percentage_of_indirect_costs == null) {
            percentage_of_indirect_costs = new LongFilter();
        }
        return percentage_of_indirect_costs;
    }

    public void setPercentage_of_indirect_costs(LongFilter percentage_of_indirect_costs) {
        this.percentage_of_indirect_costs = percentage_of_indirect_costs;
    }

    public LongFilter getPercentage_of_funding_for_eun() {
        return percentage_of_funding_for_eun;
    }

    public LongFilter percentage_of_funding_for_eun() {
        if (percentage_of_funding_for_eun == null) {
            percentage_of_funding_for_eun = new LongFilter();
        }
        return percentage_of_funding_for_eun;
    }

    public void setPercentage_of_funding_for_eun(LongFilter percentage_of_funding_for_eun) {
        this.percentage_of_funding_for_eun = percentage_of_funding_for_eun;
    }

    public LongFilter getPercentage_of_indirect_costs_for_eun() {
        return percentage_of_indirect_costs_for_eun;
    }

    public LongFilter percentage_of_indirect_costs_for_eun() {
        if (percentage_of_indirect_costs_for_eun == null) {
            percentage_of_indirect_costs_for_eun = new LongFilter();
        }
        return percentage_of_indirect_costs_for_eun;
    }

    public void setPercentage_of_indirect_costs_for_eun(LongFilter percentage_of_indirect_costs_for_eun) {
        this.percentage_of_indirect_costs_for_eun = percentage_of_indirect_costs_for_eun;
    }

    public StringFilter getFunding_extra_comment() {
        return funding_extra_comment;
    }

    public StringFilter funding_extra_comment() {
        if (funding_extra_comment == null) {
            funding_extra_comment = new StringFilter();
        }
        return funding_extra_comment;
    }

    public void setFunding_extra_comment(StringFilter funding_extra_comment) {
        this.funding_extra_comment = funding_extra_comment;
    }

    public StringFilter getProgramme() {
        return programme;
    }

    public StringFilter programme() {
        if (programme == null) {
            programme = new StringFilter();
        }
        return programme;
    }

    public void setProgramme(StringFilter programme) {
        this.programme = programme;
    }

    public StringFilter getEu_dg() {
        return eu_dg;
    }

    public StringFilter eu_dg() {
        if (eu_dg == null) {
            eu_dg = new StringFilter();
        }
        return eu_dg;
    }

    public void setEu_dg(StringFilter eu_dg) {
        this.eu_dg = eu_dg;
    }

    public StringFilter getRole_of_eun() {
        return role_of_eun;
    }

    public StringFilter role_of_eun() {
        if (role_of_eun == null) {
            role_of_eun = new StringFilter();
        }
        return role_of_eun;
    }

    public void setRole_of_eun(StringFilter role_of_eun) {
        this.role_of_eun = role_of_eun;
    }

    public StringFilter getSummary() {
        return summary;
    }

    public StringFilter summary() {
        if (summary == null) {
            summary = new StringFilter();
        }
        return summary;
    }

    public void setSummary(StringFilter summary) {
        this.summary = summary;
    }

    public StringFilter getMain_tasks() {
        return main_tasks;
    }

    public StringFilter main_tasks() {
        if (main_tasks == null) {
            main_tasks = new StringFilter();
        }
        return main_tasks;
    }

    public void setMain_tasks(StringFilter main_tasks) {
        this.main_tasks = main_tasks;
    }

    public StringFilter getExpected_outcomes() {
        return expected_outcomes;
    }

    public StringFilter expected_outcomes() {
        if (expected_outcomes == null) {
            expected_outcomes = new StringFilter();
        }
        return expected_outcomes;
    }

    public void setExpected_outcomes(StringFilter expected_outcomes) {
        this.expected_outcomes = expected_outcomes;
    }

    public StringFilter getEu_call_reference() {
        return eu_call_reference;
    }

    public StringFilter eu_call_reference() {
        if (eu_call_reference == null) {
            eu_call_reference = new StringFilter();
        }
        return eu_call_reference;
    }

    public void setEu_call_reference(StringFilter eu_call_reference) {
        this.eu_call_reference = eu_call_reference;
    }

    public StringFilter getEu_project_reference() {
        return eu_project_reference;
    }

    public StringFilter eu_project_reference() {
        if (eu_project_reference == null) {
            eu_project_reference = new StringFilter();
        }
        return eu_project_reference;
    }

    public void setEu_project_reference(StringFilter eu_project_reference) {
        this.eu_project_reference = eu_project_reference;
    }

    public StringFilter getEu_call_deadline() {
        return eu_call_deadline;
    }

    public StringFilter eu_call_deadline() {
        if (eu_call_deadline == null) {
            eu_call_deadline = new StringFilter();
        }
        return eu_call_deadline;
    }

    public void setEu_call_deadline(StringFilter eu_call_deadline) {
        this.eu_call_deadline = eu_call_deadline;
    }

    public StringFilter getProject_manager() {
        return project_manager;
    }

    public StringFilter project_manager() {
        if (project_manager == null) {
            project_manager = new StringFilter();
        }
        return project_manager;
    }

    public void setProject_manager(StringFilter project_manager) {
        this.project_manager = project_manager;
    }

    public IntegerFilter getReference_number_of_project() {
        return reference_number_of_project;
    }

    public IntegerFilter reference_number_of_project() {
        if (reference_number_of_project == null) {
            reference_number_of_project = new IntegerFilter();
        }
        return reference_number_of_project;
    }

    public void setReference_number_of_project(IntegerFilter reference_number_of_project) {
        this.reference_number_of_project = reference_number_of_project;
    }

    public StringFilter getEun_team() {
        return eun_team;
    }

    public StringFilter eun_team() {
        if (eun_team == null) {
            eun_team = new StringFilter();
        }
        return eun_team;
    }

    public void setEun_team(StringFilter eun_team) {
        this.eun_team = eun_team;
    }

    public LongFilter getFunding_id() {
        return funding_id;
    }

    public LongFilter funding_id() {
        if (funding_id == null) {
            funding_id = new LongFilter();
        }
        return funding_id;
    }

    public void setFunding_id(LongFilter funding_id) {
        this.funding_id = funding_id;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCriteria that = (ProjectCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(name, that.name) &&
            Objects.equals(acronym, that.acronym) &&
            Objects.equals(description, that.description) &&
            Objects.equals(contactEmail, that.contactEmail) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(total_budget, that.total_budget) &&
            Objects.equals(total_funding, that.total_funding) &&
            Objects.equals(total_budget_for_eun, that.total_budget_for_eun) &&
            Objects.equals(total_funding_for_eun, that.total_funding_for_eun) &&
            Objects.equals(funding_value, that.funding_value) &&
            Objects.equals(percentage_of_funding, that.percentage_of_funding) &&
            Objects.equals(percentage_of_indirect_costs, that.percentage_of_indirect_costs) &&
            Objects.equals(percentage_of_funding_for_eun, that.percentage_of_funding_for_eun) &&
            Objects.equals(percentage_of_indirect_costs_for_eun, that.percentage_of_indirect_costs_for_eun) &&
            Objects.equals(funding_extra_comment, that.funding_extra_comment) &&
            Objects.equals(programme, that.programme) &&
            Objects.equals(eu_dg, that.eu_dg) &&
            Objects.equals(role_of_eun, that.role_of_eun) &&
            Objects.equals(summary, that.summary) &&
            Objects.equals(main_tasks, that.main_tasks) &&
            Objects.equals(expected_outcomes, that.expected_outcomes) &&
            Objects.equals(eu_call_reference, that.eu_call_reference) &&
            Objects.equals(eu_project_reference, that.eu_project_reference) &&
            Objects.equals(eu_call_deadline, that.eu_call_deadline) &&
            Objects.equals(project_manager, that.project_manager) &&
            Objects.equals(reference_number_of_project, that.reference_number_of_project) &&
            Objects.equals(eun_team, that.eun_team) &&
            Objects.equals(funding_id, that.funding_id) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            status,
            name,
            acronym,
            description,
            contactEmail,
            contactName,
            total_budget,
            total_funding,
            total_budget_for_eun,
            total_funding_for_eun,
            funding_value,
            percentage_of_funding,
            percentage_of_indirect_costs,
            percentage_of_funding_for_eun,
            percentage_of_indirect_costs_for_eun,
            funding_extra_comment,
            programme,
            eu_dg,
            role_of_eun,
            summary,
            main_tasks,
            expected_outcomes,
            eu_call_reference,
            eu_project_reference,
            eu_call_deadline,
            project_manager,
            reference_number_of_project,
            eun_team,
            funding_id,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (acronym != null ? "acronym=" + acronym + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (contactEmail != null ? "contactEmail=" + contactEmail + ", " : "") +
            (contactName != null ? "contactName=" + contactName + ", " : "") +
            (total_budget != null ? "total_budget=" + total_budget + ", " : "") +
            (total_funding != null ? "total_funding=" + total_funding + ", " : "") +
            (total_budget_for_eun != null ? "total_budget_for_eun=" + total_budget_for_eun + ", " : "") +
            (total_funding_for_eun != null ? "total_funding_for_eun=" + total_funding_for_eun + ", " : "") +
            (funding_value != null ? "funding_value=" + funding_value + ", " : "") +
            (percentage_of_funding != null ? "percentage_of_funding=" + percentage_of_funding + ", " : "") +
            (percentage_of_indirect_costs != null ? "percentage_of_indirect_costs=" + percentage_of_indirect_costs + ", " : "") +
            (percentage_of_funding_for_eun != null ? "percentage_of_funding_for_eun=" + percentage_of_funding_for_eun + ", " : "") +
            (percentage_of_indirect_costs_for_eun != null ? "percentage_of_indirect_costs_for_eun=" + percentage_of_indirect_costs_for_eun + ", " : "") +
            (funding_extra_comment != null ? "funding_extra_comment=" + funding_extra_comment + ", " : "") +
            (programme != null ? "programme=" + programme + ", " : "") +
            (eu_dg != null ? "eu_dg=" + eu_dg + ", " : "") +
            (role_of_eun != null ? "role_of_eun=" + role_of_eun + ", " : "") +
            (summary != null ? "summary=" + summary + ", " : "") +
            (main_tasks != null ? "main_tasks=" + main_tasks + ", " : "") +
            (expected_outcomes != null ? "expected_outcomes=" + expected_outcomes + ", " : "") +
            (eu_call_reference != null ? "eu_call_reference=" + eu_call_reference + ", " : "") +
            (eu_project_reference != null ? "eu_project_reference=" + eu_project_reference + ", " : "") +
            (eu_call_deadline != null ? "eu_call_deadline=" + eu_call_deadline + ", " : "") +
            (project_manager != null ? "project_manager=" + project_manager + ", " : "") +
            (reference_number_of_project != null ? "reference_number_of_project=" + reference_number_of_project + ", " : "") +
            (eun_team != null ? "eun_team=" + eun_team + ", " : "") +
            (funding_id != null ? "funding_id=" + funding_id + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
