package com.sparta.newsfeed_project.domain.comment.repository;

import com.sparta.newsfeed_project.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"member"}) // EntityGraph를 사용하여 Member 엔티티를 함께 조회
    Page<Comment> findByPostId(Long postId, Pageable pageable); // 페이지 사용
}
