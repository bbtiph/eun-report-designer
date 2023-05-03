package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationInMinistryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationInMinistryDTO.class);
        OrganizationInMinistryDTO organizationInMinistryDTO1 = new OrganizationInMinistryDTO();
        organizationInMinistryDTO1.setId(1L);
        OrganizationInMinistryDTO organizationInMinistryDTO2 = new OrganizationInMinistryDTO();
        assertThat(organizationInMinistryDTO1).isNotEqualTo(organizationInMinistryDTO2);
        organizationInMinistryDTO2.setId(organizationInMinistryDTO1.getId());
        assertThat(organizationInMinistryDTO1).isEqualTo(organizationInMinistryDTO2);
        organizationInMinistryDTO2.setId(2L);
        assertThat(organizationInMinistryDTO1).isNotEqualTo(organizationInMinistryDTO2);
        organizationInMinistryDTO1.setId(null);
        assertThat(organizationInMinistryDTO1).isNotEqualTo(organizationInMinistryDTO2);
    }
}
