package com.sparta.newsfeed_project.domain.comment.controller;

import com.sparta.newsfeed_project.domain.comment.dto.RequestCommentDto;
import com.sparta.newsfeed_project.domain.comment.dto.ResponseCommentDto;
import com.sparta.newsfeed_project.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<ResponseCommentDto> createComment(@PathVariable("postId") Long postId, @Valid @RequestBody RequestCommentDto requestDto) {
        ResponseCommentDto responseDto = commentService.createComment(postId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 게시글에 대한 모든 댓글을 조회하는 메서드
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ResponseCommentDto>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        List<ResponseCommentDto> responseDtos = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(@PathVariable("commentId") Long commentId, @Valid @RequestBody RequestCommentDto requestDto) {
        ResponseCommentDto responseDto = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId, @RequestParam Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return ResponseEntity.noContent().build();
    }
}
