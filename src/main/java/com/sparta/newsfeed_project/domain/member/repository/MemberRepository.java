
package com.sparta.newsfeed_project.domain.member.repository;

import com.sparta.newsfeed_project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 유저 엔티티를 위한 JPA 레포지토리입니다.
 *
 * @since 2024-10-21
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);

    Member findByEmail(String email);

}
