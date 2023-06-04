package org.eun.back.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eun.back.domain.ReportBlocks;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ReportBlocksRepositoryWithBagRelationshipsImpl implements ReportBlocksRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ReportBlocks> fetchBagRelationships(Optional<ReportBlocks> reportBlocks) {
        return reportBlocks.map(this::fetchCountryIds);
    }

    @Override
    public Page<ReportBlocks> fetchBagRelationships(Page<ReportBlocks> reportBlocks) {
        return new PageImpl<>(
            fetchBagRelationships(reportBlocks.getContent()),
            reportBlocks.getPageable(),
            reportBlocks.getTotalElements()
        );
    }

    @Override
    public List<ReportBlocks> fetchBagRelationships(List<ReportBlocks> reportBlocks) {
        return Optional.of(reportBlocks).map(this::fetchCountryIds).orElse(Collections.emptyList());
    }

    ReportBlocks fetchCountryIds(ReportBlocks result) {
        return entityManager
            .createQuery(
                "select reportBlocks from ReportBlocks reportBlocks left join fetch reportBlocks.countryIds where reportBlocks is :reportBlocks",
                ReportBlocks.class
            )
            .setParameter("reportBlocks", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ReportBlocks> fetchCountryIds(List<ReportBlocks> reportBlocks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, reportBlocks.size()).forEach(index -> order.put(reportBlocks.get(index).getId(), index));
        List<ReportBlocks> result = entityManager
            .createQuery(
                "select distinct reportBlocks from ReportBlocks reportBlocks left join fetch reportBlocks.countryIds where reportBlocks in :reportBlocks",
                ReportBlocks.class
            )
            .setParameter("reportBlocks", reportBlocks)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
