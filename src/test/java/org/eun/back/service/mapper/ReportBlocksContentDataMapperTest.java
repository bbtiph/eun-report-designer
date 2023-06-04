package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportBlocksContentDataMapperTest {

    private ReportBlocksContentDataMapper reportBlocksContentDataMapper;

    @BeforeEach
    public void setUp() {
        reportBlocksContentDataMapper = new ReportBlocksContentDataMapperImpl();
    }
}
