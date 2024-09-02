package kr.sparta.khs.delivery.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String name;

    private String description;

    private int price;

    private String status;


}
