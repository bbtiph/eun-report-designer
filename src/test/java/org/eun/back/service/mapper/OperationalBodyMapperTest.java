package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationalBodyMapperTest {

    private OperationalBodyMapper operationalBodyMapper;

    @BeforeEach
    public void setUp() {
        operationalBodyMapper = new OperationalBodyMapperImpl();
    }
}
