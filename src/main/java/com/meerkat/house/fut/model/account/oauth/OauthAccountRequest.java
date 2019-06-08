package com.meerkat.house.fut.model.account.oauth;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OauthAccountRequest implements Serializable {

    @NotNull
    private String tokenType;

    @NotNull
    private String accessToken;

    @NotNull
    private String social;
}
