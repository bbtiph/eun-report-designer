package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EunTeamMapperTest {

    private EunTeamMapper eunTeamMapper;

    @BeforeEach
    public void setUp() {
        eunTeamMapper = new EunTeamMapperImpl();
    }
}
