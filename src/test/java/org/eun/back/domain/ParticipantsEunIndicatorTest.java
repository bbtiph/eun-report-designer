package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParticipantsEunIndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipantsEunIndicator.class);
        ParticipantsEunIndicator participantsEunIndicator1 = new ParticipantsEunIndicator();
        participantsEunIndicator1.setId(1L);
        ParticipantsEunIndicator participantsEunIndicator2 = new ParticipantsEunIndicator();
        participantsEunIndicator2.setId(participantsEunIndicator1.getId());
        assertThat(participantsEunIndicator1).isEqualTo(participantsEunIndicator2);
        participantsEunIndicator2.setId(2L);
        assertThat(participantsEunIndicator1).isNotEqualTo(participantsEunIndicator2);
        participantsEunIndicator1.setId(null);
        assertThat(participantsEunIndicator1).isNotEqualTo(participantsEunIndicator2);
    }
}
