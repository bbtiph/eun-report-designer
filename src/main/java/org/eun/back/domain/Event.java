package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "url")
    private String url;

    @Column(name = "eun_contact")
    private String eunContact;

    @Column(name = "course_modules")
    private String courseModules;

    @Column(name = "course_duration")
    private Integer courseDuration;

    @Column(name = "course_type")
    private String courseType;

    @Column(name = "modules")
    private Integer modules;

    @Column(name = "status")
    private String status;

    @Column(name = "engagement_rate")
    private Integer engagementRate;

    @Column(name = "completion_rate")
    private Integer completionRate;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = { "events", "organizations" }, allowSetters = true)
    private EventInOrganization eventInOrganization;

    @ManyToOne
    @JsonIgnoreProperties(value = { "events", "people" }, allowSetters = true)
    private EventParticipant eventParticipant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Event type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return this.location;
    }

    public Event location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return this.title;
    }

    public Event title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Event description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Event startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Event endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return this.url;
    }

    public Event url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEunContact() {
        return this.eunContact;
    }

    public Event eunContact(String eunContact) {
        this.setEunContact(eunContact);
        return this;
    }

    public void setEunContact(String eunContact) {
        this.eunContact = eunContact;
    }

    public String getCourseModules() {
        return this.courseModules;
    }

    public Event courseModules(String courseModules) {
        this.setCourseModules(courseModules);
        return this;
    }

    public void setCourseModules(String courseModules) {
        this.courseModules = courseModules;
    }

    public Integer getCourseDuration() {
        return this.courseDuration;
    }

    public Event courseDuration(Integer courseDuration) {
        this.setCourseDuration(courseDuration);
        return this;
    }

    public void setCourseDuration(Integer courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseType() {
        return this.courseType;
    }

    public Event courseType(String courseType) {
        this.setCourseType(courseType);
        return this;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Integer getModules() {
        return this.modules;
    }

    public Event modules(Integer modules) {
        this.setModules(modules);
        return this;
    }

    public void setModules(Integer modules) {
        this.modules = modules;
    }

    public String getStatus() {
        return this.status;
    }

    public Event status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEngagementRate() {
        return this.engagementRate;
    }

    public Event engagementRate(Integer engagementRate) {
        this.setEngagementRate(engagementRate);
        return this;
    }

    public void setEngagementRate(Integer engagementRate) {
        this.engagementRate = engagementRate;
    }

    public Integer getCompletionRate() {
        return this.completionRate;
    }

    public Event completionRate(Integer completionRate) {
        this.setCompletionRate(completionRate);
        return this;
    }

    public void setCompletionRate(Integer completionRate) {
        this.completionRate = completionRate;
    }

    public String getName() {
        return this.name;
    }

    public Event name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventInOrganization getEventInOrganization() {
        return this.eventInOrganization;
    }

    public void setEventInOrganization(EventInOrganization eventInOrganization) {
        this.eventInOrganization = eventInOrganization;
    }

    public Event eventInOrganization(EventInOrganization eventInOrganization) {
        this.setEventInOrganization(eventInOrganization);
        return this;
    }

    public EventParticipant getEventParticipant() {
        return this.eventParticipant;
    }

    public void setEventParticipant(EventParticipant eventParticipant) {
        this.eventParticipant = eventParticipant;
    }

    public Event eventParticipant(EventParticipant eventParticipant) {
        this.setEventParticipant(eventParticipant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", location='" + getLocation() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", url='" + getUrl() + "'" +
            ", eunContact='" + getEunContact() + "'" +
            ", courseModules='" + getCourseModules() + "'" +
            ", courseDuration=" + getCourseDuration() +
            ", courseType='" + getCourseType() + "'" +
            ", modules=" + getModules() +
            ", status='" + getStatus() + "'" +
            ", engagementRate=" + getEngagementRate() +
            ", completionRate=" + getCompletionRate() +
            ", name='" + getName() + "'" +
            "}";
    }
}
