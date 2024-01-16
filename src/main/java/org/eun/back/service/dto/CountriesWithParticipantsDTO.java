package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.Countries} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountriesWithParticipantsDTO implements Serializable {

    private Long id;

    private String countryName;

    private String countryCode;

    private Long participantsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Long participantsCount) {
        this.participantsCount = participantsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountriesWithParticipantsDTO)) {
            return false;
        }

        CountriesWithParticipantsDTO countriesDTO = (CountriesWithParticipantsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountriesDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            "}";
    }
}
