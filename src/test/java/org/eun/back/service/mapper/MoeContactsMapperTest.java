package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoeContactsMapperTest {

    private MoeContactsMapper moeContactsMapper;

    @BeforeEach
    public void setUp() {
        moeContactsMapper = new MoeContactsMapperImpl();
    }
}
