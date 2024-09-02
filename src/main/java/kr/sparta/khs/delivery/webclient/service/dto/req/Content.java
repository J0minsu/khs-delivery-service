package kr.sparta.khs.delivery.webclient.service.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Content {
    private List<Part> parts;

}
