package kr.sparta.khs.delivery.webclient.service.dto.res;

import lombok.Data;

@Data
public class Part {
    private String text;

    public String getText() { return text; }
    public void setText(String value) { this.text = value; }
}
