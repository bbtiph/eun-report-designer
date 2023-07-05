package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkingGroupReferencesMapperTest {

    private WorkingGroupReferencesMapper workingGroupReferencesMapper;

    @BeforeEach
    public void setUp() {
        workingGroupReferencesMapper = new WorkingGroupReferencesMapperImpl();
    }
}
