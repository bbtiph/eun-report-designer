package org.eun.back.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eun.back.domain.MOEParticipationReferences;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MOEParticipationReferencesRepositoryWithBagRelationshipsImpl
    implements MOEParticipationReferencesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MOEParticipationReferences> fetchBagRelationships(Optional<MOEParticipationReferences> mOEParticipationReferences) {
        return mOEParticipationReferences.map(this::fetchCountries);
    }

    @Override
    public Page<MOEParticipationReferences> fetchBagRelationships(Page<MOEParticipationReferences> mOEParticipationReferences) {
        return new PageImpl<>(
            fetchBagRelationships(mOEParticipationReferences.getContent()),
            mOEParticipationReferences.getPageable(),
            mOEParticipationReferences.getTotalElements()
        );
    }

    @Override
    public List<MOEParticipationReferences> fetchBagRelationships(List<MOEParticipationReferences> mOEParticipationReferences) {
        return Optional.of(mOEParticipationReferences).map(this::fetchCountries).orElse(Collections.emptyList());
    }

    MOEParticipationReferences fetchCountries(MOEParticipationReferences result) {
        return entityManager
            .createQuery(
                "select mOEParticipationReferences from MOEParticipationReferences mOEParticipationReferences left join fetch mOEParticipationReferences.countries where mOEParticipationReferences is :mOEParticipationReferences",
                MOEParticipationReferences.class
            )
            .setParameter("mOEParticipationReferences", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<MOEParticipationReferences> fetchCountries(List<MOEParticipationReferences> mOEParticipationReferences) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream
            .range(0, mOEParticipationReferences.size())
            .forEach(index -> order.put(mOEParticipationReferences.get(index).getId(), index));
        List<MOEParticipationReferences> result = entityManager
            .createQuery(
                "select distinct mOEParticipationReferences from MOEParticipationReferences mOEParticipationReferences left join fetch mOEParticipationReferences.countries where mOEParticipationReferences in :mOEParticipationReferences",
                MOEParticipationReferences.class
            )
            .setParameter("mOEParticipationReferences", mOEParticipationReferences)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
