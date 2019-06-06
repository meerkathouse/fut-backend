package com.meerkat.house.fut.model.account;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;

    private String id;
    private String social;
    private String email;
    private String name;
    private String nickname;
    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_need_refresh")
    private Boolean isNeedRefresh = false;
}