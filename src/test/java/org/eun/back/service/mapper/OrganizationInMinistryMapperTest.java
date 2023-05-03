package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizationInMinistryMapperTest {

    private OrganizationInMinistryMapper organizationInMinistryMapper;

    @BeforeEach
    public void setUp() {
        organizationInMinistryMapper = new OrganizationInMinistryMapperImpl();
    }
}
