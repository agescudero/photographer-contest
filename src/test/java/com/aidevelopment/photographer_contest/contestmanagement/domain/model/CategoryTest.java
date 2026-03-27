package com.aidevelopment.photographer_contest.contestmanagement.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryTest {

    @Test
    void shouldFailWhenPhotoLimitPerParticipantIsNotPositive() {
        assertThatThrownBy(() -> new Category(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Portrait",
                "Portrait category",
                CategoryType.FREE,
                0
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("photoLimitPerParticipant must be greater than zero");
    }
}
