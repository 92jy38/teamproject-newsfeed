package com.sparta.newsfeed_project.domain.buddies.controller;

import com.sparta.newsfeed_project.domain.buddies.dto.RequestBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.dto.ResponseBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.repository.BuddiesRepository;
import com.sparta.newsfeed_project.domain.buddies.service.BuddiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buddies")
public class BuddiesConrtroller {

    private final BuddiesService buddiesService;

    @PostMapping("/{memberId}")
    public ResponseBuddiesDto createBuddies(@RequestBody RequestBuddiesDto requestBuddiesDto) {
        return buddiesService.createBuddies(requestBuddiesDto);
    }

    @GetMapping("/{memberId}")
    public List<ResponseBuddiesDto> getAllBuddyies(@PathVariable Long memberId) {
        return buddiesService.getBuddies(memberId);
    }

    @PutMapping("/{memberId}")
    public void acceptBuddies(@RequestBody RequestBuddiesDto requestBuddiesDto) {
        buddiesService.acceptBuddies(requestBuddiesDto);
    }

    @DeleteMapping("/{memberId}")
    public void deleteBuddies(@RequestBody RequestBuddiesDto requestBuddiesDto) {
        buddiesService.deleteBuddies(requestBuddiesDto);
    }

}
