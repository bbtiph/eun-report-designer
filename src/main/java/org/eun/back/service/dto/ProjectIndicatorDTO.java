package org.eun.back.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.eun.back.domain.enumeration.ProjectStatus;

/**
 * A DTO for the {@link org.eun.back.domain.Project} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectIndicatorDTO implements Serializable {

    private Long id;

    @NotNull
    private ProjectStatus status;

    @NotNull
    private String name;

    private String contactEmail;

    private String contactName;

    private Long totalBudget;

    private Long totalFunding;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Long getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Long getTotalFunding() {
        return totalFunding;
    }

    public void setTotalFunding(Long totalFunding) {
        this.totalFunding = totalFunding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectIndicatorDTO)) {
            return false;
        }

        ProjectIndicatorDTO projectDTO = (ProjectIndicatorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "ProjectIndicatorDTO{" +
            "id=" + id +
            ", status=" + status +
            ", name='" + name + '\'' +
            ", contactEmail='" + contactEmail + '\'' +
            ", contactName='" + contactName + '\'' +
            ", totalBudget=" + totalBudget +
            ", totalFunding=" + totalFunding +
            '}';
    }
}
