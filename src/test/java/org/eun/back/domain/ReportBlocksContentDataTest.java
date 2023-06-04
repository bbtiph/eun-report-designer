package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportBlocksContentDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportBlocksContentData.class);
        ReportBlocksContentData reportBlocksContentData1 = new ReportBlocksContentData();
        reportBlocksContentData1.setId(1L);
        ReportBlocksContentData reportBlocksContentData2 = new ReportBlocksContentData();
        reportBlocksContentData2.setId(reportBlocksContentData1.getId());
        assertThat(reportBlocksContentData1).isEqualTo(reportBlocksContentData2);
        reportBlocksContentData2.setId(2L);
        assertThat(reportBlocksContentData1).isNotEqualTo(reportBlocksContentData2);
        reportBlocksContentData1.setId(null);
        assertThat(reportBlocksContentData1).isNotEqualTo(reportBlocksContentData2);
    }
}
