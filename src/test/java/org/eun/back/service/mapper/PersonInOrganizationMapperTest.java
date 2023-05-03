package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonInOrganizationMapperTest {

    private PersonInOrganizationMapper personInOrganizationMapper;

    @BeforeEach
    public void setUp() {
        personInOrganizationMapper = new PersonInOrganizationMapperImpl();
    }
}
