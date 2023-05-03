package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "personInProject")
    @JsonIgnoreProperties(
        value = { "countries", "eunTeamMember", "eventParticipant", "personInOrganization", "personInProject" },
        allowSetters = true
    )
    private Set<Person> people = new HashSet<>();

    @OneToMany(mappedBy = "personInProject")
    @JsonIgnoreProperties(value = { "fundings", "organizationInProject", "personInProject" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

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

    public Set<Person> getPeople() {
        return this.people;
    }

    public void setPeople(Set<Person> people) {
        if (this.people != null) {
            this.people.forEach(i -> i.setPersonInProject(null));
        }
        if (people != null) {
            people.forEach(i -> i.setPersonInProject(this));
        }
        this.people = people;
    }

    public PersonInProject people(Set<Person> people) {
        this.setPeople(people);
        return this;
    }

    public PersonInProject addPerson(Person person) {
        this.people.add(person);
        person.setPersonInProject(this);
        return this;
    }

    public PersonInProject removePerson(Person person) {
        this.people.remove(person);
        person.setPersonInProject(null);
        return this;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setPersonInProject(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setPersonInProject(this));
        }
        this.projects = projects;
    }

    public PersonInProject projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public PersonInProject addProject(Project project) {
        this.projects.add(project);
        project.setPersonInProject(this);
        return this;
    }

    public PersonInProject removeProject(Project project) {
        this.projects.remove(project);
        project.setPersonInProject(null);
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
