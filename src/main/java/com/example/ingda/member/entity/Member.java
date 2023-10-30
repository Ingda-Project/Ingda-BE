package com.example.ingda.member.entity;

import com.example.ingda.common.audit.BaseEntity;
import com.example.ingda.common.type.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private UserRoleType userRole;

    @Column
    private LocalDateTime active;

    @Column(nullable = false)
    private int reviewCount;

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void changeAccountActivation(){
        this.active =  null;
    }

    public void changeAccountInactivation() {
        this.active = LocalDateTime.now();
    }
}
