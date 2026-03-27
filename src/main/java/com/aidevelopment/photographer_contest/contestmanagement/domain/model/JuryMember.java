package com.aidevelopment.photographer_contest.contestmanagement.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class JuryMember {

    private final UUID id;
    private final UUID contestId;
    private final String displayName;

    public JuryMember(UUID id, UUID contestId, String displayName) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.contestId = Objects.requireNonNull(contestId, "contestId cannot be null");
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("displayName cannot be blank");
        }
        this.displayName = displayName;
    }

    public UUID id() {
        return id;
    }

    public UUID contestId() {
        return contestId;
    }

    public String displayName() {
        return displayName;
    }
}
