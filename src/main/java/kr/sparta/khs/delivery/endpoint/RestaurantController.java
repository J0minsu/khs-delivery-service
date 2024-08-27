package kr.sparta.khs.delivery.endpoint;

import kr.sparta.khs.delivery.domain.restaurant.service.RestaurantService;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantCreateRequest;
import kr.sparta.khs.delivery.domain.restaurant.dto.RestaurantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }



    @PostMapping
    @Secured({"MANAGER","MASTER"})
    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantCreateRequest request) {
        restaurantService.createRestaurant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant created successfully");
    }

    @PutMapping("/{id}")
    @Secured({"MANAGER","MASTER"})
    public ResponseEntity<String>updateRestaurant(@PathVariable UUID id) {
        restaurantService.updateRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable UUID id) {
        RestaurantResponse restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }


}
