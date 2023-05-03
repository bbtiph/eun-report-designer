package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "eventInOrganizations", "eventParticipants" }, allowSetters = true)
    private Event event;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "eunTeamMembers", "eventParticipants", "personInOrganizations", "personInProjects", "country" },
        allowSetters = true
    )
    private Person person;

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

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventParticipant event(Event event) {
        this.setEvent(event);
        return this;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public EventParticipant person(Person person) {
        this.setPerson(person);
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
