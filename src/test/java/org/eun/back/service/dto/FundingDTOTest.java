package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FundingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FundingDTO.class);
        FundingDTO fundingDTO1 = new FundingDTO();
        fundingDTO1.setId(1L);
        FundingDTO fundingDTO2 = new FundingDTO();
        assertThat(fundingDTO1).isNotEqualTo(fundingDTO2);
        fundingDTO2.setId(fundingDTO1.getId());
        assertThat(fundingDTO1).isEqualTo(fundingDTO2);
        fundingDTO2.setId(2L);
        assertThat(fundingDTO1).isNotEqualTo(fundingDTO2);
        fundingDTO1.setId(null);
        assertThat(fundingDTO1).isNotEqualTo(fundingDTO2);
    }
}
