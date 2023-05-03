package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A EventParticipant.
 */
@Entity
@Table(name = "event_participant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "eventParticipant")
    @JsonIgnoreProperties(value = { "eventInOrganization", "eventParticipant" }, allowSetters = true)
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "eventParticipant")
    @JsonIgnoreProperties(
        value = { "countries", "eunTeamMember", "eventParticipant", "personInOrganization", "personInProject" },
        allowSetters = true
    )
    private Set<Person> people = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventParticipant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public EventParticipant type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Event> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.setEventParticipant(null));
        }
        if (events != null) {
            events.forEach(i -> i.setEventParticipant(this));
        }
        this.events = events;
    }

    public EventParticipant events(Set<Event> events) {
        this.setEvents(events);
        return this;
    }

    public EventParticipant addEvent(Event event) {
        this.events.add(event);
        event.setEventParticipant(this);
        return this;
    }

    public EventParticipant removeEvent(Event event) {
        this.events.remove(event);
        event.setEventParticipant(null);
        return this;
    }

    public Set<Person> getPeople() {
        return this.people;
    }

    public void setPeople(Set<Person> people) {
        if (this.people != null) {
            this.people.forEach(i -> i.setEventParticipant(null));
        }
        if (people != null) {
            people.forEach(i -> i.setEventParticipant(this));
        }
        this.people = people;
    }

    public EventParticipant people(Set<Person> people) {
        this.setPeople(people);
        return this;
    }

    public EventParticipant addPerson(Person person) {
        this.people.add(person);
        person.setEventParticipant(this);
        return this;
    }

    public EventParticipant removePerson(Person person) {
        this.people.remove(person);
        person.setEventParticipant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventParticipant)) {
            return false;
        }
        return id != null && id.equals(((EventParticipant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventParticipant{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
