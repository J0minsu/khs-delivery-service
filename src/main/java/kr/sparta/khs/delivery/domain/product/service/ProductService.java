package kr.sparta.khs.delivery.domain.product.service;

import kr.sparta.khs.delivery.domain.product.dto.ProductRequest;
import kr.sparta.khs.delivery.domain.product.dto.ProductResponse;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.product.repository.ProductRepository;
import kr.sparta.khs.delivery.domain.restaurant.entity.Restaurant;
import kr.sparta.khs.delivery.domain.restaurant.repository.RestaurantRepository;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void createProduct(ProductRequest productRequest,UUID restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 restaurant ID 입니다"));
        Product product = Product.createProduct(productRequest, restaurant);
        productRepository.save(product);
    }


    @Transactional(readOnly = true)
    public ProductResponse getProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("잘못된 restaurant ID 입니다"));
        return ProductResponse.fromEntity(product);
    }


    @Transactional
    public void updateProduct(UUID productId, ProductRequest productRequest, SecurityUserDetails userDetails) {
        int userId = userDetails.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID 입니다."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 상품 입니다"));


        if (product.getRestaurant().getUser().equals(user) || user.getAuthType().equals(AuthType.MASTER)) {

            product.updateProduct(productRequest);

            productRepository.save(product);
        }

        }




    @Transactional
    public void deleteProduct(UUID id,SecurityUserDetails userDetails) {
        int userId = userDetails.getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

        Product product = productRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("존재 하지않는 상품 입니다"));

        if (product.isDeleted()) throw new IllegalArgumentException("이미 삭제 요청된 상품 입니다. "+"삭제 요청일"+product.getDeletedAt());

        if (product.getRestaurant().getUser().equals(user) || user.getAuthType().equals(AuthType.MASTER)) {
            product.delete(user.getId());
            productRepository.save(product);
        } else {
            throw new SecurityException("권한이 없습니다.");
        }

    }
}
