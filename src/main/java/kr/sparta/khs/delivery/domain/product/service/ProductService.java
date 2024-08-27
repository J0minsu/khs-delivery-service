package kr.sparta.khs.delivery.domain.product.service;

import kr.sparta.khs.delivery.domain.product.dto.ProductRequest;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.product.repository.ProductRepository;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    public ProductService(ProductRepository productRepository, RestaurantRepository restaurantRepository) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
    }
    @Transactional
    public void createProduct(ProductRequest productRequest) {

        UUID restaurantId = productRequest.getRestaurantId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid restaurant ID"));

        Product product = Product.of(productRequest,restaurant);

        restaurant.addProduct(product);

        productRepository.save(product);
        restaurantRepository.save(restaurant);
    }
}
