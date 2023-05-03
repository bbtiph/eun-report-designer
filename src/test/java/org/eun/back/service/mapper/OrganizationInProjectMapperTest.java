package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationInProjectMapperTest {

    private OrganizationInProjectMapper organizationInProjectMapper;

    @BeforeEach
    public void setUp() {
        organizationInProjectMapper = new OrganizationInProjectMapperImpl();
    }
}
