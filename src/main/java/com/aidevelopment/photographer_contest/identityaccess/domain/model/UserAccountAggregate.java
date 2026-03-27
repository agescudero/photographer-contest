package com.aidevelopment.photographer_contest.identityaccess.domain.model;

import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.PasswordHash;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.Username;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class UserAccountAggregate {

    private final UserAccount account;
    private final ParticipantProfile profile;

    private UserAccountAggregate(UserAccount account, ParticipantProfile profile) {
        this.account = Objects.requireNonNull(account, "account cannot be null");
        this.profile = profile;
    }

    public static UserAccountAggregate registerParticipant(
            UUID userId,
            Username username,
            PasswordHash passwordHash,
            Instant createdAt
    ) {
        var account = UserAccount.registerParticipant(userId, username, passwordHash, createdAt);
        return new UserAccountAggregate(account, null);
    }

    public UserAccountAggregate updateProfile(ParticipantProfile newProfile) {
        Objects.requireNonNull(newProfile, "newProfile cannot be null");
        if (!newProfile.userId().equals(account.id())) {
            throw new IllegalArgumentException("Profile userId must match account id");
        }
        return new UserAccountAggregate(account, newProfile);
    }

    public UserAccount account() {
        return account;
    }

    public Optional<ParticipantProfile> profile() {
        return Optional.ofNullable(profile);
    }

    public boolean hasProfile() {
        return profile != null;
    }
}
