package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonEunIndicatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonEunIndicator.class);
        PersonEunIndicator personEunIndicator1 = new PersonEunIndicator();
        personEunIndicator1.setId(1L);
        PersonEunIndicator personEunIndicator2 = new PersonEunIndicator();
        personEunIndicator2.setId(personEunIndicator1.getId());
        assertThat(personEunIndicator1).isEqualTo(personEunIndicator2);
        personEunIndicator2.setId(2L);
        assertThat(personEunIndicator1).isNotEqualTo(personEunIndicator2);
        personEunIndicator1.setId(null);
        assertThat(personEunIndicator1).isNotEqualTo(personEunIndicator2);
    }
}
