package com.sparta.newsfeed_project.domain.buddies.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBuddiesDto {
    private Long fromUesrId;
    private Long toUserId;
}
