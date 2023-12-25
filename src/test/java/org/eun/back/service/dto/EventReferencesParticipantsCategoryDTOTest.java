package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReferencesParticipantsCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReferencesParticipantsCategoryDTO.class);
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO1 = new EventReferencesParticipantsCategoryDTO();
        eventReferencesParticipantsCategoryDTO1.setId(1L);
        EventReferencesParticipantsCategoryDTO eventReferencesParticipantsCategoryDTO2 = new EventReferencesParticipantsCategoryDTO();
        assertThat(eventReferencesParticipantsCategoryDTO1).isNotEqualTo(eventReferencesParticipantsCategoryDTO2);
        eventReferencesParticipantsCategoryDTO2.setId(eventReferencesParticipantsCategoryDTO1.getId());
        assertThat(eventReferencesParticipantsCategoryDTO1).isEqualTo(eventReferencesParticipantsCategoryDTO2);
        eventReferencesParticipantsCategoryDTO2.setId(2L);
        assertThat(eventReferencesParticipantsCategoryDTO1).isNotEqualTo(eventReferencesParticipantsCategoryDTO2);
        eventReferencesParticipantsCategoryDTO1.setId(null);
        assertThat(eventReferencesParticipantsCategoryDTO1).isNotEqualTo(eventReferencesParticipantsCategoryDTO2);
    }
}
