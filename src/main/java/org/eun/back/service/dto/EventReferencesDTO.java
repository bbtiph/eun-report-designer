package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link org.eun.back.domain.EventReferences} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReferencesDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private Boolean isActive;

    private Set<CountriesDTO> countries = new HashSet<>();

    private Long participantsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<CountriesDTO> getCountries() {
        return countries;
    }

    public void setCountries(Set<CountriesDTO> countries) {
        this.countries = countries;
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
        if (!(o instanceof EventReferencesDTO)) {
            return false;
        }

        EventReferencesDTO eventReferencesDTO = (EventReferencesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventReferencesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReferencesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", countries=" + getCountries() +
            "}";
    }
}
