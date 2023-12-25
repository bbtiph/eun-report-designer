package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A EventReferencesParticipantsCategory.
 */
@Entity
@Table(name = "event_references_participants_category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReferencesParticipantsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "participants_count")
    private Long participantsCount;

    @ManyToOne
    @JsonIgnoreProperties(value = { "eventReferencesParticipantsCategories" }, allowSetters = true)
    private EventReferences eventReference;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventReferencesParticipantsCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public EventReferencesParticipantsCategory category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getParticipantsCount() {
        return this.participantsCount;
    }

    public EventReferencesParticipantsCategory participantsCount(Long participantsCount) {
        this.setParticipantsCount(participantsCount);
        return this;
    }

    public void setParticipantsCount(Long participantsCount) {
        this.participantsCount = participantsCount;
    }

    public EventReferences getEventReference() {
        return this.eventReference;
    }

    public void setEventReference(EventReferences eventReferences) {
        this.eventReference = eventReferences;
    }

    public EventReferencesParticipantsCategory eventReference(EventReferences eventReferences) {
        this.setEventReference(eventReferences);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReferencesParticipantsCategory)) {
            return false;
        }
        return id != null && id.equals(((EventReferencesParticipantsCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReferencesParticipantsCategory{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", participantsCount=" + getParticipantsCount() +
            "}";
    }
}
