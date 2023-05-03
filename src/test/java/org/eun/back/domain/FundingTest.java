package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FundingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funding.class);
        Funding funding1 = new Funding();
        funding1.setId(1L);
        Funding funding2 = new Funding();
        funding2.setId(funding1.getId());
        assertThat(funding1).isEqualTo(funding2);
        funding2.setId(2L);
        assertThat(funding1).isNotEqualTo(funding2);
        funding1.setId(null);
        assertThat(funding1).isNotEqualTo(funding2);
    }
}
