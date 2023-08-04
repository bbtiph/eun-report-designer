package org.eun.back.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.eun.back.domain.*; // for static metamodels
import org.eun.back.domain.WorkingGroupReferences;
import org.eun.back.repository.WorkingGroupReferencesRepository;
import org.eun.back.service.criteria.WorkingGroupReferencesCriteria;
import org.eun.back.service.dto.WorkingGroupReferencesDTO;
import org.eun.back.service.mapper.WorkingGroupReferencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link WorkingGroupReferences} entities in the database.
 * The main input is a {@link WorkingGroupReferencesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkingGroupReferencesDTO} or a {@link Page} of {@link WorkingGroupReferencesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkingGroupReferencesQueryService extends QueryService<WorkingGroupReferences> {

    private final Logger log = LoggerFactory.getLogger(WorkingGroupReferencesQueryService.class);

    private final WorkingGroupReferencesRepository workingGroupReferencesRepository;

    private final WorkingGroupReferencesMapper workingGroupReferencesMapper;

    public WorkingGroupReferencesQueryService(
        WorkingGroupReferencesRepository workingGroupReferencesRepository,
        WorkingGroupReferencesMapper workingGroupReferencesMapper
    ) {
        this.workingGroupReferencesRepository = workingGroupReferencesRepository;
        this.workingGroupReferencesMapper = workingGroupReferencesMapper;
    }

    /**
     * Return a {@link List} of {@link WorkingGroupReferencesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkingGroupReferencesDTO> findByCriteria(WorkingGroupReferencesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkingGroupReferences> specification = createSpecification(criteria);
        return workingGroupReferencesMapper.toDto(workingGroupReferencesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkingGroupReferencesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkingGroupReferencesDTO> findByCriteria(WorkingGroupReferencesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkingGroupReferences> specification = createSpecification(criteria);
        return workingGroupReferencesRepository.findAll(specification, page).map(workingGroupReferencesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkingGroupReferencesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkingGroupReferences> specification = createSpecification(criteria);
        return workingGroupReferencesRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkingGroupReferencesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkingGroupReferences> createSpecification(WorkingGroupReferencesCriteria criteria) {
        Specification<WorkingGroupReferences> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkingGroupReferences_.id));
            }
            if (criteria.getCountryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryCode(), WorkingGroupReferences_.countryCode));
            }
            if (criteria.getCountryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryName(), WorkingGroupReferences_.countryName));
            }
            if (criteria.getCountryRepresentativeFirstName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCountryRepresentativeFirstName(),
                            WorkingGroupReferences_.countryRepresentativeFirstName
                        )
                    );
            }
            if (criteria.getCountryRepresentativeLastName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCountryRepresentativeLastName(),
                            WorkingGroupReferences_.countryRepresentativeLastName
                        )
                    );
            }
            if (criteria.getCountryRepresentativeMail() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCountryRepresentativeMail(), WorkingGroupReferences_.countryRepresentativeMail)
                    );
            }
            if (criteria.getCountryRepresentativePosition() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCountryRepresentativePosition(),
                            WorkingGroupReferences_.countryRepresentativePosition
                        )
                    );
            }
            if (criteria.getCountryRepresentativeStartDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getCountryRepresentativeStartDate(),
                            WorkingGroupReferences_.countryRepresentativeStartDate
                        )
                    );
            }
            if (criteria.getCountryRepresentativeEndDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getCountryRepresentativeEndDate(),
                            WorkingGroupReferences_.countryRepresentativeEndDate
                        )
                    );
            }
            if (criteria.getCountryRepresentativeMinistry() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCountryRepresentativeMinistry(),
                            WorkingGroupReferences_.countryRepresentativeMinistry
                        )
                    );
            }
            if (criteria.getCountryRepresentativeDepartment() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(
                            criteria.getCountryRepresentativeDepartment(),
                            WorkingGroupReferences_.countryRepresentativeDepartment
                        )
                    );
            }
            if (criteria.getContactEunFirstName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getContactEunFirstName(), WorkingGroupReferences_.contactEunFirstName)
                    );
            }
            if (criteria.getContactEunLastName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getContactEunLastName(), WorkingGroupReferences_.contactEunLastName)
                    );
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), WorkingGroupReferences_.type));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), WorkingGroupReferences_.isActive));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), WorkingGroupReferences_.createdBy));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), WorkingGroupReferences_.lastModifiedBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), WorkingGroupReferences_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), WorkingGroupReferences_.lastModifiedDate));
            }
        }
        return specification;
    }
}
