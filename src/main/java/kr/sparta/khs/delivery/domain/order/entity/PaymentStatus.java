package kr.sparta.khs.delivery.domain.order.entity;

public enum PaymentStatus {
    NOT_PAID,       // 결제 전
    PENDING,        // 결제 대기
    IN_PROGRESS,    // 결제 중
    COMPLETED,       // 결제 완료
    CANCELLED       //결제 취소
}
