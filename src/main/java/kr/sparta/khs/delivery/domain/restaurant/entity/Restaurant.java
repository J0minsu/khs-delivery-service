package kr.sparta.khs.delivery.domain.restaurant.entity;

import jakarta.persistence.*;
import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.UUID;

@Entity(name = "p_restaurant")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Restaurant {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "restaurant_id")
   private UUID id;

   @Column(name = "restaurant_name")
   private String name;

   @Column(name = "restaurant_address")
   private String address;

   @Column(name = "restaurant_phone")
   private String phone;

   @Column(name = "restaurant_min_price")
   private int min_price;

   @Column(name = "restaurant_operation_hours")
   private String operation_hours;

   @Column(name = "restaurant_closed_days")
   private String closed_days;

   @Column(name = "restaurant_delivery_tip")
   private String delivery_tip;

   @Column(name = "restaurant_status")
   private String status;


   @OneToMany(mappedBy = "Restaurant")
   private ArrayList<Product> products;


   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @ManyToOne
   @JoinColumn(name = "food_category_id" , nullable = false)
   private FoodCategory foodCategory;


}
