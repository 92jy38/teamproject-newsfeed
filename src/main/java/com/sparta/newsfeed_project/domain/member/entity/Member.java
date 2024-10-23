package com.sparta.newsfeed_project.domain.member.entity;

import com.sparta.newsfeed_project.domain.common.entity.Timestamped;
import com.sparta.newsfeed_project.domain.member.dto.RequestModifyMemberDto;
import com.sparta.newsfeed_project.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
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
    private boolean deleted;

    @OneToMany(mappedBy = "member",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void update(RequestModifyMemberDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getNewPassword();
        this.nickname = dto.getNickname();
        this.username = dto.getUsername();
        this.introduce = dto.getIntroduce();
    }

    public void delete() {
        this.deleted = true;
    }
}
