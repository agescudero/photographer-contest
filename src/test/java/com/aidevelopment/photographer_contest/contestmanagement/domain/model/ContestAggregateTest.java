package com.aidevelopment.photographer_contest.contestmanagement.domain.model;

import com.aidevelopment.photographer_contest.contestmanagement.domain.valueobject.DateRange;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContestAggregateTest {

    @Test
    void shouldCreateContestInOpenStatus() {
        var aggregate = ContestAggregate.createContest(
                UUID.randomUUID(),
                "Wildlife Awards",
                "Contest rules",
                new DateRange(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1))
        );

        assertThat(aggregate.contest().status()).isEqualTo(ContestStatus.OPEN);
        assertThat(aggregate.categories()).isEmpty();
        assertThat(aggregate.juryMembers()).isEmpty();
    }

    @Test
    void shouldAddCategoryWhenBelongsToContestAndIdIsUnique() {
        var contestId = UUID.randomUUID();
        var aggregate = ContestAggregate.createContest(
                contestId,
                "Wildlife Awards",
                "Contest rules",
                new DateRange(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1))
        );
        var category = new Category(
                UUID.randomUUID(),
                contestId,
                "Landscape",
                "Landscape shots",
                CategoryType.FREE,
                3
        );

        var updated = aggregate.addCategory(category);

        assertThat(updated.categories()).containsExactly(category);
    }

    @Test
    void shouldFailWhenAddingCategoryWithDuplicateId() {
        var contestId = UUID.randomUUID();
        var categoryId = UUID.randomUUID();
        var aggregate = ContestAggregate.createContest(
                contestId,
                "Wildlife Awards",
                "Contest rules",
                new DateRange(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1))
        ).addCategory(new Category(
                categoryId,
                contestId,
                "Landscape",
                "Landscape shots",
                CategoryType.FREE,
                3
        ));

        assertThatThrownBy(() -> aggregate.addCategory(new Category(
                categoryId,
                contestId,
                "Portrait",
                "Portrait shots",
                CategoryType.PREMIUM,
                2
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category id is already present in this contest");
    }

    @Test
    void shouldAddJuryMemberWhenBelongsToContestAndIdIsUnique() {
        var contestId = UUID.randomUUID();
        var aggregate = ContestAggregate.createContest(
                contestId,
                "Wildlife Awards",
                "Contest rules",
                new DateRange(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1))
        );
        var juryMember = new JuryMember(UUID.randomUUID(), contestId, "Ana Jury");

        var updated = aggregate.addJuryMember(juryMember);

        assertThat(updated.juryMembers()).containsExactly(juryMember);
    }

    @Test
    void shouldChangeStatusFollowingConfiguredFlow() {
        var aggregate = ContestAggregate.createContest(
                UUID.randomUUID(),
                "Wildlife Awards",
                "Contest rules",
                new DateRange(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1))
        );

        var onVotation = aggregate.changeStatus(ContestStatus.ON_VOTATION);
        var closed = onVotation.changeStatus(ContestStatus.CLOSED);

        assertThat(onVotation.contest().status()).isEqualTo(ContestStatus.ON_VOTATION);
        assertThat(closed.contest().status()).isEqualTo(ContestStatus.CLOSED);
    }

    @Test
    void shouldFailWhenTransitionSkipsConfiguredFlow() {
        var aggregate = ContestAggregate.createContest(
                UUID.randomUUID(),
                "Wildlife Awards",
                "Contest rules",
                new DateRange(LocalDate.of(2026, 5, 1), LocalDate.of(2026, 6, 1))
        );

        assertThatThrownBy(() -> aggregate.changeStatus(ContestStatus.CLOSED))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid contest status transition");
    }
}
