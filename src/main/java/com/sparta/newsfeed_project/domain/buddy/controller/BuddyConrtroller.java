package com.sparta.newsfeed_project.domain.buddy.controller;

import com.sparta.newsfeed_project.domain.buddy.dto.ResponseBuddyDto;
import com.sparta.newsfeed_project.domain.buddy.repository.BuddyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buddy")
public class BuddyConrtroller {

    private final BuddyRepository buddyRepository;

    @PostMapping("/{memberId}")
    public ResponseBuddyDto createBuddy(@RequestBody final ResponseBuddyDto responseBuddyDto) {
        return null;
    }
    @GetMapping
    public List<ResponseBuddyDto> getAllBuddys() {
        return null;
    }

    @PutMapping("/{memberId}")
    public void acceptBuddy(@RequestBody final ResponseBuddyDto responseBuddyDto) {

    }

    @PutMapping("/{memberId}")
    public void updateBuddy(@PathVariable("memberId") final String memberId) {

    }


}
