package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonInProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInProject.class);
        PersonInProject personInProject1 = new PersonInProject();
        personInProject1.setId(1L);
        PersonInProject personInProject2 = new PersonInProject();
        personInProject2.setId(personInProject1.getId());
        assertThat(personInProject1).isEqualTo(personInProject2);
        personInProject2.setId(2L);
        assertThat(personInProject1).isNotEqualTo(personInProject2);
        personInProject1.setId(null);
        assertThat(personInProject1).isNotEqualTo(personInProject2);
    }
}
