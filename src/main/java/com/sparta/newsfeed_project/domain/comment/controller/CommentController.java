package com.sparta.newsfeed_project.domain.comment.controller;

import com.sparta.newsfeed_project.domain.comment.dto.RequestCommentDto;
import com.sparta.newsfeed_project.domain.comment.dto.ResponseCommentDto;
import com.sparta.newsfeed_project.domain.comment.service.CommentService;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import jakarta.servlet.http.HttpServletRequest;
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

    // 댓글 생성
    @PostMapping("/{postId}")
    public ResponseEntity<ResponseCommentDto> createComment(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody RequestCommentDto requestDto,
            HttpServletRequest request) throws ResponseException {
        ResponseCommentDto responseDto = commentService.createComment(postId, requestDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 게시글에 대한 모든 댓글 조회, 1 페이지당 10개의 댓글, 댓글 조회는 로그인 하지 않아도 볼 수 있음
    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<ResponseCommentDto>> getCommentsByPostId(
            @PathVariable("postId") Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) throws ResponseException {
        Page<ResponseCommentDto> responseDtos = commentService.getCommentsByPostId(postId, page, size);
        return ResponseEntity.ok(responseDtos);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody RequestCommentDto requestDto,
            HttpServletRequest request) throws ResponseException {
        ResponseCommentDto responseDto = commentService.updateComment(commentId, requestDto, request);
        return ResponseEntity.ok(responseDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Long commentId,
            HttpServletRequest request) throws ResponseException {
        commentService.deleteComment(commentId, request);
        return ResponseEntity.noContent().build();
    }
}