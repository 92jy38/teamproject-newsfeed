package com.sparta.newsfeed_project.domain.member.entity;

import com.sparta.newsfeed_project.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

/**
 * 회원 정보를 담는 Entity 클래스
 *
 * @since 2024-10-03
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = true, length = 255)
    private String introduce;

    @ColumnDefault("false")
    private boolean isDeleted;

//    @OneToMany(mappedBy = "members",
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            orphanRemoval = true)
//    private List<Schedule> schedules = new ArrayList<>();
//
//    @OneToMany(mappedBy = "CascadeType",
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();

    public void update(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.username = member.getUsername();
        this.introduce = member.getIntroduce();
        this.isDeleted = member.isDeleted();
    }

    public void setUserDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
