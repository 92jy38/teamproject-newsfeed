package com.sparta.newsfeed_project.domain.member.repository;

import com.sparta.newsfeed_project.domain.member.entity.DeleteMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteMemberRepository extends JpaRepository<DeleteMember, Long> {
    DeleteMember findByEmail(String email);
}
