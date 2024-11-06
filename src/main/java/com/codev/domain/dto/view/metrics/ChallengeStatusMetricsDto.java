package com.codev.domain.dto.view.metrics;

import lombok.Data;

@Data
public class ChallengeStatusMetricsDto {
    private Long inProgress;
    private Long toBegin;
    private Long finished;
    private Long canceled;

    public ChallengeStatusMetricsDto(Long inProgress, Long toBegin, Long finished, Long canceled) {
        this.inProgress = inProgress;
        this.toBegin = toBegin;
        this.finished = finished;
        this.canceled = canceled;
    }

    public ChallengeStatusMetricsDto() {}

}
