package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.EunTeamMember} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EunTeamMemberDTO implements Serializable {

    private Long id;

    private String role;

    private String status;

    private EunTeamDTO team;

    private PersonDTO person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EunTeamDTO getTeam() {
        return team;
    }

    public void setTeam(EunTeamDTO team) {
        this.team = team;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EunTeamMemberDTO)) {
            return false;
        }

        EunTeamMemberDTO eunTeamMemberDTO = (EunTeamMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eunTeamMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EunTeamMemberDTO{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            ", status='" + getStatus() + "'" +
            ", team=" + getTeam() +
            ", person=" + getPerson() +
            "}";
    }
}
