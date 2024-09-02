package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.restaurant.dto.UpdateRestaurantRequest;
import kr.sparta.khs.delivery.domain.restaurant.service.RestaurantService;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantResponse;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Result<RestaurantResponse>> getRestaurantById(@PathVariable UUID id) {
        RestaurantResponse restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(restaurant));
    }

    @GetMapping
    public ResponseEntity<Result<Page<RestaurantResponse>>> getAllRestaurants(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "CREATED_DESC")
            SortStandard sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, sort.getSort());

        Page<RestaurantResponse> restaurants = restaurantService.getAllRestaurants(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(restaurants));
    }

    @GetMapping("/search/{restaurantName}")
    public ResponseEntity<Result<Page<RestaurantResponse>>> getSearchedRestaurants(
            @PathVariable String restaurantName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "CREATED_DESC")
            SortStandard sort
    ) {


        Pageable pageable = PageRequest.of(pageNumber, size, sort.getSort());

        Page<RestaurantResponse> restaurants = restaurantService.getSearchedRestaurants(restaurantName, pageable);


        return ResponseEntity.status(HttpStatus.OK).body(Result.success(restaurants));
    }

    @PostMapping
    @Secured({"MANAGER", "MASTER"})
    public ResponseEntity<Result<String>> createRestaurant(@RequestBody RestaurantCreateRequest request , @AuthenticationPrincipal SecurityUserDetails userDetails ) {
        restaurantService.createRestaurant(request,userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(Result.success("레스토랑 생성 완료"));
    }

    @PutMapping("/{id}")
    @Secured({"MANAGER", "MASTER"})
    public ResponseEntity<Result<String>> updateRestaurant(@PathVariable UUID id, UpdateRestaurantRequest request,SecurityUserDetails userDetails) {
        restaurantService.updateRestaurant(id, request,userDetails);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Result.success("레스토랑 수정 성공"));
    }


    @DeleteMapping("/{id}")
    @Secured({"MANAGER", "MASTER"})
    public ResponseEntity<Result<String>> deleteRestaurant(@PathVariable UUID id,SecurityUserDetails userDetails) {
        restaurantService.deleteRestaurant(id,userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success("레스토랑 삭제 완료"));
    }



}
