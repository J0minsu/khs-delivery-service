package kr.sparta.khs.delivery.domain.order.entity;

public enum DeliveryStatus {
    NOT_DISPATCHED,   // 배송 전
    ASSIGNED,         // 기사 배정
    IN_TRANSIT,       // 배송 중
    DELIVERED         // 배송 완료
}