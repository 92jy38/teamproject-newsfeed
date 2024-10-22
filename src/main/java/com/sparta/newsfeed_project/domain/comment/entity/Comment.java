package com.sparta.newsfeed_project.domain.comment.entity;

import com.sparta.newsfeed_project.domain.common.entity.Timestamped;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.post.entity.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Column(nullable = false)
    private String content;

    // 멤버와의 관계 설정 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 게시글과의 관계 설정 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // setter 대신 생성자 사용
    public Comment(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }
    // 내용 업데이트 로직
    public void updateContent(String content) {
        this.content = content;
    }
}
