package com.example.ingda.domain.member.repository;

import com.example.ingda.domain.member.entity.Member;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.ingda.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> getMemberListByAdmin(String nickname, String email) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(
                    eqNickname(nickname),
                    eqEmail(email)
                )
                .fetch();
    }

    private BooleanExpression eqEmail(String email) {
        if(email == null) return null;
        return member.email.eq(email);
    }

    private BooleanExpression eqNickname(String nickname) {
        if(nickname == null) return null;
        return member.nickname.eq(nickname);
    }

}
