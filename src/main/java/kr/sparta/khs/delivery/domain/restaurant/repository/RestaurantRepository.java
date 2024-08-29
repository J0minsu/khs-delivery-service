package kr.sparta.khs.delivery.domain.restaurant.repository;

import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Page<Restaurant> findByNameContainingIgnoreCase(String restaurantName, Pageable pageable);
}
