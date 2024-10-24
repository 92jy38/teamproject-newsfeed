package com.sparta.newsfeed_project.domain.buddies.service;


import com.sparta.newsfeed_project.domain.buddies.dto.ResponseBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import com.sparta.newsfeed_project.domain.common.exception.ResponseCode;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuddiesService {

    private final BuddiesRepository buddiesRepository;
    private final MemberRepository memberRepository;


    /**
     * 회원의 친구 관계를 등록합니다
     *
     * @param memberId,userId 회원 ID,친구 ID
     * @Return 등록된 친구 관계
     * @since 2024-10-23
     */
    public ResponseBuddiesDto createBuddies(Long memberId, Long userId) throws ResponseException {
        if (!memberRepository.existsById(userId)) {
            throw new ResponseException(ResponseCode.MEMBER_NOT_FOUND);
        }
        if (memberId.equals(userId)) {
            throw new ResponseException(ResponseCode.BUDDIES_DUOLICATION_ID_ERROR);
        }
        if (buddiesRepository.existsByFromUserIdAndToUserId(memberId, userId)) {
            throw new ResponseException(ResponseCode.BUDDIES_DUOLICATION_ERROR);
        }
        Buddies buddies = Buddies.from(memberId, userId);
        buddies.approved(true);
        buddiesRepository.save(buddies);
        Buddies acceptBuddies = Buddies.upend(userId, memberId);
        buddiesRepository.save(acceptBuddies);
        return buddies.to();
    }

    /**
     * 모든 친구 관계의 목록을 불러옵니다
     *
     * @return 친구 목록
     */
    public List<ResponseBuddiesDto> getBuddies() throws ResponseException {
        List<Buddies> buddies = buddiesRepository.findAll();
        if (buddies.isEmpty()) {
            throw new ResponseException(ResponseCode.BUDDIES_NULL_ERROR);
        }
        return buddies.stream().map(Buddies::to).toList();
    }

    /**
     * 회원의 친구 관계를 수락합니다
     *
     * @param memberId,userId 회원 ID,친구 ID
     * @since 2024-10-23
     */
    public void acceptBuddies(Long memberId, Long userId) throws ResponseException {
        if (!buddiesRepository.existsById(userId)) {
            throw new ResponseException(ResponseCode.BUDDIES_DUOLICATION_ID_ERROR);
        }
        Buddies buddies = buddiesRepository
                .findOneByFromUserIdAndToUserId(memberId, userId);
        buddies.approved(true);
        buddiesRepository.save(buddies);
    }

    /**
     * 회원의 친구 관계를 취소합니다
     * 둘다 관계를 취소했을때 목록에서 삭제합니다
     *
     * @param memberId,userId 회원 ID,친구 ID
     * @since 2024-10-23
     */
    public void deleteBuddies(Long memberId, Long userId) {
        Buddies buddies = buddiesRepository
                .findOneByFromUserIdAndToUserId(memberId, userId);
        if (buddies == null) {
            throw new ResponseException(ResponseCode.BUDDIES_NULL_ERROR);
        }

        Buddies buddy = buddiesRepository
                .findOneByFromUserIdAndToUserId(userId, memberId);
        if (buddy == null) {
            throw new ResponseException(ResponseCode.BUDDIES_NULL_ERROR);
        }

        if (!buddies.isApproved() || !buddy.isApproved()) {
            throw new ResponseException(ResponseCode.BUDDIES_NULL_ERROR);
        }

        buddies.approved(false);

        if (buddies.isApproved() == buddy.isApproved()) {
            buddiesRepository.delete(buddy);
            buddiesRepository.delete(buddies);
        } else {
            buddiesRepository.save(buddies);
        }
    }

    /**
     * 회원의 친구 관계를 모두 불러옵니다
     *
     * @param memberId 회원 ID
     * @return 친구 목록
     */
    public List<ResponseBuddiesDto> getAllBuddies(Long memberId) {
        List<Buddies> buddies = buddiesRepository.findAllByToUserId(memberId);
        if (buddies.isEmpty()) {
            throw new ResponseException(ResponseCode.BUDDIES_NULL_ERROR);
        }
        return buddies.stream().map(Buddies::to).toList();
    }
}

