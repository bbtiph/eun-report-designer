package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventInOrganizationMapperTest {

    private EventInOrganizationMapper eventInOrganizationMapper;

    @BeforeEach
    public void setUp() {
        eventInOrganizationMapper = new EventInOrganizationMapperImpl();
    }
}
