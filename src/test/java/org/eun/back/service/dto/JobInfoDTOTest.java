package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobInfoDTO.class);
        JobInfoDTO jobInfoDTO1 = new JobInfoDTO();
        jobInfoDTO1.setId(1L);
        JobInfoDTO jobInfoDTO2 = new JobInfoDTO();
        assertThat(jobInfoDTO1).isNotEqualTo(jobInfoDTO2);
        jobInfoDTO2.setId(jobInfoDTO1.getId());
        assertThat(jobInfoDTO1).isEqualTo(jobInfoDTO2);
        jobInfoDTO2.setId(2L);
        assertThat(jobInfoDTO1).isNotEqualTo(jobInfoDTO2);
        jobInfoDTO1.setId(null);
        assertThat(jobInfoDTO1).isNotEqualTo(jobInfoDTO2);
    }
}
