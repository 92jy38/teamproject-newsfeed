package com.sparta.newsfeed_project.domain.buddies.controller;

import com.sparta.newsfeed_project.domain.buddies.dto.RequestBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.dto.ResponseBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.service.BuddiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buddies")
public class BuddiesConrtroller {

    private final BuddiesService buddiesService;

    /*
    * 친구 등록 API
    * @param request 친구 Id 정보(Json형태)
    * @return status(CREATED), body(입력된 친구 정보)
    * */

    @PostMapping("/{memberId}")
    public ResponseEntity<ResponseBuddiesDto> createBuddies(@RequestBody RequestBuddiesDto requestBuddiesDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(buddiesService.createBuddies(requestBuddiesDto));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<ResponseBuddiesDto>> getAllBuddyies(@PathVariable Long memberId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buddiesService.getAllBuddies(memberId));
    }

    @PutMapping("/{memberId}")
    public  ResponseEntity<Void> acceptBuddies(@RequestBody RequestBuddiesDto requestBuddiesDto) {
        buddiesService.acceptBuddies(requestBuddiesDto);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteBuddies(@RequestBody RequestBuddiesDto requestBuddiesDto) {
        buddiesService.deleteBuddies(requestBuddiesDto);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping("/List")
    public ResponseEntity<List<ResponseBuddiesDto>> getAllBuddies() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buddiesService.getBuddies());
    }


}
