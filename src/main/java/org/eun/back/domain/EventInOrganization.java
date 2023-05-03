package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A EventInOrganization.
 */
@Entity
@Table(name = "event_in_organization")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventInOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "eventInOrganization")
    @JsonIgnoreProperties(value = { "eventInOrganization", "eventParticipant" }, allowSetters = true)
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "eventInOrganization")
    @JsonIgnoreProperties(
        value = { "countries", "eventInOrganization", "organizationInMinistry", "organizationInProject", "personInOrganization" },
        allowSetters = true
    )
    private Set<Organization> organizations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventInOrganization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.setEventInOrganization(null));
        }
        if (events != null) {
            events.forEach(i -> i.setEventInOrganization(this));
        }
        this.events = events;
    }

    public EventInOrganization events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public EventInOrganization addEvent(Event event) {
        this.events.add(event);
        event.setEventInOrganization(this);
        return this;
    }

    public EventInOrganization removeEvent(Event event) {
        this.events.remove(event);
        event.setEventInOrganization(null);
        return this;
    }

    public Set<Organization> getOrganizations() {
        return this.organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        if (this.organizations != null) {
            this.organizations.forEach(i -> i.setEventInOrganization(null));
        }
        if (organizations != null) {
            organizations.forEach(i -> i.setEventInOrganization(this));
        }
        this.organizations = organizations;
    }

    public EventInOrganization organizations(Set<Organization> organizations) {
        this.setOrganizations(organizations);
        return this;
    }

    public EventInOrganization addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.setEventInOrganization(this);
        return this;
    }

    public EventInOrganization removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.setEventInOrganization(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventInOrganization)) {
            return false;
        }
        return id != null && id.equals(((EventInOrganization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventInOrganization{" +
            "id=" + getId() +
            "}";
    }
}
