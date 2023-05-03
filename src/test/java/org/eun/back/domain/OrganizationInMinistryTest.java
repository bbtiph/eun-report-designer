package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationInMinistryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationInMinistry.class);
        OrganizationInMinistry organizationInMinistry1 = new OrganizationInMinistry();
        organizationInMinistry1.setId(1L);
        OrganizationInMinistry organizationInMinistry2 = new OrganizationInMinistry();
        organizationInMinistry2.setId(organizationInMinistry1.getId());
        assertThat(organizationInMinistry1).isEqualTo(organizationInMinistry2);
        organizationInMinistry2.setId(2L);
        assertThat(organizationInMinistry1).isNotEqualTo(organizationInMinistry2);
        organizationInMinistry1.setId(null);
        assertThat(organizationInMinistry1).isNotEqualTo(organizationInMinistry2);
    }
}
