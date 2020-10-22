package com.mauve.tzfe.model.request;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull
    String account;
    @NotNull
    String password;
    @NotNull
    String nick;
}
