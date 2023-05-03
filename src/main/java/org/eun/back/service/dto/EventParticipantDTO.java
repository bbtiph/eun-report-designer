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

    private EventDTO event;

    private PersonDTO person;

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

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
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
            ", event=" + getEvent() +
            ", person=" + getPerson() +
            "}";
    }
}
