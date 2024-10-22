package com.sparta.newsfeed_project.domain.buddies.service;

import com.sparta.newsfeed_project.domain.buddies.dto.RequestBuddiesDto;
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

    public ResponseBuddiesDto createBuddies(RequestBuddiesDto requestBuddiesDto) {
        Buddies buddies = Buddies.from(requestBuddiesDto);
        buddiesRepository.save(buddies);
        Buddies acceptBuddies = Buddies.upend(requestBuddiesDto);
        buddiesRepository.save(acceptBuddies);
        return buddies.to();
    }

    public List<ResponseBuddiesDto> getBuddies(Long memberId) {
        List<Buddies> buddies = buddiesRepository.findAllByFromUesrIdOrToUserId(memberId,memberId);
        return buddies.stream().map(Buddies::to).toList();
    }
    ;

    public void acceptBuddies(RequestBuddiesDto requestBuddiesDto) {
        Buddies buddies = buddiesRepository
                .findOneByFromUesrIdAndToUserId(requestBuddiesDto.getFromUesrId(), requestBuddiesDto.getToUserId());
        buddies.Appoved(true);
        buddiesRepository.save(buddies);
    }

    public void deleteBuddies(RequestBuddiesDto requestBuddiesDto) {
        Buddies buddies = buddiesRepository
                .findOneByFromUesrIdAndToUserId(requestBuddiesDto.getFromUesrId(), requestBuddiesDto.getToUserId());
        buddies.Appoved(false);
        Buddies buddy = buddiesRepository
                .findOneByFromUesrIdAndToUserId(requestBuddiesDto.getToUserId(), requestBuddiesDto.getFromUesrId());
        if(buddies.isApproved() == buddy.isApproved()){
            buddiesRepository.delete(buddy);
            buddiesRepository.delete(buddies);
        }else{
            buddiesRepository.save(buddies);
        }
    }
}
