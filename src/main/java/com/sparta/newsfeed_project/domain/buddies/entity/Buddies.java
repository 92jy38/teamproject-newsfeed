
package com.sparta.newsfeed_project.domain.buddies.entity;

import com.sparta.newsfeed_project.domain.buddies.dto.RequestBuddiesDto;
import com.sparta.newsfeed_project.domain.buddies.dto.ResponseBuddiesDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "buddy")
public class Buddies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long fromUserId;

    @Column
    private Long toUserId;

    @Column
    private boolean approved;

    public static Buddies from(RequestBuddiesDto request) {
        Buddies buddies = new Buddies();
        buddies.init(request);
        return buddies;
    }

    private void init(RequestBuddiesDto request) {
        this.fromUserId = request.getFromUserId();
        this.toUserId = request.getToUserId();
    }
    public static Buddies from(Long memberId,Long userId) {
        Buddies buddies = new Buddies();
        buddies.init(memberId,userId);
        return buddies;
    }
    private void init(Long memberId,Long userId) {
        this.fromUserId = memberId;
        this.toUserId = userId;
    }

    public static Buddies upend(Long memberId,Long userId) {
        Buddies buddies = new Buddies();
        buddies.initUpend(memberId,userId);
        return buddies;
    }

    private void initUpend(Long memberId,Long userId) {
        this.fromUserId = memberId;
        this.toUserId = userId;
        this.approved = false;
    }

    public ResponseBuddiesDto to() {
        return new ResponseBuddiesDto(
                id,
                fromUserId,
                toUserId,
                approved
        );
    }

    public void Approved(boolean approved) {
        this.approved = approved;
    }

}
