package com.sparta.newsfeed_project.domain.buddies.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBuddiesDto {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private boolean approved;

}
