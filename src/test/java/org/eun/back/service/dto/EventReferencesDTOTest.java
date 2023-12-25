package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReferencesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReferencesDTO.class);
        EventReferencesDTO eventReferencesDTO1 = new EventReferencesDTO();
        eventReferencesDTO1.setId(1L);
        EventReferencesDTO eventReferencesDTO2 = new EventReferencesDTO();
        assertThat(eventReferencesDTO1).isNotEqualTo(eventReferencesDTO2);
        eventReferencesDTO2.setId(eventReferencesDTO1.getId());
        assertThat(eventReferencesDTO1).isEqualTo(eventReferencesDTO2);
        eventReferencesDTO2.setId(2L);
        assertThat(eventReferencesDTO1).isNotEqualTo(eventReferencesDTO2);
        eventReferencesDTO1.setId(null);
        assertThat(eventReferencesDTO1).isNotEqualTo(eventReferencesDTO2);
    }
}
