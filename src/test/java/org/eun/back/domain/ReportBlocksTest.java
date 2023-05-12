package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportBlocksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportBlocks.class);
        ReportBlocks reportBlocks1 = new ReportBlocks();
        reportBlocks1.setId(1L);
        ReportBlocks reportBlocks2 = new ReportBlocks();
        reportBlocks2.setId(reportBlocks1.getId());
        assertThat(reportBlocks1).isEqualTo(reportBlocks2);
        reportBlocks2.setId(2L);
        assertThat(reportBlocks1).isNotEqualTo(reportBlocks2);
        reportBlocks1.setId(null);
        assertThat(reportBlocks1).isNotEqualTo(reportBlocks2);
    }
}
