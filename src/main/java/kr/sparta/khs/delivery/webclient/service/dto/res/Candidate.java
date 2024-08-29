package kr.sparta.khs.delivery.webclient.service.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Candidate {
    private String finishReason;
    private Long index;
    private List<SafetyRating> safetyRatings;
    private Content content;

}
