package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.product.dto.ProductRequest;
import kr.sparta.khs.delivery.domain.product.dto.ProductResponse;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Secured({"MANAGER","MASTER"})
    @PostMapping("{restaurantId}")
    public ResponseEntity<String> createProduct(
            @PathVariable UUID restaurantId,@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body("상품이 생성됐습니다");

    }


    @GetMapping("{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID productId) {
        ProductResponse productResponse=  productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @Secured({"MANAGER","MASTER"})
    @PutMapping
    public ResponseEntity<String> updateProduct() {

        return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
    }

}
