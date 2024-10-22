package com.sparta.newsfeed_project.domain.buddies.dto;

import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import jdk.jshell.Snippet;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuddyDto {
    private Long id;
    private Long fromUesrId;
    private Long toUserId;
    private boolean approved;

    public static BuddyDto from(Buddies buddies) {
        return BuddyDto.builder()
                .id(buddies.getId())
                .fromUesrId(buddies.getFromUesrId())
                .toUserId(buddies.getToUserId())
                .approved(buddies.isApproved()).build();
    }

}
