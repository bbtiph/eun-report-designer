package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationalBodyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationalBody.class);
        OperationalBody operationalBody1 = new OperationalBody();
        operationalBody1.setId(1L);
        OperationalBody operationalBody2 = new OperationalBody();
        operationalBody2.setId(operationalBody1.getId());
        assertThat(operationalBody1).isEqualTo(operationalBody2);
        operationalBody2.setId(2L);
        assertThat(operationalBody1).isNotEqualTo(operationalBody2);
        operationalBody1.setId(null);
        assertThat(operationalBody1).isNotEqualTo(operationalBody2);
    }
}
