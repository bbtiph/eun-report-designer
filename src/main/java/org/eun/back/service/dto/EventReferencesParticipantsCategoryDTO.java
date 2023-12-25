package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.EventReferencesParticipantsCategory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReferencesParticipantsCategoryDTO implements Serializable {

    private Long id;

    private String category;

    private Long participantsCount;

    private EventReferencesDTO eventReference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Long participantsCount) {
        this.participantsCount = participantsCount;
    }

    public EventReferencesDTO getEventReference() {
        return eventReference;
    }

    public void setEventReference(EventReferencesDTO eventReference) {
        this.eventReference = eventReference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReferencesParticipantsCategoryDTO)) {
            return false;
        }

        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO = (EventReferencesParticipantsCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventReferencesParticipantsCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReferencesParticipantsCategoryDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", participantsCount=" + getParticipantsCount() +
            ", eventReference=" + getEventReference() +
            "}";
    }
}
