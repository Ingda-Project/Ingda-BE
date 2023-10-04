package com.example.ingda.member.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private Long score;

    @Column
    private Boolean active;

    @Column(nullable = false)
    private int reviewCount;

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updatePassword(String password){
        this.password = password;
    }

}
