package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EunTeamMemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EunTeamMember.class);
        EunTeamMember eunTeamMember1 = new EunTeamMember();
        eunTeamMember1.setId(1L);
        EunTeamMember eunTeamMember2 = new EunTeamMember();
        eunTeamMember2.setId(eunTeamMember1.getId());
        assertThat(eunTeamMember1).isEqualTo(eunTeamMember2);
        eunTeamMember2.setId(2L);
        assertThat(eunTeamMember1).isNotEqualTo(eunTeamMember2);
        eunTeamMember1.setId(null);
        assertThat(eunTeamMember1).isNotEqualTo(eunTeamMember2);
    }
}
