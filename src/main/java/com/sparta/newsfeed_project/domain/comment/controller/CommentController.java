package com.sparta.newsfeed_project.domain.comment.controller;

import com.sparta.newsfeed_project.domain.comment.dto.RequestCommentDto;
import com.sparta.newsfeed_project.domain.comment.dto.ResponseCommentDto;
import com.sparta.newsfeed_project.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseCommentDto> getComment(@PathVariable("postId") Long postId) {
        ResponseCommentDto responseDto = commentService.getComment(postId);
        return ResponseEntity.ok(responseDto);
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
