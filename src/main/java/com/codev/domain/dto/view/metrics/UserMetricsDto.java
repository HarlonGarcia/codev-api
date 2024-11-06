package com.codev.domain.dto.view.metrics;

import lombok.Data;

@Data
public class UserMetricsDto {
    private Long participantsCount;
    private Long challengesCount;
    private Long highestStreak;
    private String currentMonth;
    private CurrentMonthMetricsDto currentMonthMetrics;

    public UserMetricsDto(Long participantsCount, Long challengesCount, Long highestStreak,
                          String currentMonth, CurrentMonthMetricsDto currentMonthMetrics) {
        this.participantsCount = participantsCount;
        this.challengesCount = challengesCount;
        this.highestStreak = highestStreak;
        this.currentMonth = currentMonth;
        this.currentMonthMetrics = currentMonthMetrics;
    }

    public UserMetricsDto() {}
}
