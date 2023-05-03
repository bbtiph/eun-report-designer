package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonInProjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInProjectDTO.class);
        PersonInProjectDTO personInProjectDTO1 = new PersonInProjectDTO();
        personInProjectDTO1.setId(1L);
        PersonInProjectDTO personInProjectDTO2 = new PersonInProjectDTO();
        assertThat(personInProjectDTO1).isNotEqualTo(personInProjectDTO2);
        personInProjectDTO2.setId(personInProjectDTO1.getId());
        assertThat(personInProjectDTO1).isEqualTo(personInProjectDTO2);
        personInProjectDTO2.setId(2L);
        assertThat(personInProjectDTO1).isNotEqualTo(personInProjectDTO2);
        personInProjectDTO1.setId(null);
        assertThat(personInProjectDTO1).isNotEqualTo(personInProjectDTO2);
    }
}
