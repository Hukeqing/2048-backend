package com.mauve.tzfe.model.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String account;
    @NotNull
    private String password;
}
