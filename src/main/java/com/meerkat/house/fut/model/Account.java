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
    private String social;
    private String email;
    private String name;
    private String nickname;
    private String password;

    @Column(name = "image_url")
    private String imageUrl;
}
