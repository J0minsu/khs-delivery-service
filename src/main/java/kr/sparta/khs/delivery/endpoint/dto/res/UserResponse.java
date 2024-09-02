package kr.sparta.khs.delivery.endpoint.dto.res;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private int id;
    private String name;
    private String email;
    private String contact;
    private String address;
    
}
