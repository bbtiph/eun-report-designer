package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A EunTeam.
 */
@Entity
@Table(name = "eun_team")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EunTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "team")
    @JsonIgnoreProperties(value = { "team", "person" }, allowSetters = true)
    private Set<EunTeamMember> eunTeamMembers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EunTeam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public EunTeam name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public EunTeam description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<EunTeamMember> getEunTeamMembers() {
        return this.eunTeamMembers;
    }

    public void setEunTeamMembers(Set<EunTeamMember> eunTeamMembers) {
        if (this.eunTeamMembers != null) {
            this.eunTeamMembers.forEach(i -> i.setTeam(null));
        }
        if (eunTeamMembers != null) {
            eunTeamMembers.forEach(i -> i.setTeam(this));
        }
        this.eunTeamMembers = eunTeamMembers;
    }

    public EunTeam eunTeamMembers(Set<EunTeamMember> eunTeamMembers) {
        this.setEunTeamMembers(eunTeamMembers);
        return this;
    }

    public EunTeam addEunTeamMember(EunTeamMember eunTeamMember) {
        this.eunTeamMembers.add(eunTeamMember);
        eunTeamMember.setTeam(this);
        return this;
    }

    public EunTeam removeEunTeamMember(EunTeamMember eunTeamMember) {
        this.eunTeamMembers.remove(eunTeamMember);
        eunTeamMember.setTeam(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EunTeam)) {
            return false;
        }
        return id != null && id.equals(((EunTeam) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EunTeam{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
