package com.sparta.newsfeed_project.domain.member.repository;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
