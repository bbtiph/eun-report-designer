package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonEunIndicatorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonEunIndicatorDTO.class);
        PersonEunIndicatorDTO personEunIndicatorDTO1 = new PersonEunIndicatorDTO();
        personEunIndicatorDTO1.setId(1L);
        PersonEunIndicatorDTO personEunIndicatorDTO2 = new PersonEunIndicatorDTO();
        assertThat(personEunIndicatorDTO1).isNotEqualTo(personEunIndicatorDTO2);
        personEunIndicatorDTO2.setId(personEunIndicatorDTO1.getId());
        assertThat(personEunIndicatorDTO1).isEqualTo(personEunIndicatorDTO2);
        personEunIndicatorDTO2.setId(2L);
        assertThat(personEunIndicatorDTO1).isNotEqualTo(personEunIndicatorDTO2);
        personEunIndicatorDTO1.setId(null);
        assertThat(personEunIndicatorDTO1).isNotEqualTo(personEunIndicatorDTO2);
    }
}
