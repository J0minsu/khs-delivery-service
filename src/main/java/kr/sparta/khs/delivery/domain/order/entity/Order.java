package kr.sparta.khs.delivery.domain.order.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.orderProduct.entity.OrderProduct;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.endpoint.dto.req.CreateOrderRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.OrderProductDto;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static kr.sparta.khs.delivery.domain.order.entity.OrderStatus.REQUESTED;
import static kr.sparta.khs.delivery.domain.order.entity.PaymentStatus.NOT_PAID;

@Entity
@Getter
@ToString
@Builder
@Table(name = "p_order")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
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
    private Integer amount; //배달료

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>(); //Cascade     생성 ,                수정,              제거
    //order가 생성되면 orderProduct를 넣는다.


    public Order(User user, Restaurant restaurant, OrderType type, OrderStatus orderStatus, Integer payAmount, DeliveryStatus deliveryStatus, String requirement, Integer amount) {
        this.user = user;
        this.restaurant = restaurant;
        this.type = type;
        this.orderStatus = orderStatus;
        this.payAmount = payAmount;
        this.paymentStatus = PaymentStatus.NOT_PAID;
        this.deliveryStatus = deliveryStatus;
        this.requirement = requirement;
        this.amount = amount;
    }
    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public static Order of(User user, Restaurant restaurant, OrderType type, OrderStatus orderStatus, Integer payAmount, DeliveryStatus deliveryStatus, String requirement, Integer amount, List<OrderProductDto> orderProducts) {
        Order order = new Order(user, restaurant, type, orderStatus, payAmount, deliveryStatus, requirement, amount);
        List<OrderProduct> op =new ArrayList<>();
        for(OrderProductDto orderProduct : orderProducts) {
            OrderProduct orderProduct1 = new OrderProduct(orderProduct.getProductName(), orderProduct.getQuantity(),orderProduct.getPrice());
            op.add(orderProduct1);
        }
        order.getOrderProducts().addAll(op);
        return order;
    }

}
