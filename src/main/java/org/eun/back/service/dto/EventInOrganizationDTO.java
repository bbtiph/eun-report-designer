package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.EventInOrganization} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventInOrganizationDTO implements Serializable {

    private Long id;

    private EventDTO event;

    private OrganizationDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventInOrganizationDTO)) {
            return false;
        }

        EventInOrganizationDTO eventInOrganizationDTO = (EventInOrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventInOrganizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventInOrganizationDTO{" +
            "id=" + getId() +
            ", event=" + getEvent() +
            ", organization=" + getOrganization() +
            "}";
    }
}
