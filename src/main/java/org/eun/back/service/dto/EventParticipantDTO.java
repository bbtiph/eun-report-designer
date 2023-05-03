package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.EventParticipant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventParticipantDTO implements Serializable {

    private Long id;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof EventParticipantDTO)) {
            return false;
        }

        EventParticipantDTO eventParticipantDTO = (EventParticipantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventParticipantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventParticipantDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
