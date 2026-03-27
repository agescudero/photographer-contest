package com.aidevelopment.photographer_contest.contestmanagement.domain.model;

import com.aidevelopment.photographer_contest.contestmanagement.domain.valueobject.DateRange;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ContestAggregate {

    private final Contest contest;
    private final List<Category> categories;
    private final List<JuryMember> juryMembers;

    private ContestAggregate(Contest contest, List<Category> categories, List<JuryMember> juryMembers) {
        this.contest = Objects.requireNonNull(contest, "contest cannot be null");
        this.categories = List.copyOf(Objects.requireNonNullElse(categories, List.of()));
        this.juryMembers = List.copyOf(Objects.requireNonNullElse(juryMembers, List.of()));
    }

    public static ContestAggregate createContest(UUID contestId, String title, String rules, DateRange dateRange) {
        var contest = new Contest(contestId, title, rules, dateRange, ContestStatus.OPEN);
        return new ContestAggregate(contest, List.of(), List.of());
    }

    public ContestAggregate addCategory(Category category) {
        Objects.requireNonNull(category, "category cannot be null");
        if (!category.contestId().equals(contest.id())) {
            throw new IllegalArgumentException("Category must belong to this contest");
        }
        if (containsCategory(category.id())) {
            throw new IllegalArgumentException("Category id is already present in this contest");
        }
        var updatedCategories = new java.util.ArrayList<>(categories);
        updatedCategories.add(category);
        return new ContestAggregate(contest, updatedCategories, juryMembers);
    }

    public ContestAggregate addJuryMember(JuryMember juryMember) {
        Objects.requireNonNull(juryMember, "juryMember cannot be null");
        if (!juryMember.contestId().equals(contest.id())) {
            throw new IllegalArgumentException("Jury member must belong to this contest");
        }
        if (containsJuryMember(juryMember.id())) {
            throw new IllegalArgumentException("Jury member id is already present in this contest");
        }
        var updatedJury = new java.util.ArrayList<>(juryMembers);
        updatedJury.add(juryMember);
        return new ContestAggregate(contest, categories, updatedJury);
    }

    public ContestAggregate changeStatus(ContestStatus newStatus) {
        Objects.requireNonNull(newStatus, "newStatus cannot be null");
        if (!isValidTransition(contest.status(), newStatus)) {
            throw new IllegalStateException(
                    "Invalid contest status transition from " + contest.status() + " to " + newStatus
            );
        }
        return new ContestAggregate(contest.withStatus(newStatus), categories, juryMembers);
    }

    private boolean containsCategory(UUID categoryId) {
        return categories.stream().anyMatch(category -> category.id().equals(categoryId));
    }

    private boolean containsJuryMember(UUID juryMemberId) {
        return juryMembers.stream().anyMatch(member -> member.id().equals(juryMemberId));
    }

    private boolean isValidTransition(ContestStatus current, ContestStatus next) {
        if (current == next) {
            return true;
        }
        return (current == ContestStatus.OPEN && next == ContestStatus.ON_VOTATION)
                || (current == ContestStatus.ON_VOTATION && next == ContestStatus.CLOSED);
    }

    public Contest contest() {
        return contest;
    }

    public List<Category> categories() {
        return categories;
    }

    public List<JuryMember> juryMembers() {
        return juryMembers;
    }
}
