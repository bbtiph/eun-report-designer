package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventParticipantMapperTest {

    private EventParticipantMapper eventParticipantMapper;

    @BeforeEach
    public void setUp() {
        eventParticipantMapper = new EventParticipantMapperImpl();
    }
}
