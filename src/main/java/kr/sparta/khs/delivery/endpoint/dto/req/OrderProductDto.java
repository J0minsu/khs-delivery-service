package kr.sparta.khs.delivery.endpoint.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {
    UUID orderId;
    UUID productId;
    int quantity;
}
