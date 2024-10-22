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
    private Long fromUesrId;

    @Column
    private Long toUserId;

    @Column
    private boolean appoved;

    public static Buddies from(RequestBuddiesDto request) {
        Buddies buddies = new Buddies();
        buddies.init(request);
        return buddies;
    }

    private void init(RequestBuddiesDto request) {
        this.fromUesrId = request.getFromUesrId();
        this.toUserId = request.getToUserId();
        this.appoved = request.isApproved();
    }

    public static Buddies upend(RequestBuddiesDto request) {
        Buddies buddies = new Buddies();
        buddies.initUpend(request);
        return buddies;
    }

    private void initUpend(RequestBuddiesDto request) {
        this.fromUesrId = request.getToUserId();
        this.toUserId = request.getFromUesrId();
        this.appoved = false;
    }

    public ResponseBuddiesDto to() {
        return new ResponseBuddiesDto(
                id,
                fromUesrId,
                toUserId,
                appoved
        );
    }

    public void Appoved(boolean b) {
        this.appoved = b;
    }
}
