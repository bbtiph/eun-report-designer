package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A EventReferences.
 */
@Entity
@Table(name = "event_references")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "eventReference")
    @JsonIgnoreProperties(value = { "eventReference" }, allowSetters = true)
    private Set<EventReferencesParticipantsCategory> eventReferencesParticipantsCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventReferences id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EventReferences name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public EventReferences type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<EventReferencesParticipantsCategory> getEventReferencesParticipantsCategories() {
        return this.eventReferencesParticipantsCategories;
    }

    public void setEventReferencesParticipantsCategories(Set<EventReferencesParticipantsCategory> eventReferencesParticipantsCategories) {
        if (this.eventReferencesParticipantsCategories != null) {
            this.eventReferencesParticipantsCategories.forEach(i -> i.setEventReference(null));
        }
        if (eventReferencesParticipantsCategories != null) {
            eventReferencesParticipantsCategories.forEach(i -> i.setEventReference(this));
        }
        this.eventReferencesParticipantsCategories = eventReferencesParticipantsCategories;
    }

    public EventReferences eventReferencesParticipantsCategories(
        Set<EventReferencesParticipantsCategory> eventReferencesParticipantsCategories
    ) {
        this.setEventReferencesParticipantsCategories(eventReferencesParticipantsCategories);
        return this;
    }

    public EventReferences addEventReferencesParticipantsCategory(EventReferencesParticipantsCategory eventReferencesParticipantsCategory) {
        this.eventReferencesParticipantsCategories.add(eventReferencesParticipantsCategory);
        eventReferencesParticipantsCategory.setEventReference(this);
        return this;
    }

    public EventReferences removeEventReferencesParticipantsCategory(
        EventReferencesParticipantsCategory eventReferencesParticipantsCategory
    ) {
        this.eventReferencesParticipantsCategories.remove(eventReferencesParticipantsCategory);
        eventReferencesParticipantsCategory.setEventReference(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReferences)) {
            return false;
        }
        return id != null && id.equals(((EventReferences) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReferences{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
