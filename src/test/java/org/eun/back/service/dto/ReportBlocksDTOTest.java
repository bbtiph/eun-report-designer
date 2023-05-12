package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportBlocksDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportBlocksDTO.class);
        ReportBlocksDTO reportBlocksDTO1 = new ReportBlocksDTO();
        reportBlocksDTO1.setId(1L);
        ReportBlocksDTO reportBlocksDTO2 = new ReportBlocksDTO();
        assertThat(reportBlocksDTO1).isNotEqualTo(reportBlocksDTO2);
        reportBlocksDTO2.setId(reportBlocksDTO1.getId());
        assertThat(reportBlocksDTO1).isEqualTo(reportBlocksDTO2);
        reportBlocksDTO2.setId(2L);
        assertThat(reportBlocksDTO1).isNotEqualTo(reportBlocksDTO2);
        reportBlocksDTO1.setId(null);
        assertThat(reportBlocksDTO1).isNotEqualTo(reportBlocksDTO2);
    }
}
