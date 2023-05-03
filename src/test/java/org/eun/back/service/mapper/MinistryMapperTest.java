package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MinistryMapperTest {

    private MinistryMapper ministryMapper;

    @BeforeEach
    public void setUp() {
        ministryMapper = new MinistryMapperImpl();
    }
}
