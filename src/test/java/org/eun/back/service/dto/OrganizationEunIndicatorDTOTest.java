package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationEunIndicatorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationEunIndicatorDTO.class);
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO1 = new OrganizationEunIndicatorDTO();
        organizationEunIndicatorDTO1.setId(1L);
        OrganizationEunIndicatorDTO organizationEunIndicatorDTO2 = new OrganizationEunIndicatorDTO();
        assertThat(organizationEunIndicatorDTO1).isNotEqualTo(organizationEunIndicatorDTO2);
        organizationEunIndicatorDTO2.setId(organizationEunIndicatorDTO1.getId());
        assertThat(organizationEunIndicatorDTO1).isEqualTo(organizationEunIndicatorDTO2);
        organizationEunIndicatorDTO2.setId(2L);
        assertThat(organizationEunIndicatorDTO1).isNotEqualTo(organizationEunIndicatorDTO2);
        organizationEunIndicatorDTO1.setId(null);
        assertThat(organizationEunIndicatorDTO1).isNotEqualTo(organizationEunIndicatorDTO2);
    }
}
