package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.PersonInProject} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonInProjectDTO implements Serializable {

    private Long id;

    private String roleInProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleInProject() {
        return roleInProject;
    }

    public void setRoleInProject(String roleInProject) {
        this.roleInProject = roleInProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonInProjectDTO)) {
            return false;
        }

        PersonInProjectDTO personInProjectDTO = (PersonInProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personInProjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonInProjectDTO{" +
            "id=" + getId() +
            ", roleInProject='" + getRoleInProject() + "'" +
            "}";
    }
}
