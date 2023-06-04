package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportBlocksContentMapperTest {

    private ReportBlocksContentMapper reportBlocksContentMapper;

    @BeforeEach
    public void setUp() {
        reportBlocksContentMapper = new ReportBlocksContentMapperImpl();
    }
}
