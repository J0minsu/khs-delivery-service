package kr.sparta.khs.delivery.domain.restaurant.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import java.util.List;
import java.util.UUID;

@Entity(name = "p_restaurant")
@Getter
@DynamicUpdate
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Restaurant extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "restaurant_id")
   private UUID id;

   @Column(name = "restaurant_name" , nullable = false)
   private String name;

   @Column(name = "restaurant_address" , nullable = false)
   private String address;

   @Column(name = "restaurant_phone")
   private String phone;

   @Column(name = "restaurant_min_price")
   @Comment("최소주문금액")
   private int minPrice;

   @Column(name = "restaurant_operation_hours")
   @Comment("운영시간")
   private String operationHours;

   @Column(name = "restaurant_closed_days")
   @Comment("휴일")
   private String closedDays;

   @Column(name = "restaurant_delivery_tip")
   @Comment("배달팁")
   private String deliveryTip;

   @Column(name = "restaurant_status" , nullable = false)
   @Comment("가게 운영상태")
   private String status;


   @OneToMany(mappedBy = "restaurant")
   private List<Product> products;


   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @ManyToOne
   @JoinColumn(name = "food_category_id" , nullable = false)
   private FoodCategory foodCategory;


   public static Restaurant of(RestaurantCreateRequest request,FoodCategory foodCategory , User user) {
        return Restaurant.builder()
                 .name(request.getName())
                 .address(request.getAddress())
                 .phone(request.getPhone())
                 .minPrice(request.getMinPrice())
                 .operationHours(request.getOperationHours())
                 .closedDays(request.getClosedDays())
                 .deliveryTip(request.getDeliveryTip())
                 .status(request.getStatus())
                 .foodCategory(foodCategory)
                 .user(user)
                 .build();

   }

   public void addProduct(Product product) {
      products.add(product);
   }

}
