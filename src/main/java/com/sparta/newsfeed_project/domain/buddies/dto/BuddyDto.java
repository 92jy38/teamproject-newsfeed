package com.sparta.newsfeed_project.domain.buddies.dto;

import com.sparta.newsfeed_project.domain.buddies.entity.Buddies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuddyDto {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private boolean approved;

    public static BuddyDto from(Buddies buddies) {
        return BuddyDto.builder()
                .id(buddies.getId())
                .fromUserId(buddies.getFromUserId())
                .toUserId(buddies.getToUserId())
                .approved(buddies.isApproved()).build();
    }

}
