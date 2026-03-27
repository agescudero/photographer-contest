package com.aidevelopment.photographer_contest.identityaccess.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    void shouldCreateEmailWhenFormatIsValid() {
        var email = new Email("alice@example.com");

        assertThat(email.value()).isEqualTo("alice@example.com");
    }

    @Test
    void shouldFailWhenEmailFormatIsInvalid() {
        assertThatThrownBy(() -> new Email("invalid-email"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email format is invalid");
    }
}
