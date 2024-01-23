package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobInfo.class);
        JobInfo jobInfo1 = new JobInfo();
        jobInfo1.setId(1L);
        JobInfo jobInfo2 = new JobInfo();
        jobInfo2.setId(jobInfo1.getId());
        assertThat(jobInfo1).isEqualTo(jobInfo2);
        jobInfo2.setId(2L);
        assertThat(jobInfo1).isNotEqualTo(jobInfo2);
        jobInfo1.setId(null);
        assertThat(jobInfo1).isNotEqualTo(jobInfo2);
    }
}
