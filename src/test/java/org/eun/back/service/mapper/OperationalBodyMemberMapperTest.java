package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationalBodyMemberMapperTest {

    private OperationalBodyMemberMapper operationalBodyMemberMapper;

    @BeforeEach
    public void setUp() {
        operationalBodyMemberMapper = new OperationalBodyMemberMapperImpl();
    }
}
