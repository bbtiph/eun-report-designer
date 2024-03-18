package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MOEParticipationReferencesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MOEParticipationReferencesDTO.class);
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO1 = new MOEParticipationReferencesDTO();
        mOEParticipationReferencesDTO1.setId(1L);
        MOEParticipationReferencesDTO mOEParticipationReferencesDTO2 = new MOEParticipationReferencesDTO();
        assertThat(mOEParticipationReferencesDTO1).isNotEqualTo(mOEParticipationReferencesDTO2);
        mOEParticipationReferencesDTO2.setId(mOEParticipationReferencesDTO1.getId());
        assertThat(mOEParticipationReferencesDTO1).isEqualTo(mOEParticipationReferencesDTO2);
        mOEParticipationReferencesDTO2.setId(2L);
        assertThat(mOEParticipationReferencesDTO1).isNotEqualTo(mOEParticipationReferencesDTO2);
        mOEParticipationReferencesDTO1.setId(null);
        assertThat(mOEParticipationReferencesDTO1).isNotEqualTo(mOEParticipationReferencesDTO2);
    }
}
