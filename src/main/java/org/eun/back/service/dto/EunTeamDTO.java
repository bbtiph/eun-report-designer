package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.eun.back.domain.EunTeam} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EunTeamDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private EunTeamMemberDTO eunTeamMember;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EunTeamMemberDTO getEunTeamMember() {
        return eunTeamMember;
    }

    public void setEunTeamMember(EunTeamMemberDTO eunTeamMember) {
        this.eunTeamMember = eunTeamMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EunTeamDTO)) {
            return false;
        }

        EunTeamDTO eunTeamDTO = (EunTeamDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eunTeamDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EunTeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", eunTeamMember=" + getEunTeamMember() +
            "}";
    }
}
