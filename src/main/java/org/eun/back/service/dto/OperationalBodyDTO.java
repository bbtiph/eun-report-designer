package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link org.eun.back.domain.OperationalBody} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OperationalBodyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String acronym;

    private String description;

    private String type;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationalBodyDTO)) {
            return false;
        }

        OperationalBodyDTO operationalBodyDTO = (OperationalBodyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, operationalBodyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationalBodyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
