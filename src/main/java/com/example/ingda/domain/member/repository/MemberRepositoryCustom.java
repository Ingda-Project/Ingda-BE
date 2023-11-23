package com.example.ingda.domain.member.repository;

import com.example.ingda.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> getMemberListByAdmin(String nickname, String email);

    void resetReviewCounts();
}
