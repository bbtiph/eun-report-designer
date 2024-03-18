package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MOEParticipationReferencesMapperTest {

    private MOEParticipationReferencesMapper mOEParticipationReferencesMapper;

    @BeforeEach
    public void setUp() {
        mOEParticipationReferencesMapper = new MOEParticipationReferencesMapperImpl();
    }
}
