package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventInOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventInOrganizationDTO.class);
        EventInOrganizationDTO eventInOrganizationDTO1 = new EventInOrganizationDTO();
        eventInOrganizationDTO1.setId(1L);
        EventInOrganizationDTO eventInOrganizationDTO2 = new EventInOrganizationDTO();
        assertThat(eventInOrganizationDTO1).isNotEqualTo(eventInOrganizationDTO2);
        eventInOrganizationDTO2.setId(eventInOrganizationDTO1.getId());
        assertThat(eventInOrganizationDTO1).isEqualTo(eventInOrganizationDTO2);
        eventInOrganizationDTO2.setId(2L);
        assertThat(eventInOrganizationDTO1).isNotEqualTo(eventInOrganizationDTO2);
        eventInOrganizationDTO1.setId(null);
        assertThat(eventInOrganizationDTO1).isNotEqualTo(eventInOrganizationDTO2);
    }
}
