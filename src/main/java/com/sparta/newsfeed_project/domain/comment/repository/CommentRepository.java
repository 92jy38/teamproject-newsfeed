package com.sparta.newsfeed_project.domain.comment.repository;

import com.sparta.newsfeed_project.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // EntityGraph를 사용하여 Member 엔티티를 함께 조회
    @EntityGraph(attributePaths = {"member"})
    List<Comment> findByPostId(Long postId);
}