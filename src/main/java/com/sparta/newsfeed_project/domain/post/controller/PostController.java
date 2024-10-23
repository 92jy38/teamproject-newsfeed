package com.sparta.newsfeed_project.domain.post.controller;


import com.sparta.newsfeed_project.domain.post.dto.RequestPostDeleteDto;
import com.sparta.newsfeed_project.domain.post.dto.RequestPostDto;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostDto;
import com.sparta.newsfeed_project.domain.post.dto.ResponsePostPage;
import com.sparta.newsfeed_project.domain.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 등록
    @PostMapping()
    public ResponseEntity<ResponsePostDto> createPost(@RequestBody @Valid RequestPostDto requestDto, HttpServletRequest req) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(requestDto, req));

    }

    // 게시물 전체 조회(나와 친구 피드)
    @GetMapping()
    public ResponseEntity<ResponsePostPage> findAllPost(@RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size,
                                                        @RequestParam(required = false, defaultValue = "createAt") String criteria,
                                                        HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.findAllPost(page, size, criteria, req));

    }

    // 특정 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostDto> findByPostId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.findByPostId(id));
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponsePostDto> modifyPost(@PathVariable Long id, @RequestBody @Valid RequestPostDto requestDto, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.modifyPost(id, requestDto, req));
    }


    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestBody RequestPostDeleteDto requestDto, HttpServletRequest req) {
        postService.deletePost(id, requestDto, req);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
