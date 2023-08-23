package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationEunIndicatorMapperTest {

    private OrganizationEunIndicatorMapper organizationEunIndicatorMapper;

    @BeforeEach
    public void setUp() {
        organizationEunIndicatorMapper = new OrganizationEunIndicatorMapperImpl();
    }
}
