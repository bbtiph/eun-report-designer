package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReferencesParticipantsCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReferencesParticipantsCategory.class);
        EventReferencesParticipantsCategory eventReferencesParticipantsCategory1 = new EventReferencesParticipantsCategory();
        eventReferencesParticipantsCategory1.setId(1L);
        EventReferencesParticipantsCategory eventReferencesParticipantsCategory2 = new EventReferencesParticipantsCategory();
        eventReferencesParticipantsCategory2.setId(eventReferencesParticipantsCategory1.getId());
        assertThat(eventReferencesParticipantsCategory1).isEqualTo(eventReferencesParticipantsCategory2);
        eventReferencesParticipantsCategory2.setId(2L);
        assertThat(eventReferencesParticipantsCategory1).isNotEqualTo(eventReferencesParticipantsCategory2);
        eventReferencesParticipantsCategory1.setId(null);
        assertThat(eventReferencesParticipantsCategory1).isNotEqualTo(eventReferencesParticipantsCategory2);
    }
}
