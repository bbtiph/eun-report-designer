package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReferenceTableSettingsMapperTest {

    private ReferenceTableSettingsMapper referenceTableSettingsMapper;

    @BeforeEach
    public void setUp() {
        referenceTableSettingsMapper = new ReferenceTableSettingsMapperImpl();
    }
}
