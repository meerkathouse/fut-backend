package com.meerkat.house.fut.model.kakao.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties
public class KakaoUserResponse implements Serializable {
    private String id;
    private KakaoUserProperties properties;

    @JsonProperty("kakao_account")
    private KakaoUserAccountResponse kakaoAccount;
}