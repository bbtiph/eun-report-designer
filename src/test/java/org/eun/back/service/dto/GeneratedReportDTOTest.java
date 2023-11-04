package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeneratedReportDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneratedReportDTO.class);
        GeneratedReportDTO generatedReportDTO1 = new GeneratedReportDTO();
        generatedReportDTO1.setId(1L);
        GeneratedReportDTO generatedReportDTO2 = new GeneratedReportDTO();
        assertThat(generatedReportDTO1).isNotEqualTo(generatedReportDTO2);
        generatedReportDTO2.setId(generatedReportDTO1.getId());
        assertThat(generatedReportDTO1).isEqualTo(generatedReportDTO2);
        generatedReportDTO2.setId(2L);
        assertThat(generatedReportDTO1).isNotEqualTo(generatedReportDTO2);
        generatedReportDTO1.setId(null);
        assertThat(generatedReportDTO1).isNotEqualTo(generatedReportDTO2);
    }
}
