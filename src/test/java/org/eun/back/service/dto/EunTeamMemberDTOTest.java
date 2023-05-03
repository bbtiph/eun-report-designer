package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EunTeamMemberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EunTeamMemberDTO.class);
        EunTeamMemberDTO eunTeamMemberDTO1 = new EunTeamMemberDTO();
        eunTeamMemberDTO1.setId(1L);
        EunTeamMemberDTO eunTeamMemberDTO2 = new EunTeamMemberDTO();
        assertThat(eunTeamMemberDTO1).isNotEqualTo(eunTeamMemberDTO2);
        eunTeamMemberDTO2.setId(eunTeamMemberDTO1.getId());
        assertThat(eunTeamMemberDTO1).isEqualTo(eunTeamMemberDTO2);
        eunTeamMemberDTO2.setId(2L);
        assertThat(eunTeamMemberDTO1).isNotEqualTo(eunTeamMemberDTO2);
        eunTeamMemberDTO1.setId(null);
        assertThat(eunTeamMemberDTO1).isNotEqualTo(eunTeamMemberDTO2);
    }
}
