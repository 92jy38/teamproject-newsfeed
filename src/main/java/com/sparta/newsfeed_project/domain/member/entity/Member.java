package com.sparta.newsfeed_project.domain.member.entity;

import com.sparta.newsfeed_project.domain.common.entity.Timestamped;
import jakarta.persistence.*;

@Entity
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String username;
}
