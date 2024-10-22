package com.sparta.newsfeed_project.domain.post.entity;

import com.sparta.newsfeed_project.domain.common.entity.Timestamped;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caption;

    @Column(nullable = false)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public static Post from(String caption, String downloadLink) {
        Post post = new Post();
        post.init(caption, downloadLink);
        return post;
    }

    private void init(String caption, String downloadLink) {
        this.caption = caption;
        this.imgUrl = downloadLink;
    }

    public ResponsePostDto to() {

        return new ResponsePostDto(id, caption, imgUrl, getCreateAt(), getUpdateAt());
    }

    public void update(String caption, String downloadLink) {
        this.caption = caption;
        this.imgUrl = downloadLink;
    }
}
