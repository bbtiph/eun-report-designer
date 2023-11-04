package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeneratedReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneratedReport.class);
        GeneratedReport generatedReport1 = new GeneratedReport();
        generatedReport1.setId(1L);
        GeneratedReport generatedReport2 = new GeneratedReport();
        generatedReport2.setId(generatedReport1.getId());
        assertThat(generatedReport1).isEqualTo(generatedReport2);
        generatedReport2.setId(2L);
        assertThat(generatedReport1).isNotEqualTo(generatedReport2);
        generatedReport1.setId(null);
        assertThat(generatedReport1).isNotEqualTo(generatedReport2);
    }
}
