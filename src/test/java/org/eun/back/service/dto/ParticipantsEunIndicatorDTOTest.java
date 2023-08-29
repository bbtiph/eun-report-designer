package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParticipantsEunIndicatorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipantsEunIndicatorDTO.class);
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO1 = new ParticipantsEunIndicatorDTO();
        participantsEunIndicatorDTO1.setId(1L);
        ParticipantsEunIndicatorDTO participantsEunIndicatorDTO2 = new ParticipantsEunIndicatorDTO();
        assertThat(participantsEunIndicatorDTO1).isNotEqualTo(participantsEunIndicatorDTO2);
        participantsEunIndicatorDTO2.setId(participantsEunIndicatorDTO1.getId());
        assertThat(participantsEunIndicatorDTO1).isEqualTo(participantsEunIndicatorDTO2);
        participantsEunIndicatorDTO2.setId(2L);
        assertThat(participantsEunIndicatorDTO1).isNotEqualTo(participantsEunIndicatorDTO2);
        participantsEunIndicatorDTO1.setId(null);
        assertThat(participantsEunIndicatorDTO1).isNotEqualTo(participantsEunIndicatorDTO2);
    }
}
