package com.meerkat.house.fut.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Account extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;

    private String id;
    private String email;
    private String password;
    private String nickname;
    private String name;
    private String social;

    @Column(name = "client_type")
    private String clientType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;
}
