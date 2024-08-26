package kr.sparta.khs.delivery.domain.order.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@ToString
@Table(name = "p_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @Column(name = "order_type", nullable = false)
    private OrderType type;  //주문종류(배달, 대면)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus; //주문상태(신청, 조리중, 최소, 완료)상태값 enum으로 만들어달라
    @Column(name = "delivery_address") //대면 주문 시 NULL
    private String address;
    @Column(nullable = false)
    private Integer payAmount;
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus; //결제상태(결제 전,, 결제 대기, 결제 중, 결제 완료, etc)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus; //배송상태(배송 전, 기사 배정, 배송 중, 배송완료)
    @Column
    private String requirement;
    @Column(name = "delivery_amount", nullable = false)
    private Integer amount;

}
