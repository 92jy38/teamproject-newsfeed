package com.sparta.newsfeed_project.domain.member.service;

import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.common.util.PasswordEncoder;
import com.sparta.newsfeed_project.domain.member.dto.*;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import com.sparta.newsfeed_project.domain.post.entity.Post;
import com.sparta.newsfeed_project.domain.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 사용자(Member) 서비스 클래스
 * MemberRepository 이용하여 사용자를 관리합니다.
 *
 * @since 2024-10-21
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final String SUBJECT_ATTRIBUTE_KEY = "loggedInWithId";
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final BuddiesRepository buddiesRepository;
    private final PostRepository postRepository;

    /**
     * 회원가입을 처리합니다.
     *
     * @param requestDto 회원가입 요청 정보
     * @return 회원가입 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    public ResponseStatusDto createMember(RequestCreateMemberDto requestDto) throws ResponseException {
        validateCreateInfo(requestDto.getEmail());
        String encodingPassword = passwordEncoder.encoder(requestDto.getPassword());
        Member member = requestDto.from(requestDto, encodingPassword);

        memberRepository.save(member);
        return new ResponseStatusDto(HttpStatus.CREATED, "회원 가입 성공");
    }

    /**
     * 사용자 생성 시 올바른 정보를 입력하였는지 검사합니다.
     *
     * @param email 생형하려는 이메일
     * @throws ResponseException 아이디가 중복될 경우 발생
     * @since 2024-10-21
     */
    public void validateCreateInfo(String email) throws ResponseException {
        Member findMember = memberRepository.findByEmail(email);
        if (findMember != null)
            throw new ResponseException(ResponseCode.MEMBER_EMAIL_DUPLICATED);
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param req HttpServletRequest 객체
     * @return 회원 조회 결과 (UserResponseDto)
     * @since 2024-10-21
     */
    public ResponseMemberDto searchMember(HttpServletRequest req, Long id) throws ResponseException {
        Member loginMember = (Member) req.getAttribute(SUBJECT_ATTRIBUTE_KEY);
        Member findMember = findById(id);
        MemberDto memberDto = createMemberDto(loginMember.getId(), findMember);
        return ResponseMemberDto.create(memberDto);
    }

    /**
     * 회원 정보 DTO를 생성합니다.
     *
     * @param id     현재 로그인한 회원 ID
     * @param member 정보를 조회할 회원 엔티티
     * @return 변환된 회원 DTO
     */
    private MemberDto createMemberDto(Long id, Member member) {
        boolean hiddenInfo = !Objects.equals(member.getId(), id);
        int fromBuddyCount = getFromBuddyCount(member.getId());
        int toBuddyCount = getToBuddyCount(member.getId());
        int postCount = getPostCount(member.getId());

        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setEmail(member.getEmail());
        memberDto.setNickname(member.getNickname());
        memberDto.setFromBuddyCount(fromBuddyCount);
        memberDto.setToBuddyCount(toBuddyCount);
        memberDto.setPostCount(postCount);
        memberDto.setUsername(member.getUsername());
        memberDto.setUpdateAt(member.getUpdateAt());
        if (!hiddenInfo) {
            memberDto.setPassword(member.getPassword());
            memberDto.setCreateAt(member.getCreateAt());
        }

        return memberDto;
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 회원 정보 수정 요청 정보
     * @return 회원 정보 수정 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    @Transactional
    public ResponseStatusDto updateMember(HttpServletRequest req, RequestModifyMemberDto requestDto) throws ResponseException {
        Member loginMember = (Member) req.getAttribute(SUBJECT_ATTRIBUTE_KEY);
        validateUpdatePassword(requestDto, loginMember);
        Member updateMember = findById(loginMember.getId());

        String newPassword = passwordEncoder.encoder(requestDto.getNewPassword());
        requestDto.setNewPassword(newPassword);
        updateMember.update(requestDto);
        return new ResponseStatusDto(HttpStatus.OK, "유저 수정 성공");
    }

    /**
     * 멤버 정보 수정 요청을 검증합니다.
     *
     * @param requestDto  수정 데이터가 들어있는 DTO
     * @param loginMember 로그인한 멤버 정보
     * @throws ResponseException 검증 실패 시 발생하는 예외
     * @since 2024-10-21
     */
    private void validateUpdatePassword(RequestModifyMemberDto requestDto, Member loginMember) throws ResponseException {
        if (!passwordEncoder.matches(requestDto.getPassword(), loginMember.getPassword()))
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);
        else if (passwordEncoder.matches(requestDto.getNewPassword(), loginMember.getPassword()))
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_DUPLICATED);
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 회원 삭제 요청 정보
     * @return 회원 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    @Transactional
    public ResponseStatusDto deleteMember(HttpServletRequest req, RequestRemoveMemberDto requestDto) throws ResponseException {
        Member loginMember = (Member) req.getAttribute(SUBJECT_ATTRIBUTE_KEY);
        if (!passwordEncoder.matches(requestDto.getPassword(), loginMember.getPassword()))
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);

        Member deleteMember = findById(loginMember.getId());
        if (deleteMember.isDeleted())
            throw new ResponseException(ResponseCode.MEMBER_DELETE);

        deleteMember.delete();
        deleteMemberBuddies(loginMember.getId());
        deleteMemberPosts(loginMember.getId());

        return new ResponseStatusDto(HttpStatus.NO_CONTENT, "유저 삭제 성공");
    }

    /**
     * 멤버 번호로 유저를 조회합니다.
     *
     * @param id 유저 id
     * @return 검색된 회원
     * @throws ResponseException 검색된 유저가 없을시 발생하는 예외
     * @since 2024-10-23
     */
    private Member findById(Long id) throws ResponseException {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null)
            throw new ResponseException(ResponseCode.MEMBER_NOT_FOUND);
        else if (member.isDeleted())
            throw new ResponseException(ResponseCode.MEMBER_DELETE);
        return member;
    }

    /**
     * 회원의 친구 관계를 모두 삭제합니다.
     *
     * @param memberId 회원 ID
     * @since 2024-10-23
     */
    public void deleteMemberBuddies(Long memberId) {
        List<Buddies> buddies = buddiesRepository.findByFromUserIdOrToUserId(memberId, memberId);
        if (buddies != null && !buddies.isEmpty())
            buddiesRepository.deleteAll(buddies);

    }

    /**
     * 회원이 친구 추가한 친구 수를 조회합니다.
     *
     * @param memberId 회원 ID
     * @return 친구 수
     */
    public int getFromBuddyCount(Long memberId) {
        return buddiesRepository.countByFromUserId(memberId).intValue();
    }

    /**
     * 회원을 친구 추가한 친구 수를 조회합니다.
     *
     * @param memberId 회원 ID
     * @return 친구 수
     */
    public int getToBuddyCount(Long memberId) {
        return buddiesRepository.countByToUserId(memberId).intValue();
    }

    /**
     * 회원이 작성한 게시글을 모두 삭제합니다.
     *
     * @param memberId 회원 ID
     * @since 2024-10-23
     */
    public void deleteMemberPosts(Long memberId) {
        List<Post> posts = postRepository.findAllByMemberId(memberId);
        if (posts != null && !posts.isEmpty())
            postRepository.deleteAll(posts);
    }

    /**
     * 회원의 게시물 수를 조회합니다.
     *
     * @param memberId 회원 ID
     * @return 게시물 수
     * @since 2024-10-23
     */
    public int getPostCount(Long memberId) {
        return postRepository.countByMemberId(memberId).intValue();
    }
}
