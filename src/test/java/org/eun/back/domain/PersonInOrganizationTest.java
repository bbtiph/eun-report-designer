package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonInOrganizationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInOrganization.class);
        PersonInOrganization personInOrganization1 = new PersonInOrganization();
        personInOrganization1.setId(1L);
        PersonInOrganization personInOrganization2 = new PersonInOrganization();
        personInOrganization2.setId(personInOrganization1.getId());
        assertThat(personInOrganization1).isEqualTo(personInOrganization2);
        personInOrganization2.setId(2L);
        assertThat(personInOrganization1).isNotEqualTo(personInOrganization2);
        personInOrganization1.setId(null);
        assertThat(personInOrganization1).isNotEqualTo(personInOrganization2);
    }
}
