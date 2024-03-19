package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.EventReferences;
import org.eun.back.domain.MOEParticipationReferences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MOEParticipationReferences entity.
 *
 * When extending this class, extend MOEParticipationReferencesRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface MOEParticipationReferencesRepository
    extends MOEParticipationReferencesRepositoryWithBagRelationships, JpaRepository<MOEParticipationReferences, Long> {
    List<MOEParticipationReferences> findAllByIsActive(Boolean isActive);

    List<MOEParticipationReferences> findAllByIdIn(List<Long> ids);

    default Optional<MOEParticipationReferences> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<MOEParticipationReferences> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<MOEParticipationReferences> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
