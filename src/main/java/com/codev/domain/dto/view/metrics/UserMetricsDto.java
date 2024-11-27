package com.codev.domain.dto.view.metrics;

import lombok.Data;

@Data
public class UserMetricsDto {
    private Long participantsCount;
    private Long challengesCount;
    private Long highestStreak;
    private CurrentMonthMetricsDto currentMonth;

    public UserMetricsDto(Long participantsCount, Long challengesCount, Long highestStreak, CurrentMonthMetricsDto currentMonth) {
        this.participantsCount = participantsCount;
        this.challengesCount = challengesCount;
        this.highestStreak = highestStreak;
        this.currentMonth = currentMonth;
    }

    public UserMetricsDto() {}
}
