package org.eun.back.domain;

import java.io.Serializable;
import java.util.Objects;

public class RelMOEParticipationReferencesCountriesId implements Serializable {

    private Long countriesId;
    private Long moeParticipationReferencesId;

    public RelMOEParticipationReferencesCountriesId() {}

    public RelMOEParticipationReferencesCountriesId(Long countriesId, Long moeParticipationReferencesId) {
        this.countriesId = countriesId;
        this.moeParticipationReferencesId = moeParticipationReferencesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelMOEParticipationReferencesCountriesId that = (RelMOEParticipationReferencesCountriesId) o;
        return (
            Objects.equals(countriesId, that.countriesId) && Objects.equals(moeParticipationReferencesId, that.moeParticipationReferencesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(countriesId, moeParticipationReferencesId);
    }
}
