package kr.sparta.khs.delivery.domain.restaurant.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.common.entity.BaseEntity;
import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.dto.UpdateRestaurantRequest;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.List;
import java.util.UUID;

@Entity(name = "p_restaurant")
@Getter
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


   @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
   private List<Product> products;


   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @ManyToOne
   @JoinColumn(name = "food_category_id" , nullable = false)
   private FoodCategory foodCategory;


   public Restaurant(String name, String address, String phone, int minPrice, String operationHours,
                     String closedDays, String deliveryTip, String status, FoodCategory foodCategory, User user) {
      this.name = name;
      this.address = address;
      this.phone = phone;
      this.minPrice = minPrice;
      this.operationHours = operationHours;
      this.closedDays = closedDays;
      this.deliveryTip = deliveryTip;
      this.status = status;
      this.foodCategory = foodCategory;
      this.user = user;
   }

   public static Restaurant createRestaurant(RestaurantCreateRequest request, FoodCategory foodCategory, User user) {
      return new Restaurant(
              request.getName(),
              request.getAddress(),
              request.getPhone(),
              request.getMinPrice(),
              request.getOperationHours(),
              request.getClosedDays(),
              request.getDeliveryTip(),
              request.getStatus(),
              foodCategory,
              user
      );
   }

   public void addProduct(Product product) {
      products.add(product);
   }

    public void updateRestaurant(UpdateRestaurantRequest request ,FoodCategory foodCategory) {

      this.name = request.getName();
      this.foodCategory = foodCategory;
      this.address = request.getAddress();
      this.phone = request.getPhone();
      this.minPrice = request.getMinPrice();
      this.operationHours = request.getOperationHours();
      this.closedDays = request.getClosedDays();
      this.deliveryTip = request.getDeliveryTip();
      this.status = request.getStatus();

    }
}
