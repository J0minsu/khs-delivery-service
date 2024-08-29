package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.restaurant.dto.UpdateRestaurantRequest;
import kr.sparta.khs.delivery.domain.restaurant.service.RestaurantService;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantResponse;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable UUID id) {
        RestaurantResponse restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> getAllRestaurants(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC")
            SortStandard sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, sort.getSort());

        Page<RestaurantResponse> restaurants = restaurantService.getAllRestaurants(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @GetMapping("/search/{restaurantName}")
    public ResponseEntity<Page<RestaurantResponse>> getSearchedRestaurants(
            @PathVariable String restaurantName,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC")
            SortStandard sort
    ) {


        Pageable pageable = PageRequest.of(pageNumber, size, sort.getSort());

        Page<RestaurantResponse> restaurants = restaurantService.getSearchedRestaurants(restaurantName, pageable);


        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @PostMapping
    @Secured({"MANAGER", "MASTER"})
    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantCreateRequest request) {
        restaurantService.createRestaurant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant created successfully");
    }

    @PutMapping("/{id}")
    @Secured({"MANAGER", "MASTER"})
    public ResponseEntity<String> updateRestaurant(@PathVariable UUID id, UpdateRestaurantRequest request) {
        restaurantService.updateRestaurant(id, request);
        return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
    }


    @DeleteMapping("/{id}")
    @Secured({"MANAGER", "MASTER"})
    public ResponseEntity<String> deleteRestaurant(@PathVariable UUID id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body("레스토랑 삭제 완료");
    }





}
