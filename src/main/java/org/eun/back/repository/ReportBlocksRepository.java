package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.ReportBlocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReportBlocks entity.
 *
 * When extending this class, extend ReportBlocksRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ReportBlocksRepository extends ReportBlocksRepositoryWithBagRelationships, JpaRepository<ReportBlocks, Long> {
    default Optional<ReportBlocks> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ReportBlocks> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ReportBlocks> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
