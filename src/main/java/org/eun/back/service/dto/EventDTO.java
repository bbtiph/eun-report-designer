package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.Event} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventDTO implements Serializable {

    private Long id;

    private String type;

    private String location;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String url;

    private String eunContact;

    private String courseModules;

    private Integer courseDuration;

    private String courseType;

    private Integer modules;

    private String status;

    private Integer engagementRate;

    private Integer completionRate;

    private String name;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEunContact() {
        return eunContact;
    }

    public void setEunContact(String eunContact) {
        this.eunContact = eunContact;
    }

    public String getCourseModules() {
        return courseModules;
    }

    public void setCourseModules(String courseModules) {
        this.courseModules = courseModules;
    }

    public Integer getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(Integer courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Integer getModules() {
        return modules;
    }

    public void setModules(Integer modules) {
        this.modules = modules;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEngagementRate() {
        return engagementRate;
    }

    public void setEngagementRate(Integer engagementRate) {
        this.engagementRate = engagementRate;
    }

    public Integer getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Integer completionRate) {
        this.completionRate = completionRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
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
