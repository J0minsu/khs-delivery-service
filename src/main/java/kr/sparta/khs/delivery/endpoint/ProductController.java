package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.product.dto.ProductRequest;
import kr.sparta.khs.delivery.domain.product.dto.ProductResponse;
import kr.sparta.khs.delivery.domain.product.entity.Product;
import kr.sparta.khs.delivery.domain.product.service.ProductService;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Secured({"MANAGER","MASTER"})
    @PostMapping("{restaurantId}")
    public ResponseEntity<Result<String>> createProduct(
            @PathVariable UUID restaurantId,@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success("상품이 생성됐습니다"));

    }


    @GetMapping("{productId}")
    public ResponseEntity<Result<ProductResponse>> getProduct(@PathVariable UUID productId) {
        ProductResponse productResponse=  productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(productResponse));
    }

    @Secured({"MANAGER","MASTER"})
    @PutMapping("/{productId}")
    public ResponseEntity<Result<String>> updateProduct(@PathVariable UUID productId,
                                                @RequestBody ProductRequest productRequest,
                                                SecurityUserDetails userDetails) {

        productService.updateProduct(productId, productRequest,userDetails);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("수정 성공"));
    }


    @Secured({"MANAGER","MASTER"})
    @DeleteMapping("/{productId}")
    public ResponseEntity<Result<String>> deleteProduct(@PathVariable UUID productId, SecurityUserDetails userDetails) {
        productService.deleteProduct(productId,userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(Result.success("레스토랑 삭제 완료"));

    }

}
