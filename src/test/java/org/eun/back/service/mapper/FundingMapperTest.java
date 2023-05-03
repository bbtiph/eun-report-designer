package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FundingMapperTest {

    private FundingMapper fundingMapper;

    @BeforeEach
    public void setUp() {
        fundingMapper = new FundingMapperImpl();
    }
}
