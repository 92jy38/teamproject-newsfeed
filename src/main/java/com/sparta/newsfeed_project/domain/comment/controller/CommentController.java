package com.sparta.newsfeed_project.domain.comment.controller;

import com.sparta.newsfeed_project.domain.comment.dto.RequestCommentDto;
import com.sparta.newsfeed_project.domain.comment.dto.ResponseCommentDto;
import com.sparta.newsfeed_project.domain.comment.service.CommentService;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<ResponseCommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody RequestCommentDto requestDto) throws ResponseException {
        ResponseCommentDto responseDto = commentService.createComment(postId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 게시글에 대한 모든 댓글을 조회하는 메서드
    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<ResponseCommentDto>> getCommentsByPostId(
            @PathVariable("postId") Long postId,
            @RequestParam(defaultValue = "1") int page, // 페이지 번호 (1부터 시작)
            @RequestParam(defaultValue = "10") int size) { // 페이지 사이즈
        Page<ResponseCommentDto> responseDtos = commentService.getCommentsByPostId(postId, page, size);
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(@PathVariable("commentId") Long commentId, @Valid @RequestBody RequestCommentDto requestDto) throws ResponseException {
        ResponseCommentDto responseDto = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId, @RequestParam Long memberId) throws ResponseException {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.noContent().build();
    }
}
