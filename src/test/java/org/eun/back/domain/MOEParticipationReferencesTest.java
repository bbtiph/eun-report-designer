package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MOEParticipationReferencesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MOEParticipationReferences.class);
        MOEParticipationReferences mOEParticipationReferences1 = new MOEParticipationReferences();
        mOEParticipationReferences1.setId(1L);
        MOEParticipationReferences mOEParticipationReferences2 = new MOEParticipationReferences();
        mOEParticipationReferences2.setId(mOEParticipationReferences1.getId());
        assertThat(mOEParticipationReferences1).isEqualTo(mOEParticipationReferences2);
        mOEParticipationReferences2.setId(2L);
        assertThat(mOEParticipationReferences1).isNotEqualTo(mOEParticipationReferences2);
        mOEParticipationReferences1.setId(null);
        assertThat(mOEParticipationReferences1).isNotEqualTo(mOEParticipationReferences2);
    }
}
