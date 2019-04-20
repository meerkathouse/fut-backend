package com.meerkat.house.fut.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModel {
    private String id;
    private String nickname;
    private String social;
    private String imageUrl;
}
