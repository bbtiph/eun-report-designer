package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link org.eun.back.domain.ReferenceTableSettings} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReferenceTableSettingsDTO implements Serializable {

    private Long id;

    private String refTable;

    private String displayName;

    private String columns;

    private String columnsOfTemplate;

    private String actions;

    private String path;

    private Boolean isActive;

    @Lob
    private byte[] file;

    private String fileContentType;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getColumnsOfTemplate() {
        return columnsOfTemplate;
    }

    public void setColumnsOfTemplate(String columnsOfTemplate) {
        this.columnsOfTemplate = columnsOfTemplate;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
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
            ", displayName='" + getDisplayName() + "'" +
            ", columns='" + getColumns() + "'" +
            ", columnsOfTemplate='" + getColumnsOfTemplate() + "'" +
            ", actions='" + getActions() + "'" +
            ", path='" + getPath() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", file='" + getFile() + "'" +
            "}";
    }
}
