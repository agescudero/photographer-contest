package com.aidevelopment.photographer_contest.contestmanagement.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Category {

    private final UUID id;
    private final UUID contestId;
    private final String name;
    private final String description;
    private final CategoryType type;
    private final int photoLimitPerParticipant;

    public Category(
            UUID id,
            UUID contestId,
            String name,
            String description,
            CategoryType type,
            int photoLimitPerParticipant
    ) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.contestId = Objects.requireNonNull(contestId, "contestId cannot be null");
        this.name = requireText(name, "name");
        this.description = requireText(description, "description");
        this.type = Objects.requireNonNull(type, "type cannot be null");
        if (photoLimitPerParticipant <= 0) {
            throw new IllegalArgumentException("photoLimitPerParticipant must be greater than zero");
        }
        this.photoLimitPerParticipant = photoLimitPerParticipant;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value;
    }

    public UUID id() {
        return id;
    }

    public UUID contestId() {
        return contestId;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public CategoryType type() {
        return type;
    }

    public int photoLimitPerParticipant() {
        return photoLimitPerParticipant;
    }
}
