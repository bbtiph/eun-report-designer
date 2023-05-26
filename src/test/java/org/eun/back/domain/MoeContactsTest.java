package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoeContactsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoeContacts.class);
        MoeContacts moeContacts1 = new MoeContacts();
        moeContacts1.setId(1L);
        MoeContacts moeContacts2 = new MoeContacts();
        moeContacts2.setId(moeContacts1.getId());
        assertThat(moeContacts1).isEqualTo(moeContacts2);
        moeContacts2.setId(2L);
        assertThat(moeContacts1).isNotEqualTo(moeContacts2);
        moeContacts1.setId(null);
        assertThat(moeContacts1).isNotEqualTo(moeContacts2);
    }
}
