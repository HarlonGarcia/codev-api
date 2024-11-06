package com.codev.domain.dto.view.metrics;

import lombok.Data;

@Data
public class CurrentMonthMetricsDto {
    private Long streak;
    private ChallengeStatusMetricsDto challenges;

    public CurrentMonthMetricsDto(Long streak, ChallengeStatusMetricsDto challenges) {
        this.streak = streak;
        this.challenges = challenges;
    }

    public CurrentMonthMetricsDto() {}
}
