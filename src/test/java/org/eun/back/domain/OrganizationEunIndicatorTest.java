package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationEunIndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationEunIndicator.class);
        OrganizationEunIndicator organizationEunIndicator1 = new OrganizationEunIndicator();
        organizationEunIndicator1.setId(1L);
        OrganizationEunIndicator organizationEunIndicator2 = new OrganizationEunIndicator();
        organizationEunIndicator2.setId(organizationEunIndicator1.getId());
        assertThat(organizationEunIndicator1).isEqualTo(organizationEunIndicator2);
        organizationEunIndicator2.setId(2L);
        assertThat(organizationEunIndicator1).isNotEqualTo(organizationEunIndicator2);
        organizationEunIndicator1.setId(null);
        assertThat(organizationEunIndicator1).isNotEqualTo(organizationEunIndicator2);
    }
}
