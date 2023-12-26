package org.eun.back.domain;

import java.io.Serializable;
import java.util.Objects;

public class RelEventReferencesCountriesId implements Serializable {

    private Long countriesId;
    private Long eventReferencesId;

    public RelEventReferencesCountriesId() {}

    public RelEventReferencesCountriesId(Long countriesId, Long eventReferencesId) {
        this.countriesId = countriesId;
        this.eventReferencesId = eventReferencesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelEventReferencesCountriesId that = (RelEventReferencesCountriesId) o;
        return Objects.equals(countriesId, that.countriesId) && Objects.equals(eventReferencesId, that.eventReferencesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countriesId, eventReferencesId);
    }
}
