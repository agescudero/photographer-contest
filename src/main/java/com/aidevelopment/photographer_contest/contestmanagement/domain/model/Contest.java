package com.aidevelopment.photographer_contest.contestmanagement.domain.model;

import com.aidevelopment.photographer_contest.contestmanagement.domain.valueobject.DateRange;

import java.util.Objects;
import java.util.UUID;

public final class Contest {

    private final UUID id;
    private final String title;
    private final String rules;
    private final DateRange dateRange;
    private final ContestStatus status;

    public Contest(UUID id, String title, String rules, DateRange dateRange, ContestStatus status) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.title = requireText(title, "title");
        this.rules = requireText(rules, "rules");
        this.dateRange = Objects.requireNonNull(dateRange, "dateRange cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value;
    }

    public Contest withStatus(ContestStatus newStatus) {
        return new Contest(id, title, rules, dateRange, newStatus);
    }

    public UUID id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String rules() {
        return rules;
    }

    public DateRange dateRange() {
        return dateRange;
    }

    public ContestStatus status() {
        return status;
    }
}
