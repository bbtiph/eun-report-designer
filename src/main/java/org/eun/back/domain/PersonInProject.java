package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A PersonInProject.
 */
@Entity
@Table(name = "person_in_project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonInProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "role_in_project")
    private String roleInProject;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "eunTeamMembers", "eventParticipants", "personInOrganizations", "personInProjects", "country" },
        allowSetters = true
    )
    private Person person;

    @ManyToOne
    @JsonIgnoreProperties(value = { "organizationInProjects", "personInProjects", "funding" }, allowSetters = true)
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PersonInProject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleInProject() {
        return this.roleInProject;
    }

    public PersonInProject roleInProject(String roleInProject) {
        this.setRoleInProject(roleInProject);
        return this;
    }

    public void setRoleInProject(String roleInProject) {
        this.roleInProject = roleInProject;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PersonInProject person(Person person) {
        this.setPerson(person);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PersonInProject project(Project project) {
        this.setProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonInProject)) {
            return false;
        }
        return id != null && id.equals(((PersonInProject) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonInProject{" +
            "id=" + getId() +
            ", roleInProject='" + getRoleInProject() + "'" +
            "}";
    }
}
