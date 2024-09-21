package com.igrowker.miniproject.utils.Response;

import java.util.Optional;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
