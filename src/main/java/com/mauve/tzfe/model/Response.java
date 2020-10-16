package com.mauve.tzfe.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Response<T> success(T data) {
        return new Response<T>(0, "", data);
    }

    public static <T> Response<T> fail(String msg) {
        return new Response<T>(0, msg, null);
    }
}
