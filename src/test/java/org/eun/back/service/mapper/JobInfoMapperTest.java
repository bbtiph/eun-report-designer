package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JobInfoMapperTest {

    private JobInfoMapper jobInfoMapper;

    @BeforeEach
    public void setUp() {
        jobInfoMapper = new JobInfoMapperImpl();
    }
}
