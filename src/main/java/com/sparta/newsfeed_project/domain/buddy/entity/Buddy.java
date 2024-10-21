package com.sparta.newsfeed_project.domain.buddy.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.newsfeed_project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "buddy")
public class Buddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn( name = "mamber_id")
    @ManyToOne
    @JsonBackReference
    private Member fromUesrId;

    @JoinColumn(name = "mamber_id")
    @ManyToOne
    @JsonBackReference
    private Member toUserId;

    @Column
    private boolean isBuddy;
}
