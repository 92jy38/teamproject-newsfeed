package com.sparta.newsfeed_project.domain.comment.repository;

import com.sparta.newsfeed_project.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}