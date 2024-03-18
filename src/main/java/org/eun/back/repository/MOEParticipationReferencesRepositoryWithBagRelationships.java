package org.eun.back.repository;

import java.util.List;
import java.util.Optional;
import org.eun.back.domain.MOEParticipationReferences;
import org.springframework.data.domain.Page;

public interface MOEParticipationReferencesRepositoryWithBagRelationships {
    Optional<MOEParticipationReferences> fetchBagRelationships(Optional<MOEParticipationReferences> mOEParticipationReferences);

    List<MOEParticipationReferences> fetchBagRelationships(List<MOEParticipationReferences> mOEParticipationReferences);

    Page<MOEParticipationReferences> fetchBagRelationships(Page<MOEParticipationReferences> mOEParticipationReferences);
}
