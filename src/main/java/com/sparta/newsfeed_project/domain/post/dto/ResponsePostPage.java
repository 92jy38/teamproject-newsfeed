package com.sparta.newsfeed_project.domain.post.dto;

import com.sparta.newsfeed_project.domain.post.entity.Post;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ResponsePostPage {

    private List<ResponsePostDto> posts;
    private int totalPages;
    private long totalElements;

    public ResponsePostPage(Page<Post> page) {

        this.posts = page.getContent().stream()
                .map(Post::to).toList();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
