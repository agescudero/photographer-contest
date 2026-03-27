package com.aidevelopment.photographer_contest.identityaccess.domain.model;

import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.Age;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.Email;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.PasswordHash;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.PhoneNumber;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.Username;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserAccountAggregateTest {

    @Test
    void shouldRegisterParticipantWithActiveAccount() {
        var userId = UUID.randomUUID();

        var aggregate = UserAccountAggregate.registerParticipant(
                userId,
                new Username("alice"),
                new PasswordHash("hashed-value"),
                Instant.now()
        );

        assertThat(aggregate.account().id()).isEqualTo(userId);
        assertThat(aggregate.account().role()).isEqualTo(UserRole.PARTICIPANT);
        assertThat(aggregate.account().status()).isEqualTo(AccountStatus.ACTIVE);
        assertThat(aggregate.hasProfile()).isFalse();
    }

    @Test
    void shouldUpdateProfileWhenUserIdsMatch() {
        var userId = UUID.randomUUID();
        var aggregate = UserAccountAggregate.registerParticipant(
                userId,
                new Username("alice"),
                new PasswordHash("hashed-value"),
                Instant.now()
        );

        var profile = new ParticipantProfile(
                userId,
                "Alice",
                "Madrid",
                new Email("alice@example.com"),
                new PhoneNumber("+34123456789"),
                new Age(28),
                null
        );

        var updated = aggregate.updateProfile(profile);

        assertThat(updated.hasProfile()).isTrue();
        assertThat(updated.profile()).contains(profile);
    }

    @Test
    void shouldFailWhenProfileBelongsToAnotherUser() {
        var aggregate = UserAccountAggregate.registerParticipant(
                UUID.randomUUID(),
                new Username("alice"),
                new PasswordHash("hashed-value"),
                Instant.now()
        );

        var profile = new ParticipantProfile(
                UUID.randomUUID(),
                "Alice",
                "Madrid",
                new Email("alice@example.com"),
                new PhoneNumber("+34123456789"),
                new Age(28),
                null
        );

        assertThatThrownBy(() -> aggregate.updateProfile(profile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Profile userId must match account id");
    }
}
