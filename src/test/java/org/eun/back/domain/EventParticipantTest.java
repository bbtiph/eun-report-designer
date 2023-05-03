package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventParticipantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventParticipant.class);
        EventParticipant eventParticipant1 = new EventParticipant();
        eventParticipant1.setId(1L);
        EventParticipant eventParticipant2 = new EventParticipant();
        eventParticipant2.setId(eventParticipant1.getId());
        assertThat(eventParticipant1).isEqualTo(eventParticipant2);
        eventParticipant2.setId(2L);
        assertThat(eventParticipant1).isNotEqualTo(eventParticipant2);
        eventParticipant1.setId(null);
        assertThat(eventParticipant1).isNotEqualTo(eventParticipant2);
    }
}
