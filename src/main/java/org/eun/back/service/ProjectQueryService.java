package org.eun.back.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.eun.back.domain.*; // for static metamodels
import org.eun.back.domain.Project;
import org.eun.back.repository.ProjectRepository;
import org.eun.back.service.criteria.ProjectCriteria;
import org.eun.back.service.dto.ProjectDTO;
import org.eun.back.service.mapper.ProjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Project} entities in the database.
 * The main input is a {@link ProjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectDTO} or a {@link Page} of {@link ProjectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectQueryService extends QueryService<Project> {

    private final Logger log = LoggerFactory.getLogger(ProjectQueryService.class);

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectQueryService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * Return a {@link List} of {@link ProjectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectDTO> findByCriteria(ProjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Project> specification = createSpecification(criteria);
        return projectMapper.toDto(projectRepository.findAll((Sort) specification));
    }

    /**
     * Return a {@link Page} of {@link ProjectDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    //    @Transactional(readOnly = true)
    //    public Page<ProjectDTO> findByCriteria(ProjectCriteria criteria, Pageable page) {
    //        log.debug("find by criteria : {}, page: {}", criteria, page);
    //        final Specification<Project> specification = createSpecification(criteria);
    //        return projectRepository.findAll(specification, page).map(projectMapper::toDto);
    //    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    //    @Transactional(readOnly = true)
    //    public long countByCriteria(ProjectCriteria criteria) {
    //        log.debug("count by criteria : {}", criteria);
    //        final Specification<Project> specification = createSpecification(criteria);
    //        return projectRepository.count(specification);
    //    }

    /**
     * Function to convert {@link ProjectCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Project> createSpecification(ProjectCriteria criteria) {
        Specification<Project> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Project_.id));
            }
            //            if (criteria.getStatus() != null) {
            //                specification = specification.and(buildStringSpecification(criteria.getStatus(), Project_.status));
            //            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Project_.name));
            }
            if (criteria.getAcronym() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcronym(), Project_.acronym));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Project_.description));
            }
            if (criteria.getContactEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmail(), Project_.contactEmail));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), Project_.contactName));
            }
            if (criteria.getTotal_budget() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal_budget(), Project_.totalBudget));
            }
            if (criteria.getTotal_funding() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal_funding(), Project_.totalFunding));
            }
            if (criteria.getTotal_budget_for_eun() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal_budget_for_eun(), Project_.totalBudgetForEun));
            }
            if (criteria.getTotal_funding_for_eun() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotal_funding_for_eun(), Project_.totalFundingForEun));
            }
            if (criteria.getFunding_value() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFunding_value(), Project_.fundingValue));
            }
            if (criteria.getPercentage_of_funding() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPercentage_of_funding(), Project_.percentageOfFunding));
            }
            if (criteria.getPercentage_of_indirect_costs() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getPercentage_of_indirect_costs(), Project_.percentageOfIndirectCosts)
                    );
            }
            if (criteria.getPercentage_of_funding_for_eun() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getPercentage_of_funding_for_eun(), Project_.percentageOfFundingForEun)
                    );
            }
            if (criteria.getPercentage_of_indirect_costs_for_eun() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getPercentage_of_indirect_costs_for_eun(),
                            Project_.percentageOfIndirectCostsForEun
                        )
                    );
            }
            if (criteria.getFunding_extra_comment() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFunding_extra_comment(), Project_.fundingExtraComment));
            }
            if (criteria.getProgramme() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProgramme(), Project_.programme));
            }
            if (criteria.getEu_dg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEu_dg(), Project_.euDg));
            }
            if (criteria.getRole_of_eun() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole_of_eun(), Project_.roleOfEun));
            }
            if (criteria.getSummary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSummary(), Project_.summary));
            }
            if (criteria.getMain_tasks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMain_tasks(), Project_.mainTasks));
            }
            if (criteria.getExpected_outcomes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpected_outcomes(), Project_.expectedOutcomes));
            }
            if (criteria.getEu_call_reference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEu_call_reference(), Project_.euCallReference));
            }
            if (criteria.getEu_project_reference() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getEu_project_reference(), Project_.euProjectReference));
            }
            if (criteria.getEu_call_deadline() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEu_call_deadline(), Project_.euCallDeadline));
            }
            if (criteria.getProject_manager() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProject_manager(), Project_.projectManager));
            }
            if (criteria.getReference_number_of_project() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getReference_number_of_project(), Project_.referenceNumberOfProject)
                    );
            }
            if (criteria.getEun_team() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEun_team(), Project_.eunTeam));
            }
            if (criteria.getFunding_id() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFunding_id(), Project_.fundingValue));
            }
        }
        return specification;
    }
}
