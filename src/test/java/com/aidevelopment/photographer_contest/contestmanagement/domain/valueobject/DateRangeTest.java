package com.aidevelopment.photographer_contest.contestmanagement.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateRangeTest {

    @Test
    void shouldCreateDateRangeWhenEndDateIsEqualToOrAfterStartDate() {
        var start = LocalDate.of(2026, 4, 1);
        var end = LocalDate.of(2026, 4, 30);

        var dateRange = new DateRange(start, end);

        assertThat(dateRange.startDate()).isEqualTo(start);
        assertThat(dateRange.endDate()).isEqualTo(end);
    }

    @Test
    void shouldFailWhenEndDateIsBeforeStartDate() {
        assertThatThrownBy(() -> new DateRange(LocalDate.of(2026, 4, 30), LocalDate.of(2026, 4, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End date cannot be before start date");
    }
}
