package kr.sparta.khs.delivery.webclient.service.dto.res;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SafetyRating {
    private String probability;
    private String category;

}
