package com.meerkat.house.fut.model.account.oauth.kakao;

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
public class KakaoUserAccountResponse implements Serializable {
    @JsonProperty("has_email")
    private Boolean hasEmail;

    @JsonProperty("is_email_valid")
    private Boolean isEmailValid;

    @JsonProperty("is_email_verified")
    private Boolean isEmailVerified;

    private String email;

    @JsonProperty("has_age_range")
    private Boolean hasAgeRange;

    @JsonProperty("age_range")
    private String ageRange;

    @JsonProperty("has_birthday")
    private Boolean hasBirthday;

    private String birthday;

    @JsonProperty("has_gender")
    private Boolean hasGender;

    private String gender;
}

