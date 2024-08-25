package kr.sparta.khs.delivery.domain.foodcategory.repository;

import kr.sparta.khs.delivery.domain.foodcategory.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, UUID> {
}
