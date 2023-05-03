package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventInOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventInOrganization.class);
        EventInOrganization eventInOrganization1 = new EventInOrganization();
        eventInOrganization1.setId(1L);
        EventInOrganization eventInOrganization2 = new EventInOrganization();
        eventInOrganization2.setId(eventInOrganization1.getId());
        assertThat(eventInOrganization1).isEqualTo(eventInOrganization2);
        eventInOrganization2.setId(2L);
        assertThat(eventInOrganization1).isNotEqualTo(eventInOrganization2);
        eventInOrganization1.setId(null);
        assertThat(eventInOrganization1).isNotEqualTo(eventInOrganization2);
    }
}
