package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventReferencesParticipantsCategoryMapperTest {

    private EventReferencesParticipantsCategoryMapper eventReferencesParticipantsCategoryMapper;

    @BeforeEach
    public void setUp() {
        eventReferencesParticipantsCategoryMapper = new EventReferencesParticipantsCategoryMapperImpl();
    }
}
