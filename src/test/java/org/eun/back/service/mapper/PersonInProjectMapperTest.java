package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonInProjectMapperTest {

    private PersonInProjectMapper personInProjectMapper;

    @BeforeEach
    public void setUp() {
        personInProjectMapper = new PersonInProjectMapperImpl();
    }
}
