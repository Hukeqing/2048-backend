package com.mauve.tzfe.model.request;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DealRequest {
    @NotNull
    Integer id;
}
