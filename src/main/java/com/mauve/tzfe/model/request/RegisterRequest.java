package com.mauve.tzfe.model.request;

import com.sun.istack.internal.NotNull;
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
