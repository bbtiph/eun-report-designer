package org.eun.back.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eun.back.domain.EventReferences;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class EventReferencesRepositoryWithBagRelationshipsImpl implements EventReferencesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EventReferences> fetchBagRelationships(Optional<EventReferences> eventReferences) {
        return eventReferences.map(this::fetchCountries);
    }

    @Override
    public Page<EventReferences> fetchBagRelationships(Page<EventReferences> eventReferences) {
        return new PageImpl<>(
            fetchBagRelationships(eventReferences.getContent()),
            eventReferences.getPageable(),
            eventReferences.getTotalElements()
        );
    }

    @Override
    public List<EventReferences> fetchBagRelationships(List<EventReferences> eventReferences) {
        return Optional.of(eventReferences).map(this::fetchCountries).orElse(Collections.emptyList());
    }

    EventReferences fetchCountries(EventReferences result) {
        return entityManager
            .createQuery(
                "select eventReferences from EventReferences eventReferences left join fetch eventReferences.countries where eventReferences is :eventReferences",
                EventReferences.class
            )
            .setParameter("eventReferences", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<EventReferences> fetchCountries(List<EventReferences> eventReferences) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, eventReferences.size()).forEach(index -> order.put(eventReferences.get(index).getId(), index));
        List<EventReferences> result = entityManager
            .createQuery(
                "select distinct eventReferences from EventReferences eventReferences left join fetch eventReferences.countries where eventReferences in :eventReferences",
                EventReferences.class
            )
            .setParameter("eventReferences", eventReferences)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
