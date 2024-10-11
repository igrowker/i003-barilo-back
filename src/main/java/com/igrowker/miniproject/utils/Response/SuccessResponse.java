package com.igrowker.miniproject.utils.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class SuccessResponse<T> {
    private T data;
    private Optional<String> message;
    private HttpStatus status;

    public SuccessResponse(T data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    public SuccessResponse(T data, String message, HttpStatus status) {
        this(data, status);
        this.message = Optional.ofNullable(message);
    }

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<T>(data, HttpStatus.OK);
    }

    public static <T> SuccessResponse<T> of(T data, HttpStatus status) {
        return new SuccessResponse<T>(data, status);
    }

    public static <T> SuccessResponse<T> of(T data, String message) {
        return new SuccessResponse<T>(data, message, HttpStatus.OK);
    }

    public static <T> SuccessResponse<T> of(T data, String message, HttpStatus status) {
        return new SuccessResponse<T>(data, message, status);
    }
}
