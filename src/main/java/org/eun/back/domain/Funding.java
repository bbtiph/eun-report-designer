package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Funding.
 */
@Entity
@Table(name = "funding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "funding")
    @JsonIgnoreProperties(value = { "organizationInProjects", "personInProjects", "funding" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Funding name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public Funding type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public Funding parentId(Long parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return this.description;
    }

    public Funding description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setFunding(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setFunding(this));
        }
        this.projects = projects;
    }

    public Funding projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Funding addProject(Project project) {
        this.projects.add(project);
        project.setFunding(this);
        return this;
    }

    public Funding removeProject(Project project) {
        this.projects.remove(project);
        project.setFunding(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funding)) {
            return false;
        }
        return id != null && id.equals(((Funding) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funding{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", parentId=" + getParentId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
