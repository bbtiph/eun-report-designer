package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReferenceTableSettingsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceTableSettingsDTO.class);
        ReferenceTableSettingsDTO referenceTableSettingsDTO1 = new ReferenceTableSettingsDTO();
        referenceTableSettingsDTO1.setId(1L);
        ReferenceTableSettingsDTO referenceTableSettingsDTO2 = new ReferenceTableSettingsDTO();
        assertThat(referenceTableSettingsDTO1).isNotEqualTo(referenceTableSettingsDTO2);
        referenceTableSettingsDTO2.setId(referenceTableSettingsDTO1.getId());
        assertThat(referenceTableSettingsDTO1).isEqualTo(referenceTableSettingsDTO2);
        referenceTableSettingsDTO2.setId(2L);
        assertThat(referenceTableSettingsDTO1).isNotEqualTo(referenceTableSettingsDTO2);
        referenceTableSettingsDTO1.setId(null);
        assertThat(referenceTableSettingsDTO1).isNotEqualTo(referenceTableSettingsDTO2);
    }
}
