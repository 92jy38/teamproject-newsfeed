package com.sparta.newsfeed_project.domain.member.service;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.dto.RequestCreateMemberDto;
import com.sparta.newsfeed_project.domain.member.dto.RequestModifyMemberDto;
import com.sparta.newsfeed_project.domain.member.dto.RequestRemoveMemberDto;
import com.sparta.newsfeed_project.domain.member.dto.ResponseMemberDto;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 사용자(Member) 서비스 클래스
 * MemberRepository 이용하여 사용자를 관리합니다.
 *
 * @since 2024-10-21
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository userRepository;

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
        validationCreateMember(member);
        userRepository.save(member);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
    }

    /**
     * 사용자 생성 시 올바른 정보를 입력하였는지 검사합니다.
     *
     * @param user 생성하려는 사용자 정보
     * @throws ResponseException 아이디가 중복될 경우 발생
     * @since 2024-10-21
     */
    public void validationCreateMember(Member user) throws ResponseException {
        Member findMember = userRepository.findByEmail(user.getEmail());
        if (findMember != null) // 중복 이메일 확인
            throw new ResponseException(ResponseCode.USER_EMAIL_DUPLICATED);
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param req HttpServletRequest 객체
     * @return 회원 조회 결과 (UserResponseDto)
     * @since 2024-10-21
     */
    public ResponseMemberDto searchMember(HttpServletRequest req) throws ResponseException {
        Member loginMember = (Member) req.getAttribute("member");
//        Member loginMember = Member.builder().id(1L).build();
        Member findMember = userRepository.findById(loginMember.getId()).orElseThrow(() -> new ResponseException(ResponseCode.USER_NOT_FOUND));
        return ResponseMemberDto.create(findMember, ResponseCode.SUCCESS_SEARCH_USER);
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
        Member loginMember = (Member) req.getAttribute("member");
//        Member loginMember = Member.builder().id(1L).build();
        // TODO. khj 두번째 파라미터에 암호화된 비밀번호 넣어줄 것.
        loginMember = requestDto.from(requestDto, loginMember.getId(), requestDto.getPassword());
        Member updateMember = userRepository.findById(loginMember.getId()).orElseThrow(() -> new ResponseException(ResponseCode.USER_NOT_FOUND));
        updateMember.update(loginMember);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_USER);
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 회원 삭제 요청 정보
     * @return 회원 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @Transactional
    public ResponseStatusDto deleteMember(HttpServletRequest req, RequestRemoveMemberDto requestDto) throws ResponseException {
        Member loginMember = (Member) req.getAttribute("member");
//        Member loginMember = Member.builder().id(1L).build();
        loginMember = requestDto.from(requestDto, loginMember.getId());
        Optional<Member> deleteUser = userRepository.findById(loginMember.getId());
        validationDeleteMember(loginMember, deleteUser);
        userRepository.delete(deleteUser.get());
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_USER);
    }

    /**
     * 삭제할 사용자 정보를 검증합니다.
     *
     * @param loginUser  로그인한 사용자 정보
     * @param deleteUser 검증할 사용자 정보
     * @throws ResponseException 사용자가 존재하지 않거나, 권한이 부족한 경우 예외 발생
     * @since 2024-10-21
     */
    private void validationDeleteMember(Member loginUser, Optional<Member> deleteUser) throws ResponseException {
        if (deleteUser.isEmpty())
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);

        if (!loginUser.getPassword().equals(deleteUser.get().getPassword()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }
}
