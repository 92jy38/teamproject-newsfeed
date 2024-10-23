package com.sparta.newsfeed_project.domain.buddies.controller;

import com.sparta.newsfeed_project.domain.buddies.dto.ResponseBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import com.sparta.newsfeed_project.domain.buddies.service.BuddiesService;
import com.sparta.newsfeed_project.domain.common.exception.ResponseException;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buddies")
public class BuddiesController {

    private final BuddiesService buddiesService;
    private final String SUBJECT_ATTRIBUTE_KEY = "loggedInWithId";

    /*
     * 친구 등록 API
     * @param request 친구 Id 정보(Json형태)
     * @return status(CREATED), body(입력된 친구 정보)
     * */
    @PostMapping("/{memberId}")
    public ResponseEntity<ResponseBuddiesDto> createBuddies(@PathVariable Long memberId, HttpServletRequest req) throws ResponseException {
        Member member = (Member) req.getAttribute(SUBJECT_ATTRIBUTE_KEY);
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(buddiesService.createBuddies(member.getId(), memberId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    /*
     * 친구 신청 리스트
     *
     * */
    @GetMapping("/{memberId}")
    public ResponseEntity<List<ResponseBuddiesDto>> getAllBuddies(HttpServletRequest req) {
        Member member = (Member) req.getAttribute(SUBJECT_ATTRIBUTE_KEY);
        Long memberId = member.getId();
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(buddiesService.getAllBuddies(memberId));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> acceptBuddies(@PathVariable Long memberId, HttpServletRequest req) throws ResponseException {
        Member member = (Member) req.getAttribute(SUBJECT_ATTRIBUTE_KEY);
        Long userId = member.getId();
        buddiesService.acceptBuddies(userId, memberId);
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteBuddies(@PathVariable Long memberId, HttpServletRequest req) throws ResponseException {
        Member member = (Member) req.getAttribute(SUBJECT_ATTRIBUTE_KEY);
        Long userId = member.getId();
        buddiesService.deleteBuddies(userId, memberId);
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/List")
    public ResponseEntity<List<ResponseBuddiesDto>> getAllBuddies() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(buddiesService.getBuddies());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

}
