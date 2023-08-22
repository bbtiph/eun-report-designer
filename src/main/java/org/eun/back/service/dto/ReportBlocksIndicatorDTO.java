package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link org.eun.back.domain.ReportBlocks} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReportBlocksIndicatorDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportBlocksIndicatorDTO)) {
            return false;
        }

        ReportBlocksIndicatorDTO reportBlocksDTO = (ReportBlocksIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reportBlocksDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "ReportBlocksIndicatorDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
