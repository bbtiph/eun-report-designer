package org.eun.back.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Privilege.
 */
@Entity
@Table(name = "privilege")
@SuppressWarnings("common-java:DuplicatedBlocks")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "privileges")
    @JsonIgnoreProperties(value = { "privileges" }, allowSetters = true)
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Privilege id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Privilege name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.removePrivilege(this));
        }
        if (roles != null) {
            roles.forEach(i -> i.addPrivilege(this));
        }
        this.roles = roles;
    }

    public Privilege roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Privilege addRole(Role role) {
        this.roles.add(role);
        role.getPrivileges().add(this);
        return this;
    }

    public Privilege removeRole(Role role) {
        this.roles.remove(role);
        role.getPrivileges().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Privilege)) {
            return false;
        }
        return id != null && id.equals(((Privilege) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Privilege{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
