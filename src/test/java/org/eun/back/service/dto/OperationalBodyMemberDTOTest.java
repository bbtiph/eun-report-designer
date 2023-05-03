package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationalBodyMemberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationalBodyMemberDTO.class);
        OperationalBodyMemberDTO operationalBodyMemberDTO1 = new OperationalBodyMemberDTO();
        operationalBodyMemberDTO1.setId(1L);
        OperationalBodyMemberDTO operationalBodyMemberDTO2 = new OperationalBodyMemberDTO();
        assertThat(operationalBodyMemberDTO1).isNotEqualTo(operationalBodyMemberDTO2);
        operationalBodyMemberDTO2.setId(operationalBodyMemberDTO1.getId());
        assertThat(operationalBodyMemberDTO1).isEqualTo(operationalBodyMemberDTO2);
        operationalBodyMemberDTO2.setId(2L);
        assertThat(operationalBodyMemberDTO1).isNotEqualTo(operationalBodyMemberDTO2);
        operationalBodyMemberDTO1.setId(null);
        assertThat(operationalBodyMemberDTO1).isNotEqualTo(operationalBodyMemberDTO2);
    }
}
