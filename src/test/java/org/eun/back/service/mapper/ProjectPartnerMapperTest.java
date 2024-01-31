package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectPartnerMapperTest {

    private ProjectPartnerMapper projectPartnerMapper;

    @BeforeEach
    public void setUp() {
        projectPartnerMapper = new ProjectPartnerMapperImpl();
    }
}
