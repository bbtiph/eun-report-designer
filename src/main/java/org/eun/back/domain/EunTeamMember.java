package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A EunTeamMember.
 */
@Entity
@Table(name = "eun_team_member")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EunTeamMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "eunTeamMember")
    @JsonIgnoreProperties(value = { "eunTeamMember" }, allowSetters = true)
    private Set<EunTeam> teams = new HashSet<>();

    @OneToMany(mappedBy = "eunTeamMember")
    @JsonIgnoreProperties(
        value = { "countries", "eunTeamMember", "eventParticipant", "personInOrganization", "personInProject" },
        allowSetters = true
    )
    private Set<Person> people = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EunTeamMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public EunTeamMember role(String role) {
        this.setRole(role);
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return this.status;
    }

    public EunTeamMember status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<EunTeam> getTeams() {
        return this.teams;
    }

    public void setTeams(Set<EunTeam> eunTeams) {
        if (this.teams != null) {
            this.teams.forEach(i -> i.setEunTeamMember(null));
        }
        if (eunTeams != null) {
            eunTeams.forEach(i -> i.setEunTeamMember(this));
        }
        this.teams = eunTeams;
    }

    public EunTeamMember teams(Set<EunTeam> eunTeams) {
        this.setTeams(eunTeams);
        return this;
    }

    public EunTeamMember addTeam(EunTeam eunTeam) {
        this.teams.add(eunTeam);
        eunTeam.setEunTeamMember(this);
        return this;
    }

    public EunTeamMember removeTeam(EunTeam eunTeam) {
        this.teams.remove(eunTeam);
        eunTeam.setEunTeamMember(null);
        return this;
    }

    public Set<Person> getPeople() {
        return this.people;
    }

    public void setPeople(Set<Person> people) {
        if (this.people != null) {
            this.people.forEach(i -> i.setEunTeamMember(null));
        }
        if (people != null) {
            people.forEach(i -> i.setEunTeamMember(this));
        }
        this.people = people;
    }

    public EunTeamMember people(Set<Person> people) {
        this.setPeople(people);
        return this;
    }

    public EunTeamMember addPerson(Person person) {
        this.people.add(person);
        person.setEunTeamMember(this);
        return this;
    }

    public EunTeamMember removePerson(Person person) {
        this.people.remove(person);
        person.setEunTeamMember(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EunTeamMember)) {
            return false;
        }
        return id != null && id.equals(((EunTeamMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EunTeamMember{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
