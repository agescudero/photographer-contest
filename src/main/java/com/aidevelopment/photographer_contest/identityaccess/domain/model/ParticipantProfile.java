package com.aidevelopment.photographer_contest.identityaccess.domain.model;

import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.Age;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.Email;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.ExtraFieldValue;
import com.aidevelopment.photographer_contest.identityaccess.domain.valueobject.PhoneNumber;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ParticipantProfile {

    private final UUID userId;
    private final String fullName;
    private final String location;
    private final Email email;
    private final PhoneNumber phone;
    private final Age age;
    private final List<ExtraFieldValue> extraFields;

    public ParticipantProfile(
            UUID userId,
            String fullName,
            String location,
            Email email,
            PhoneNumber phone,
            Age age,
            List<ExtraFieldValue> extraFields
    ) {
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
        this.fullName = requireText(fullName, "fullName");
        this.location = requireText(location, "location");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.phone = Objects.requireNonNull(phone, "phone cannot be null");
        this.age = Objects.requireNonNull(age, "age cannot be null");
        this.extraFields = List.copyOf(Objects.requireNonNullElse(extraFields, List.of()));
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value;
    }

    public UUID userId() {
        return userId;
    }

    public String fullName() {
        return fullName;
    }

    public String location() {
        return location;
    }

    public Email email() {
        return email;
    }

    public PhoneNumber phone() {
        return phone;
    }

    public Age age() {
        return age;
    }

    public List<ExtraFieldValue> extraFields() {
        return extraFields;
    }
}
