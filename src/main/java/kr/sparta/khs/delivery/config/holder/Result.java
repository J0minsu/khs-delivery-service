package kr.sparta.khs.delivery.config.holder;

import com.sun.net.httpserver.Authenticator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    private String result;
    private T data;
    private String message;

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS, data, null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(FAILURE, null, message);
    }
}
