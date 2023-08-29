package org.eun.back.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParticipantsEunIndicatorMapperTest {

    private ParticipantsEunIndicatorMapper participantsEunIndicatorMapper;

    @BeforeEach
    public void setUp() {
        participantsEunIndicatorMapper = new ParticipantsEunIndicatorMapperImpl();
    }
}
