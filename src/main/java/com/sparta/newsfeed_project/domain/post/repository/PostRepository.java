package com.sparta.newsfeed_project.domain.post.repository;

import com.sparta.newsfeed_project.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.member.id IN :idArray")
    Page<Post> findAllPost(@Param("idArray") List<Long> idArray, Pageable pageable);


    @Query("SELECT p.id FROM Post p WHERE p.member.id = :memberId")
    Long findByMemberId(@Param("memberId") Long memberId);
}
