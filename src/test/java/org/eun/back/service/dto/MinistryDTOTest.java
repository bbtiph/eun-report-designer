package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MinistryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MinistryDTO.class);
        MinistryDTO ministryDTO1 = new MinistryDTO();
        ministryDTO1.setId(1L);
        MinistryDTO ministryDTO2 = new MinistryDTO();
        assertThat(ministryDTO1).isNotEqualTo(ministryDTO2);
        ministryDTO2.setId(ministryDTO1.getId());
        assertThat(ministryDTO1).isEqualTo(ministryDTO2);
        ministryDTO2.setId(2L);
        assertThat(ministryDTO1).isNotEqualTo(ministryDTO2);
        ministryDTO1.setId(null);
        assertThat(ministryDTO1).isNotEqualTo(ministryDTO2);
    }
}
