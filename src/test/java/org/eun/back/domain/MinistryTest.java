package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MinistryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ministry.class);
        Ministry ministry1 = new Ministry();
        ministry1.setId(1L);
        Ministry ministry2 = new Ministry();
        ministry2.setId(ministry1.getId());
        assertThat(ministry1).isEqualTo(ministry2);
        ministry2.setId(2L);
        assertThat(ministry1).isNotEqualTo(ministry2);
        ministry1.setId(null);
        assertThat(ministry1).isNotEqualTo(ministry2);
    }
}
