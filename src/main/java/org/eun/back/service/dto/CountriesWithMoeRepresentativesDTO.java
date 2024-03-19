package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.Countries} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CountriesWithMoeRepresentativesDTO implements Serializable {

    private Long id;

    private String countryName;

    private String countryCode;

    private Long moeRepresentatives;

    private String type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountriesWithMoeRepresentativesDTO)) {
            return false;
        }

        CountriesWithMoeRepresentativesDTO countriesDTO = (CountriesWithMoeRepresentativesDTO) o;
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
