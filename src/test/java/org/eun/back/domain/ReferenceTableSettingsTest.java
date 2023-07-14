package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReferenceTableSettingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceTableSettings.class);
        ReferenceTableSettings referenceTableSettings1 = new ReferenceTableSettings();
        referenceTableSettings1.setId(1L);
        ReferenceTableSettings referenceTableSettings2 = new ReferenceTableSettings();
        referenceTableSettings2.setId(referenceTableSettings1.getId());
        assertThat(referenceTableSettings1).isEqualTo(referenceTableSettings2);
        referenceTableSettings2.setId(2L);
        assertThat(referenceTableSettings1).isNotEqualTo(referenceTableSettings2);
        referenceTableSettings1.setId(null);
        assertThat(referenceTableSettings1).isNotEqualTo(referenceTableSettings2);
    }
}
