package org.eun.back.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.eun.back.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkingGroupReferencesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingGroupReferencesDTO.class);
        WorkingGroupReferencesDTO workingGroupReferencesDTO1 = new WorkingGroupReferencesDTO();
        workingGroupReferencesDTO1.setId(1L);
        WorkingGroupReferencesDTO workingGroupReferencesDTO2 = new WorkingGroupReferencesDTO();
        assertThat(workingGroupReferencesDTO1).isNotEqualTo(workingGroupReferencesDTO2);
        workingGroupReferencesDTO2.setId(workingGroupReferencesDTO1.getId());
        assertThat(workingGroupReferencesDTO1).isEqualTo(workingGroupReferencesDTO2);
        workingGroupReferencesDTO2.setId(2L);
        assertThat(workingGroupReferencesDTO1).isNotEqualTo(workingGroupReferencesDTO2);
        workingGroupReferencesDTO1.setId(null);
        assertThat(workingGroupReferencesDTO1).isNotEqualTo(workingGroupReferencesDTO2);
    }
}
