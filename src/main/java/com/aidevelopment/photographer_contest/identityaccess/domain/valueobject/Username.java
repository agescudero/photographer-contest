package com.aidevelopment.photographer_contest.identityaccess.domain.valueobject;

public record Username(String value) {

    public Username {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
    }
}
