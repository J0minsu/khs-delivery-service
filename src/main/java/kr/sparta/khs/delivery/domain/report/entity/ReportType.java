package kr.sparta.khs.delivery.domain.report.entity;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import lombok.Getter;

@Getter
public enum ReportType {

    ORDER,
    DELIVERY,
    RESTAURANT,
    PRODUCT,
    REVIEW,
    AI,
    ;


}
