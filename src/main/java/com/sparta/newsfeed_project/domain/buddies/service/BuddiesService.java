package com.sparta.newsfeed_project.domain.buddies.service;


import com.sparta.newsfeed_project.domain.buddies.dto.ResponseBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuddiesService {

    private final BuddiesRepository buddiesRepository;


    public ResponseBuddiesDto createBuddies(Long memberId, Long userId) {
        if (memberId.equals(userId)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        if (buddiesRepository.existsByFromUserIdAndToUserId(memberId, userId)) {
            throw new IllegalArgumentException("존재하는 친구 목록입니다.");
        }
        Buddies buddies = Buddies.from(memberId, userId);
        buddies.Approved(true);
        buddiesRepository.save(buddies);
        Buddies acceptBuddies = Buddies.upend(userId, memberId);
        buddiesRepository.save(acceptBuddies);
        return buddies.to();
    }

    public List<ResponseBuddiesDto> getBuddies() {
        List<Buddies> buddies = buddiesRepository.findAll();
        return buddies.stream().map(Buddies::to).toList();
    }

    public void acceptBuddies(Long memberId, Long userId) {
        Buddies buddies = buddiesRepository
                .findOneByFromUserIdAndToUserId(memberId, userId);
        buddies.Approved(true);
        buddiesRepository.save(buddies);
    }

    public void deleteBuddies(Long memberId, Long userId) {
        Buddies buddies = buddiesRepository
                .findOneByFromUserIdAndToUserId(memberId, userId);
        buddies.Approved(false);
        Buddies buddy = buddiesRepository
                .findOneByFromUserIdAndToUserId(userId, memberId);
        if (buddies.isApproved() == buddy.isApproved()) {
            buddiesRepository.delete(buddy);
            buddiesRepository.delete(buddies);
        } else {
            buddiesRepository.save(buddies);
        }
    }

    public List<ResponseBuddiesDto> getAllBuddies(Long memberId) {
        List<Buddies> buddies = buddiesRepository.findAllByToUserId(memberId);
        return buddies.stream().map(Buddies::to).toList();
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
}

