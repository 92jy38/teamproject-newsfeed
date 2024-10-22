package com.sparta.newsfeed_project.domain.post.entity;

import com.sparta.newsfeed_project.domain.comment.entity.Comment;
import com.sparta.newsfeed_project.domain.common.entity.Timestamped;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

//    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
//    private List<Comment> comments = new ArrayList<>();

    public static Post from(String caption, String downloadLink, Member member) {
        Post post = new Post();
        post.init(caption, downloadLink, member);
        return post;
    }

    private void init(String caption, String downloadLink, Member member) {
        this.caption = caption;
        this.imgUrl = downloadLink;
        this.member = member;
    }

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

        return new ResponsePostDto(id, caption, imgUrl, "nickname", getCreateAt(), getUpdateAt());
    }

    public void update(String caption, String downloadLink) {
        this.caption = caption;
        this.imgUrl = downloadLink;
    }
}
