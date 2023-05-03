package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonInOrganizationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInOrganizationDTO.class);
        PersonInOrganizationDTO personInOrganizationDTO1 = new PersonInOrganizationDTO();
        personInOrganizationDTO1.setId(1L);
        PersonInOrganizationDTO personInOrganizationDTO2 = new PersonInOrganizationDTO();
        assertThat(personInOrganizationDTO1).isNotEqualTo(personInOrganizationDTO2);
        personInOrganizationDTO2.setId(personInOrganizationDTO1.getId());
        assertThat(personInOrganizationDTO1).isEqualTo(personInOrganizationDTO2);
        personInOrganizationDTO2.setId(2L);
        assertThat(personInOrganizationDTO1).isNotEqualTo(personInOrganizationDTO2);
        personInOrganizationDTO1.setId(null);
        assertThat(personInOrganizationDTO1).isNotEqualTo(personInOrganizationDTO2);
    }
}
