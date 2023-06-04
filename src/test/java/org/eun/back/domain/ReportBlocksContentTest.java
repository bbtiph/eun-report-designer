package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReportBlocksContentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportBlocksContent.class);
        ReportBlocksContent reportBlocksContent1 = new ReportBlocksContent();
        reportBlocksContent1.setId(1L);
        ReportBlocksContent reportBlocksContent2 = new ReportBlocksContent();
        reportBlocksContent2.setId(reportBlocksContent1.getId());
        assertThat(reportBlocksContent1).isEqualTo(reportBlocksContent2);
        reportBlocksContent2.setId(2L);
        assertThat(reportBlocksContent1).isNotEqualTo(reportBlocksContent2);
        reportBlocksContent1.setId(null);
        assertThat(reportBlocksContent1).isNotEqualTo(reportBlocksContent2);
    }
}
