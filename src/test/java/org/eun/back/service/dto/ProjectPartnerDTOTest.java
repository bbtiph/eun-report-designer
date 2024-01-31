package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectPartnerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectPartnerDTO.class);
        ProjectPartnerDTO projectPartnerDTO1 = new ProjectPartnerDTO();
        projectPartnerDTO1.setId(1L);
        ProjectPartnerDTO projectPartnerDTO2 = new ProjectPartnerDTO();
        assertThat(projectPartnerDTO1).isNotEqualTo(projectPartnerDTO2);
        projectPartnerDTO2.setId(projectPartnerDTO1.getId());
        assertThat(projectPartnerDTO1).isEqualTo(projectPartnerDTO2);
        projectPartnerDTO2.setId(2L);
        assertThat(projectPartnerDTO1).isNotEqualTo(projectPartnerDTO2);
        projectPartnerDTO1.setId(null);
        assertThat(projectPartnerDTO1).isNotEqualTo(projectPartnerDTO2);
    }
}
