package org.eun.back.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link org.eun.back.domain.JobInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobInfoFromERPDTO implements Serializable {

    private List<JobInfoContentFromERPDTO> value;

    public List<JobInfoContentFromERPDTO> getValue() {
        return value;
    }

    public void setValue(List<JobInfoContentFromERPDTO> value) {
        this.value = value;
    }

    public JobInfoFromERPDTO(List<JobInfoContentFromERPDTO> value) {
        this.value = value;
    }

    public JobInfoFromERPDTO() {}

    @Override
    public String toString() {
        return "JobInfoFromERPDTO{" + "value=" + value + '}';
    }
}
