package com.aidevelopment.photographer_contest.identityaccess.domain.valueobject;

public record ExtraFieldValue(String key, String value) {

    public ExtraFieldValue {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Extra field key cannot be blank");
        }
        if (value == null) {
            throw new IllegalArgumentException("Extra field value cannot be null");
        }
    }
}
