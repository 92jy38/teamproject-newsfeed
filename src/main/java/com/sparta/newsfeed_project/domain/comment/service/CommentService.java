package com.sparta.newsfeed_project.domain.comment.service;

import com.sparta.newsfeed_project.domain.comment.dto.RequestCommentDto;
import com.sparta.newsfeed_project.domain.comment.dto.ResponseCommentDto;
import com.sparta.newsfeed_project.domain.comment.entity.Comment;
import com.sparta.newsfeed_project.domain.comment.repository.CommentRepository;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import com.sparta.newsfeed_project.domain.post.entity.Post;
import com.sparta.newsfeed_project.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    // 예외처리 수정 해야함
    @Transactional
    public ResponseCommentDto createComment(Long postId, RequestCommentDto requestDto) throws ResponseException {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new ResponseException(ResponseCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseException(ResponseCode.POST_NOT_FOUND));

        Comment comment = new Comment(requestDto.getContent(), member, post);
        commentRepository.save(comment);
        return new ResponseCommentDto(comment);
    }

    // 게시글에 대한 모든 댓글 조회 -> 페이지로 조회 수정, createAt 기준으로 내림차순 정렬
    @Transactional(readOnly = true)
    public Page<ResponseCommentDto> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createAt"));
        Page<Comment> commentPage = commentRepository.findByPostId(postId, pageable);
        return commentPage.map(ResponseCommentDto::new);
    }

    // 댓글 수정
    @Transactional
    public ResponseCommentDto updateComment(Long id, RequestCommentDto requestDto) throws ResponseException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseException(ResponseCode.COMMENT_NOT_FOUND));

        // 작성자 본인인지 확인
        if (!comment.getMember().getId().equals(requestDto.getMemberId())) {
            throw new ResponseException(ResponseCode.COMMENT_INVALID_PERMISSION);
        }
        comment.updateContent(requestDto.getContent());
        return new ResponseCommentDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long id, Long memberId) throws ResponseException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseException(ResponseCode.COMMENT_NOT_FOUND));

        // 작성자 본인인지 확인
        if (!comment.getMember().getId().equals(memberId)) {
            throw new ResponseException(ResponseCode.COMMENT_INVALID_PERMISSION);
        }
        commentRepository.delete(comment);
    }
}
