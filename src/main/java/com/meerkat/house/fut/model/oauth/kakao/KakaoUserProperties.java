package com.meerkat.house.fut.model.oauth.kakao;

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
public class KakaoUserProperties implements Serializable {
    private String nickname;

    @JsonProperty("thumbnail_image")
    private String thumbnailImage;

    @JsonProperty("profile_image")
    private String profileImage;

}
