package com.mauve.tzfe.model.request;

import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String account;
    @NotNull
    private String password;
}
