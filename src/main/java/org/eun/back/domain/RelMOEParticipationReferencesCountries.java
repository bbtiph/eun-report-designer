package org.eun.back.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "rel_moe_participation_references_countries")
@IdClass(RelMOEParticipationReferencesCountriesId.class)
public class RelMOEParticipationReferencesCountries implements Serializable {

    @Id
    @Column(name = "countries_id")
    private Long countriesId;

    @Id
    @Column(name = "moe_participation_references_id")
    private Long moeParticipationReferencesId;

    @Column(name = "moe_representatives")
    private Long moeRepresentatives;

    @Column(name = "type")
    private String type;

    @Column(name = "is_active")
    private Boolean isActive;

    public RelMOEParticipationReferencesCountries() {}

    public RelMOEParticipationReferencesCountries(
        Long countriesId,
        Long moeParticipationReferencesId,
        Long moeRepresentatives,
        String type,
        Boolean isActive
    ) {
        this.countriesId = countriesId;
        this.moeParticipationReferencesId = moeParticipationReferencesId;
        this.moeRepresentatives = moeRepresentatives;
        this.type = type;
        this.isActive = isActive;
    }

    public Long getCountriesId() {
        return countriesId;
    }

    public void setCountriesId(Long countriesId) {
        this.countriesId = countriesId;
    }

    public Long getMoeParticipationReferencesId() {
        return moeParticipationReferencesId;
    }

    public void setMoeParticipationReferencesId(Long moeParticipationReferencesId) {
        this.moeParticipationReferencesId = moeParticipationReferencesId;
    }

    public Long getMoeRepresentatives() {
        return moeRepresentatives;
    }

    public void setMoeRepresentatives(Long moeRepresentatives) {
        this.moeRepresentatives = moeRepresentatives;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return (
            "RelMOEParticipationReferencesCountries{" +
            "countriesId=" +
            countriesId +
            ", moeParticipationReferencesId=" +
            moeParticipationReferencesId +
            ", moeRepresentatives=" +
            moeRepresentatives +
            ", type='" +
            type +
            '\'' +
            ", isActive=" +
            isActive +
            '}'
        );
    }
}
