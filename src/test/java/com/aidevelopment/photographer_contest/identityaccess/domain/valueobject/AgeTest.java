package com.aidevelopment.photographer_contest.identityaccess.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AgeTest {

    @Test
    void shouldCreateAgeWhenValueInRange() {
        var age = new Age(18);

        assertThat(age.value()).isEqualTo(18);
    }

    @Test
    void shouldFailWhenAgeOutOfRange() {
        assertThatThrownBy(() -> new Age(17))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Age must be between 18 and 120");
    }
}
