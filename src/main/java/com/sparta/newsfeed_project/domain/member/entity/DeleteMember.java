package com.sparta.newsfeed_project.domain.member.entity;

import com.sparta.newsfeed_project.domain.common.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 삭제한 회원 정보를 담는 Entity 클래스 [학습 전용]
 *
 * @since 2024-10-03
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deleteMember")
@EntityListeners(AuditingEntityListener.class) // 자동으로 시간을 넣어주는 기능 사용 가능
public class DeleteMember {
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

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deleteAt;
}
