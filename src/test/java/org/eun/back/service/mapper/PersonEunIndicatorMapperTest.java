package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonEunIndicatorMapperTest {

    private PersonEunIndicatorMapper personEunIndicatorMapper;

    @BeforeEach
    public void setUp() {
        personEunIndicatorMapper = new PersonEunIndicatorMapperImpl();
    }
}
