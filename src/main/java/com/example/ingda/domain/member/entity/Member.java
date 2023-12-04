package com.example.ingda.domain.member.entity;

import com.example.ingda.common.audit.BaseEntity;
import com.example.ingda.domain.member.type.OAuthType;
import com.example.ingda.domain.member.type.UserRoleType;
import com.example.ingda.domain.score.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column
    private OAuthType oAuthType;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private UserRoleType userRole;

    @Column
    private String sex;

    @Column
    private LocalDate birth;
    @Column
    private LocalDateTime inactive;

    @Column(nullable = false)
    private int reviewCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    private Score score;
    @Column
    private LocalDateTime lastConnectedAt;

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void changeAccountActivation(){
        this.inactive =  null;
    }

    public void changeAccountInactivation() {
        this.inactive = LocalDateTime.now();
    }

    public void lastConnectedAt(){
        this.lastConnectedAt = LocalDateTime.now();
    }

    public void minusReviewCount(){
        if(this.reviewCount > 0) this.reviewCount -= 1;
    }

    public Member oAuthUpdate(OAuthType kakao) {
        this.oAuthType = kakao;
        return this;
    }
}
