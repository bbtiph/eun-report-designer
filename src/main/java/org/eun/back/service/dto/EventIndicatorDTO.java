package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.Event} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventIndicatorDTO implements Serializable {

    private Long id;

    private String type;

    private String location;

    private String title;

    private String eunContact;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventIndicatorDTO)) {
            return false;
        }

        EventIndicatorDTO eventDTO = (EventIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "EventIndicatorDTO{" +
            "id=" +
            id +
            ", type='" +
            type +
            '\'' +
            ", location='" +
            location +
            '\'' +
            ", title='" +
            title +
            '\'' +
            ", eunContact='" +
            eunContact +
            '\'' +
            '}'
        );
    }
}
