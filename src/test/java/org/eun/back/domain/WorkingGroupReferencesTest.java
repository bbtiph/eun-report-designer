package org.eun.back.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkingGroupReferencesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingGroupReferences.class);
        WorkingGroupReferences workingGroupReferences1 = new WorkingGroupReferences();
        workingGroupReferences1.setId(1L);
        WorkingGroupReferences workingGroupReferences2 = new WorkingGroupReferences();
        workingGroupReferences2.setId(workingGroupReferences1.getId());
        assertThat(workingGroupReferences1).isEqualTo(workingGroupReferences2);
        workingGroupReferences2.setId(2L);
        assertThat(workingGroupReferences1).isNotEqualTo(workingGroupReferences2);
        workingGroupReferences1.setId(null);
        assertThat(workingGroupReferences1).isNotEqualTo(workingGroupReferences2);
    }
}
