package com.sparta.newsfeed_project.domain.member.service;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.dto.*;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.member.entity.TempBuddy;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import com.sparta.newsfeed_project.domain.member.repository.TempBuddyRepository;
import com.sparta.newsfeed_project.domain.member.repository.TempPostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    private final MemberRepository memberRepository;
    private final TempBuddyRepository buddyRepository;
    private final TempPostRepository postRepository;

    /**
     * 회원가입을 처리합니다.
     *
     * @param requestDto 회원가입 요청 정보
     * @return 회원가입 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    public ResponseStatusDto createMember(RequestCreateMemberDto requestDto) throws ResponseException {
        // TODO. khj 두번째 parameter에 암호화된 비밀번호 넣어줄 것.
        Member member = requestDto.from(requestDto, requestDto.getPassword());
        validateCreateInfo(member);
        memberRepository.save(member);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
    }

    /**
     * 사용자 생성 시 올바른 정보를 입력하였는지 검사합니다.
     *
     * @param member 생성하려는 사용자 정보
     * @throws ResponseException 아이디가 중복될 경우 발생
     * @since 2024-10-21
     */
    public void validateCreateInfo(Member member) throws ResponseException {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null)
            throw new ResponseException(ResponseCode.MEMBER_EMAIL_DUPLICATED);
    }

    /**
     * 로그인 합니다.
     *
     * @param requestDto 로그인 요청 정보
     * @return 로그인 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    public ResponseStatusDto login(HttpServletResponse res, RequestSearchMemberDto requestDto) throws ResponseException {
        validateLoginInfo(requestDto.getEmail(), requestDto.getPassword());
        // TODO. 쿠키 생성 필요
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN);
    }

    /**
     * 로그아웃 합니다.
     *
     * @return 로그아웃 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    public ResponseStatusDto logout(HttpServletResponse res) throws ResponseException {
        // TODO. 쿠키 해제
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGOUT);
    }

    /**
     * 로그인 시 유저 정보를 검사합니다.
     *
     * @param email    입력한 email
     * @param password 입력한 비밀번호
     * @throws ResponseException 유저 정보가 올바르지 않은 경우 예외를 발생시킵니다.
     * @since 2024-10-21
     */
    public void validateLoginInfo(String email, String password) throws ResponseException {
        Member findMember = memberRepository.findByEmail(email);
        if (findMember == null)
            throw new ResponseException(ResponseCode.MEMBER_NOT_FOUND);
            // TODO. 암호화된 비밀번호와 비교 필요
            // else if (!passwordEncoder.matches(inputUser.getPassword(), findUser.getPassword()))
        else if (!password.equals(findMember.getPassword()))
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param req HttpServletRequest 객체
     * @return 회원 조회 결과 (UserResponseDto)
     * @since 2024-10-21
     */
    public ResponseMemberDto searchMember(HttpServletRequest req, Long id) throws ResponseException {
        // TODO. khj jwt 완성시 아래 주석으로 대체
        // Member loginMember = (Member) req.getAttribute("member");
        Member loginMember = Member.builder().id(1L).build();
        Member findMember = findById(loginMember.getId());
        MemberDto memberDto = createMemberDto(id, findMember);
        return ResponseMemberDto.create(memberDto, ResponseCode.SUCCESS_SEARCH_USER);
    }

    /**
     * 회원 정보 DTO를 생성합니다.
     *
     * @param id     회원 ID
     * @param member 회원 엔티티
     * @return 변환된 회원 DTO
     */
    private MemberDto createMemberDto(Long id, Member member) {
        boolean hiddenInfo = !Objects.equals(member.getId(), id);
        int buddyCount = getBuddyCount(member.getId());
        int postCount = getPostCount(member.getId());

        MemberDto memberDto = new MemberDto();
        memberDto.setId(id);
        memberDto.setEmail(member.getEmail());
        memberDto.setNickname(member.getNickname());
        memberDto.setBuddyCount(buddyCount);
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
     * 회원의 친구 수를 조회합니다.
     *
     * @param id 회원 ID
     * @return 친구 수
     */
    private int getBuddyCount(Long id) {
        List<TempBuddy> buddies = buddyRepository.findByStateAndFromIdOrToId(true, id, id);
        return buddies == null ? 0 : buddies.size() / 2;
    }

    /**
     * 회원의 게시물 수를 조회합니다.
     *
     * @param id 회원 ID
     * @return 게시물 수
     */
    private int getPostCount(Long id) {
        return postRepository.countByMemberId(id).intValue();
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
        // TODO. khj jwt완성시 아래 주석으로 대체
        // Member loginMember = (Member) req.getAttribute("member");
        Member loginMember = Member.builder().id(1L).password("1q2w3e4r#").build();
        validateUpdatePassword(requestDto, loginMember);
        Member updateMember = findById(loginMember.getId());
        // TODO. khj 암호화된 비밀번호 넣어줄 것.
        requestDto.setNewPassword(requestDto.getPassword());
        updateMember.update(requestDto);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_USER);
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
        // TODO. khj 그 뭐냐....ㅠㅠ 암튼 그거 비밀번호 암호..?
        // if (!passwordEncoder.matches(inputUser.getPassword(), findUser.getPassword()))
        if (!requestDto.getPassword().equals(loginMember.getPassword()))
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);
        else if (requestDto.getNewPassword().equals(loginMember.getPassword()))
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
        // TODO. khj jwt완성시 아래 주석으로 대체.
        // Member loginMember = (Member) req.getAttribute("member");
        Member loginMember = Member.builder().id(1L).password("Admin123!").build();
        // TODO. khj 암호화.. 어쩌구..
        if (!loginMember.getPassword().equals(requestDto.getPassword()))
            throw new ResponseException(ResponseCode.MEMBER_PASSWORD_NOT_MATCH);

        Member deleteMember = findById(loginMember.getId());
        if (deleteMember.isDeleted())
            throw new ResponseException(ResponseCode.MEMBER_DELETE);

        deleteMember.delete();
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_USER);
    }

    /**
     * 멤버 번호로 유저를 조회합니다.
     *
     * @param id 유저 id
     * @return 검색된 회원
     * * @throws ResponseException 검색된 유저가 없을시 발생하는 예외
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
}
