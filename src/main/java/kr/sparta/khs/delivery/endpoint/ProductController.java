package kr.sparta.khs.delivery.endpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.product.dto.ProductRequest;
import kr.sparta.khs.delivery.domain.product.dto.ProductResponse;
import kr.sparta.khs.delivery.domain.product.service.ProductService;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import kr.sparta.khs.delivery.util.CommonApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
@CommonApiResponses
@Tag(name = "상품 API", description = "상품 관리 목적의 API Docs")
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("{restaurantId}")
    @Secured({"MANAGER", "MASTER"})
    @Operation(summary = "상품 생성", description = "새로운 상품을 생성")
    public ResponseEntity<Result<String>> createProduct(
            @PathVariable UUID restaurantId, @RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success("상품이 생성되었습니다"));
    }

    @GetMapping("{productId}")
    @Operation(summary = "상품 조회", description = "특정 상품의 상세 정보를 조회")
    public ResponseEntity<Result<ProductResponse>> getProduct(@PathVariable UUID productId) {
        ProductResponse productResponse = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(productResponse));
    }

    @PutMapping("/{productId}")
    @Secured({"MANAGER", "MASTER"})
    @Operation(summary = "상품 수정", description = "기존 상품의 정보를 수정")
    public ResponseEntity<Result<String>> updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductRequest productRequest,
            SecurityUserDetails userDetails) {

        productService.updateProduct(productId, productRequest, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success("수정 성공"));
    }

    @DeleteMapping("/{productId}")
    @Secured({"MANAGER", "MASTER"})
    @Operation(summary = "상품 삭제", description = "기존 상품을 삭제")
    public ResponseEntity<Result<String>> deleteProduct(
            @PathVariable UUID productId, SecurityUserDetails userDetails) {
        productService.deleteProduct(productId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success("상품 삭제 완료"));
    }
}