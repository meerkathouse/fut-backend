package com.meerkat.house.fut.model.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {
    private Account account;

    private String tokenType;

    private String accessToken;
    private Long accessTokenExpireTime;

    private String refreshToken;
    private Long refreshTokenExpireTime;
}
