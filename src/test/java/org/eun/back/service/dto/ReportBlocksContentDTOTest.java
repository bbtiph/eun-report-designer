package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportBlocksContentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportBlocksContentDTO.class);
        ReportBlocksContentDTO reportBlocksContentDTO1 = new ReportBlocksContentDTO();
        reportBlocksContentDTO1.setId(1L);
        ReportBlocksContentDTO reportBlocksContentDTO2 = new ReportBlocksContentDTO();
        assertThat(reportBlocksContentDTO1).isNotEqualTo(reportBlocksContentDTO2);
        reportBlocksContentDTO2.setId(reportBlocksContentDTO1.getId());
        assertThat(reportBlocksContentDTO1).isEqualTo(reportBlocksContentDTO2);
        reportBlocksContentDTO2.setId(2L);
        assertThat(reportBlocksContentDTO1).isNotEqualTo(reportBlocksContentDTO2);
        reportBlocksContentDTO1.setId(null);
        assertThat(reportBlocksContentDTO1).isNotEqualTo(reportBlocksContentDTO2);
    }
}
