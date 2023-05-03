package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EunTeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EunTeam.class);
        EunTeam eunTeam1 = new EunTeam();
        eunTeam1.setId(1L);
        EunTeam eunTeam2 = new EunTeam();
        eunTeam2.setId(eunTeam1.getId());
        assertThat(eunTeam1).isEqualTo(eunTeam2);
        eunTeam2.setId(2L);
        assertThat(eunTeam1).isNotEqualTo(eunTeam2);
        eunTeam1.setId(null);
        assertThat(eunTeam1).isNotEqualTo(eunTeam2);
    }
}
