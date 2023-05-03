package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrganizationInProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationInProject.class);
        OrganizationInProject organizationInProject1 = new OrganizationInProject();
        organizationInProject1.setId(1L);
        OrganizationInProject organizationInProject2 = new OrganizationInProject();
        organizationInProject2.setId(organizationInProject1.getId());
        assertThat(organizationInProject1).isEqualTo(organizationInProject2);
        organizationInProject2.setId(2L);
        assertThat(organizationInProject1).isNotEqualTo(organizationInProject2);
        organizationInProject1.setId(null);
        assertThat(organizationInProject1).isNotEqualTo(organizationInProject2);
    }
}
