package com.aidevelopment.photographer_contest.identityaccess.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UsernameTest {

    @Test
    void shouldCreateUsernameWhenValueIsValid() {
        var username = new Username("alice_photo");

        assertThat(username.value()).isEqualTo("alice_photo");
    }

    @Test
    void shouldFailWhenUsernameIsBlank() {
        assertThatThrownBy(() -> new Username(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username cannot be blank");
    }
}
