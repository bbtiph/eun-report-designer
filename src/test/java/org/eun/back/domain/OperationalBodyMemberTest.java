package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationalBodyMemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationalBodyMember.class);
        OperationalBodyMember operationalBodyMember1 = new OperationalBodyMember();
        operationalBodyMember1.setId(1L);
        OperationalBodyMember operationalBodyMember2 = new OperationalBodyMember();
        operationalBodyMember2.setId(operationalBodyMember1.getId());
        assertThat(operationalBodyMember1).isEqualTo(operationalBodyMember2);
        operationalBodyMember2.setId(2L);
        assertThat(operationalBodyMember1).isNotEqualTo(operationalBodyMember2);
        operationalBodyMember1.setId(null);
        assertThat(operationalBodyMember1).isNotEqualTo(operationalBodyMember2);
    }
}
