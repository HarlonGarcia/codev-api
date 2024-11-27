package com.codev.domain.dto.view.metrics;

public record UserMetricsResult(
    long participantsCount,
    long challengesCount,
    long highestStreak,
    long currentMonthStreak,
    long inProgress,
    long toBegin,
    long finished,
    long canceled
) {
    public static UserMetricsResult from(Object[] result) {
        return new UserMetricsResult(
            ((Number) result[0]).longValue(),
            ((Number) result[1]).longValue(),
            ((Number) result[2]).longValue(),
            ((Number) result[3]).longValue(),
            ((Number) result[4]).longValue(),
            ((Number) result[5]).longValue(),
            ((Number) result[6]).longValue(),
            ((Number) result[7]).longValue()
        );
    }
}