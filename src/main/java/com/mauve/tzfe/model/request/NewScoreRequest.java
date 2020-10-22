package com.mauve.tzfe.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewScoreRequest {
    @NotNull
    Integer score;
}
