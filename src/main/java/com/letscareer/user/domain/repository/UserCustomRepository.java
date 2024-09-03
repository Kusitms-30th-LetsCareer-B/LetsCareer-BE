package com.letscareer.user.domain.repository;

import com.letscareer.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.letscareer.user.domain.QUser.user;
import static com.letscareer.career.domain.QEducation.education;
import static com.letscareer.career.domain.QCertificate.certificate;
import static com.letscareer.career.domain.QWorkExperience.workExperience;
import static com.letscareer.career.domain.QActivity.activity;
import static com.letscareer.career.domain.QPortfolio.portfolio;

@RequiredArgsConstructor
@Repository
public class UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    public User findByUserId(Long userId) {
        return queryFactory.selectFrom(user)
                .leftJoin(user.educations, education).fetchJoin()
                .leftJoin(user.certificates, certificate).fetchJoin()
                .leftJoin(user.workExperiences, workExperience).fetchJoin()
                .leftJoin(user.activities, activity).fetchJoin()
                .leftJoin(user.portfolio, portfolio).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();
    }
}
