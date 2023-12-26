package org.eun.back.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "rel_event_references_countries")
@IdClass(RelEventReferencesCountriesId.class)
public class RelEventReferencesCountries implements Serializable {

    @Id
    @Column(name = "countries_id")
    private Long countriesId;

    @Id
    @Column(name = "event_references_id")
    private Long eventReferencesId;

    @Column(name = "participants_count")
    private Long participantsCount;

    public RelEventReferencesCountries() {}

    public RelEventReferencesCountries(Long countriesId, Long eventReferencesId, Long participantsCount) {
        this.countriesId = countriesId;
        this.eventReferencesId = eventReferencesId;
        this.participantsCount = participantsCount;
    }

    public Long getCountriesId() {
        return countriesId;
    }

    public void setCountriesId(Long countriesId) {
        this.countriesId = countriesId;
    }

    public Long getEventReferencesId() {
        return eventReferencesId;
    }

    public void setEventReferencesId(Long eventReferencesId) {
        this.eventReferencesId = eventReferencesId;
    }

    public Long getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Long participantsCount) {
        this.participantsCount = participantsCount;
    }

    @Override
    public String toString() {
        return (
            "RelEventReferencesCountries{" +
            "countriesId=" +
            countriesId +
            ", eventReferencesId=" +
            eventReferencesId +
            ", participantsCount=" +
            participantsCount +
            '}'
        );
    }
}
