package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperationalBodyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationalBodyDTO.class);
        OperationalBodyDTO operationalBodyDTO1 = new OperationalBodyDTO();
        operationalBodyDTO1.setId(1L);
        OperationalBodyDTO operationalBodyDTO2 = new OperationalBodyDTO();
        assertThat(operationalBodyDTO1).isNotEqualTo(operationalBodyDTO2);
        operationalBodyDTO2.setId(operationalBodyDTO1.getId());
        assertThat(operationalBodyDTO1).isEqualTo(operationalBodyDTO2);
        operationalBodyDTO2.setId(2L);
        assertThat(operationalBodyDTO1).isNotEqualTo(operationalBodyDTO2);
        operationalBodyDTO1.setId(null);
        assertThat(operationalBodyDTO1).isNotEqualTo(operationalBodyDTO2);
    }
}
