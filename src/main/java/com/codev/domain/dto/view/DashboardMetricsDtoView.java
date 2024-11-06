package com.codev.domain.dto.view;

import lombok.Data;

@Data
public class DashboardMetricsDtoView {
    private Long countToBegin;
    private Long countInProgress;
    private Long countFinished;

    private String currentMonth;
    private Long challengesCreatedThisMonth;
    private Long challengesToReachRecord;
    private Long highestStreak;

    public DashboardMetricsDtoView(Long countToBegin, Long countInProgress, Long countFinished, String currentMonth, Long challengesCreatedThisMonth, Long challengesToReachRecord, Long highestStreak) {
        this.countToBegin = countToBegin;
        this.countInProgress = countInProgress;
        this.countFinished = countFinished;
        this.currentMonth = currentMonth;
        this.challengesCreatedThisMonth = challengesCreatedThisMonth;
        this.challengesToReachRecord = challengesToReachRecord;
        this.highestStreak = highestStreak;
    }

    public DashboardMetricsDtoView() {}
}
