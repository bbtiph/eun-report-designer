package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.EventReferences;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventReferences entity.
 *
 * When extending this class, extend EventReferencesRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface EventReferencesRepository extends EventReferencesRepositoryWithBagRelationships, JpaRepository<EventReferences, Long> {
    default Optional<EventReferences> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    EventReferences findByNameAndType(String name, String type);

    List<EventReferences> findAllByIsActive(Boolean isActive);

    @Modifying
    @Query("UPDATE EventReferences e SET e.isActive = false")
    void updateAllIsActiveToFalse();

    default List<EventReferences> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<EventReferences> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
