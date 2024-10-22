package com.sparta.newsfeed_project.domain.buddies.service;

import com.sparta.newsfeed_project.domain.buddies.dto.RequestBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.dto.ResponseBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuddiesService {

    private final BuddiesRepository buddiesRepository;

    public ResponseBuddiesDto createBuddies(RequestBuddiesDto requestBuddiesDto) {
        if(requestBuddiesDto.getFromUserId().equals(requestBuddiesDto.getToUserId())){
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        if(buddiesRepository.existsByFromUserIdAndToUserId(requestBuddiesDto.getFromUserId(), requestBuddiesDto.getToUserId())){
            throw new IllegalArgumentException("존재하는 친구 목록입니다.");
        }
        Buddies buddies = Buddies.from(requestBuddiesDto);
        buddies.Approved(true);
        buddiesRepository.save(buddies);
        Buddies acceptBuddies = Buddies.upend(requestBuddiesDto);
        buddiesRepository.save(acceptBuddies);
        return buddies.to();
    }

    public List<ResponseBuddiesDto> getBuddies() {
        List<Buddies> buddies = buddiesRepository.findAll();
        return buddies.stream().map(Buddies::to).toList();
    }

    public void acceptBuddies(RequestBuddiesDto requestBuddiesDto) {
        Buddies buddies = buddiesRepository
                .findOneByFromUserIdAndToUserId(requestBuddiesDto.getFromUserId(),
                        requestBuddiesDto.getToUserId());
        buddies.Approved(true);
        buddiesRepository.save(buddies);
    }

    public void deleteBuddies(RequestBuddiesDto requestBuddiesDto) {
        Buddies buddies = buddiesRepository
                .findOneByFromUserIdAndToUserId(requestBuddiesDto.getFromUserId(), requestBuddiesDto.getToUserId());
        buddies.Approved(false);
        Buddies buddy = buddiesRepository
                .findOneByFromUserIdAndToUserId(requestBuddiesDto.getToUserId(), requestBuddiesDto.getFromUserId());
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

    private List<ResponseBuddiesDto> createList(Buddies buddies, Buddies acceptBuddies){
        List<ResponseBuddiesDto> buddiesDtos = new ArrayList<>();
        buddiesDtos.add(buddies.to());
        buddiesDtos.add(acceptBuddies.to());
        return buddiesDtos;
    }
}
