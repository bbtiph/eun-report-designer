package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventReferencesMapperTest {

    private EventReferencesMapper eventReferencesMapper;

    @BeforeEach
    public void setUp() {
        eventReferencesMapper = new EventReferencesMapperImpl();
    }
}
