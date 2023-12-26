package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Countries.
 */
@Entity
@Table(name = "countries")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @ManyToMany(mappedBy = "countryIds")
    @JsonIgnoreProperties(value = { "countryIds", "reportIds", "reportBlocksContents" }, allowSetters = true)
    private Set<ReportBlocks> reportBlockIds = new HashSet<>();

    @ManyToMany(mappedBy = "countries")
    @JsonIgnoreProperties(value = { "eventReferencesParticipantsCategories", "countries" }, allowSetters = true)
    private Set<EventReferences> eventReferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Countries id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public Countries countryName(String countryName) {
        this.setCountryName(countryName);
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Countries countryCode(String countryCode) {
        this.setCountryCode(countryCode);
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Set<ReportBlocks> getReportBlockIds() {
        return this.reportBlockIds;
    }

    public void setReportBlockIds(Set<ReportBlocks> reportBlocks) {
        if (this.reportBlockIds != null) {
            this.reportBlockIds.forEach(i -> i.removeCountryId(this));
        }
        if (reportBlocks != null) {
            reportBlocks.forEach(i -> i.addCountryId(this));
        }
        this.reportBlockIds = reportBlocks;
    }

    public Countries reportBlockIds(Set<ReportBlocks> reportBlocks) {
        this.setReportBlockIds(reportBlocks);
        return this;
    }

    public Countries addReportBlockId(ReportBlocks reportBlocks) {
        this.reportBlockIds.add(reportBlocks);
        reportBlocks.getCountryIds().add(this);
        return this;
    }

    public Countries removeReportBlockId(ReportBlocks reportBlocks) {
        this.reportBlockIds.remove(reportBlocks);
        reportBlocks.getCountryIds().remove(this);
        return this;
    }

    public Set<EventReferences> getEventReferences() {
        return this.eventReferences;
    }

    public void setEventReferences(Set<EventReferences> eventReferences) {
        if (this.eventReferences != null) {
            this.eventReferences.forEach(i -> i.removeCountries(this));
        }
        if (eventReferences != null) {
            eventReferences.forEach(i -> i.addCountries(this));
        }
        this.eventReferences = eventReferences;
    }

    public Countries eventReferences(Set<EventReferences> eventReferences) {
        this.setEventReferences(eventReferences);
        return this;
    }

    public Countries addEventReferences(EventReferences eventReferences) {
        this.eventReferences.add(eventReferences);
        eventReferences.getCountries().add(this);
        return this;
    }

    public Countries removeEventReferences(EventReferences eventReferences) {
        this.eventReferences.remove(eventReferences);
        eventReferences.getCountries().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Countries)) {
            return false;
        }
        return id != null && id.equals(((Countries) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Countries{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            "}";
    }
}
