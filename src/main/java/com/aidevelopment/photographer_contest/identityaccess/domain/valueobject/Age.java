package com.aidevelopment.photographer_contest.identityaccess.domain.valueobject;

public record Age(int value) {

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;

    public Age {
        if (value < MIN_AGE || value > MAX_AGE) {
            throw new IllegalArgumentException("Age must be between 18 and 120");
        }
    }
}
