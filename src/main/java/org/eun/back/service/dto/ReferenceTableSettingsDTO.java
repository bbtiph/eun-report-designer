package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.ReferenceTableSettings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReferenceTableSettingsDTO implements Serializable {

    private Long id;

    private String refTable;

    private String columns;

    private String path;

    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefTable() {
        return refTable;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferenceTableSettingsDTO)) {
            return false;
        }

        ReferenceTableSettingsDTO referenceTableSettingsDTO = (ReferenceTableSettingsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, referenceTableSettingsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferenceTableSettingsDTO{" +
            "id=" + getId() +
            ", refTable='" + getRefTable() + "'" +
            ", columns='" + getColumns() + "'" +
            ", path='" + getPath() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
