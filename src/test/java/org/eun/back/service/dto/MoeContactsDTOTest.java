package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoeContactsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoeContactsDTO.class);
        MoeContactsDTO moeContactsDTO1 = new MoeContactsDTO();
        moeContactsDTO1.setId(1L);
        MoeContactsDTO moeContactsDTO2 = new MoeContactsDTO();
        assertThat(moeContactsDTO1).isNotEqualTo(moeContactsDTO2);
        moeContactsDTO2.setId(moeContactsDTO1.getId());
        assertThat(moeContactsDTO1).isEqualTo(moeContactsDTO2);
        moeContactsDTO2.setId(2L);
        assertThat(moeContactsDTO1).isNotEqualTo(moeContactsDTO2);
        moeContactsDTO1.setId(null);
        assertThat(moeContactsDTO1).isNotEqualTo(moeContactsDTO2);
    }
}
