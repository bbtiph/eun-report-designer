package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectPartnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectPartner.class);
        ProjectPartner projectPartner1 = new ProjectPartner();
        projectPartner1.setId(1L);
        ProjectPartner projectPartner2 = new ProjectPartner();
        projectPartner2.setId(projectPartner1.getId());
        assertThat(projectPartner1).isEqualTo(projectPartner2);
        projectPartner2.setId(2L);
        assertThat(projectPartner1).isNotEqualTo(projectPartner2);
        projectPartner1.setId(null);
        assertThat(projectPartner1).isNotEqualTo(projectPartner2);
    }
}
