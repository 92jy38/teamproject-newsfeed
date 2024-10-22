package com.sparta.newsfeed_project.domain.member.repository;

import com.sparta.newsfeed_project.domain.member.entity.TempPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempPostRepository extends JpaRepository<TempPost, Long> {
    Long countByMemberId(Long memberId);
}
