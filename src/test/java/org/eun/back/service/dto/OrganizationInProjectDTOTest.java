package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationInProjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationInProjectDTO.class);
        OrganizationInProjectDTO organizationInProjectDTO1 = new OrganizationInProjectDTO();
        organizationInProjectDTO1.setId(1L);
        OrganizationInProjectDTO organizationInProjectDTO2 = new OrganizationInProjectDTO();
        assertThat(organizationInProjectDTO1).isNotEqualTo(organizationInProjectDTO2);
        organizationInProjectDTO2.setId(organizationInProjectDTO1.getId());
        assertThat(organizationInProjectDTO1).isEqualTo(organizationInProjectDTO2);
        organizationInProjectDTO2.setId(2L);
        assertThat(organizationInProjectDTO1).isNotEqualTo(organizationInProjectDTO2);
        organizationInProjectDTO1.setId(null);
        assertThat(organizationInProjectDTO1).isNotEqualTo(organizationInProjectDTO2);
    }
}
