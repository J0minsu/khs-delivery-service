package kr.sparta.khs.delivery.domain.order.entity;

public enum OrderStatus {
    REQUESTED,  // 신청
    ACCEPT,     // 승락
    COOKING,    // 조리 중
    CANCELED,   // 취소
    COMPLETED   // 완료
}