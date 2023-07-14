package org.eun.back.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ReferenceTableSettings.
 */
@Entity
@Table(name = "reference_table_settings")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReferenceTableSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ref_table")
    private String refTable;

    @Column(name = "columns")
    private String columns;

    @Column(name = "path")
    private String path;

    @Column(name = "is_active")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReferenceTableSettings id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefTable() {
        return this.refTable;
    }

    public ReferenceTableSettings refTable(String refTable) {
        this.setRefTable(refTable);
        return this;
    }

    public void setRefTable(String refTable) {
        this.refTable = refTable;
    }

    public String getColumns() {
        return this.columns;
    }

    public ReferenceTableSettings columns(String columns) {
        this.setColumns(columns);
        return this;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getPath() {
        return this.path;
    }

    public ReferenceTableSettings path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ReferenceTableSettings isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferenceTableSettings)) {
            return false;
        }
        return id != null && id.equals(((ReferenceTableSettings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReferenceTableSettings{" +
            "id=" + getId() +
            ", refTable='" + getRefTable() + "'" +
            ", columns='" + getColumns() + "'" +
            ", path='" + getPath() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
