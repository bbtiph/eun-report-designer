package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EunTeamDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EunTeamDTO.class);
        EunTeamDTO eunTeamDTO1 = new EunTeamDTO();
        eunTeamDTO1.setId(1L);
        EunTeamDTO eunTeamDTO2 = new EunTeamDTO();
        assertThat(eunTeamDTO1).isNotEqualTo(eunTeamDTO2);
        eunTeamDTO2.setId(eunTeamDTO1.getId());
        assertThat(eunTeamDTO1).isEqualTo(eunTeamDTO2);
        eunTeamDTO2.setId(2L);
        assertThat(eunTeamDTO1).isNotEqualTo(eunTeamDTO2);
        eunTeamDTO1.setId(null);
        assertThat(eunTeamDTO1).isNotEqualTo(eunTeamDTO2);
    }
}
