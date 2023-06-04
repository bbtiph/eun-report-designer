package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportBlocksContentDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportBlocksContentDataDTO.class);
        ReportBlocksContentDataDTO reportBlocksContentDataDTO1 = new ReportBlocksContentDataDTO();
        reportBlocksContentDataDTO1.setId(1L);
        ReportBlocksContentDataDTO reportBlocksContentDataDTO2 = new ReportBlocksContentDataDTO();
        assertThat(reportBlocksContentDataDTO1).isNotEqualTo(reportBlocksContentDataDTO2);
        reportBlocksContentDataDTO2.setId(reportBlocksContentDataDTO1.getId());
        assertThat(reportBlocksContentDataDTO1).isEqualTo(reportBlocksContentDataDTO2);
        reportBlocksContentDataDTO2.setId(2L);
        assertThat(reportBlocksContentDataDTO1).isNotEqualTo(reportBlocksContentDataDTO2);
        reportBlocksContentDataDTO1.setId(null);
        assertThat(reportBlocksContentDataDTO1).isNotEqualTo(reportBlocksContentDataDTO2);
    }
}
