package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportBlocksMapperTest {

    private ReportBlocksMapper reportBlocksMapper;

    @BeforeEach
    public void setUp() {
        reportBlocksMapper = new ReportBlocksMapperImpl();
    }
}
