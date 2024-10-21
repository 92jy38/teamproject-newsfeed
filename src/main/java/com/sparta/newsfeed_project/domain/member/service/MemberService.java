package com.sparta.newsfeed_project.domain.member.service;

import com.sparta.newsfeed_project.domain.common.dto.ResponseStatusDto;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.dto.*;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import com.sparta.newsfeed_project.domain.member.repository.DeleteMemberRepository;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private final MemberRepository memberRepository;
    private final DeleteMemberRepository deleteMemberRepository;

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
        memberRepository.save(member);
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
        Member findMember = memberRepository.findByEmail(user.getEmail());
        if (findMember != null) // 중복 이메일 확인
            throw new ResponseException(ResponseCode.USER_EMAIL_DUPLICATED);
    }

    /**
     * 로그인 합니다.
     *
     * @param requestDto 로그인 요청 정보
     * @return 로그인 결과 (ResponseStatusDto)
     * @since 2024-10-21
     */
    public ResponseStatusDto login(HttpServletResponse res, RequestSearchMemberDto requestDto) throws ResponseException {
        Member member = requestDto.from(requestDto);
        Member findMember = memberRepository.findByEmail(member.getEmail());
        validationLoginMember(member, findMember);
        // TODO. 쿠키 생성 필요
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN);
    }

    /**
     * 로그인 시 유저 정보를 검사합니다.
     *
     * @param loginMember 입력된 유저 정보
     * @param findUser  조회된 유저 정보
     * @throws ResponseException 유저 정보가 올바르지 않은 경우 예외를 발생시킵니다.
     * @since 2024-10-03
     */
    public void validationLoginMember(Member loginMember, Member findUser) throws ResponseException {
        if (findUser == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);
        // TODO. 암호화 적용 필요
//        else if (!passwordEncoder.matches(inputUser.getPassword(), findUser.getPassword()))
        else if (!loginMember.getPassword().equals(findUser.getPassword()))
            throw new ResponseException(ResponseCode.USER_PASSWORD_NOT_MATCH);
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param req HttpServletRequest 객체
     * @return 회원 조회 결과 (UserResponseDto)
     * @since 2024-10-21
     */
    public ResponseMemberDto searchMember(HttpServletRequest req) throws ResponseException {
        // TODO. khj jwt완성시 아래 주석으로 대체
        // Member loginMember = (Member) req.getAttribute("member");
        Member loginMember = Member.builder().id(1L).build();
        Member findMember = memberRepository.findById(loginMember.getId()).orElseThrow(() -> new ResponseException(ResponseCode.USER_NOT_FOUND));
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
        // TODO. khj jwt완성시 아래 주석으로 대체
        // Member loginMember = (Member) req.getAttribute("member");
        Member loginMember = Member.builder().id(1L).password("1q2w3e4r#").build();
        Optional<Member> updateMember = memberRepository.findById(loginMember.getId());
        validationUpdateMember(requestDto, loginMember, updateMember);
        // TODO. khj 세번째 파라미터에 암호화된 비밀번호 넣어줄 것.
        Member updateInfo = requestDto.from(requestDto, loginMember.getId(), requestDto.getNewPassword());
        updateMember.get().update(updateInfo);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_USER);
    }

    /**
     * 멤버 정보 수정 요청을 검증합니다.
     *
     * @param requestDto 수정 요청 DTO
     * @param loginMember 로그인한 멤버 정보
     * @param updateMember 수정하려는 멤버 정보 (Optional)
     * @throws ResponseException 검증 실패 시 발생하는 예외
     * @since 2024-10-21
     */
    private void validationUpdateMember(RequestModifyMemberDto requestDto, Member loginMember, Optional<Member> updateMember) throws ResponseException {
        if (updateMember.isEmpty())
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);
        // TODO. khj 그 뭐냐....ㅠㅠ 암튼 그거 비밀번호 암호..?
//        else if (!passwordEncoder.matches(inputUser.getPassword(), findUser.getPassword()))
        else if(!requestDto.getPassword().equals(loginMember.getPassword()))
            throw new ResponseException(ResponseCode.USER_PASSWORD_NOT_MATCH);
        else if (requestDto.getNewPassword().equals(loginMember.getPassword()))
            throw new ResponseException(ResponseCode.USER_PASSWORD_DUPLICATED);
    }

//    /**
//     * 회원을 삭제합니다.
//     *
//     * @param req        HttpServletRequest 객체
//     * @param requestDto 회원 삭제 요청 정보
//     * @return 회원 삭제 결과 (ResponseStatusDto)
//     * @since 2024-10-21
//     */
//    @Transactional
//    public ResponseStatusDto deleteMember(HttpServletRequest req, RequestRemoveMemberDto requestDto) throws ResponseException {
//        // TODO. khj jwt완성시 아래 주석으로 대체
//        // Member loginMember = (Member) req.getAttribute("member");
//        Member loginMember = Member.builder().id(1L).build();
//        loginMember = requestDto.from(requestDto, loginMember.getId());
//        Optional<Member> deleteUser = memberRepository.findById(loginMember.getId());
//        validationDeleteMember(loginMember, deleteUser);
//        deleteMemberRepository.save(deleteUser.get());
//        memberRepository.delete(deleteUser.get());
//        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_USER);
//    }

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
         Member loginMember = Member.builder().id(1L).build();
//        Member loginMember = (Member) req.getAttribute("member");
        loginMember = requestDto.from(requestDto, loginMember);
        Optional<Member> deleteMember = memberRepository.findById(loginMember.getId());
        validationDeleteMember(loginMember, deleteMember);
        deleteMember.get().setUserDelete(true);
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
        // TODO. khj 암호화.. 어쩌구..
//        else if (!passwordEncoder.matches(inputUser.getPassword(), findUser.getPassword()))
        else if (!loginUser.getPassword().equals(deleteUser.get().getPassword()))
            throw new ResponseException(ResponseCode.USER_PASSWORD_NOT_MATCH);
    }
}
