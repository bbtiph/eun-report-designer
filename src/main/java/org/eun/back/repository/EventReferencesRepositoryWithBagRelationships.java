package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.EventReferences;
import org.springframework.data.domain.Page;

public interface EventReferencesRepositoryWithBagRelationships {
    Optional<EventReferences> fetchBagRelationships(Optional<EventReferences> eventReferences);

    List<EventReferences> fetchBagRelationships(List<EventReferences> eventReferences);

    Page<EventReferences> fetchBagRelationships(Page<EventReferences> eventReferences);
}
