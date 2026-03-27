package com.aidevelopment.photographer_contest.identityaccess.domain.model;

import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.PasswordHash;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.Username;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class UserAccount {

    private final UUID id;
    private final Username username;
    private final PasswordHash passwordHash;
    private final UserRole role;
    private final AccountStatus status;
    private final Instant createdAt;

    public UserAccount(
            UUID id,
            Username username,
            PasswordHash passwordHash,
            UserRole role,
            AccountStatus status,
            Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.username = Objects.requireNonNull(username, "username cannot be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash cannot be null");
        this.role = Objects.requireNonNull(role, "role cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt cannot be null");
    }

    public static UserAccount registerParticipant(UUID id, Username username, PasswordHash passwordHash, Instant createdAt) {
        return new UserAccount(id, username, passwordHash, UserRole.PARTICIPANT, AccountStatus.ACTIVE, createdAt);
    }

    public UUID id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public PasswordHash passwordHash() {
        return passwordHash;
    }

    public UserRole role() {
        return role;
    }

    public AccountStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }
}
