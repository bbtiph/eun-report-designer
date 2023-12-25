package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventReferencesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventReferences.class);
        EventReferences eventReferences1 = new EventReferences();
        eventReferences1.setId(1L);
        EventReferences eventReferences2 = new EventReferences();
        eventReferences2.setId(eventReferences1.getId());
        assertThat(eventReferences1).isEqualTo(eventReferences2);
        eventReferences2.setId(2L);
        assertThat(eventReferences1).isNotEqualTo(eventReferences2);
        eventReferences1.setId(null);
        assertThat(eventReferences1).isNotEqualTo(eventReferences2);
    }
}
