package com.mauve.tzfe.model.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddFriendRequest {
    @NotNull
    private String account;
}
